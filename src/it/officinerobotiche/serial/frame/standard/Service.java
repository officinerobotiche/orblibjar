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
package it.officinerobotiche.serial.frame.standard;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Raffaello Bonghi
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
