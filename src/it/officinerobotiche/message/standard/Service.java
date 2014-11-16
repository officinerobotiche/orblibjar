/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.officinerobotiche.message.standard;

import it.officinerobotiche.message.Jmessage;
import java.util.ArrayList;

/**
 *
 * @author Raffaello
 */
public class Service extends Jmessage {

    public enum Type {

        RESET('*'),
        DATE_CODE('d'),
        NAME_BOARD('n'),
        VERSION('v'),
        AUTHOR('a');

        private final char name;

        private Type(char name) {
            this.name = name;
        }

        public char getName() {
            return name;
        }
    }
    
    private final byte type;
    /**
     * type packet: * (D) Default messages (in top on this file) 
     * * other type messages (in UNAV file)
     */
    private final static byte type_message = (byte) 'D';
    
    /**
     * command message
     */
    private final static byte command = 0;
    
    private final static int BUFF_SERVICE = 20;
    private byte[] data = new byte[BUFF_SERVICE + 1];
    
    public Service(char name) {
        type = Jmessage.Type.REQUEST.getName();
        data[0] = (byte) name;
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
