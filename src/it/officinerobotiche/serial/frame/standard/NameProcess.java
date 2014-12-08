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
public class NameProcess extends StandardFrame {

    public final NameProcess LENGTH = new NameProcess(REQUIRE_LENGTH);

    private static int REQUIRE_LENGTH = -1;
    private static int BUFF_NAME_PROCESS = 20;
    private int number;
    private String name;

    public NameProcess(int number) {
        this.information = Information.REQUEST;
        this.number = number;
        this.in = new byte[BUFF_NAME_PROCESS + 2];
        this.in = AbstractFrame.intToByteArray(in, 0, number);
    }

    public NameProcess(boolean sync, int command, byte[] in) {
        super(sync, command, in);
        this.number = AbstractFrame.byteArrayToInt(in, 0);
        if (number != REQUIRE_LENGTH) {
            this.name = AbstractFrame.getString(in, 2);
        } else {
            this.name = null;
        }
    }

    public NameProcess(boolean sync, int command, Information info) {
        super(sync, command, info);
    }

    @Override
    public Command getCommand() {
        return Command.NAME_PROCESS;
    }

    public int getNumber() {
        return number;
    }

    public String getName() {
        return name;
    }

}
