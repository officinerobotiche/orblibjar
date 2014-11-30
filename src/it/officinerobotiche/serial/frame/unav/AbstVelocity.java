/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.officinerobotiche.serial.frame.unav;

import it.officinerobotiche.serial.frame.AbstractFrame;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/**
 *
 * @author Raffaello
 */
public abstract class AbstVelocity extends UnavFrame {

    public static class Velocity extends AbstVelocity {

        public Velocity() {
            super();
        }
        
        public Velocity(float lin, float ang) {
           this.information = Information.REQUEST;
           this.in = new byte[8];
           byte[] linbyte = AbstractFrame.float2ByteArray(lin);
           byte[] angbyte = AbstractFrame.float2ByteArray(ang);
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

    private float linear;
    private float angular;

    public AbstVelocity() {
        super();
    }

    public AbstVelocity(boolean sync, byte[] in) {
        super(sync, in);
        this.linear = AbstractFrame.getFloat(in, 0);
        this.angular = AbstractFrame.getFloat(in, 4);
    }

    public AbstVelocity(boolean sync, Information info) {
        super(sync, info);
    }

    public float getLinear() {
        return linear;
    }

    public float getAngular() {
        return angular;
    }
}
