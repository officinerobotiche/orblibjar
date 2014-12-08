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

import it.officinerobotiche.serial.frame.AbstractFrame;

/**
 *
 * @author Raffaello Bonghi
 */
public class Velocity extends MotionFrame {

    public static final Velocity VELOCITY = new Velocity(Command.VELOCITY);
    public static final Velocity VELOCITY_MIS = new Velocity(Command.VELOCITY_MIS);

    private float linear;
    private float angular;
    private final Command comm;

    private Velocity(Command comm) {
        super();
        this.comm = comm;
    }

    public Velocity(float lin, float ang) {
        this.information = Information.REQUEST;
        this.comm = Command.VELOCITY;
        this.in = new byte[8];
        this.linear = lin;
        this.angular = ang;
        buildData();
    }

    public Velocity(boolean sync, int command, byte[] in) {
        super(sync, command, in);
        this.comm = Command.getCommand(command);
        this.linear = AbstractFrame.byteArrayToFloat(in, 0);
        this.angular = AbstractFrame.byteArrayToFloat(in, 4);
    }

    public Velocity(boolean sync, int command, Information info) {
        super(sync, command, info);
        this.comm = Command.getCommand(command);
    }

    private void buildData() {
        this.in = AbstractFrame.floatToByteArray(in, 0, this.linear);
        this.in = AbstractFrame.floatToByteArray(in, 4, this.angular);
    }

    public boolean setLinear(float linear) {
        if (comm.equals(Command.VELOCITY)) {
            this.linear = linear;
            buildData();
            return true;
        } else {
            return false;
        }
    }

    public boolean setAngular(float angular) {
        if (comm.equals(Command.VELOCITY)) {
            this.angular = angular;
            buildData();
            return true;
        } else {
            return false;
        }
    }

    public float getLinear() {
        return linear;
    }

    public float getAngular() {
        return angular;
    }

    @Override
    public Command getCommand() {
        return comm;
    }
}
