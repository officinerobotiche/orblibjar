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
 *
 * @author Raffaello Bonghi
 */
public class MProcess extends StandardFrame {

    public static final MProcess.Frequency FREQUENCY = new MProcess.Frequency();
    public static final MProcess.Priority PRIORITY = new MProcess.Priority();
    public static final MProcess TIME = new MProcess(Command.TIME_PROCESS);

    public static class Priority extends MProcess {

        public Priority() {
            super(Command.PRIORITY_PROCESS);
        }
    }

    public static class Frequency extends MProcess {

        private Frequency() {
            super(Command.FRQ_PROCESS);
        }
    }

    private final Command comm;

    protected int idle;
    protected int parse_packet;
    private int[] processes;

    private MProcess(Command comm) {
        super();
        this.comm = comm;
    }

    public MProcess(boolean sync, int command, byte[] in) {
        super(sync, command, in);
        processes = new int[AbstractFrame.byteArrayToInt(in, 0)];
        idle = AbstractFrame.byteArrayToInt(in, 2);
        parse_packet = AbstractFrame.byteArrayToInt(in, 4);
        for (int i = 0; i < processes.length; i++) {
            processes[i] = AbstractFrame.byteArrayToInt(in, (2*i) + 6);
        }
        this.comm = Command.getCommand(command);
    }

    public MProcess(boolean sync, int command, Information info) {
        super(sync, command, info);
        this.comm = Command.getCommand(command);
    }

    public int[] getProcesses() {
        return processes;
    }

    public int getIdle() {
        return idle;
    }

    public int getParse_packet() {
        return parse_packet;
    }

    @Override
    public Command getCommand() {
        return comm;
    }
}
