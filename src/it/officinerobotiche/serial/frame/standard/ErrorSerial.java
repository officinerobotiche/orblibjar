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
public class ErrorSerial extends StandardFrame {
    
    public static final ErrorSerial ERROR_SERIAL = new ErrorSerial();

    private enum ERROR {

        FRAMMING(-1, "Framming"),
        OVERRUN(-2, "Overrun"),
        HEADER(-3, "Header"),
        LENGTH(-4, "Length"),
        DATA(-5, "Data"),
        CKS(-6, "CheckSum"),
        CMD(-7, "Command"),
        NACK(-8, "NACK"),
        OPTION(-9, "Option"),
        PKG(-10, "Package"),
        CREATE_PKG(-11, "Create package");

        private final int error;
        private final String name;
        private static final Map<Integer, ERROR> lookup = new HashMap<>();

        private ERROR(int error, String name) {
            this.error = error;
            this.name = name;
        }

        static {
            for (ERROR s : EnumSet.allOf(ERROR.class)) {
                lookup.put(s.getError(), s);
            }
        }

        public int getError() {
            return error;
        }

        public String getName() {
            return name;
        }

        public static ERROR get(int Value) {
            //the reverse lookup by simply getting 
            //the value from the lookup HsahMap. 
            return lookup.get(Value);
        }
    };
    
    private static final Map<Integer, String> lookup = new HashMap<>();

    private ErrorSerial() {
        super();
    }

    public ErrorSerial(boolean sync, int command, byte[] in) {
        super(sync, command, in);
        for(int i=0; i < in.length; i += 2) {
            lookup.put(AbstractFrame.byteArrayToInt(in, i), ERROR.get(-i/2).getName());
        }
    }

    public static Map<Integer, String> getLookup() {
        return lookup;
    }

    public ErrorSerial(boolean sync, int command, Information info) {
        super(sync, command, info);
    }

    @Override
    public Command getCommand() {
        return Command.ERROR_SERIAL;
    }

    public byte[] getErrors() {
        return in;
    }
}
