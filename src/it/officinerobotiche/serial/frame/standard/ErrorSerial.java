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
 * Definition to receive all serial error from board.
 *
 * @author Raffaello Bonghi
 */
public class ErrorSerial extends StandardFrame {

    /**
     * Quick definition for Error Serial frame message. This is usefull on
     * require a information on board.
     */
    public static final ErrorSerial ERROR_SERIAL = new ErrorSerial();

    /**
     * All number associated at relative error received in array.
     */
    private enum ERROR {

        /**
         * Framming error.
         */
        FRAMMING(-1, "Framming"),
        /**
         * Overrun error.
         */
        OVERRUN(-2, "Overrun"),
        /**
         * Error to receive header packet.
         */
        HEADER(-3, "Header"),
        /**
         * Length received too long.
         */
        LENGTH(-4, "Length"),
        /**
         * Data error.
         */
        DATA(-5, "Data"),
        /**
         * Checksum error.
         */
        CKS(-6, "CheckSum"),
        /**
         * Command error received.
         */
        CMD(-7, "Command"),
        /**
         * NACK error.
         */
        NACK(-8, "NACK"),
        /**
         * Option message error.
         */
        OPTION(-9, "Option"),
        /**
         * Packet error.
         */
        PKG(-10, "Package"),
        /**
         * Create packet error.˙
         */
        CREATE_PKG(-11, "Create package");

        /**
         * number of error.
         */
        private final int error;
        /**
         * Name error.
         */
        private final String name;
        /**
         * Map between number error and enumeration.
         */
        private static final Map<Integer, ERROR> lookup = new HashMap<>();

        /**
         * Initialization all enumeration with number and name.
         *
         * @param error number error.
         * @param name name error.
         */
        private ERROR(int error, String name) {
            this.error = error;
            this.name = name;
        }

        /**
         * Static collection all Enumeration with number error.
         */
        static {
            for (ERROR s : EnumSet.allOf(ERROR.class)) {
                lookup.put(s.getError(), s);
            }
        }

        /**
         * Get the value of error
         *
         * @return the value of error
         */
        public int getError() {
            return error;
        }

        /**
         * Get the value of name
         *
         * @return the value of name
         */
        public String getName() {
            return name;
        }

        /**
         * Get associated enumeration from number. This method used to
         * reconstruct name error received.
         *
         * @param value value of frame message.
         * @return Associated enumeration error.
         */
        public static ERROR get(int value) {
            //the reverse lookup by simply getting 
            //the value from the lookup HsahMap. 
            return lookup.get(value);
        }
    };

    /**
     * Map for reconstruction enumeration from number.
     */
    private static final Map<Integer, String> lookup = new HashMap<>();

    private ErrorSerial() {
        super();
    }

    /**
     * Initialize message with data received from board. This message is used
     * normally from parser to set data received, it is a message with data.
     *
     * @param sync type of packet received (syncronous or not).
     * @param command type command received.
     * @param in byte received.
     */
    public ErrorSerial(boolean sync, int command, byte[] in) {
        super(sync, command, in);
        for (int i = 0; i < in.length; i += 2) {
            lookup.put(AbstractFrame.byteArrayToInt(in, i), ERROR.get(-i / 2).getName());
        }
    }

    /**
     * Initialize message with ACK, NACK information. This message is used
     * normally from parser.
     *
     * @param sync type of packet received (syncronous or not).
     * @param command type command received.
     * @param info Information about message.
     */
    public ErrorSerial(boolean sync, int command, Information info) {
        super(sync, command, info);
    }

    /**
     * Get map with number error and relative name.
     *
     * @return the map of errors.
     */
    public static Map<Integer, String> getLookup() {
        return lookup;
    }

    @Override
    public Command getCommand() {
        return Command.ERROR_SERIAL;
    }

    /**
     * Get the value of errors
     *
     * @return the value of errors
     */
    public byte[] getErrors() {
        return in;
    }
}
