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

/**
 *
 * @author Raffaello Bonghi
 */
public abstract class MProcess extends StandardFrame {

    public static class Time extends MProcess {

        public Time() {
            super();
        }

        public Time(boolean sync, int command, byte[] in) {
            super(sync, command, in);
        }

        public Time(boolean sync, int command, Information info) {
            super(sync, command, info);
        }

        public int getIdle() {
            return idle;
        }

        public int getParse_packet() {
            return parse_packet;
        }

        @Override
        public Command getCommand() {
            return Command.TIME_PROCESS;
        }
    }

    public static class Priority extends MProcess {

        public Priority() {
            super();
        }

        public Priority(boolean sync, int command, byte[] in) {
            super(sync, command, in);
        }

        public Priority(boolean sync, int command, Information info) {
            super(sync, command, info);
        }

        public int getParse_packet() {
            return parse_packet;
        }

        @Override
        public Command getCommand() {
            return Command.PRIORITY_PROCESS;
        }

    }

    public static class Frequency extends MProcess {

        public Frequency() {
            super();
        }

        public Frequency(boolean sync, int command, byte[] in) {
            super(sync, command, in);
        }

        public Frequency(boolean sync, int command, Information info) {
            super(sync, command, info);
        }

        @Override
        public Command getCommand() {
            return Command.FRQ_PROCESS;
        }
    }

    protected int idle;
    protected int parse_packet;
    private int[] processes;

    public MProcess() {
        super();
    }

    public MProcess(boolean sync, int command, byte[] in) {
        super(sync, command, in);
        processes = new int[in[0]];
        idle = in[1];
        parse_packet = in[2];
        for (int i = 0; i < processes.length; i++) {
            processes[i] = in[i + 3];
        }
    }

    public MProcess(boolean sync, int command, Information info) {
        super(sync, command, info);
    }

    public int[] getProcesses() {
        return processes;
    }

}
