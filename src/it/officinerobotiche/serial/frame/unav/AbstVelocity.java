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

import it.officinerobotiche.serial.frame.AbstractFrame;

/**
 *
 * @author Raffaello Bonghi
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
