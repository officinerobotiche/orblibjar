/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.officinerobotiche.serial.frame.standard;

import it.officinerobotiche.serial.frame.Jmessage;

/**
 *
 * @author Raffaello
 */
public class NameProcess extends StandardFrame {
    
    public NameProcess() {
        super();
    }

    public NameProcess(boolean sync, byte[] in) {
        super(sync, in);
    }
    
    public NameProcess(boolean sync, Information info) {
        super(sync, info);
    }

    @Override
    public Command getCommand() {
        return Command.NAME_PROCESS;
    }

}
