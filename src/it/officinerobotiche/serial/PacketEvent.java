/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.officinerobotiche.serial;

import java.util.EventObject;

/**
 *
 * @author Raffaello
 */
class PacketEvent extends EventObject {

    private final Packet packet;
    public PacketEvent(Object source) {
        super(source);
        packet = (Packet) source;
    }
    
    public Packet getPacket() {
        return packet;
    }
    
}
