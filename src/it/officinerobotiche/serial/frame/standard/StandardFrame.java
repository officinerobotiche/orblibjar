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
public abstract class StandardFrame extends AbstractFrame {

    public enum Command implements ICommand {

        SERVICES(0, "Service"),
        TIME_PROCESS(1, "MProcess"),
        PRIORITY_PROCESS(2, "MProcess"),
        FRQ_PROCESS(3, "MProcess"),
        PARAMETER_SYSTEM(4, "ParamSystem"),
        ERROR_SERIAL(5, "ErrorSerial"),
        NAME_PROCESS(6, "NameProcess");

        private final int number;
        private final String name;
        private static final Map<Integer, String> lookup = new HashMap<>();
        private static final Map<Integer, Command> look_comm = new HashMap<>();

        private Command(int number, String name) {
            this.number = number;
            this.name = name;
        }

        @Override
        public int getNumber() {
            return number;
        }

        @Override
        public byte getByte() {
            return (byte) number;
        }

        @Override
        public String getName() {
            return name;
        }

        static {
            for (Command s : EnumSet.allOf(Command.class)) {
                lookup.put(s.getNumber(), s.getName());
                look_comm.put(s.getNumber(), s);
            }
        }

        public static Command getCommand(int number) {
            return look_comm.get(number);
        }

        public static String getStringCommand(int number) {
            return lookup.get(number);
        }
    };

    public StandardFrame() {
        super();
    }

    public StandardFrame(boolean sync, int name, byte[] in) {
        super(sync, name, in);
    }

    public StandardFrame(boolean sync, int name, Information info) {
        super(sync, name, info);
    }

    @Override
    public TypeMessage getTypeMessage() {
        return TypeMessage.DEFAULT;
    }

    @Override
    abstract public Command getCommand();
}
