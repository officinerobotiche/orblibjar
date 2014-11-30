/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.officinerobotiche.serial.message.standard;

import it.officinerobotiche.serial.message.Jmessage;

/**
 *
 * @author Raffaello
 */
public class ErrorSerial extends StandardFrame {
    
    public ErrorSerial() {
        super();
    }

    public ErrorSerial(byte[] in) {
        super(in);
    }
    
    public ErrorSerial(Jmessage.Information info) {
        super(info);
    }

    @Override
    public StandardFrame.Command getCommand() {
        return StandardFrame.Command.ERROR_SERIAL;
    }

}
