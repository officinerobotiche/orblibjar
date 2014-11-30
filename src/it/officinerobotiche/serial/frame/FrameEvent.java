/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.officinerobotiche.serial.frame;

import java.util.EventObject;

/**
 *
 * @author Raffaello
 */
public class FrameEvent extends EventObject {

    private final AbstractFrame packet;
    public FrameEvent(Object source) {
        super(source);
        packet = (AbstractFrame) source;
    }
    
    public AbstractFrame getFrame() {
        return packet;
    }
    
}
