/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.officinerobotiche.message;

import java.util.EventObject;

/**
 *
 * @author Raffaello
 */
public class MessageEvent extends EventObject {

    private final Jmessage packet;
    public MessageEvent(Object source) {
        super(source);
        packet = (Jmessage) source;
    }
    
    public Jmessage getPacket() {
        return packet;
    }
    
}
