/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.officinerobotiche.serial.frame.unav;

/**
 *
 * @author Raffaello
 */
public class Enable extends UnavFrame {
    
    public Enable() {
        super();
    }

    public Enable(boolean sync, byte[] in) {
        super(sync, in);
    }
    
    public Enable(boolean sync, Information info) {
        super(sync, info);
    }
    
    @Override
    public Command getCommand() {
        return Command.ENABLE;
    }
    
}
