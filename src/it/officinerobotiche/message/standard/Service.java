/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.officinerobotiche.message.standard;

import it.officinerobotiche.message.Jmessage;

/**
 *
 * @author Raffaello
 */
public class Service extends Jmessage {

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
    }
    
    
    /**
     * type packet: * (D) Default messages (in top on this file) 
     * * other type messages (in UNAV file)
     */
    public final static byte TYPE_MESSAGE = (byte) 'D';
    /**
     * command message
     */
    public final static byte[] ALL_COMMANDS = {0};
    private byte command;
    private final static int BUFF_SERVICE = 20;
    private byte[] data;
    private String name_service;
    private final byte type;
    
    public Service(byte command, byte[] data) {
        this.data = data;
        this.command = command;
        name_service = decodeService(data[0]);
        type = Jmessage.Type.DATA.getName();
    }
    
    public Service(NameService name) {
        type = Jmessage.Type.REQUEST.getName();
        data = new byte[BUFF_SERVICE + 1];
        data[0] = name.getByte();
    }
    
    private String decodeService(byte name_service) {
        if(name_service == NameService.AUTHOR.getByte()) {
            return NameService.AUTHOR.toString();
        } else if (name_service == NameService.DATE_CODE.getByte()) {
            return NameService.DATE_CODE.toString();
        } else if (name_service == NameService.NAME_BOARD.getByte()) {
            return NameService.NAME_BOARD.toString();
        } else if (name_service == NameService.VERSION.getByte()) {
            return NameService.VERSION.toString();
        }
        return null;
    }
    
    public String getName() {
        return name_service;
    }
    
    @Override
    public byte getLength() {
        return (byte) (Jmessage.LNG_HEADER + BUFF_SERVICE + 1);
    }

    @Override
    public byte getType() {
        return type;
    }

    @Override
    public byte getTypeMessage() {
        return TYPE_MESSAGE;
    }

    @Override
    public byte getCommand() {
        return ALL_COMMANDS[0];
    }

    @Override
    public byte[] getData() {
        return data;
    }

}
