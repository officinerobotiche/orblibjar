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

import it.officinerobotiche.serial.Packet;
import it.officinerobotiche.serial.PacketListener;
import it.officinerobotiche.serial.SerialPacket;
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
 * Object to send and receive message. This object connect to serial packet.
 *
 * @author Raffaello Bonghi
 */
public class SerialFrame extends SerialPacket implements PacketListener {

    /**
     * Defition dot for reflection.
     */
    private final static char DOT = '.';
    /**
     * Definition slash for reflection.
     */
    private final static char SLASH = '/';
    /**
     * Suffix for class.
     */
    private final static String CLASS_SUFFIX = ".class";
    /**
     * Definition for subclass.
     */
    private final static String SUBCLASS = "$";
    /**
     * Definition enumeration for command. You must have this enumeration,
     * extends ICommand on Jmessage.
     */
    private final static String ENUM_COMMAND = "Command";
    /**
     * Definition method to read associated string for name class command.
     */
    private final static String METHOD_FRAME = "getStringCommand";
    /**
     * List all tyoe messages. This list used on initialization for save all
     * class that extend AbstractFrame.
     */
    private final List<Class<? extends AbstractFrame>> allClasses;
    /**
     * List event receiver.
     */
    protected EventListenerList listenerList = new EventListenerList();

    /**
     * Initialize serial port using same configuration in SerialPacket.
     * Associate Packet listener for asyctronous packet.
     *
     * @param portName Serial name port.
     */
    public SerialFrame(String portName) {
        super(portName);
        //Load all messages with extension Jmessage 
        allClasses = SerialFrame.getJmessageClasses(SerialFrame.class.getPackage().getName());
        super.addPacketEventListener(this);
    }

    /**
     * Automatic send frame messages received from Asycronous packet.
     *
     * @param packet asyncronous packet received from serial port.
     */
    @Override
    public void asyncPacketEvent(Packet packet) {
        for (AbstractFrame frame : parsePacket(packet)) {
            fireFrameEvent(frame);
        }
    }

    /**
     * Add parser packet listener.
     *
     * @param listener add listener of frame messages.
     */
    public void addParserEventListener(ParserListener listener) {
        listenerList.add(ParserListener.class, listener);
    }

    /**
     * Remove parser packet listener.
     *
     * @param listener remove listener of frame messages.
     */
    public void removeParserEventListener(ParserListener listener) {
        listenerList.remove(ParserListener.class, listener);
    }

    /**
     * Send a frame to all event listener.
     *
     * @param frame frame to send.
     */
    private void fireFrameEvent(AbstractFrame frame) {
        Object[] listeners = listenerList.getListenerList();
        for (int i = 0; i < listeners.length; i = i + 2) {
            if (listeners[i] == ParserListener.class) {
                ((ParserListener) listeners[i + 1]).parserEvent(frame);
            }
        }
    }

    /**
     * Send a list of messages and push on all event listener all frames.
     *
     * @param <P> all type of Frame
     * @param message list message to send.
     */
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

    /**
     * Send a message and push on all event listener all frames.
     *
     * @param <P> all type of Frame
     * @param message message to send.
     */
    public <P extends AbstractFrame> void parseSyncFrame(P message) {
        try {
            AbstractFrame sendSyncFrame = sendSyncFrame(message);
            fireFrameEvent(sendSyncFrame);
        } catch (InterruptedException ex) {
            Logger.getLogger(SerialFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Send in asyncronus a message.
     *
     * @param <P> all type of Frame
     * @param message message to send.
     */
    public <P extends AbstractFrame> void sendASyncFrame(P message) {
        try {
            sendFrame(false, message);
        } catch (InterruptedException ex) {
            Logger.getLogger(SerialFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Send asyncronus list of messages.
     *
     * @param <P> all type of Frame
     * @param message list message to send.
     */
    public <P extends AbstractFrame> void sendASyncFrame(ArrayList<P> message) {
        try {
            sendFrame(false, message);
        } catch (InterruptedException ex) {
            Logger.getLogger(SerialFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Send a message and receive same type of message.
     *
     * @param <P> all type of Frame
     * @param message message to send.
     * @return list received.
     * @throws InterruptedException If the serial port go in timeout connection.
     */
    public <P extends AbstractFrame> P sendSyncFrame(P message) throws InterruptedException {
        return (P) parsePacket(sendFrame(true, message)).get(0);
    }

    /**
     * Send a list of messages and receive a same list with all frames.
     *
     * @param <P> all type of Frame
     * @param message list of message to send.
     * @return list received.
     * @throws InterruptedException If the serial port go in timeout connection.
     */
    public <P extends AbstractFrame> List<AbstractFrame> sendSyncFrame(ArrayList<P> message) throws InterruptedException {
        return parsePacket(sendFrame(true, message));
    }

    /**
     * Send a packet in serial port and set type of message.
     *
     * @param <P> all type of Frame
     * @param sync type of packet.
     * @param message message to send.
     * @return Packet from serial port.
     * @throws InterruptedException If board does not respond in timeout limit
     */
    private <P extends AbstractFrame> Packet sendFrame(boolean sync, P message) throws InterruptedException {
        Packet packet = new Packet(sync);
        packet.addMessage(message.getFrame());
        return super.sendPacket(packet);
    }

    /**
     * Send a list of packet in serial port and set type of message.
     *
     * @param <P> all type of Frame
     * @param sync type of packet.
     * @param message list of message to send.
     * @return Packet from serial port.
     * @throws InterruptedException If board does not respond in timeout limit
     */
    private <P extends AbstractFrame> Packet sendFrame(boolean sync, ArrayList<P> message) throws InterruptedException {
        Packet packet = new Packet(sync);
        for (P message_i : message) {
            packet.addMessage(message_i.getFrame());
        }
        return super.sendPacket(packet);
    }

    /**
     * This method parse a packet with reflection. Use to decode a packet the
     * information collected on head frame: 1) length of packet 2) type of
     * message 3) Command. The parser find a same object associated in
     * enumeration on AbstractFrame and start a new instance associated.
     *
     * @param packet Packet to read and transform in a list of packets.
     * @return a list with all frame casted.
     */
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
                            Constructor<? extends AbstractFrame> declaredConstructor = message.getDeclaredConstructor(boolean.class, int.class, byte[].class);
                            list_receive.add(declaredConstructor.newInstance(packet.isSync(), command, data_message));
                        }
                        break;

                    case ACK:
                    case NACK:
                        Constructor<? extends AbstractFrame> declaredConstructor = message.getDeclaredConstructor(boolean.class, int.class, Information.class);
                        list_receive.add(declaredConstructor.newInstance(packet.isSync(), command, get));
                        break;
                }
            } catch (NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
                //Logger.getLogger(SerialFrame.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return list_receive;
    }

    /**
     * Reconstruct from int variable a associated name class. This method use
     * reflection to invoke method relative in class.
     *
     * @param abstractClass Name abstract class to fine relative command in
     * enumeration
     * @param comm_rec Command to find command.
     * @return Name frame.
     */
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

    /**
     * Find a class name from name class to relative abstract class and name
     * frame.
     *
     * @param abstractClass name abstract class
     * @param frame name frame
     * @return class frame.˙
     */
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
                    //Logger.getLogger(SerialFrame.class.getString()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return ret;
    }
}
