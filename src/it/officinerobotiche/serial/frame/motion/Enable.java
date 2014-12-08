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
package it.officinerobotiche.serial.frame.motion;

/**
 *
 * @author Raffaello Bonghi
 */
public class Enable extends MotionFrame {
    
    public static final Enable ENABLE = new Enable();

    private boolean enable;

    public Enable() {
        super();
        this.in = new byte[1];
    }
    
    public Enable(boolean enable) {
        super();
        this.in = new byte[1];
        this.enable = enable;
        buildData();
    }

    public Enable(boolean sync, int command, byte[] in) {
        super(sync, command, in);
        this.enable = (in[0] == 1);
    }

    public Enable(boolean sync, int command, Information info) {
        super(sync, command, info);
    }

    @Override
    public Command getCommand() {
        return Command.ENABLE;
    }
    
    @Override
    protected void buildData() {
        this.in[0] = (byte) (this.enable ? 1 : 0);
    }

    /**
     * Get the value of enable
     *
     * @return the value of enable
     */
    public boolean isEnable() {
        return enable;
    }

    /**
     * Set the value of enable
     *
     * @param enable new value of enable
     */
    public void setEnable(boolean enable) {
        this.enable = enable;
        buildData();
    }

}
