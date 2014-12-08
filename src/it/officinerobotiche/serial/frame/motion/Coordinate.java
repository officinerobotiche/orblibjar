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
public class Coordinate extends MotionFrame {
    
    public static final Coordinate COORDINATE = new Coordinate();

    private final static int LNG_COORD = 4 * 4;
    protected float x, y, theta;
    protected float space = 0;

    private Coordinate() {
        super();
        this.in = new byte[LNG_COORD];
    }

    public Coordinate(float x, float y, float theta) {
        super();
        this.in = new byte[LNG_COORD];
        this.x = x;
        this.y = y;
        this.theta = theta;
        buildData();
    }

    public Coordinate(boolean sync, int command, byte[] in) {
        super(sync, command, in);
        this.x = AbstractFrame.byteArrayToFloat(in, 0);
        this.y = AbstractFrame.byteArrayToFloat(in, 4);
        this.theta = AbstractFrame.byteArrayToFloat(in, 8);
        this.space = AbstractFrame.byteArrayToFloat(in, 12);
    }

    public Coordinate(boolean sync, int command, Information info) {
        super(sync, command, info);
    }

    @Override
    public Command getCommand() {
        return Command.COORDINATE;
    }
    
    @Override
    protected void buildData() {
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
