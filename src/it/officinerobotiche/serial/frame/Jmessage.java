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
 *
 * @author Raffaello Bonghi
 */
public interface Jmessage {

    public interface ICommand {

        public int getNumber();

        public byte getByte();

        public String getName();
    };

    public static enum Information {

        REQUEST('R'),
        DATA('D'),
        ACK('K'),
        NACK('N');

        private static final Map<Byte, Information> lookup = new HashMap<>();
        private final char name;

        private Information(char name) {
            this.name = name;
        }

        public final byte getByte() {
            return (byte) name;
        }

        static {
            for (Information s : EnumSet.allOf(Information.class)) {
                lookup.put(s.getByte(), s);
            }
        }

        /**
         * the reverse lookup by simply getting the value from the lookup
         * HsahMap.
         *
         * @param name
         * @return
         */
        public static Information get(byte name) {
            return lookup.get(name);
        }
    }

    public <P extends ICommand> P getCommand();
    
    public boolean isSync();

}
