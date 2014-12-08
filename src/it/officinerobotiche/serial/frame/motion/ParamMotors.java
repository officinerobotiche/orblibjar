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
public class ParamMotors extends MotionFrame {

    public static final ParamMotors PARAM_MOTORS = new ParamMotors();
    
    private static final int LNG_PARAM_MOTOR = 4*2+4+4*2+4*2+4+2;
    private float[] radius = new float[2];
    private float wheelBase;
    private float[] kVel = new float[2];
    private float[] kAng = new float[2];
    private float spMin;
    private int pwmStep;

    public ParamMotors() {
        super();
        this.in = new byte[LNG_PARAM_MOTOR];
    }

    public ParamMotors(boolean sync, int command, byte[] in) {
        super(sync, command, in);
        this.radius = AbstractFrame.byteArrayToFloatArray(in, 0, 2);
        this.wheelBase = AbstractFrame.byteArrayToFloat(in, 8);
        this.kVel = AbstractFrame.byteArrayToFloatArray(in, 12, 2);
        this.kAng = AbstractFrame.byteArrayToFloatArray(in, 20, 2);
        this.spMin = AbstractFrame.byteArrayToFloat(in, 28);
        this.pwmStep = AbstractFrame.byteArrayToInt(in, 32);
    }

    public ParamMotors(boolean sync, int command, Information info) {
        super(sync, command, info);
    }

    @Override
    public Command getCommand() {
        return Command.PARAM_MOTORS;
    }

    @Override
    protected void buildData() {
        this.in = AbstractFrame.floatArrayToByteArray(in, 0, this.radius);
        this.in = AbstractFrame.floatToByteArray(in, 8, this.wheelBase);
        this.in = AbstractFrame.floatArrayToByteArray(in, 12, this.kVel);
        this.in = AbstractFrame.floatArrayToByteArray(in, 20, this.kAng);
        this.in = AbstractFrame.floatToByteArray(in, 28, this.spMin);
        this.in = AbstractFrame.intToByteArray(in, 32, this.pwmStep);
    }

    /**
     * Get the value of pwmStep
     *
     * @return the value of pwmStep
     */
    public int getPwmStep() {
        return pwmStep;
    }

    /**
     * Set the value of pwmStep
     *
     * @param pwmStep new value of pwmStep
     */
    public void setPwmStep(int pwmStep) {
        this.pwmStep = pwmStep;
        buildData();
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
     * Get the value of kAng
     *
     * @return the value of kAng
     */
    public float[] getkAng() {
        return kAng;
    }

    /**
     * Set the value of kAng
     *
     * @param kAng new value of kAng
     */
    public void setkAng(float[] kAng) {
        this.kAng = kAng;
        buildData();
    }

    /**
     * Get the value of kAng at specified index
     *
     * @param index the index of kAng
     * @return the value of kAng at specified index
     */
    public float getkAng(int index) {
        return this.kAng[index];
    }

    /**
     * Set the value of kAng at specified index.
     *
     * @param index the index of kAng
     * @param kAng new value of kAng at specified index
     */
    public void setkAng(int index, float kAng) {
        this.kAng[index] = kAng;
        buildData();
    }

    /**
     * Get the value of kVel
     *
     * @return the value of kVel
     */
    public float[] getkVel() {
        return kVel;
    }

    /**
     * Set the value of kVel
     *
     * @param kVel new value of kVel
     */
    public void setkVel(float[] kVel) {
        this.kVel = kVel;
        buildData();
    }

    /**
     * Get the value of kVel at specified index
     *
     * @param index the index of kVel
     * @return the value of kVel at specified index
     */
    public float getkVel(int index) {
        return this.kVel[index];
    }

    /**
     * Set the value of kVel at specified index.
     *
     * @param index the index of kVel
     * @param kVel new value of kVel at specified index
     */
    public void setkVel(int index, float kVel) {
        this.kVel[index] = kVel;
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
