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

    public static NameProcess LENGTH = new NameProcess(Type.LENGTH);

    private static int BUFF_NAME_PROCESS = 20;

    private enum Type {

        LENGTH, NAME;
    }

    private NameProcess(Type type) {
        this.information = Information.REQUEST;
        this.in = new byte[BUFF_NAME_PROCESS + 2];
        switch (type) {
            case LENGTH:
                byte[] lng = AbstractFrame.intToByteArray(-1);
                this.in[0] = lng[0];
                this.in[1] = lng[1];
                break;
            case NAME:

                break;
        }
    }

    public NameProcess(boolean sync, int command, byte[] in) {
        super(sync, command, in);
    }

    public NameProcess(boolean sync, int command, Information info) {
        super(sync, command, info);
    }

    @Override
    public Command getCommand() {
        return Command.NAME_PROCESS;
    }

}
