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

    public enum Type {

        RESET('*', "Reset"),
        DATE_CODE('d', "Date code"),
        NAME_BOARD('n', "Name board"),
        VERSION('v', "Version code"),
        AUTHOR('a', "Author");

        private final char name;
        private final String name_s;

        private Type(char name, String name_s) {
            this.name = name;
            this.name_s = name_s;
        }

        public char getName() {
            return name;
        }
        
        @Override
        public String toString() {
            return name_s;
        }
    }
    
    private final byte type;
    /**
     * type packet: * (D) Default messages (in top on this file) 
     * * other type messages (in UNAV file)
     */
    public final static byte type_message = (byte) 'D';
    
    /**
     * command message
     */
    public final static byte command = 0;
    
    private final static int BUFF_SERVICE = 20;
    private byte[] data = new byte[BUFF_SERVICE + 1];
    
    public Service(byte type, byte[] data) {
        this.type = type;
        this.data = data;
    }
    
    public Service(char name) {
        type = Jmessage.Type.REQUEST.getName();
        data[0] = (byte) name;
    }
    
    public String getName() {
        return null;
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
    public byte getType_message() {
        return type_message;
    }

    @Override
    public byte getCommand() {
        return command;
    }

    @Override
    public byte[] getData() {
        return data;
    }

}
