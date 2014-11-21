/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.officinerobotiche.message;

import it.officinerobotiche.message.Jmessage.Command;
import it.officinerobotiche.serial.Packet;
import it.officinerobotiche.serial.SerialPacket;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.net.URL;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Raffaello Bonghi
 */
public class SerialMessage extends SerialPacket {

    private final static char DOT = '.';
    private final static char SLASH = '/';
    private final static String CLASS_SUFFIX = ".class";
    private final static String FIELD_TYPE_MESSAGE = "TYPE_MESSAGE";
    private final static String FIELD_COMMAND = "ALL_COMMANDS";
    // List all messages
    private final List<Class<? extends Jmessage>> allClasses;

    public SerialMessage(String portName) {
        super(portName);
        //Load all messages with extension Jmessage 
        allClasses = SerialMessage.getJmessageClasses(SerialMessage.class.getPackage().getName());
    }

    public <P extends Jmessage> void sendASyncMessage(P message) {
        try {
            sendMessage(false, message);
        } catch (InterruptedException ex) {
            Logger.getLogger(SerialMessage.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public <P extends Jmessage> void sendASyncMessage(ArrayList<P> message) {
        try {
            sendMessage(false, message);
        } catch (InterruptedException ex) {
            Logger.getLogger(SerialMessage.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public <P extends Jmessage> P sendSyncMessage(P message) throws InterruptedException {
        return (P) parsePacket(sendMessage(true, message)).get(0);
    }

    public <P extends Jmessage> ArrayList<P> sendSyncMessage(ArrayList<P> message) {
        try {
            return parsePacket(sendMessage(true, message));
        } catch (InterruptedException ex) {
            Logger.getLogger(SerialMessage.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public <P extends Jmessage> ArrayList<P> parsePacket(Packet packet) {
        ArrayList<P> list_receive = new ArrayList<>();
        byte[] data = packet.getDataStructure();
        for (int i = 0; i < data.length; i += data[i]) {
            try {
                for (Class<? extends Jmessage> message : allClasses) {
                    //Find the correct Jmessage
                    if (data[i + 2] == getByteFromField(message, FIELD_TYPE_MESSAGE)) {
                        byte[] commands = getArrayFromField(message, FIELD_COMMAND);
                        for (byte j : commands) {
                            if (data[i + 3] == j) {
                                //Add data array
                                byte[] data_message = new byte[data[i] - Jmessage.LNG_HEADER];
                                System.arraycopy(data, i + Jmessage.LNG_HEADER, data_message, 0, data[i] - Jmessage.LNG_HEADER);
                                list_receive.add((P) message.getDeclaredConstructor(byte.class, byte[].class)
                                        .newInstance(data[i + 1], data_message));
                            }
                        }
                    }
                }
            } catch (Exception ex) {
                Logger.getLogger(SerialMessage.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return list_receive;
    }

    static public <E extends Enum<E>> Class<?> getEnum(Class<? extends Jmessage> message, String name_field) throws NoSuchFieldException {
        Class<?>[] enumConstants2 = message.getClasses();
        for (Class<?> i : enumConstants2) {
            if (i.isEnum()) {
                Class<?>[] interfaces = i.getInterfaces();
                for (Class<?> j : interfaces) {
                    if (j.equals(Jmessage.Command.class)) {
                        return i;
                    }
                }

            }
        }
        return null;
    }
    
    private static byte getByteFromField(Class<? extends Jmessage> message, String name_field) throws NoSuchFieldException, IllegalArgumentException, IllegalAccessException {
        Field type_field = message.getField(name_field);
        if (type_field.getType() == byte.class) {
            return type_field.getByte(null);
        }
        return 0;
    }

    public static byte[] getArrayFromField(Class<? extends Jmessage> message, String name_field) throws NoSuchFieldException, IllegalArgumentException, IllegalAccessException {
        Field type_field = message.getField(name_field);
        if (type_field.getType().isArray()) {
            return (byte[]) type_field.get(null);
        } else {
            return null;
        }
    }

    private <P extends Jmessage> Packet sendMessage(boolean sync, P message) throws InterruptedException {
        Packet packet = new Packet(sync);
        packet.addMessage(getMessage(message));
        return super.sendPacket(packet);
    }

    private <P extends Jmessage> Packet sendMessage(boolean sync, ArrayList<P> message) throws InterruptedException {
        Packet packet = new Packet(sync);
        for (P message_i : message) {
            packet.addMessage(getMessage(message_i));
        }
        return super.sendPacket(packet);
    }

    private <P extends Jmessage> ArrayList<Byte> getMessage(P message) {
        ArrayList<Byte> data = new ArrayList<Byte>();
        data.add(message.getLength());
        data.add(message.getType().getName());
        data.add(message.getTypeMessage());
        data.add(message.getCommand());
        if (message.getData() != null) {
            for (byte i : message.getData()) {
                data.add(i);
            }
        }
        return data;
    }

    /**
     * Recursively fetches a list of all the classes in a given directory (and
     * sub-directories) that have Jmessage extention.
     *
     * @param packageName The top level package to search.
     * @return The list of all @UnitTestable classes.
     */
    public static final List<Class<? extends Jmessage>> getJmessageClasses(String packageName) {
        // State what package we are exploring
        System.out.println("Exploring package: " + packageName);
        // Create the list that will hold the testable classes
        List<Class<? extends Jmessage>> ret = new ArrayList<Class<? extends Jmessage>>();
        // Load a class loader.
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        // Convert the package path to file path
        String path = packageName.replace(DOT, SLASH);
        // Try to get all of nested directories.
        try {
            // Get all of the resources for the given path
            Enumeration<URL> res = loader.getResources(path);
            // While we have directories to look at, recursively
            // get all their classes.
            while (res.hasMoreElements()) {
                // Get the file path the the directory
                String dirPath = URLDecoder.decode(res.nextElement().getPath(), "UTF-8");
                // Make a file handler for easy managing
                File dir = new File(dirPath);
                // Check every file in the directory, if it's a
                // directory, recursively add its viable files
                for (File file : dir.listFiles()) {
                    if (file.isDirectory()) {
                        ret.addAll(getJmessageClasses(packageName + '.' + file.getName()));
                    }
                }
            }
        } catch (IOException e) {
            // We failed to get any nested directories. State
            // so and continue; this directory may still have
            // some UnitTestable classes.
            System.out.println("Failed to load resources for [" + packageName + ']');
        }
        // We need access to our directory, so we can pull
        // all the classes.
        URL tmp = loader.getResource(path);
        System.out.println(tmp);
        if (tmp == null) {
            return ret;
        }
        File currDir = new File(tmp.getPath());
        // Now we iterate through all of the classes we find
        for (String classFile : currDir.list()) {
            // Ensure that we only find class files; can't load gif's!
            if (classFile.endsWith(CLASS_SUFFIX)) {
                // Attempt to load the class or state the issue
                try {
                    // Try loading the class
                    Class<?> add = Class.forName(packageName + DOT + classFile.substring(0, classFile.length() - 6));
                    // Load all classes with
                    Class<? extends Jmessage> asSubclass = add.asSubclass(Jmessage.class);
                    //It isn't a Jmessage class this is correct!
                    if (!Modifier.isAbstract(add.getModifiers())) {
                        ret.add((Class<? extends Jmessage>) add);
                    }
                } catch (Exception ex) {
                    //Logger.getLogger(SerialMessage.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return ret;
    }
}
