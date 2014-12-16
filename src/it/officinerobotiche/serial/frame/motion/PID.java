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
 * Definition to set or get gain PID parameter from board. You can set Kp,
 * proportional gain Ki integral gain, Kd derivative gain.
 *
 * @author Raffaello Bonghi
 */
public class PID extends MotionFrame {

    /**
     * Quick definition for PID LEFT frame message. This is usefull on require a
     * information on board.
     */
    public static final PID LEFT = new PID(Command.PID_L);

    /**
     * Quick definition for PID RIGHT frame message. This is usefull on require
     * a information on board.
     */
    public static final PID RIGHT = new PID(Command.PID_R);

    /**
     * Class definition for PID LEFT. You can set to particular PID a set
     * configuration.
     */
    public static class PIDLeft extends PID {

        /**
         * Initialize PID LEFT message. This Constructor is used for require
         * data from board.
         */
        public PIDLeft() {
            super(Command.PID_L);
        }

        /**
         * Initialize PID LEFT message with Kp, Ki, Kd parameters. This
         * Constructor is used for require data from board.
         *
         * @param kP Proportional gain.
         * @param kI Integral gain.
         * @param kD Derivative gain.
         */
        public PIDLeft(float kP, float kI, float kD) {
            super(Command.PID_L);
            this.kP = kP;
            this.kI = kI;
            this.kD = kD;
            buildData();
        }
    }

    /**
     * Class definition for PID RIGHT. You can set to particular PID a set
     * configuration.
     */
    public static class PIDRight extends PID {

        /**
         * Initialize PID RIGHT message. This Constructor is used for require
         * data from board.
         */
        public PIDRight() {
            super(Command.PID_R);
        }

        /**
         * Initialize PID RIGHT message with Kp, Ki, Kd parameters. This
         * Constructor is used for require data from board.
         *
         * @param kP Proportional gain.
         * @param kI Integral gain.
         * @param kD Derivative gain.
         */
        public PIDRight(float kP, float kI, float kD) {
            super(Command.PID_R);
            this.kP = kP;
            this.kI = kI;
            this.kD = kD;
            buildData();
        }
    }

    /**
     * Length of PID frame. Tree floats for all data.
     */
    private static final int LNG_PID = 4 * 3;

    /**
     * Set type of command received.
     */
    private final Command comm;

    /**
     * Proportional gain.
     */
    protected float kP;

    /**
     * Integral gain.
     */
    protected float kI;

    /**
     * Derivative gain.
     */
    protected float kD;

    /**
     * Initialize PID message. This Constructor is used for require data from
     * board.
     *
     * @param comm Type of command required.
     */
    protected PID(Command comm) {
        super();
        this.comm = comm;
        this.in = new byte[LNG_PID];
    }

    /**
     * Initialize message with data received from board. This message is used
     * normally from parser to set data received, it is a message with data.
     *
     * @param sync type of packet received (syncronous or not).
     * @param command type command received.
     * @param in byte received.
     */
    public PID(boolean sync, int command, byte[] in) {
        super(sync, command, in);
        this.comm = Command.getCommand(command);
        this.kP = AbstractFrame.byteArrayToFloat(in, 0);
        this.kI = AbstractFrame.byteArrayToFloat(in, 4);
        this.kD = AbstractFrame.byteArrayToFloat(in, 8);
    }

    /**
     * Initialize message with ACK, NACK information. This message is used
     * normally from parser.
     *
     * @param sync type of packet received (syncronous or not).
     * @param command type command received.
     * @param info Information about message.
     */
    public PID(boolean sync, int command, Information info) {
        super(sync, command, info);
        this.comm = Command.getCommand(command);
    }

    @Override
    public Command getCommand() {
        return comm;
    }

    /**
     * Construct byte data array with information on object.
     */
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
