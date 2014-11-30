/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.officinerobotiche.serial.message.unav;

/**
 *
 * @author Raffaello
 */
public abstract class PID extends UnavFrame {
    
    public PID() {
        super();
    }

    public PID(byte[] in) {
        super(in);
    }
    
    public PID(Information info) {
        super(info);
    }

    public static class PIDLeft extends PID {
        
        public PIDLeft() {
            super();
        }

        public PIDLeft(byte[] in) {
            super(in);
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

        public PIDRight(byte[] in) {
            super(in);
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
