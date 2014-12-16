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
 * Definition to receive service information from board. You can read author
 * board, date to write code, name board, type of board and version. Finally you
 * can reset board.
 *
 * @author Raffaello Bonghi
 */
public class Service extends StandardFrame {

    /**
     * Quick definition for author service frame message. This is usefull on
     * require a information on board.
     */
    public static final Service AUTHOR = new Service(NameService.AUTHOR);

    /**
     * Quick definition for date code service frame message. This is usefull on
     * require a information on board.
     */
    public static final Service DATE_CODE = new Service(NameService.DATE_CODE);

    /**
     * Quick definition for name board service frame message. This is usefull on
     * require a information on board.
     */
    public static final Service NAME_BOARD = new Service(NameService.NAME_BOARD);

    /**
     * Quick definition for type board service frame message. This is usefull on
     * require a information on board.
     */
    public static final Service TYPE_BOARD = new Service(NameService.TYPE_BOARD);

    /**
     * Quick definition for reset service frame message. This is usefull on
     * require a information on board.
     */
    public static final Service RESET = new Service(NameService.RESET);

    /**
     * Quick definition for version service frame message. This is usefull on
     * require a information on board.
     */
    public static final Service VERSION = new Service(NameService.VERSION);

    /**
     * Enumeration associated for all name service.
     */
    private static enum NameService {

        /**
         * Author board.
         */
        AUTHOR('a', "Author"),
        /**
         * Date write code.
         */
        DATE_CODE('d', "Date"),
        /**
         * Name board used.
         */
        NAME_BOARD('n', "Name board"),
        /**
         * Type of board.
         */
        TYPE_BOARD('t', "Type board"),
        /**
         * Reset board.
         */
        RESET('*', "Reset"),
        /**
         * Version of code used.
         */
        VERSION('v', "Version");

        /**
         * Character name request for service.
         */
        private final char name;

        /**
         * Name service.
         */
        private final String name_string;

        /**
         * Map between character service and name.
         */
        private static final Map<Character, NameService> lookup = new HashMap<>();

        /**
         * Initialize enumeration with character and associated name.
         *
         * @param name chracter service.
         * @param name_string name service.
         */
        private NameService(char name, String name_string) {
            this.name = name;
            this.name_string = name_string;
        }

        /**
         * Get the value of name
         *
         * @return the value of name
         */
        public char getName() {
            return name;
        }

        /**
         * Get the relative byte associated at character service
         *
         * @return the value in byte associated to service.
         */
        public byte getByte() {
            return (byte) name;
        }

        @Override
        public String toString() {
            return name_string;
        }

        /**
         * Static collection all enumeration between name service and
         * enumeration.
         */
        static {
            for (NameService s : EnumSet.allOf(NameService.class)) {
                lookup.put(s.getName(), s);
            }
        }

        /**
         * Get associated enumeration from character. This method used to
         * reconstruct frame message received.
         *
         * @param number value of frame message.
         * @return Associated enumeration command.
         */
        public static NameService get(char Value) {
            //the reverse lookup by simply getting 
            //the value from the lookup HsahMap. 
            return lookup.get(Value);
        }
    };

    /**
     * Length of buffer service.
     */
    private final static int BUFF_SERVICE = 20;

    /**
     * Name received.
     */
    private String name;

    /**
     * Enumeration associated for service.
     */
    private NameService service;

    /**
     * Initialize Service message. This Constructor is used for require data
     * from board.
     *
     * @param name Type of command required.
     */
    private Service(NameService name) {
        this.information = Information.REQUEST;
        this.in = new byte[BUFF_SERVICE + 1];
        this.in[0] = name.getByte();
    }

    /**
     * Initialize message with data received from board. This message is used
     * normally from parser to set data received, it is a message with data.
     *
     * @param sync type of packet received (syncronous or not).
     * @param command type command received.
     * @param in byte received.
     */
    public Service(boolean sync, int command, byte[] in) {
        super(sync, command, in);
        this.service = NameService.get((char) in[0]);
        this.name = AbstractFrame.getString(in, 1);
    }

    /**
     * Initialize message with ACK, NACK information. This message is used
     * normally from parser.
     *
     * @param sync type of packet received (syncronous or not).
     * @param command type command received.
     * @param info Information about message.
     */
    public Service(boolean sync, int command, Information info) {
        super(sync, command, info);
    }

    @Override
    public Command getCommand() {
        return Command.SERVICES;
    }

    /**
     * Get the value of information required
     *
     * @return the value of information required.
     */
    public String getInformation() {
        return service.toString() + ": " + name;
    }

}
