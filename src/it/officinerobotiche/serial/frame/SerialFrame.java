/*
 * Copyright (C) 2014 Officine Robotiche.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser General Public License
 * (LGPL) version 2.1 which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/lgpl-2.1.html
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * Contributors:
 *     Raffaello Bonghi - raffaello.bonghi@officinerobotiche.it
 */
package it.officinerobotiche.serial.frame;

import it.officinerobotiche.serial.*;
import it.officinerobotiche.serial.frame.Jmessage.Information;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.net.URL;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.event.EventListenerList;

/**
 *
 * @author Raffaello Bonghi
 */
public class SerialFrame extends SerialPacket implements PacketListener {

    private final static char DOT = '.';
    private final static char SLASH = '/';
    private final static String CLASS_SUFFIX = ".class";
    private final static String SUBCLASS = "$";
    private final static String ENUM_COMMAND = "Command";
    private final static String METHOD_FRAME = "getCommand";
    // List all messages
    private final List<Class<? extends AbstractFrame>> allClasses;

    protected EventListenerList listenerList = new EventListenerList();

    public SerialFrame(String portName) {
        super(portName);
        //Load all messages with extension Jmessage 
        allClasses = SerialFrame.getJmessageClasses(SerialFrame.class.getPackage().getName());
        super.addPacketEventListener(this);
    }

    @Override
    public void asyncPacketEvent(Packet packet) {
        for (AbstractFrame frame : parsePacket(packet)) {
            fireFrameEvent(frame);
        }
    }

    public void addParserEventListener(ParserListener listener) {
        listenerList.add(ParserListener.class, listener);
    }

    public void removeParserEventListener(ParserListener listener) {
        listenerList.remove(ParserListener.class, listener);
    }

    private void fireFrameEvent(AbstractFrame frame) {
        Object[] listeners = listenerList.getListenerList();
        for (int i = 0; i < listeners.length; i = i + 2) {
            if (listeners[i] == ParserListener.class) {
                ((ParserListener) listeners[i + 1]).parserEvent(frame);
            }
        }
    }

    public <P extends AbstractFrame> void parseSyncFrame(ArrayList<P> message) {
        try {
            List<AbstractFrame> sendSyncFrame = sendSyncFrame(message);
            for (AbstractFrame frame : sendSyncFrame) {
                fireFrameEvent(frame);
            }
        } catch (InterruptedException ex) {
            Logger.getLogger(SerialFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public <P extends AbstractFrame> void parseSyncFrame(P message) {
        try {
            AbstractFrame sendSyncFrame = sendSyncFrame(message);
            fireFrameEvent(sendSyncFrame);
        } catch (InterruptedException ex) {
            Logger.getLogger(SerialFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public <P extends AbstractFrame> void sendASyncFrame(P message) {
        try {
            sendFrame(false, message);
        } catch (InterruptedException ex) {
            Logger.getLogger(SerialFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public <P extends AbstractFrame> void sendASyncFrame(ArrayList<P> message) {
        try {
            sendFrame(false, message);
        } catch (InterruptedException ex) {
            Logger.getLogger(SerialFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public <P extends AbstractFrame> P sendSyncFrame(P message) throws InterruptedException {
        return (P) parsePacket(sendFrame(true, message)).get(0);
    }

    public <P extends AbstractFrame> List<AbstractFrame> sendSyncFrame(ArrayList<P> message) throws InterruptedException {
        return parsePacket(sendFrame(true, message));
    }

    public List<AbstractFrame> parsePacket(Packet packet) {
        ArrayList<AbstractFrame> list_receive = new ArrayList<>();
        byte[] data = packet.getDataStructure();
        for (int i = 0; i < data.length; i += data[i]) {
            try {
                // Find a correct frame
                char type = (char) data[i + 2];
                int command = data[i + 3];
                String abstractClass = AbstractFrame.TypeMessage.getAbstractClass(type);
                String frame = getNameFrame(abstractClass, command);
                Class<? extends AbstractFrame> message = getClassFrame(abstractClass, frame);

                Information get = Information.get(data[i + 1]);
                // Data Message
                switch (get) {
                    case DATA:
                        if (data[i] > AbstractFrame.LNG_HEADER) {
                            byte[] data_message = new byte[data[i] - AbstractFrame.LNG_HEADER];
                            System.arraycopy(data, i + AbstractFrame.LNG_HEADER, data_message, 0, data[i] - AbstractFrame.LNG_HEADER);
                            Constructor<? extends AbstractFrame> declaredConstructor = message.getDeclaredConstructor(boolean.class, byte[].class);
                            list_receive.add(declaredConstructor.newInstance(packet.isSync(), data_message));
                        }
                        break;

                    case ACK:
                    case NACK:
                        Constructor<? extends AbstractFrame> declaredConstructor = message.getDeclaredConstructor(boolean.class, Information.class);
                        list_receive.add(declaredConstructor.newInstance(packet.isSync(), get));
                        break;
                }
            } catch (NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
                Logger.getLogger(SerialFrame.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return list_receive;
    }

    private Class<? extends AbstractFrame> getClassFrame(String abstractClass, String frame) {
        for (Class<? extends AbstractFrame> classMes : allClasses) {
            try {
                Class<? extends AbstractFrame> asSubclass = (Class<? extends AbstractFrame>) classMes.asSubclass(Class.forName(abstractClass));
                if (asSubclass.getSimpleName().equals(frame)) {
                    return asSubclass;
                }
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(SerialFrame.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassCastException ex) {
            }
        }
        return null;
    }

    private static String getNameFrame(String abstractClass, int comm_rec) {
        try {
            Class<?> forName = Class.forName(abstractClass + SUBCLASS + ENUM_COMMAND);
            Method method = forName.getMethod(METHOD_FRAME, int.class);
            return (String) method.invoke(null, comm_rec);
        } catch (ClassNotFoundException | NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
            Logger.getLogger(SerialFrame.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    private <P extends AbstractFrame> Packet sendFrame(boolean sync, P message) throws InterruptedException {
        Packet packet = new Packet(sync);
        packet.addMessage(message.getFrame());
        return super.sendPacket(packet);
    }

    private <P extends AbstractFrame> Packet sendFrame(boolean sync, ArrayList<P> message) throws InterruptedException {
        Packet packet = new Packet(sync);
        for (P message_i : message) {
            packet.addMessage(message_i.getFrame());
        }
        return super.sendPacket(packet);
    }

    /**
     * Recursively fetches a list of all the classes in a given directory (and
     * sub-directories) that have Jmessage extention.
     *
     * @param packageName The top level package to search.
     * @return The list of all @UnitTestable classes.
     */
    private static List<Class<? extends AbstractFrame>> getJmessageClasses(String packageName) {
        // State what package we are exploring
        System.out.println("Exploring package: " + packageName);
        // Create the list that will hold the testable classes
        List<Class<? extends AbstractFrame>> ret = new ArrayList<>();
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
                    Class<? extends AbstractFrame> asSubclass = add.asSubclass(AbstractFrame.class);
                    //It isn't a Jmessage class this is correct!
                    if (!Modifier.isAbstract(asSubclass.getModifiers())) {
                        ret.add(asSubclass);
                    }
                } catch (Exception ex) {
                    //Logger.getLogger(SerialFrame.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return ret;
    }
}
