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
 * Definition to receive time information for all process on board or set and
 * read frequency and priority on board.
 *
 * @author Raffaello Bonghi
 */
public class MProcess extends StandardFrame {

    /**
     * Quick definition for Frequency frame message. This is usefull on require
     * a information on board.
     */
    public static final MProcess.Frequency FREQUENCY = new MProcess.Frequency();

    /**
     * Quick definition for Priority frame message. This is usefull on require a
     * information on board.
     */
    public static final MProcess.Priority PRIORITY = new MProcess.Priority();

    /**
     * Quick definition for Time frame message. This is usefull on require a
     * information on board.
     */
    public static final MProcess TIME = new MProcess(Command.TIME_PROCESS);

    /**
     * Class definition for priority message.
     */
    public static class Priority extends MProcess {

        /**
         * Initialize priority message. This Constructor is used for require
         * data from board.
         */
        public Priority() {
            super(Command.PRIORITY_PROCESS);
        }
    }

    /**
     * Class definition for frequency message.
     */
    public static class Frequency extends MProcess {

        /**
         * Initialize frequency message. This Constructor is used for require
         * data from board.
         */
        private Frequency() {
            super(Command.FRQ_PROCESS);
        }
    }

    /**
     * Set type of command received.
     */
    private final Command comm;

    /**
     * Idle time process.
     */
    protected int idle;

    /**
     * Parsing packet time process.
     */
    protected int parse_packet;

    /**
     * List of all processes on board.
     */
    private int[] processes;

    /**
     * Initialize process message. This Constructor is used for require data
     * from board.
     */
    private MProcess(Command comm) {
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
    public MProcess(boolean sync, int command, byte[] in) {
        super(sync, command, in);
        processes = new int[AbstractFrame.byteArrayToInt(in, 0)];
        idle = AbstractFrame.byteArrayToInt(in, 2);
        parse_packet = AbstractFrame.byteArrayToInt(in, 4);
        for (int i = 0; i < processes.length; i++) {
            processes[i] = AbstractFrame.byteArrayToInt(in, (2 * i) + 6);
        }
        this.comm = Command.getCommand(command);
    }

    /**
     * Initialize message with ACK, NACK information. This message is used
     * normally from parser.
     *
     * @param sync type of packet received (syncronous or not).
     * @param command type command received.
     * @param info Information about message.
     */
    public MProcess(boolean sync, int command, Information info) {
        super(sync, command, info);
        this.comm = Command.getCommand(command);
    }

    /**
     * Get list of all processes
     *
     * @return the list of all processes
     */
    public int[] getProcesses() {
        return processes;
    }

    /**
     * Get the value of idle
     *
     * @return the value of idle
     */
    public int getIdle() {
        return idle;
    }

    /**
     * Get the value of parse packet
     *
     * @return the value of parse packet
     */
    public int getParse_packet() {
        return parse_packet;
    }

    @Override
    public Command getCommand() {
        return comm;
    }
}
