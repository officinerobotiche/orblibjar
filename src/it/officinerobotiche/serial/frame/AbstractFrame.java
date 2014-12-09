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

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

/**
 * Abstract class with general information about frame in a packet. This class
 * include information about type of messages. Exist a enum TypeMessage with
 * information about major macro categories of messages.
 *
 * @author Raffaello Bonghi
 */
public abstract class AbstractFrame implements Jmessage {

    /**
     * Dimension header for frame message.
     */
    public static final int LNG_HEADER = 4;
    /**
     * Byte dimension for float variable.
     */
    protected static final int LNG_BYTE_FLOAT = 4;

    /**
     * Enumeration for tipologies of frame. In this enumeration, you see the
     * character "name" associated for a message packet class "abstractClass".
     */
    public static enum TypeMessage {

        /**
         * Standard messages.
         */
        DEFAULT('D', "Standard"),
        /**
         * Motion messages.
         */
        MOTION('M', "Motion");
        /**
         * Name for package.
         */
        private static final String NAME_PKG = TypeMessage.class.getPackage().getName();
        /**
         * Second part name for relative abstract class.
         */
        private static final String NAME_FRAME = "Frame";
        /**
         * Map for reconstruction enumeration from characrter.
         */
        private static final Map<Character, TypeMessage> lookup = new HashMap<>();
        /**
         * Character name associated for selected enumeration.
         */
        private final char name;
        /**
         * Strign name associater for selected enumeration.
         */
        private final String abstractClass;

        /**
         * Private constructor to initialize enumeration.
         *
         * @param name character for describe message on frame.
         * @param abstractClass string to describe package class.
         */
        private TypeMessage(char name, String abstractClass) {
            this.name = name;
            this.abstractClass = abstractClass;
        }

        /**
         * Get the value of name.
         *
         * @return the value of name
         */
        public char getName() {
            return name;
        }

        /**
         * Get the value name converted in byte.
         *
         * @return the value name converted in byte.
         */
        public byte getByte() {
            return (byte) name;
        }

        /**
         * Get the name associated abstract class. Used for reflection.
         *
         * @return the value name associated abstract .
         */
        private String getAbstractClass() {
            return AbstractFrame.TypeMessage.NAME_PKG + "." + abstractClass.toLowerCase()
                    + "." + abstractClass + AbstractFrame.TypeMessage.NAME_FRAME;
        }

        /**
         * Static collection all Enumeration rescpect character name.
         */
        static {
            for (TypeMessage s : EnumSet.allOf(TypeMessage.class)) {
                lookup.put(s.getName(), s);
            }
        }

        /**
         * the reverse lookup by simply getting the value from the lookup
         * HashMap.
         *
         * @param name_rec character name received from frame decoded on packet
         * @return associated enumeration.
         */
        public static String getAbstractClass(char name_rec) {
            return lookup.get(name_rec).getAbstractClass();
        }

    }
    /**
     * Byte array with information to send or receive.
     */
    protected byte[] in;
    /**
     * Type of message. Require, message with data or finally ACK, NACK.
     */
    protected Information information;
    /**
     * Sync message, or not.
     */
    protected boolean sync;

    /**
     * Standard initalization for a Standard frame. In general dosen't have a
     * bytes, and is a request to send a board to receive data.
     */
    public AbstractFrame() {
        this.sync = true;
        this.in = null;
        this.information = Information.REQUEST;
    }

    /**
     * This is usaly from parser to build a selected object. This message the
     * SerialFrame object use by reflection, to generate a correct object. You
     * must implement in your frame object.
     *
     * @param sync Type of packet. Syncronus or not.
     * @param name Command associated. In general the SerialFrame automatic cast
     * in a correct object.
     * @param info Type of frame message. ACK or NACK message. The SerialFrame
     * doesn't use this constructor to generate request or data message.
     */
    public AbstractFrame(boolean sync, int name, Information info) {
        this.sync = sync;
        this.in = null;
        this.information = info;
    }

    /**
     * This is usaly from parser to build a selected object. This message the
     * SerialFrame object use by reflection, to generate a correct object. You
     * must implement in your frame object.
     *
     * @param sync Type of packet. Syncronus or not.
     * @param name Command associated. In general the SerialFrame automatic cast
     * in a correct object.
     * @param in Byte received from SerialFrame associated from packet.
     * Automatic Information message associated: Data.
     */
    public AbstractFrame(boolean sync, int name, byte[] in) {
        this.sync = sync;
        this.in = in;
        this.information = Information.DATA;
    }

    /**
     * Return type message required.
     *
     * @return enumeration with type message.
     */
    abstract public TypeMessage getTypeMessage();

    /**
     * Return Interface command type associated.
     *
     * @return Command associated.
     */
    @Override
    abstract public ICommand getCommand();

    /**
     * Build a Frame to add in Packet. In order add: 1) Length of packet; 2)
     * Information about packet: request, data, ack, nack. 3) Type of message;
     * 4) Command; 5) Optional byte data.
     *
     * @return list with all bytes.
     */
    public ArrayList<Byte> getFrame() {
        int length = (in != null) ? in.length : 0;
        ArrayList<Byte> frame = new ArrayList<>(LNG_HEADER + length);
        frame.add((byte) (LNG_HEADER + length));
        frame.add(information.getByte());
        frame.add(getTypeMessage().getByte());
        frame.add(getCommand().getByte());
        if (in != null) {
            for (byte i : in) {
                frame.add(i);
            }
        }
        return frame;
    }

    /**
     * Get the value of sync
     *
     * @return the value of sync
     */
    @Override
    public boolean isSync() {
        return sync;
    }

    /**
     * Get the String to quickly read the frame.
     *
     * @return the frame data information.
     */
    //@Override
    //abstract public String toString();
    /**
     * Convert array byte in a String.
     *
     * @param data bytes data.
     * @param start offset to start convertion.
     * @return the string associated.
     */
    public static String getString(byte[] data, int start) {
        String name_data = "";
        for (int i = start; i < data.length; i++) {
            if (data[i] != (char) '\0') {
                name_data += (char) data[i];
            } else {
                break;
            }
        }
        return name_data;
    }

    /**
     * Convert from byte array in Integer variable.
     *
     * @param b byte array.
     * @param offset offset.
     * @return integer value associated.
     */
    public static int byteArrayToInt(byte[] b, int offset) {
        ByteBuffer buf = ByteBuffer.wrap(b, offset, 2).order(ByteOrder.LITTLE_ENDIAN);
        return buf.getInt();
    }

    /**
     * Convert from byte array in float variable.
     *
     * @param b byte array.
     * @param offset offset.
     * @return float value associated.
     */
    public static float byteArrayToFloat(byte[] b, int offset) {
        ByteBuffer buf = ByteBuffer.wrap(b, offset, LNG_BYTE_FLOAT).order(ByteOrder.LITTLE_ENDIAN);
        return buf.getFloat();
    }

    /**
     * Convert from byte array in float array variable.
     *
     * @param b byte array.
     * @param offset offset.
     * @param count element of array.
     * @return float value associated.
     */
    public static float[] byteArrayToFloatArray(byte[] b, int offset, int count) {
        float[] val = new float[count];
        for (int i = 0; i < count; i++) {
            val[i] = byteArrayToFloat(b, offset + i * LNG_BYTE_FLOAT);
        }
        return val;
    }

    /**
     * Convert integer value and ad in array.
     *
     * @param in array to add converted integer value.
     * @param offset offset to put the variable.
     * @param value integer variable.
     * @return the same byte array variable in with integer value converted.
     */
    public static byte[] intToByteArray(byte[] in, int offset, int value) {
        byte[] array = ByteBuffer.allocate(2).putInt(value).array();
        System.arraycopy(array, 0, in, offset, array.length);
        return in;
    }

    /**
     * Convert long value and ad in array.
     *
     * @param in array to add converted long value.
     * @param offset offset to put the variable.
     * @param value long variable.
     * @return the same byte array variable in with long value converted.
     */
    public static byte[] longToByteArray(byte[] in, int offset, long value) {
        byte[] array = ByteBuffer.allocate(4).putLong(value).array();
        System.arraycopy(array, 0, in, offset, array.length);
        return in;
    }

    /**
     * Convert float value and ad in array.
     *
     * @param in array to add converted float value.
     * @param offset offset to put the variable.
     * @param value float variable.
     * @return the same byte array variable in with float value converted.
     */
    public static byte[] floatToByteArray(byte[] in, int offset, float value) {
        byte[] array = ByteBuffer.allocate(LNG_BYTE_FLOAT).putFloat(value).array();
        System.arraycopy(array, 0, in, offset, array.length);
        return in;
    }

    /**
     * Convert float array value and ad in array.
     *
     * @param in array to add converted float array value.
     * @param offset offset to put the variable.
     * @param value float array variable.
     * @return the same byte array variable in with float array value converted.
     */
    public static byte[] floatArrayToByteArray(byte[] in, int offset, float[] value) {
        for (int i = 0; i < value.length; i++) {
            in = floatToByteArray(in, offset + i * LNG_BYTE_FLOAT, value[i]);
        }
        return in;
    }
}
