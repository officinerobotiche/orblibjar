/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.officinerobotiche.message.standard;

import it.officinerobotiche.message.Jmessage;

/**
 *
 * @author Raffaello
 */
public class MicroProcess extends Jmessage {

    public enum TypeCommand {
        
        TIME(1),
        PRIORITY(2),
        FREQUENCY(3);
        
        private final int number;
        
        private TypeCommand(int number) {
            this.number = number;
        }
        
        public byte getNumber() {
            return (byte) number;
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
    private final byte type;
    
    public MicroProcess(byte command, byte[] data) {
        type = Jmessage.Type.DATA.getName();
        this.command = command;
        this.data = data;
    }

    public MicroProcess(TypeCommand command) {
        type = Jmessage.Type.REQUEST.getName();
        this.command = command.getNumber();
        this.data = null;
    }

    @Override
    public byte getLength() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public byte getType() {
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

}
