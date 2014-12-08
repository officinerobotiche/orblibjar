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
public class PID extends MotionFrame {

    public static final PID LEFT = new PID(Command.PID_L);
    public static final PID RIGHT = new PID(Command.PID_R);

    public static class PIDLeft extends PID {

        public PIDLeft() {
            super(Command.PID_L);
        }

        public PIDLeft(float kP, float kI, float kD) {
            super(Command.PID_L);
            this.kP = kP;
            this.kI = kI;
            this.kD = kD;
            buildData();
        }
    }

    public static class PIDRight extends PID {

        public PIDRight() {
            super(Command.PID_R);
        }

        public PIDRight(float kP, float kI, float kD) {
            super(Command.PID_R);
            this.kP = kP;
            this.kI = kI;
            this.kD = kD;
            buildData();
        }
    }

    private static final int LNG_PID = 4 * 3;
    private final Command comm;
    protected float kP;
    protected float kI;
    protected float kD;

    protected PID(Command comm) {
        super();
        this.comm = comm;
        this.in = new byte[LNG_PID];
    }

    public PID(boolean sync, int command, byte[] in) {
        super(sync, command, in);
        this.comm = Command.getCommand(command);
        this.kP = AbstractFrame.byteArrayToFloat(in, 0);
        this.kI = AbstractFrame.byteArrayToFloat(in, 4);
        this.kD = AbstractFrame.byteArrayToFloat(in, 8);
    }

    public PID(boolean sync, int command, Information info) {
        super(sync, command, info);
        this.comm = Command.getCommand(command);
    }

    @Override
    public Command getCommand() {
        return comm;
    }

    @Override
    protected void buildData() {
        this.in = AbstractFrame.floatToByteArray(in, 0, kP);
        this.in = AbstractFrame.floatToByteArray(in, 4, kI);
        this.in = AbstractFrame.floatToByteArray(in, 8, kD);
    }

    /**
     * Get the value of kD
     *
     * @return the value of kD
     */
    public float getkD() {
        return kD;
    }

    /**
     * Set the value of kD
     *
     * @param kD new value of kD
     */
    public void setkD(float kD) {
        this.kD = kD;
        buildData();
    }

    /**
     * Get the value of kI
     *
     * @return the value of kI
     */
    public float getkI() {
        return kI;
    }

    /**
     * Set the value of kI
     *
     * @param kI new value of kI
     */
    public void setkI(float kI) {
        this.kI = kI;
        buildData();
    }

    /**
     * Get the value of kP
     *
     * @return the value of kP
     */
    public float getkP() {
        return kP;
    }

    /**
     * Set the value of kP
     *
     * @param kP new value of kP
     */
    public void setkP(float kP) {
        this.kP = kP;
        buildData();
    }

}
