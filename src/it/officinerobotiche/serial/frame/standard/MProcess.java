/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.officinerobotiche.serial.frame.standard;

/**
 *
 * @author Raffaello
 */
public abstract class MProcess extends StandardFrame {

    public static class Time extends MProcess {

        public Time() {
            super();
        }

        public Time(boolean sync, byte[] in) {
            super(sync, in);
        }

        public Time(boolean sync, Information info) {
            super(sync, info);
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

        public Priority(boolean sync, byte[] in) {
            super(sync, in);
        }

        public Priority(boolean sync, Information info) {
            super(sync, info);
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

        public Frequency(boolean sync, byte[] in) {
            super(sync, in);
        }

        public Frequency(boolean sync, Information info) {
            super(sync, info);
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

    public MProcess(boolean sync, byte[] in) {
        super(sync, in);
        processes = new int[in[0]];
        idle = in[1];
        parse_packet = in[2];
        for (int i = 0; i < processes.length; i++) {
            processes[i] = in[i + 3];
        }
    }

    public MProcess(boolean sync, Information info) {
        super(sync, info);
    }

    public int[] getProcesses() {
        return processes;
    }

}
