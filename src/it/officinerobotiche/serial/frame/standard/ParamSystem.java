/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.officinerobotiche.serial.frame.standard;

/**
 *
 * @author Raffaello
 */
public class ParamSystem extends StandardFrame {
    
    public ParamSystem() {
        super();
    }

    public ParamSystem(boolean sync, byte[] in) {
        super(sync, in);
    }
    
    public ParamSystem(boolean sync, Information info) {
        super(sync, info);
    }

    @Override
    public Command getCommand() {
        return Command.PARAMETER_SYSTEM;
    }

}
