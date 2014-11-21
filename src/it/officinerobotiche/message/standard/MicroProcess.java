/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.officinerobotiche.message.standard;

import it.officinerobotiche.message.Jmessage;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Raffaello
 */
public class MicroProcess extends Jmessage {

    public static enum TypeCommand implements Command {

        TIME(1),
        PRIORITY(2),
        FREQUENCY(3);

        private final byte number;

        private TypeCommand(int number) {
            this.number = (byte) number;
        }

        @Override
        public byte getNumber() {
            return number;
        }

        private static final Map<Byte, TypeCommand> lookup = new HashMap<>();

        static {
            for (TypeCommand s : EnumSet.allOf(TypeCommand.class)) {
                lookup.put(s.getNumber(), s);
            }
        }
        
        public static TypeCommand get(byte Value) { 
        //the reverse lookup by simply getting 
        //the value from the lookup HsahMap. 
          return lookup.get(Value); 
     }
        
    }

    /**
     * type packet: * (D) Default messages (in top on this file) * other type
     * messages (in UNAV file)
     */
    public final static byte TYPE_MESSAGE = (byte) 'D';
    /**
     * command message
     */
    public final static byte[] ALL_COMMANDS = {TypeCommand.TIME.getNumber(),
        TypeCommand.PRIORITY.getNumber(),
        TypeCommand.FREQUENCY.getNumber()};

    private final byte[] data;
    private final byte command;
    private final Type type;

    public MicroProcess(byte command, byte[] data) {
        type = Jmessage.Type.DATA;
        this.command = command;
        this.data = data;
    }

    public MicroProcess(TypeCommand command) {
        type = Jmessage.Type.REQUEST;
        this.command = command.getNumber();
        this.data = null;
    }

    public MicroProcess(Type type, TypeCommand command) {
        this.type = type;
        this.command = command.getNumber();
        this.data = null;
    }

    @Override
    public boolean isACK() {
        return type.equals(Jmessage.Type.ACK) || type.equals(Jmessage.Type.NACK);
    }

    @Override
    public byte getLength() {
        int length = 0;
        if (data != null) {
            length = data.length;
        }
        return (byte) (Jmessage.LNG_HEADER + length);
    }

    @Override
    public Type getType() {
        return type;
    }

    @Override
    public byte getTypeMessage() {
        return TYPE_MESSAGE;
    }

    @Override
    public byte getCommand() {
        return command;
    }

    @Override
    public byte[] getData() {
        return data;
    }

    @Override
    public String toString() {
        return "MicroProcess{" + "data=" + data + ", command=" + command + ", type=" + type + '}';
    }

}
