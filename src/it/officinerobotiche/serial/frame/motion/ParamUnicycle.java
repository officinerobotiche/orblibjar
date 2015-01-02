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
 * Definition parameters on robots. You can set radius and wheelbase on unicycle
 * robot, in particular meter, radiand, radians to sec.
 *
 * @author Raffaello Bonghi
 */
public class ParamUnicycle extends MotionFrame {

    /**
     * Quick definition for parameters unicycle frame message. This is usefull on
     * require a information on board.
     */
    public static final ParamUnicycle PARAM_UNICYCLE = new ParamUnicycle();

    /**
     * Length of velocity frame. Two floats for all data.
     */
    private static final int LNG_PARAM_MOTOR = 4 * 4;

    /**
     * Radius wheel. First is left wheel, secondo right wheel.
     */
    private float[] radius = new float[2];

    /**
     * Wheelbase robot.
     */
    private float wheelBase;

    /**
     * Minimum value to set odometry in "linear movement".
     */
    private float spMin;

    /**
     * Initialize parameters unicycle message. This Constructor is used for
     * require data from board.
     */
    public ParamUnicycle() {
        super();
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
    public ParamUnicycle(boolean sync, int command, byte[] in) {
        super(sync, command, in);
        this.radius = AbstractFrame.byteArrayToFloatArray(in, 0, 2);
        this.wheelBase = AbstractFrame.byteArrayToFloat(in, 8);
        this.spMin = AbstractFrame.byteArrayToFloat(in, 12);
    }

    /**
     * Initialize message with ACK, NACK information. This message is used
     * normally from parser.
     *
     * @param sync type of packet received (syncronous or not).
     * @param command type command received.
     * @param info Information about message.
     */
    public ParamUnicycle(boolean sync, int command, Information info) {
        super(sync, command, info);
    }

    @Override
    public Command getCommand() {
        return Command.PARAM_UNICYCLE;
    }

    /**
     * Construct byte data array with information on object.
     */
    @Override
    protected void buildData() {
        this.in = AbstractFrame.floatArrayToByteArray(in, 0, this.radius);
        this.in = AbstractFrame.floatToByteArray(in, 8, this.wheelBase);
        this.in = AbstractFrame.floatToByteArray(in, 12, this.spMin);
    }

    /**
     * Get the value of spMin
     *
     * @return the value of spMin
     */
    public float getSpMin() {
        return spMin;
    }

    /**
     * Set the value of spMin
     *
     * @param spMin new value of spMin
     */
    public void setSpMin(float spMin) {
        this.spMin = spMin;
        buildData();
    }

    /**
     * Get the value of wheelBase
     *
     * @return the value of wheelBase
     */
    public float getWheelBase() {
        return wheelBase;
    }

    /**
     * Set the value of wheelBase
     *
     * @param wheelBase new value of wheelBase
     */
    public void setWheelBase(float wheelBase) {
        this.wheelBase = wheelBase;
        buildData();
    }

    /**
     * Get the value of radius
     *
     * @return the value of radius
     */
    public float[] getRadius() {
        return radius;
    }

    /**
     * Set the value of radius
     *
     * @param radius new value of radius
     */
    public void setRadius(float[] radius) {
        this.radius = radius;
        buildData();
    }

    /**
     * Get the value of radius at specified index
     *
     * @param index the index of radius
     * @return the value of radius at specified index
     */
    public float getRadius(int index) {
        return this.radius[index];
    }

    /**
     * Set the value of radius at specified index.
     *
     * @param index the index of radius
     * @param radius new value of radius at specified index
     */
    public void setRadius(int index, float radius) {
        this.radius[index] = radius;
        buildData();
    }

}
