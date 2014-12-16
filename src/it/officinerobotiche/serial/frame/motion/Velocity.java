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
 * Definition to set velocity to robot or read velocity measured. Used IS to
 * configure this data, in particular meter to sec and radiand to sec.
 *
 * @author Raffaello Bonghi
 */
public class Velocity extends MotionFrame {

    /**
     * Quick definition for reference velocity frame message. This is usefull on
     * require a information on board.
     */
    public static final Velocity VELOCITY = new Velocity(Command.VELOCITY);

    /**
     * Quick definition for measured velocity frame message. This is usefull on
     * require a information on board.
     */
    public static final Velocity VELOCITY_MIS = new Velocity(Command.VELOCITY_MIS);

    /**
     * Length of velocity frame. Two floats for all data.
     */
    private final static int LNG_VELOCITY = 2 * 4;

    /**
     * Linear velocity.
     */
    private float linear;

    /**
     * Angular velocity.
     */
    private float angular;

    /**
     * Set type of command received.
     */
    private final Command comm;

    /**
     * Initialize velocity message. This Constructor is used for require data
     * from board.
     */
    private Velocity(Command comm) {
        super();
        this.comm = comm;
    }

    /**
     * Initialize velocity message with linear and angular velocity. This
     * Constructor is used for require data from board.
     *
     * @param lin linear velocity.
     * @param ang angular velocity.
     */
    public Velocity(float lin, float ang) {
        this.information = Information.REQUEST;
        this.comm = Command.VELOCITY;
        this.in = new byte[LNG_VELOCITY];
        this.linear = lin;
        this.angular = ang;
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
    public Velocity(boolean sync, int command, byte[] in) {
        super(sync, command, in);
        this.comm = Command.getCommand(command);
        this.linear = AbstractFrame.byteArrayToFloat(in, 0);
        this.angular = AbstractFrame.byteArrayToFloat(in, 4);
    }

    /**
     * Initialize message with ACK, NACK information. This message is used
     * normally from parser.
     *
     * @param sync type of packet received (syncronous or not).
     * @param command type command received.
     * @param info Information about message.
     */
    public Velocity(boolean sync, int command, Information info) {
        super(sync, command, info);
        this.comm = Command.getCommand(command);
    }

    /**
     * Construct byte data array with information on object.
     */
    @Override
    protected final void buildData() {
        this.in = AbstractFrame.floatToByteArray(in, 0, this.linear);
        this.in = AbstractFrame.floatToByteArray(in, 4, this.angular);
    }

    /**
     * Set the value of linear velocity
     *
     * @param linear new value of linear velocity
     * @return If the message is for reference message.
     */
    public boolean setLinear(float linear) {
        if (comm.equals(Command.VELOCITY)) {
            this.linear = linear;
            buildData();
            return true;
        } else {
            return false;
        }
    }

    /**
     * Set the value of angular velocity
     *
     * @param angular new value of angular velocity
     * @return If the message is for reference message.
     */
    public boolean setAngular(float angular) {
        if (comm.equals(Command.VELOCITY)) {
            this.angular = angular;
            buildData();
            return true;
        } else {
            return false;
        }
    }

    /**
     * Get the value of linear velocity
     *
     * @return the value of linear velocity
     */
    public float getLinear() {
        return linear;
    }

    /**
     * Get the value of angular velocity
     *
     * @return the value of angular velocity
     */
    public float getAngular() {
        return angular;
    }

    @Override
    public Command getCommand() {
        return comm;
    }
}
