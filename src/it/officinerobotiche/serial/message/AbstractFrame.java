/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.officinerobotiche.serial.message;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Raffaello
 */
public abstract class AbstractFrame implements Jmessage {

    public static enum TypeMessage {

        DEFAULT('D', "Standard"), UNAV('U', "Unav");

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

    private final byte[] in;
    private final Information information;

    public AbstractFrame() {
        this.in = null;
        this.information = Information.REQUEST;
    }

    public AbstractFrame(Information info) {
        this.in = null;
        this.information = info;
    }

    public AbstractFrame(byte[] in) {
        this.in = in;
        this.information = Information.DATA;
    }

    abstract public TypeMessage getTypeMessage();
    
    @Override
    abstract public ICommand getCommand();
    
    public ArrayList<Byte> getFrame() {
        int length = (in != null) ? in.length : 0;
        ArrayList<Byte> frame = new ArrayList<>(Jmessage.LNG_HEADER + length);
        frame.add((byte)(Jmessage.LNG_HEADER + length));
        frame.add(information.getByte());
        frame.add(getTypeMessage().getByte());
        frame.add(getCommand().getByte());
        if(in != null) {
            for (byte i : in) {
                frame.add(i);
            }
        }
        return frame;
    }

    //@Override
    //abstract public String toString();
}
