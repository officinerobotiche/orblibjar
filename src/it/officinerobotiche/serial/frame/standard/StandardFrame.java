/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.officinerobotiche.serial.frame.standard;

import it.officinerobotiche.serial.frame.AbstractFrame;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Raffaello
 */
public abstract class StandardFrame extends AbstractFrame {

    public enum Command implements ICommand {

        SERVICES(0, "Service"),
        TIME_PROCESS(1, "Time"),
        PRIORITY_PROCESS(2, "Priority"),
        FRQ_PROCESS(3, "Frequency"),
        PARAMETER_SYSTEM(4, "ParamSystem"),
        ERROR_SERIAL(5, "ErrorSerial"),
        NAME_PROCESS(6, "NameProcess");

        private final int number;
        private final String name;
        private static final Map<Integer, String> lookup = new HashMap<>();

        private Command(int number, String name) {
            this.number = number;
            this.name = name;
        }

        @Override
        public int getNumber() {
            return number;
        }
        
        @Override
        public byte getByte() {
            return (byte) number;
        }

        @Override
        public String getName() {
            return name;
        }

        static {
            for (Command s : EnumSet.allOf(Command.class)) {
                lookup.put(s.getNumber(), s.getName());
            }
        }

        public static String getCommand(int number) {
            return lookup.get(number);
        }
    };

    public StandardFrame() {
        super();
    }

    public StandardFrame(byte[] in) {
        super(in);
    }
    
    public StandardFrame(Information info) {
        super(info);
    }

    @Override
    public TypeMessage getTypeMessage() {
        return TypeMessage.DEFAULT;
    }

    @Override
    abstract public Command getCommand();
}
