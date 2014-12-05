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
 *
 * @author Raffaello Bonghi
 */
public abstract class MotionFrame extends AbstractFrame {

    public enum Command implements ICommand {

        PID_L(0, "PIDLeft"),
        PID_R(1, "PIDRight"),
        MOTOR_L(2, "MotorLeft"),
        MOTOR_R(3,"MotorRight"),
        COORDINATE(4,"Coordinate"),
        PARAM_MOTORS(5,"ParamMotors"),
        CONSTRAINT(6,"Constraint"),
        VELOCITY(7,"Velocity"),
        VELOCITY_MIS(8,"VelocityMis"),
        ENABLE(9,"Enable"),
        EMERGENCY(10,"Emergency"),
        DELTA_ODO(11,"DeltaOdo");

        private final int number;
        private final String name;
        private static final Map<Integer, String> lookup = new HashMap<>();

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

        static {
            for (Command s : EnumSet.allOf(Command.class)) {
                lookup.put(s.getNumber(), s.getName());
            }
        }

        public static String getCommand(int number) {
            return lookup.get(number);
        }
    };

    public MotionFrame() {
        super();
    }

    public MotionFrame(boolean sync, int name, byte[] in) {
        super(sync, name, in);
    }

    public MotionFrame(boolean sync, int name, Information info) {
        super(sync, name, info);
    }
    
    @Override
    public TypeMessage getTypeMessage() {
        return TypeMessage.UNAV;
    }

    @Override
    abstract public Command getCommand();
}
