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

import it.officinerobotiche.serial.frame.AbstractFrame;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Raffaello Bonghi
 */
public class Service extends StandardFrame {

    public static final Service AUTHOR = new Service(NameService.AUTHOR);
    public static final Service DATE_CODE = new Service(NameService.DATE_CODE);
    public static final Service NAME_BOARD = new Service(NameService.NAME_BOARD);
    public static final Service RESET = new Service(NameService.RESET);
    public static final Service VERSION = new Service(NameService.VERSION);

    private static enum NameService {

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

    private final static int BUFF_SERVICE = 20;

    private String name;
    private NameService service;

    private Service(NameService name) {
        this.information = Information.REQUEST;
        this.in = new byte[BUFF_SERVICE + 1];
        this.in[0] = name.getByte();
    }

    public Service(boolean sync, int command, byte[] in) {
        super(sync, command, in);
        this.service = NameService.get((char) in[0]);
        this.name = AbstractFrame.getString(in, 1);
    }

    public Service(boolean sync, int command, Information info) {
        super(sync, command, info);
    }

    @Override
    public Command getCommand() {
        return Command.SERVICES;
    }

    public String getInformation() {
        return service.toString() + ": " + name;
    }

}
