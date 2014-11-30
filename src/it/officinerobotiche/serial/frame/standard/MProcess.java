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

        public int getFrequency() {
            return 1;
        }

        @Override
        public Command getCommand() {
            return Command.FRQ_PROCESS;
        }
    }
    
    public MProcess() {
        super();
    }

    public MProcess(boolean sync, byte[] in) {
        super(sync, in);
    }
    
    public MProcess(boolean sync, Information info) {
        super(sync, info);
    }

}
