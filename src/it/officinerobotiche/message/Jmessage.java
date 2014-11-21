/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.officinerobotiche.message;

import java.util.ArrayList;

/**
 *
 * @author Raffaello bonghi
 */
public abstract class Jmessage {

    protected final static byte LNG_HEADER = (byte) 4;
    
    public interface Command {
        public byte getNumber();
    }

    protected static enum Type {

        REQUEST('R'),
        DATA('D'),
        ACK('K'),
        NACK('N');

        private final char name;

        private Type(char name) {
            this.name = name;
        }

        public final byte getName() {
            return (byte) name;
        }
    }
    
    public abstract boolean isACK();
    
    public abstract byte getLength();
    
    public abstract Type getType();
    
    public abstract byte getTypeMessage();
    
    public abstract byte getCommand();
    
    public abstract byte[] getData();
    
    @Override
    public abstract String toString();
}
