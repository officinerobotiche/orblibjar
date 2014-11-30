/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.officinerobotiche.serial.frame;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Raffaello Bonghi
 */
public interface Jmessage {

    public interface ICommand {

        public int getNumber();

        public byte getByte();

        public String getName();
    };

    public static enum Information {

        REQUEST('R'),
        DATA('D'),
        ACK('K'),
        NACK('N');

        private static final Map<Byte, Information> lookup = new HashMap<>();
        private final char name;

        private Information(char name) {
            this.name = name;
        }

        public final byte getByte() {
            return (byte) name;
        }

        static {
            for (Information s : EnumSet.allOf(Information.class)) {
                lookup.put(s.getByte(), s);
            }
        }

        /**
         * the reverse lookup by simply getting the value from the lookup
         * HsahMap.
         *
         * @param name
         * @return
         */
        public static Information get(byte name) {
            return lookup.get(name);
        }
    }

    public <P extends ICommand> P getCommand();
    
    public boolean isAsync();

}
