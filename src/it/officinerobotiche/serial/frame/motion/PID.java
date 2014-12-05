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
public abstract class PID extends MotionFrame {
    
    public PID() {
        super();
    }

    public PID(boolean sync, byte[] in) {
        super(sync, in);
    }
    
    public PID(boolean sync, Information info) {
        super(sync, info);
    }

    public static class PIDLeft extends PID {
        
        public PIDLeft() {
            super();
        }

        public PIDLeft(boolean sync, byte[] in) {
            super(sync, in);
        }
        
        public PIDLeft(boolean sync, Information info) {
            super(sync, info);
        }

        @Override
        public Command getCommand() {
            return Command.PID_L;
        }

    }
    
    public static class PIDRight extends PID {
        
        public PIDRight() {
            super();
        }

        public PIDRight(boolean sync, byte[] in) {
            super(sync, in);
        }
        
        public PIDRight(boolean sync, Information info) {
            super(sync, info);
        }

        @Override
        public Command getCommand() {
            return Command.PID_R;
        }

    }

}
