/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.officinerobotiche.message;

import it.officinerobotiche.serial.Packet;
import it.officinerobotiche.serial.SerialPacket;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Raffaello Bonghi
 */
public class SerialMessage extends SerialPacket {

    // List all messages
    //private final Set<Class<? extends Jmessage>> allClasses;
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
        Packet packet = sendMessage(true, message);
        return null;
    }

    public <P extends Jmessage> ArrayList<P> sendSyncMessage(ArrayList<P> message) throws InterruptedException {
        Packet packet = sendMessage(true, message);
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
