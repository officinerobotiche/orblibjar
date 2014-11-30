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

    public PID(boolean async, byte[] in) {
        super(async, in);
    }
    
    public PID(Information info) {
        super(info);
    }

    public static class PIDLeft extends PID {
        
        public PIDLeft() {
            super();
        }

        public PIDLeft(boolean async, byte[] in) {
            super(async, in);
        }
        
        public PIDLeft(Information info) {
            super(info);
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

        public PIDRight(boolean async, byte[] in) {
            super(async, in);
        }
        
        public PIDRight(Information info) {
            super(info);
        }

        @Override
        public Command getCommand() {
            return Command.PID_R;
        }

    }

}
