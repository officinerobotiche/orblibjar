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

/**
 *
 * @author Raffaello Bonghi
 */
public class ErrorSerial extends StandardFrame {

    private enum ERROR {

        ERROR_FRAMMING(-1),
        ERROR_OVERRUN(-2),
        ERROR_HEADER(-3),
        ERROR_LENGTH(-4),
        ERROR_DATA(-5),
        ERROR_CKS(-6),
        ERROR_CMD(-7),
        ERROR_NACK(-8),
        ERROR_OPTION(-9),
        ERROR_PKG(-10),
        ERROR_CREATE_PKG(-11);
        private final int error;

        private ERROR(int error) {
            this.error = error;
        }
    };

    public ErrorSerial() {
        super();
    }

    public ErrorSerial(boolean sync, int command, byte[] in) {
        super(sync, command, in);
    }

    public ErrorSerial(boolean sync, int command, Information info) {
        super(sync, command, info);
    }

    @Override
    public Command getCommand() {
        return Command.ERROR_SERIAL;
    }

    public byte[] getErrors() {
        return in;
    }
}
