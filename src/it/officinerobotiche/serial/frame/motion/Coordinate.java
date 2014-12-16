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
 * Definition for coordinate robot. 1) position [x, y, theta] 2) space. Used IS 
 * to configure this data, in particular meter and radiand.
 *
 * @author Raffaello Bonghi
 */
public class Coordinate extends MotionFrame {

    /**
     * Quick definition for coordinate frame message. This is usefull on require
     * a information on board.
     */
    public static final Coordinate COORDINATE = new Coordinate();

    /**
     * Length of coordiante frame. Four floats for all data.
     */
    private final static int LNG_COORD = 4 * 4;
    /**
     * X position.
     */
    protected float x,
            /**
             * Y position.
             */
            y,
            /**
             * Angle position.
             */
            theta;

    /**
     * Distance robot.
     */
    protected float space = 0;

    /**
     * Initialize coordinate message. This Constructor is used for require data
     * from board.
     */
    private Coordinate() {
        super();
        this.in = new byte[LNG_COORD];
    }

    /**
     * Initialiaze coordindate message with x,y,theta position robot. This
     * message is a message with data.
     *
     * @param x cartesian postion x.
     * @param y cartesian postion y.
     * @param theta angle respect x axis.
     */
    public Coordinate(float x, float y, float theta) {
        super();
        this.in = new byte[LNG_COORD];
        this.x = x;
        this.y = y;
        this.theta = theta;
        buildData();
    }

    /**
     * Initialize message with data received from board. This message is used
     * normally from parser to set data received, it is a message with data.
     *
     * @param sync type of packet received (syncronous or not).
     * @param command type command received.
     * @param in byte received.
     */
    public Coordinate(boolean sync, int command, byte[] in) {
        super(sync, command, in);
        this.x = AbstractFrame.byteArrayToFloat(in, 0);
        this.y = AbstractFrame.byteArrayToFloat(in, 4);
        this.theta = AbstractFrame.byteArrayToFloat(in, 8);
        this.space = AbstractFrame.byteArrayToFloat(in, 12);
    }

    /**
     * Initialize message with ACK, NACK information. This message is used
     * normally from parser.
     *
     * @param sync type of packet received (syncronous or not).
     * @param command type command received.
     * @param info Information about message.
     */
    public Coordinate(boolean sync, int command, Information info) {
        super(sync, command, info);
    }

    /**
     * Command associated at this object. This is a constant enumeration.
     *
     * @return Enumeration command.
     */
    @Override
    public Command getCommand() {
        return Command.COORDINATE;
    }

    /**
     * Construct byte data array with information on object.
     */
    @Override
    protected final void buildData() {
        this.in = AbstractFrame.floatToByteArray(in, 0, x);
        this.in = AbstractFrame.floatToByteArray(in, 4, y);
        this.in = AbstractFrame.floatToByteArray(in, 8, theta);
        this.in = AbstractFrame.floatToByteArray(in, 16, space);
    }

    /**
     * Get the value of space
     *
     * @return the value of space
     */
    public float getSpace() {
        return space;
    }

    /**
     * Set the value of space
     *
     * @param space new value of space
     */
    public void setSpace(float space) {
        this.space = space;
        buildData();
    }

    /**
     * Get the value of theta
     *
     * @return the value of theta
     */
    public float getTheta() {
        return theta;
    }

    /**
     * Set the value of theta
     *
     * @param theta new value of theta
     */
    public void setTheta(float theta) {
        this.theta = theta;
        buildData();
    }

    /**
     * Get the value of y
     *
     * @return the value of y
     */
    public float getY() {
        return y;
    }

    /**
     * Set the value of y
     *
     * @param y new value of y
     */
    public void setY(float y) {
        this.y = y;
        buildData();
    }

    /**
     * Get the value of x
     *
     * @return the value of x
     */
    public float getX() {
        return x;
    }

    /**
     * Set the value of x
     *
     * @param x new value of x
     */
    public void setX(float x) {
        this.x = x;
        buildData();
    }

}
