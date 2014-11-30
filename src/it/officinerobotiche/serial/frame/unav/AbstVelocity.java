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
public abstract class AbstVelocity extends UnavFrame {

    public static class Velocity extends AbstVelocity {

        public Velocity() {
            super();
        }

        public Velocity(boolean sync, byte[] in) {
            super(sync, in);
        }

        public Velocity(boolean sync, Information info) {
            super(sync, info);
        }

        @Override
        public Command getCommand() {
            return Command.VELOCITY;
        }
    }
    
    public static class VelocityMis extends AbstVelocity {

        public VelocityMis() {
            super();
        }

        public VelocityMis(boolean sync, byte[] in) {
            super(sync, in);
        }

        public VelocityMis(boolean sync, Information info) {
            super(sync, info);
        }

        @Override
        public Command getCommand() {
            return Command.VELOCITY_MIS;
        }
    }

    public AbstVelocity() {
        super();
    }

    public AbstVelocity(boolean sync, byte[] in) {
        super(sync, in);
    }

    public AbstVelocity(boolean sync, Information info) {
        super(sync, info);
    }

}
