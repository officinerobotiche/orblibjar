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
public class NameProcess extends StandardFrame {
    
    public NameProcess() {
        super();
    }

    public NameProcess(byte[] in) {
        super(in);
    }
    
    public NameProcess(Jmessage.Information info) {
        super(info);
    }

    @Override
    public StandardFrame.Command getCommand() {
        return StandardFrame.Command.NAME_PROCESS;
    }

}
