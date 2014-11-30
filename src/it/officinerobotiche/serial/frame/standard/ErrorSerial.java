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

    public ErrorSerial(boolean sync, byte[] in) {
        super(sync, in);
    }

    public ErrorSerial(boolean sync, Information info) {
        super(sync, info);
    }

    @Override
    public Command getCommand() {
        return Command.ERROR_SERIAL;
    }

    public byte[] getErrors() {
        return in;
    }
}
