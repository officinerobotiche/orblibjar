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
public class Motor extends MotionFrame {
    
    public static final Motor LEFT = new Motor(Command.MOTOR_L);
    public static final Motor RIGHT = new Motor(Command.MOTOR_R);
    
    private final Command comm;

    private Motor(Command comm) {
        super();
        this.comm = comm;
    }

    public Motor(boolean sync, int command, byte[] in) {
        super(sync, command, in);
        this.comm = Command.getCommand(command);
    }

    public Motor(boolean sync, int command, Information info) {
        super(sync, command, info);
        this.comm = Command.getCommand(command);
    }
    
    @Override
    public Command getCommand() {
        return comm;
    }
}
