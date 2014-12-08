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
public class Constraint extends MotionFrame {
    
    public static final Constraint CONSTRAINT = new Constraint();

    public static final int LNG_CONSTRAINT = 4*2;
    private float maxLeft, maxRight;

    public Constraint() {
        super();
        this.in = new byte[LNG_CONSTRAINT];
    }
    
    public Constraint(float maxLeft, float maxRight) {
        super();
        this.in = new byte[LNG_CONSTRAINT];
        this.maxLeft = maxLeft;
        this.maxRight = maxRight;
        buildData();
    }

    public Constraint(boolean sync, int command, byte[] in) {
        super(sync, command, in);
        this.maxLeft = AbstractFrame.byteArrayToFloat(in, 0);
        this.maxRight = AbstractFrame.byteArrayToFloat(in, 4);
    }

    public Constraint(boolean sync, int command, Information info) {
        super(sync, command, info);
    }
    
    @Override
    protected final void buildData() {
        this.in = AbstractFrame.floatToByteArray(in, 0, maxLeft);
        this.in = AbstractFrame.floatToByteArray(in, 4, maxRight);
    }

    @Override
    public Command getCommand() {
        return Command.CONSTRAINT;
    }

    /**
     * Get the value of maxRight
     *
     * @return the value of maxRight
     */
    public float getMaxRight() {
        return maxRight;
    }

    /**
     * Set the value of maxRight
     *
     * @param maxRight new value of maxRight
     */
    public void setMaxRight(float maxRight) {
        this.maxRight = maxRight;
        buildData();
    }

    /**
     * Get the value of maxLeft
     *
     * @return the value of maxLeft
     */
    public float getMaxLeft() {
        return maxLeft;
    }

    /**
     * Set the value of maxLeft
     *
     * @param maxLeft new value of maxLeft
     */
    public void setMaxLeft(float maxLeft) {
        this.maxLeft = maxLeft;
        buildData();
    }

}
