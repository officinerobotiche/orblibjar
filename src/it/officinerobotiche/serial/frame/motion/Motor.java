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
 * Definition to read data from board about motors. You can read reference set
 * from board, control set from PID and velocity measured from motors. The
 * messages read are in millirad to sec.
 *
 * @author Raffaello Bonghi
 */
public class Motor extends MotionFrame {

    /**
     * Quick definition for Motor LEFT frame message. This is usefull on require
     * a information on board.
     */
    public static final Motor LEFT = new Motor(Command.MOTOR_L);

    /**
     * Quick definition for Motor RIGHT frame message. This is usefull on
     * require a information on board.
     */
    public static final Motor RIGHT = new Motor(Command.MOTOR_R);

    /**
     * Set type of command received.
     */
    private final Command comm;

    /**
     * Value saved from Motor message about reference velocity set.
     */
    protected int riferVel;

    /**
     * Value saved from Motor message about control velocity set.
     */
    protected int controlVel;

    /**
     * Value saved from Motor message about measured velocity read.
     */
    protected int measureVel;

    /**
     * Value saved from Motor message about current used from motor.
     */
    protected int current;

    /**
     * Initialize Motor message. This Constructor is used for require data from
     * board.
     */
    private Motor(Command comm) {
        super();
        this.comm = comm;
    }

    /**
     * Initialize message with data received from board. This message is used
     * normally from parser to set data received, it is a message with data.
     *
     * @param sync type of packet received (syncronous or not).
     * @param command type command received.
     * @param in byte received.
     */
    public Motor(boolean sync, int command, byte[] in) {
        super(sync, command, in);
        this.comm = Command.getCommand(command);
        this.riferVel = AbstractFrame.byteArrayToInt(in, 0);
        this.controlVel = AbstractFrame.byteArrayToInt(in, 2);
        this.measureVel = AbstractFrame.byteArrayToInt(in, 4);
        this.current = AbstractFrame.byteArrayToInt(in, 6);
    }

    /**
     * Initialize message with ACK, NACK information. This message is used
     * normally from parser.
     *
     * @param sync type of packet received (syncronous or not).
     * @param command type command received.
     * @param info Information about message.
     */
    public Motor(boolean sync, int command, Information info) {
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
    }

    /**
     * Get the value of current
     *
     * @return the value of current
     */
    public int getCurrent() {
        return current;
    }

    /**
     * Get the value of measureVel
     *
     * @return the value of measureVel
     */
    public int getMeasureVel() {
        return measureVel;
    }

    /**
     * Get the value of controlVel
     *
     * @return the value of controlVel
     */
    public int getControlVel() {
        return controlVel;
    }

    /**
     * Get the value of riferVel
     *
     * @return the value of riferVel
     */
    public int getRiferVel() {
        return riferVel;
    }

}
