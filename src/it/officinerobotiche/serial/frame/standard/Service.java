/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.officinerobotiche.serial.frame.standard;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Raffaello
 */
public class Service extends StandardFrame {
    
    private final static int BUFF_SERVICE = 20;

    public enum NameService {

        RESET('*'),
        DATE_CODE('d'),
        NAME_BOARD('n'),
        VERSION('v'),
        AUTHOR('a');

        private final char name;
        private static final Map<Character, NameService> lookup = new HashMap<>();

        private NameService(char name) {
            this.name = name;
        }

        public char getName() {
            return name;
        }

        public byte getByte() {
            return (byte) name;
        }

        static {
            for (NameService s : EnumSet.allOf(NameService.class)) {
                lookup.put(s.getName(), s);
            }
        }

        public static NameService get(byte Value) {
            //the reverse lookup by simply getting 
            //the value from the lookup HsahMap. 
            return lookup.get(Value);
        }
    };
    
    private String name;
    
    public Service(NameService name) {
        this.information = Information.REQUEST;
        this.in = new byte[BUFF_SERVICE + 1];
        this.in[0] = name.getByte();
    }

    public Service(boolean async, byte[] in) {
        super(async, in);
        byte[] data = new byte[BUFF_SERVICE];
        System.arraycopy(in, 1, data, 0, BUFF_SERVICE);
        this.name = new String(data);
    }
    
    public Service(Information info) {
        super(info);
    }
    
    private String decodeService(NameService name_service, String board) {
        String name = "";
        switch (name_service) {
            case AUTHOR:
                name += NameService.AUTHOR.toString();
                break;
            case DATE_CODE:
                name += NameService.DATE_CODE.toString();
                break;
            case NAME_BOARD:
                name += NameService.NAME_BOARD.toString();
                break;
            case VERSION:
                name += NameService.VERSION.toString();
                break;
        }
        return name + ": " + board;
    }

    @Override
    public Command getCommand() {
        return Command.SERVICES;
    }
    
    public String getInformation() {
        return name;
    }

}
