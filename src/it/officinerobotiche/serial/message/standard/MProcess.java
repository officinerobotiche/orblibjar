/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.officinerobotiche.serial.message.standard;

/**
 *
 * @author Raffaello
 */
public abstract class MProcess extends StandardFrame {

    public static class Time extends MProcess {
        
        public Time() {
            super();
        }

        public Time(byte[] in) {
            super(in);
        }
        
        public Time(Information info) {
            super(info);
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

        public Priority(byte[] in) {
            super(in);
        }
        
        public Priority(Information info) {
            super(info);
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

        public Frequency(byte[] in) {
            super(in);
        }
        
        public Frequency(Information info) {
            super(info);
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

    public MProcess(byte[] in) {
        super(in);
    }
    
    public MProcess(Information info) {
        super(info);
    }

}
