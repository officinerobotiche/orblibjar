/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.officinerobotiche.message;

import java.util.EventListener;

/**
 *
 * @author Raffaello Bonghi
 */
public interface ParserListener extends EventListener {
    
    public void defaultMessagesEvent(MessageEvent evt);
    public void syncMessagesEvent(MessageEvent evt);
}
