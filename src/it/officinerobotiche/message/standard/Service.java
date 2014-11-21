/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.officinerobotiche.message.standard;

import it.officinerobotiche.message.Jmessage;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Raffaello
 */
public class Service extends Jmessage {

    private final static int BUFF_SERVICE = 20;

    public enum NameService {

        RESET('*', "Reset"),
        DATE_CODE('d', "Date code"),
        NAME_BOARD('n', "Name board"),
        VERSION('v', "Version code"),
        AUTHOR('a', "Author");

        private final char name;
        private final String name_s;

        private NameService(char name, String name_s) {
            this.name = name;
            this.name_s = name_s;
        }

        public byte getByte() {
            return (byte) name;
        }

        public char getName() {
            return name;
        }

        @Override
        public String toString() {
            return name_s;
        }
        
        private static final Map<Character, NameService> lookup = new HashMap<>();

        static {
            for (NameService s : EnumSet.allOf(NameService.class)) {
                lookup.put(s.getName(), s);
            }
        }
        
        public static NameService get(byte Value) { 
        //the reverse lookup by simply getting 
        //the value from the lookup HashMap. 
          return lookup.get(Value); 
     }
    }

    private static enum ServiceCommand implements Command {

        SERVICE;

        @Override
        public byte getNumber() {
            return 0;
        }
        
        public static ServiceCommand get(byte Value) {
            return SERVICE;
        }

    }

    /**
     * type packet: * (D) Default messages (in top on this file) * other type
     * messages (in UNAV file)
     */
    public final static byte TYPE_MESSAGE = (byte) 'D';
    /**
     * command message
     */
    public final static byte[] ALL_COMMANDS = {0};
    private final byte[] data;
    private byte command;
    private final Type type;

    private String name_service;

    public Service(byte command, byte[] data) {
        this.data = data;
        this.command = command;
        byte[] info_data = new byte[BUFF_SERVICE];
        System.arraycopy(data, 1, info_data, 0, info_data.length);
        name_service = decodeService(NameService.get(data[0]), new String(info_data));
        type = Jmessage.Type.DATA;
    }

    public Service(NameService name) {
        type = Jmessage.Type.REQUEST;
        data = new byte[BUFF_SERVICE + 1];
        data[0] = name.getByte();
        this.command = ServiceCommand.SERVICE.getNumber();
    }

    private String decodeService(NameService name_service, String board) {
        String data = "";
        switch(name_service) {
            case AUTHOR:
                data += NameService.AUTHOR.toString();
                break;
            case DATE_CODE:
                data += NameService.DATE_CODE.toString();
                break;
            case NAME_BOARD:
                data += NameService.NAME_BOARD.toString();
                break;
            case VERSION:
                data += NameService.VERSION.toString();
                break;
        }
        return data + ": " + board;
    }

    public String getName() {
        return name_service;
    }

    @Override
    public boolean isACK() {
        return type.equals(Jmessage.Type.ACK) || type.equals(Jmessage.Type.NACK);
    }

    @Override
    public byte getLength() {
        return (byte) (Jmessage.LNG_HEADER + BUFF_SERVICE + 1);
    }

    @Override
    public Type getType() {
        return type;
    }

    @Override
    public byte getTypeMessage() {
        return TYPE_MESSAGE;
    }

    @Override
    public byte getCommand() {
        return ServiceCommand.SERVICE.getNumber();
    }

    @Override
    public byte[] getData() {
        return data;
    }

    @Override
    public String toString() {
        return "Service{" + "data=" + data + ", command=" + command + ", type=" + type + ", name_service=" + name_service + '}';
    }

}
