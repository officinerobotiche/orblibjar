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

        RESET('*', "Reset"),
        DATE_CODE('d', "Date"),
        NAME_BOARD('n', "Name board"),
        VERSION('v', "Version"),
        AUTHOR('a', "Author");

        private final char name;
        private final String name_string;
        private static final Map<Character, NameService> lookup = new HashMap<>();

        private NameService(char name, String name_string) {
            this.name = name;
            this.name_string = name_string;
        }

        public char getName() {
            return name;
        }

        public byte getByte() {
            return (byte) name;
        }
        
        @Override
        public String toString() {
            return name_string;
        }

        static {
            for (NameService s : EnumSet.allOf(NameService.class)) {
                lookup.put(s.getName(), s);
            }
        }

        public static NameService get(char Value) {
            //the reverse lookup by simply getting 
            //the value from the lookup HsahMap. 
            return lookup.get(Value);
        }
    };

    private String name;
    private NameService service;

    public Service(NameService name) {
        this.information = Information.REQUEST;
        this.in = new byte[BUFF_SERVICE + 1];
        this.in[0] = name.getByte();
    }

    public Service(boolean sync, byte[] in) {
        super(sync, in);
        this.service = NameService.get((char) in[0]);
        this.name = getName(in, 1);
    }

    private String getName(byte[] data, int start) {
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

    public Service(boolean sync, Information info) {
        super(sync, info);
    }

    @Override
    public Command getCommand() {
        return Command.SERVICES;
    }

    public String getInformation() {
        return service.toString() + ": " + name;
    }

}
