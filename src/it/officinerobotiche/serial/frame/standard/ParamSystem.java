/*
 * Copyright (C) 2014 Officine Robotiche.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser General Public License
 * (LGPL) version 2.1 which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/lgpl-2.1.html
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * Contributors:
 *     Raffaello Bonghi - raffaello.bonghi@officinerobotiche.it
 */
package it.officinerobotiche.serial.frame.standard;

import it.officinerobotiche.serial.frame.AbstractFrame;

/**
 *
 * @author Raffaello Bonghi
 */
public class ParamSystem extends StandardFrame {
    
    private int stepTimer;
    private int timeMill;
    
    public ParamSystem() {
        super();
    }

    public ParamSystem(boolean sync, int command, byte[] in) {
        super(sync, command, in);
        stepTimer = AbstractFrame.byteArrayToInt(in, 0);
        timeMill = AbstractFrame.byteArrayToInt(in, 2);
    }
    
    public ParamSystem(boolean sync, int command, Information info) {
        super(sync, command, info);
    }

    @Override
    public Command getCommand() {
        return Command.PARAMETER_SYSTEM;
    }

    public int getStepTimer() {
        return stepTimer;
    }

    public int getTimeMill() {
        return timeMill;
    }
}
