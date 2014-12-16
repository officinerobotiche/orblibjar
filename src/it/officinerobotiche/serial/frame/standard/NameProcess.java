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
package it.officinerobotiche.serial.frame.standard;

import it.officinerobotiche.serial.frame.AbstractFrame;

/**
 * Definition to receive Name process on board.
 *
 * @author Raffaello Bonghi
 */
public class NameProcess extends StandardFrame {

    /**
     * Quick definition for LENGTH name processes frame message. This is usefull
     * on require a information on board.
     */
    public final NameProcess LENGTH = new NameProcess(REQUIRE_LENGTH);

    /**
     * Number to require length for all processes.
     */
    private static int REQUIRE_LENGTH = -1;

    /**
     * Length buffer process.
     */
    private static int BUFF_NAME_PROCESS = 20;

    /**
     * Number process or length all process on board.
     */
    private int number;

    /**
     * Name board.
     */
    private String name;

    /**
     * Initialize Name process message. This Constructor is used for require
     * data from board.
     *
     * @param number Number of process associated.
     */
    public NameProcess(int number) {
        this.information = Information.REQUEST;
        this.number = number;
        this.in = new byte[BUFF_NAME_PROCESS + 2];
        this.in = AbstractFrame.intToByteArray(in, 0, number);
    }

    /**
     * Initialize message with data received from board. This message is used
     * normally from parser to set data received, it is a message with data.
     *
     * @param sync type of packet received (syncronous or not).
     * @param command type command received.
     * @param in byte received.
     */
    public NameProcess(boolean sync, int command, byte[] in) {
        super(sync, command, in);
        this.number = AbstractFrame.byteArrayToInt(in, 0);
        if (number != REQUIRE_LENGTH) {
            this.name = AbstractFrame.getString(in, 2);
        } else {
            this.name = null;
        }
    }

    /**
     * Initialize message with ACK, NACK information. This message is used
     * normally from parser.
     *
     * @param sync type of packet received (syncronous or not).
     * @param command type command received.
     * @param info Information about message.
     */
    public NameProcess(boolean sync, int command, Information info) {
        super(sync, command, info);
    }

    @Override
    public Command getCommand() {
        return Command.NAME_PROCESS;
    }

    /**
     * Get the value of number process or length of all processes
     *
     * @return the value of number process or length of all processes
     */
    public int getNumber() {
        return number;
    }

    /**
     * Get the value of name process
     *
     * @return the value of name process
     */
    public String getName() {
        return name;
    }

}
