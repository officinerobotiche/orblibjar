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
 *
 * @author Raffaello Bonghi
 */
public abstract class AbstractFrame implements Jmessage {

    public static final int LNG_HEADER = 4;

    public static enum TypeMessage {

        DEFAULT('D', "Standard"), MOTION('M', "Motion");

        private static final String NAME_PKG = TypeMessage.class.getPackage().getName();
        private static final String NAME_FRAME = "Frame";
        private static final Map<Character, TypeMessage> lookup = new HashMap<>();
        private final char name;
        private final String abstractClass;

        private TypeMessage(char name, String abstractClass) {
            this.name = name;
            this.abstractClass = abstractClass;
        }

        public char getName() {
            return name;
        }

        public byte getByte() {
            return (byte) name;
        }

        private String getAbstractClass() {
            return AbstractFrame.TypeMessage.NAME_PKG + "." + abstractClass.toLowerCase()
                    + "." + abstractClass + AbstractFrame.TypeMessage.NAME_FRAME;
        }

        static {
            for (TypeMessage s : EnumSet.allOf(TypeMessage.class)) {
                lookup.put(s.getName(), s);
            }
        }

        /**
         * the reverse lookup by simply getting the value from the lookup
         * HsahMap.
         *
         * @param name_rec
         * @return
         */
        public static String getAbstractClass(char name_rec) {
            return lookup.get(name_rec).getAbstractClass();
        }

    }

    protected byte[] in;
    protected Information information;
    protected boolean sync;

    public AbstractFrame() {
        this.sync = true;
        this.in = null;
        this.information = Information.REQUEST;
    }

    public AbstractFrame(boolean sync, int name, Information info) {
        this.sync = sync;
        this.in = null;
        this.information = info;
    }

    public AbstractFrame(boolean sync, int name, byte[] in) {
        this.sync = sync;
        this.in = in;
        this.information = Information.DATA;
    }

    abstract public TypeMessage getTypeMessage();

    @Override
    abstract public ICommand getCommand();

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

    @Override
    public boolean isSync() {
        return sync;
    }

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
    
    public static int byteArrayToInt(byte[] b, int offset) {
        ByteBuffer buf = ByteBuffer.wrap(b, offset, 2).order(ByteOrder.LITTLE_ENDIAN);
        return buf.getInt();
    }
    
    public static float byteArrayToFloat(byte[] b, int offset) {
        ByteBuffer buf = ByteBuffer.wrap(b, offset, 4).order(ByteOrder.LITTLE_ENDIAN);
        return buf.getFloat();
    }
    
    public static byte[] intToByteArray(byte[] in, int offset, int value) {
        byte[] array = ByteBuffer.allocate(2).putInt(value).array();
        System.arraycopy(array, 0, in, offset, array.length);
        return in;
    }

    public static byte[] longToByteArray(long value) {
        return ByteBuffer.allocate(4).putLong(value).array();
    }

    public static byte[] floatToByteArray(byte[] in, int offset, float value) {
        byte[] array = ByteBuffer.allocate(4).putFloat(value).array();
        System.arraycopy(array, 0, in, offset, array.length);
        return in;
    }

    //@Override
    //abstract public String toString();
}
