/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.officinerobotiche.serial.message.standard;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Raffaello
 */
public class Service extends StandardFrame {

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
    
    public Service() {
        super();
    }

    public Service(byte[] in) {
        super(in);
    }
    
    public Service(Information info) {
        super(info);
    }

    @Override
    public Command getCommand() {
        return Command.SERVICES;
    }

}
