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
 *
 * @author Raffaello Bonghi
 */
public class Emergency extends MotionFrame {

    /**
     * Initialize Emergency message. This Constructor is used for require data
     * from board.
     */
    public Emergency() {
        super();
    }

    /**
     * Initialize message with data received from board. This message is used
     * normally from parser to set data received, it is a message with data.
     *
     * @param sync type of packet received (syncronous or not).
     * @param command type command received.
     * @param in byte received.
     */
    public Emergency(boolean sync, int command, byte[] in) {
        super(sync, command, in);
    }

    /**
     * Initialize message with ACK, NACK information. This message is used
     * normally from parser.
     *
     * @param sync type of packet received (syncronous or not).
     * @param command type command received.
     * @param info Information about message.
     */
    public Emergency(boolean sync, int command, Information info) {
        super(sync, command, info);
    }

    /**
     * Command associated at this object. This is a constant enumeration.
     *
     * @return Enumeration command.
     */
    @Override
    public Command getCommand() {
        return Command.EMERGENCY;
    }

    /**
     * Construct byte data array with information on object.
     */
    @Override
    protected final void buildData() {

    }

}
