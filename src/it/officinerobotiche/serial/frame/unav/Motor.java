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
