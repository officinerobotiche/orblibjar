/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.officinerobotiche.serial.message;

/**
 *
 * @author Raffaello Bonghi
 */
public interface Jmessage {

    public static final int LNG_HEADER = 4;

    public interface ICommand {
        public int getNumber();
        public byte getByte();
        public String getName();
        //public String getFrame(int number);
    };
    
    public static enum Information {

        REQUEST('R'),
        DATA('D'),
        ACK('K'),
        NACK('N');

        private final char name;

        private Information(char name) {
            this.name = name;
        }

        public final byte getByte() {
            return (byte) name;
        }
    }

    public <P extends ICommand> P getCommand();

}
