/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.officinerobotiche.serial.frame.unav;

import it.officinerobotiche.serial.frame.AbstractFrame;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Raffaello
 */
public abstract class UnavFrame extends AbstractFrame {

    public enum Command implements ICommand {

        PID_L(0, "PIDLeft"),
        PID_R(1, "PIDRight");

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

    public UnavFrame() {
        super();
    }

    public UnavFrame(boolean sync, byte[] in) {
        super(sync, in);
    }

    public UnavFrame(boolean sync, Information info) {
        super(sync, info);
    }

    @Override
    public TypeMessage getTypeMessage() {
        return TypeMessage.UNAV;
    }

    @Override
    abstract public Command getCommand();
}
