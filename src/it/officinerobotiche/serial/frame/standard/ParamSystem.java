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
 * Definition to require Parameter setting on board.
 *
 * @author Raffaello Bonghi
 */
public class ParamSystem extends StandardFrame {

    /**
     * Quick definition for parameter on board frame message. This is usefull on
     * require a information on board.
     */
    public static final ParamSystem PARAM_SYSTEM = new ParamSystem();

    /**
     * number of step on timer.
     */
    private int stepTimer;

    /**
     * Frequency timer in millisecond.
     */
    private int timeMill;

    /**
     * Initialize Parameter System message. This Constructor is used for require
     * data from board.
     */
    public ParamSystem() {
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
    public ParamSystem(boolean sync, int command, byte[] in) {
        super(sync, command, in);
        stepTimer = AbstractFrame.byteArrayToInt(in, 0);
        timeMill = AbstractFrame.byteArrayToInt(in, 2);
    }

    /**
     * Initialize message with ACK, NACK information. This message is used
     * normally from parser.
     *
     * @param sync type of packet received (syncronous or not).
     * @param command type command received.
     * @param info Information about message.
     */
    public ParamSystem(boolean sync, int command, Information info) {
        super(sync, command, info);
    }

    @Override
    public Command getCommand() {
        return Command.PARAMETER_SYSTEM;
    }

    /**
     * Get the value of timer step.
     *
     * @return the value of timer step.
     */
    public int getStepTimer() {
        return stepTimer;
    }

    /**
     * Get the value of timer in millisecond.
     *
     * @return the value of timer in millisecond.
     */
    public int getTimeMill() {
        return timeMill;
    }
}
