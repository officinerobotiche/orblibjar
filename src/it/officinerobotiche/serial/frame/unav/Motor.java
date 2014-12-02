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
package it.officinerobotiche.serial.frame.unav;

/**
 *
 * @author Raffaello Bonghi
 */
public abstract class Motor extends UnavFrame {

    public static class MotorLeft extends UnavFrame {

        public MotorLeft() {
            super();
        }

        public MotorLeft(boolean sync, byte[] in) {
            super(sync, in);
        }

        public MotorLeft(boolean sync, Information info) {
            super(sync, info);
        }

        @Override
        public Command getCommand() {
            return Command.MOTOR_L;
        }
    }
    
    public static class MotorRight extends UnavFrame {

        public MotorRight() {
            super();
        }

        public MotorRight(boolean sync, byte[] in) {
            super(sync, in);
        }

        public MotorRight(boolean sync, Information info) {
            super(sync, info);
        }

        @Override
        public Command getCommand() {
            return Command.MOTOR_R;
        }
    }

    public Motor() {
        super();
    }

    public Motor(boolean sync, byte[] in) {
        super(sync, in);
    }

    public Motor(boolean sync, Information info) {
        super(sync, info);
    }
}
