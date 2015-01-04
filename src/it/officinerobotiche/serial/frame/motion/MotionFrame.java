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
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

/**
 * Abstract class with general information for Motion messages frame.
 *
 * @author Raffaello Bonghi
 */
public abstract class MotionFrame extends AbstractFrame {

    /**
     * All command that you can use for communicate with motion board. This is
     * an enumeration with all command and relative association name object.
     * This enumeration extend ICommand and have default methods to read
     * association between byte command and name.
     */
    public enum Command implements ICommand {

        /**
         * PID Left command.
         */
        PID_L(0, "PID"),
        /**
         * PID Right Command.
         */
        PID_R(1, "PID"),
        /**
         * Motor Left command.
         */
        MOTOR_L(2, "Motor"),
        /**
         * Motor Right command.
         */
        MOTOR_R(3, "Motor"),
        /**
         * Coordinate command.
         */
        COORDINATE(4, "Coordinate"),
        /**
         * Parameter motors command.
         */
        PARAM_UNICYCLE(5, "ParamUnicycle"),
        /**
         * Parameter motors command.
         */
        PARAM_MOTOR_L(6, "ParamMotor"),
        /**
         * Parameter motors command.
         */
        PARAM_MOTOR_R(7, "ParamMotor"),
        /**
         * Contraint command.
         */
        CONSTRAINT(8, "Constraint"),
        /**
         * Velocity reference command.
         */
        VELOCITY(9, "Velocity"),
        /**
         * Velocity measured command.
         */
        VELOCITY_MIS(10, "Velocity"),
        /**
         * Enable bridge command.
         */
        ENABLE(11, "Enable"),
        /**
         * Emergency configuration command.
         */
        EMERGENCY(12, "Emergency"),
        /**
         * Relative odometry command.
         */
        DELTA_ODO(13, "DeltaOdo");

        /**
         * Number command.
         */
        private final int number;
        /**
         * Name associated object.
         */
        private final String name;
        /**
         * Map between number command and name.
         */
        private static final Map<Integer, String> lookup = new HashMap<>();
        /**
         * Map between number command and enumeration.
         */
        private static final Map<Integer, Command> look_comm = new HashMap<>();

        /**
         * Initialize enumeration with integer name and associated Object name.
         *
         * @param number number command.
         * @param name Object name.
         */
        private Command(int number, String name) {
            this.number = number;
            this.name = name;
        }

        @Override
        public int getNumber() {
            return number;
        }

        @Override
        public byte getByte() {
            return (byte) number;
        }

        @Override
        public String getName() {
            return name;
        }

        /**
         * Static collection all enumeration between number with name and string
         * name with enumeration.
         */
        static {
            for (Command s : EnumSet.allOf(Command.class)) {
                lookup.put(s.getNumber(), s.getName());
                look_comm.put(s.getNumber(), s);
            }
        }

        /**
         * Get associated enumeration from number. This method used to
         * reconstruct frame message received.
         *
         * @param number value of frame message.
         * @return Associated enumeration command.
         */
        public static Command getCommand(int number) {
            return look_comm.get(number);
        }

        /**
         * Get associated string command from number frame message. This method
         * used from reflection.
         *
         * @param number value of frame message.
         * @return Associated string object name.
         */
        public static String getStringCommand(int number) {
            return lookup.get(number);
        }
    };

    /**
     * Initialize Motion message. This Constructor is used for require data from
     * board.
     */
    public MotionFrame() {
        super();
    }

    /**
     * Initialize message with data received from board. This message is used
     * normally from parser to set data received, it is a message with data.
     *
     * @param sync type of packet received (syncronous or not).
     * @param command type command received.
     * @param in byte received.
     */
    public MotionFrame(boolean sync, int command, byte[] in) {
        super(sync, command, in);
    }

    /**
     * Initialize message with ACK, NACK information. This message is used
     * normally from parser.
     *
     * @param sync type of packet received (syncronous or not).
     * @param command type command received.
     * @param info Information about message.
     */
    public MotionFrame(boolean sync, int command, Information info) {
        super(sync, command, info);
    }

    @Override
    public TypeMessage getTypeMessage() {
        return TypeMessage.MOTION;
    }

    @Override
    abstract public Command getCommand();

    /**
     * Construct byte data array with information on object.
     */
    abstract protected void buildData();
}
