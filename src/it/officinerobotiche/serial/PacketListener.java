/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.officinerobotiche.serial;

import java.util.EventListener;

/**
 *
 * @author Raffaello Bonghi
 */
public interface PacketListener extends EventListener {
    
    public void asyncPacketEvent(PacketEvent evt);
}
