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
 * Definition to save or load motor parameter from board. You can set gain to
 * convert value read from Input capture or QEI. Used IS to configure this data,
 * in particular meter, radiand, radians to sec.
 *
 * @author Raffaello Bonghi
 */
public class ParamMotor extends MotionFrame {

    /**
     * Quick definition for Parameter motor LEFT frame message. This is usefull
     * on require a information on board.
     */
    public static final ParamMotor LEFT = new ParamMotor(Command.PARAM_MOTOR_L);

    /**
     * Quick definition for Parameter motor RIGHT frame message. This is usefull
     * on require a information on board.
     */
    public static final ParamMotor RIGHT = new ParamMotor(Command.PARAM_MOTOR_R);

    /**
     * Class definition for Parameter motor LEFT. You can set to particular
     * Parameter motor configuration.
     */
    public static class ParamMotorLeft extends ParamMotor {

        /**
         * Initialize Parameter motor LEFT message. This Constructor is used for
         * require data from board.
         */
        public ParamMotorLeft() {
            super(Command.PARAM_MOTOR_L);
        }

        /**
         * Initialize Parameter motor LEFT message with K_Ang, K_Vel, default
         * value enable h-bridge and enableSwap confguration parameters. This
         * Constructor is used for require data from board.
         *
         * @param kAng Gain to convert QEI value to angular position.
         * @param kVel Gain to convert Input Capture value to angular velocity motor.
         * @param encoderSwap Set to swap encoder velocity measure.
         * @param enable Set default level configuration h-bridge enable.
         */
        public ParamMotorLeft(float kAng, float kVel, boolean encoderSwap, boolean enable) {
            super(Command.PARAM_MOTOR_L);
            this.kAng = kAng;
            this.kVel = kVel;
            this.encoderSwap = encoderSwap;
            this.enable = enable;
            buildData();
        }
    }

    /**
     * Class definition for PID RIGHT. You can set to particular PID a set
     * configuration.
     */
    public static class ParamMotorRight extends ParamMotor {

        /**
         * Initialize PID RIGHT message. This Constructor is used for require
         * data from board.
         */
        public ParamMotorRight() {
            super(Command.PARAM_MOTOR_R);
        }

        /**
         * Initialize Parameter motor RIGHT message with K_Ang, K_Vel, default
         * value enable h-bridge and enableSwap confguration parameters. This
         * Constructor is used for require data from board.
         *
         * @param kAng Gain to convert QEI value to angular position.
         * @param kVel Gain to convert Input Capture value to angular velocity motor.
         * @param encoderSwap Set to swap encoder velocity measure.
         * @param enable Set default level configuration h-bridge enable.
         */
        public ParamMotorRight(float kAng, float kVel, boolean encoderSwap, boolean enable) {
            super(Command.PARAM_MOTOR_R);
            this.kAng = kAng;
            this.kVel = kVel;
            this.encoderSwap = encoderSwap;
            this.enable = enable;
            buildData();
        }
    }

    /**
     * Length of PID frame. Tree floats for all data.
     */
    private static final int LNG_PARAM_MOTOR = 4 * 2 + 1 * 2;

    /**
     * Set type of command received.
     */
    private final Command comm;

    /**
     * Proportional gain to convert angular position.
     */
    protected float kAng;

    /**
     * Proportional gain to convert angular velocity.
     */
    protected float kVel;

    /**
     * Set default value bridge in off (Normally high level voltage or
     * viceversa).
     */
    protected boolean enable;

    /**
     * Set encoder velocity read to swap configuration.
     */
    protected boolean encoderSwap;

    /**
     * Initialize parameter motors message. This Constructor is used for require
     * data from board.
     *
     * @param comm Type of command required.
     */
    protected ParamMotor(Command comm) {
        super();
        this.comm = comm;
        this.in = new byte[LNG_PARAM_MOTOR];
    }

    /**
     * Initialize message with data received from board. This message is used
     * normally from parser to set data received, it is a message with data.
     *
     * @param sync type of packet received (syncronous or not).
     * @param command type command received.
     * @param in byte received.
     */
    public ParamMotor(boolean sync, int command, byte[] in) {
        super(sync, command, in);
        this.comm = Command.getCommand(command);
        this.kAng = AbstractFrame.byteArrayToFloat(in, 0);
        this.kVel = AbstractFrame.byteArrayToFloat(in, 4);
        this.encoderSwap = AbstractFrame.byteArrayToBoolean(in, 8);
        this.enable = AbstractFrame.byteArrayToBoolean(in, 9);
    }

    /**
     * Initialize message with ACK, NACK information. This message is used
     * normally from parser.
     *
     * @param sync type of packet received (syncronous or not).
     * @param command type command received.
     * @param info Information about message.
     */
    public ParamMotor(boolean sync, int command, Information info) {
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
        this.in = AbstractFrame.floatToByteArray(in, 0, kAng);
        this.in = AbstractFrame.floatToByteArray(in, 4, kVel);
        this.in = AbstractFrame.booleanToByteArray(in, 8, encoderSwap);
        this.in = AbstractFrame.booleanToByteArray(in, 9, enable);
    }

    /**
     * Get the value of encoderSwap
     *
     * @return the value of encoderSwap
     */
    public boolean isEncoderSwap() {
        return encoderSwap;
    }

    /**
     * Set the value of encoderSwap
     *
     * @param encoderSwap new value of encoderSwap
     */
    public void setEncoderSwap(boolean encoderSwap) {
        this.encoderSwap = encoderSwap;
        buildData();
    }

    /**
     * Get the value of enable
     *
     * @return the value of enable
     */
    public boolean isEnable() {
        return enable;
    }

    /**
     * Set the value of enable
     *
     * @param enable new value of enable
     */
    public void setEnable(boolean enable) {
        this.enable = enable;
        buildData();
    }

    /**
     * Get the value of kVel
     *
     * @return the value of kVel
     */
    public float getkVel() {
        return kVel;
    }

    /**
     * Set the value of kVel
     *
     * @param kVel new value of kVel
     */
    public void setkVel(float kVel) {
        this.kVel = kVel;
        buildData();
    }

    /**
     * Get the value of kAng
     *
     * @return the value of kAng
     */
    public float getkAng() {
        return kAng;
    }

    /**
     * Set the value of kAng
     *
     * @param kAng new value of kAng
     */
    public void setkAng(float kAng) {
        this.kAng = kAng;
        buildData();
    }

}
