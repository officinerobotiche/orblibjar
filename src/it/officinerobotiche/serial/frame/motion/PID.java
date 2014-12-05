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
package it.officinerobotiche.serial.frame.motion;

/**
 *
 * @author Raffaello Bonghi
 */
public class PID extends MotionFrame {
    private enum Type {

        PID_LEFT(Command.PID_L), PID_RIGHT(Command.PID_R);

        private final Command command;

        private Type(Command command) {
            this.command = command;
        }

        public Command getCommand() {
            return command;
        }
        
    };
    
    public static final PID LEFT = new PID(Type.PID_LEFT);
    public static final PID RIGHT = new PID(Type.PID_RIGHT);
    
    private final Type comm;

    private PID(Type comm) {
        super();
        this.comm = comm;
    }

    public PID(boolean sync, int command, byte[] in) {
        super(sync, command, in);
        this.comm = (command == Command.PID_L.getByte()) ? Type.PID_LEFT : Type.PID_RIGHT;
    }

    public PID(boolean sync, int command, Information info) {
        super(sync, command, info);
        this.comm = (command == Command.PID_L.getByte()) ? Type.PID_LEFT : Type.PID_RIGHT;
    }

    @Override
    public Command getCommand() {
        return comm.getCommand();
    }
}
