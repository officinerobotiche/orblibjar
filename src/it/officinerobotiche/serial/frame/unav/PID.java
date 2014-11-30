/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.officinerobotiche.serial.frame.unav;

/**
 *
 * @author Raffaello
 */
public abstract class PID extends UnavFrame {
    
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
