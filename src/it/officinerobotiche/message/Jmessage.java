/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.officinerobotiche.message;

/**
 * 
 * @author Raffaello Bonghi
 */
public interface Jmessage {

    public final static byte LNG_HEADER = (byte) 4;

    public enum TypeMessage {

        DEFAULT('D'),
        UNAV('U');

        private final char name;

        private TypeMessage(char name) {
            this.name = name;
        }

        public char getName() {
            return name;
        }

        public byte getByte() {
            return (byte) name;
        }
    }

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
    
    public interface MessageClassListener {

        public void handleMyEventClassEvent();
    }

    public <P extends Jmessage> P set(byte[] data);

    public <P extends Jmessage> P set(Information info);

    public byte getNumber();

    public Information getInformation();

    public <P extends Jmessage> P get(byte Value);

    public void addMessageListener(MessageClassListener listener);

    public void removeMessageListener(MessageClassListener listener);
}