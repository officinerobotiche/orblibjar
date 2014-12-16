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
 * Message for definiton contraint for velocity controller. Max velocity for
 * left and right motor. Used IS to configure this data, in particular rad to
 * sec.
 *
 * @author Raffaello Bonghi
 */
public class Constraint extends MotionFrame {

    /**
     * Quick definition for constraint frame message. This is usefull on require
     * a information on board.
     */
    public static final Constraint CONSTRAINT = new Constraint();

    /**
     * Length of constraint frame. Two floats for max number velocity.
     */
    public static final int LNG_CONSTRAINT = 4 * 2;

    /**
     * Definition variables, for left and right constraint.
     */
    private float maxLeft, maxRight;

    /**
     * Initialize constraint message. This Constructor is used for require data
     * from board.
     */
    public Constraint() {
        super();
        this.in = new byte[LNG_CONSTRAINT];
    }

    /**
     * Initialize constraint message with max left and right value. This message
     * is a message with data.
     *
     * @param maxLeft value for left constraint in rad to sec.
     * @param maxRight value for right constraint in rad to sec.
     */
    public Constraint(float maxLeft, float maxRight) {
        super();
        this.in = new byte[LNG_CONSTRAINT];
        this.maxLeft = maxLeft;
        this.maxRight = maxRight;
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
    public Constraint(boolean sync, int command, byte[] in) {
        super(sync, command, in);
        this.maxLeft = AbstractFrame.byteArrayToFloat(in, 0);
        this.maxRight = AbstractFrame.byteArrayToFloat(in, 4);
    }

    /**
     * Initialize message with ACK, NACK information. This message is used
     * normally from parser.
     *
     * @param sync type of packet received (syncronous or not).
     * @param command type command received.
     * @param info Information about message.
     */
    public Constraint(boolean sync, int command, Information info) {
        super(sync, command, info);
    }

    /**
     * Construct byte data array with information on object.
     */
    @Override
    protected final void buildData() {
        this.in = AbstractFrame.floatToByteArray(in, 0, maxLeft);
        this.in = AbstractFrame.floatToByteArray(in, 4, maxRight);
    }

    /**
     * Command associated at this object. This is a constant enumeration.
     *
     * @return Enumeration command.
     */
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
