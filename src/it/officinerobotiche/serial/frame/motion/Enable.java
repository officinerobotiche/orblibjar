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

/**
 * Defintion for enable message. You can set or read state of bridge motor.
 *
 * @author Raffaello Bonghi
 */
public class Enable extends MotionFrame {

    /**
     * Quick definition for coordinate frame message. This is usefull on require
     * a information on board.
     */
    public static final Enable ENABLE = new Enable();

    /**
     * Definition for enable information bridge motor.
     */
    private boolean enable;

    /**
     * Initialize enable message. This Constructor is used for require data from
     * board.
     */
    public Enable() {
        super();
        this.in = new byte[1];
    }

    /**
     * Initialize message with configuration enable bridge motor. This message
     * is a message with data.
     *
     * @param enable state of bridge motor.
     */
    public Enable(boolean enable) {
        super();
        this.in = new byte[1];
        this.enable = enable;
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
    public Enable(boolean sync, int command, byte[] in) {
        super(sync, command, in);
        this.enable = (in[0] == 1);
    }

    /**
     * Initialize message with ACK, NACK information. This message is used
     * normally from parser.
     *
     * @param sync type of packet received (syncronous or not).
     * @param command type command received.
     * @param info Information about message.
     */
    public Enable(boolean sync, int command, Information info) {
        super(sync, command, info);
    }

    /**
     * Command associated at this object. This is a constant enumeration.
     *
     * @return Enumeration command.
     */
    @Override
    public Command getCommand() {
        return Command.ENABLE;
    }

    /**
     * Construct byte data array with information on object.
     */
    @Override
    protected void buildData() {
        this.in[0] = (byte) (this.enable ? 1 : 0);
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

}
