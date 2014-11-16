/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.officinerobotiche.message;

import it.officinerobotiche.serial.Packet;
import it.officinerobotiche.serial.SerialPacket;
import java.util.ArrayList;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Raffaello Bonghi
 */
public class SerialMessage extends SerialPacket {

    // List all messages
    private Set<Class<? extends Jmessage>> allClasses;

    public SerialMessage(String portName) {
        super(portName);
    }

    public <P extends Jmessage> void sendASyncMessage(P message) {
        try {
            sendMessage(false, message);
        } catch (InterruptedException ex) {
            Logger.getLogger(SerialMessage.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public <P extends Jmessage> void sendASyncMessage(ArrayList<P> message) {
        try {
            sendMessage(false, message);
        } catch (InterruptedException ex) {
            Logger.getLogger(SerialMessage.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public <P extends Jmessage> P sendSyncMessage(P message) throws InterruptedException {
        return (P) parsePacket(sendMessage(true, message)).get(0);
    }

    public <P extends Jmessage> ArrayList<P> sendSyncMessage(ArrayList<P> message) throws InterruptedException {
        return parsePacket(sendMessage(true, message));
    }
    
    public <P extends Jmessage> ArrayList<P> parsePacket(Packet packet) {
        ArrayList<P> list_receive = new ArrayList<P>();
        byte[] data = packet.getDataStructure();
        for(int i = 0; i < data.length; i += data[i]) {
            try {
                char type_message = (char) data[i+2];
                int command = (int) data[i+3];
                //Add data array
                byte[] data_message = new byte[data[i]];
                System.arraycopy(data, i+4, data, 0, data[i]);
                for (Class<? extends Jmessage> message : allClasses) {
                    //TODO Use type_message and command for find correct class;
                    if(true) {
                        list_receive.add((P) message.getDeclaredConstructor(byte.class, byte[].class).newInstance(data[i+1], data_message));
                    }
                }
            } catch (Exception ex) {
                Logger.getLogger(SerialMessage.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return null;
    }

    private <P extends Jmessage> Packet sendMessage(boolean sync, P message) throws InterruptedException {
        Packet packet = new Packet(sync);
        packet.addMessage(getMessage(message));
        return super.sendPacket(packet);
    }

    private <P extends Jmessage> Packet sendMessage(boolean sync, ArrayList<P> message) throws InterruptedException {
        Packet packet = new Packet(sync);
        for (P message_i : message) {
            packet.addMessage(getMessage(message_i));
        }
        return super.sendPacket(packet);
    }

    private <P extends Jmessage> ArrayList<Byte> getMessage(P message) {
        ArrayList<Byte> data = new ArrayList<Byte>();
        data.add(message.getLength());
        data.add(message.getType());
        data.add(message.getType_message());
        data.add(message.getCommand());
        for (byte i : message.getData()) {
            data.add(i);
        }
        return data;
    }
}
