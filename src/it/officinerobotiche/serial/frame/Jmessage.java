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
package it.officinerobotiche.serial.frame;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

/**
 * Generic interface with information about message.
 *
 * @author Raffaello Bonghi
 */
public interface Jmessage {

    /**
     * Generic interface associated an Command enumeration.
     */
    public interface ICommand {

        /**
         * Get the value of command number.
         *
         * @return the value of number.
         */
        public int getNumber();

        /**
         * Get the value of command in byte.
         *
         * @return the value of command in byte.
         */
        public byte getByte();

        /**
         * Get the value of name.
         *
         * @return the value of name
         */
        public String getName();
    };

    /**
     * Information about frame message.
     */
    public static enum Information {

        /**
         * Request message, required to turn back information about a precise
         * message.
         */
        REQUEST('R'),
        /**
         * Frame message with data. Used for parser to decode byte data
         * associated.
         */
        DATA('D'),
        /**
         * ACK message.
         */
        ACK('K'),
        /**
         * NACK message.
         */
        NACK('N');

        /**
         * Lookup table associated to reconstruct the correct enumeration form
         * character.
         */
        private static final Map<Byte, Information> lookup = new HashMap<>();
        /**
         * Character name associated for selected enumeration.
         */
        private final char name;

        /**
         * Private constructor to initialize enumeration.
         *
         * @param name character for describe message on frame.
         */
        private Information(char name) {
            this.name = name;
        }

        /**
         * Get the value name converted in byte.
         *
         * @return the value name converted in byte.
         */
        public final byte getByte() {
            return (byte) name;
        }

        /**
         * Static collection all Enumeration rescpect character name.
         */
        static {
            for (Information s : EnumSet.allOf(Information.class)) {
                lookup.put(s.getByte(), s);
            }
        }

        /**
         * the reverse lookup by simply getting the value from the lookup
         * HashMap.
         *
         * @param name byte name received from frame decoded on packet.
         * @return associated enumeration.
         */
        public static Information get(byte name) {
            return lookup.get(name);
        }
    }

    /**
     * Return Interface command type associated.
     *
     * @param <P> type of command.
     * @return Command associated.
     */
    public <P extends ICommand> P getCommand();

    /**
     * Get the value of sync
     *
     * @return the value of sync
     */
    public boolean isSync();

}
