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

import it.officinerobotiche.serial.frame.AbstractFrame;

/**
 *
 * @author Raffaello Bonghi
 */
public class Motor extends MotionFrame {

    public static final Motor LEFT = new Motor(Command.MOTOR_L);
    public static final Motor RIGHT = new Motor(Command.MOTOR_R);

    private final Command comm;
    protected int riferVel;
    protected int controlVel;
    protected int measureVel;
    protected int current;

    private Motor(Command comm) {
        super();
        this.comm = comm;
    }

    public Motor(boolean sync, int command, byte[] in) {
        super(sync, command, in);
        this.comm = Command.getCommand(command);
        this.riferVel = AbstractFrame.byteArrayToInt(in, 0);
        this.controlVel = AbstractFrame.byteArrayToInt(in, 2);
        this.measureVel = AbstractFrame.byteArrayToInt(in, 4);
        this.current = AbstractFrame.byteArrayToInt(in, 6);
    }

    public Motor(boolean sync, int command, Information info) {
        super(sync, command, info);
        this.comm = Command.getCommand(command);
    }

    @Override
    public Command getCommand() {
        return comm;
    }

    @Override
    protected void buildData() {
    }

    /**
     * Get the value of current
     *
     * @return the value of current
     */
    public int getCurrent() {
        return current;
    }

    /**
     * Get the value of measureVel
     *
     * @return the value of measureVel
     */
    public int getMeasureVel() {
        return measureVel;
    }

    /**
     * Get the value of controlVel
     *
     * @return the value of controlVel
     */
    public int getControlVel() {
        return controlVel;
    }

    /**
     * Get the value of riferVel
     *
     * @return the value of riferVel
     */
    public int getRiferVel() {
        return riferVel;
    }

}
