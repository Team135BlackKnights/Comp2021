// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

public interface RobotMap {
    public static final int FRONT_LEFT_MOTOR_ID = 10, FRONT_RIGHT_MOTOR_ID = 11, BACK_LEFT_MOTOR_ID = 12,
            BACK_RIGHT_MOTOR_ID = 13;
    public static final int HORIZONTAL_AXIS = 0, VERTICAL_AXIS = 1, ROTATIONAL_AXIS = 2, SLIDER_AXIS = 3;

    public interface KOI {
        // Variables for joysticks and their buttons initiation
        public static final int LEFT_JOYSTICK = 1, RIGHT_JOYSTICK = 2, MANIP_JOYSTICK = 0,

                TRIGGER_BUTTON = 1, THUMB_BUTTON = 2,

                HANDLE_BOTTOM_LEFT_BUTTON = 3, HANDLE_BOTTOM_RIGHT_BUTTON = 4, HANDLE_TOP_LEFT_BUTTON = 5,
                HANDLE_TOP_RIGHT_BUTTON = 6,

                BASE_TOP_LEFT_BUTTON = 7, BASE_TOP_RIGHT_BUTTON = 8, BASE_MIDDLE_LEFT_BUTTON = 9,
                BASE_MIDDLE_RIGHT_BUTTON = 10, BASE_BOTTOM_LEFT_BUTTON = 11, BASE_BOTTOM_RIGHT_BUTTON = 12;
    }

    public interface INTAKE {
        final int ROLLER_SPARK = 15;

    }

    public interface STORAGE {
        final int CONVEYOR_SPARK = 14, ROLLER_SPARK = 7, INTAKE_TRIP_SWITCH = 0;
    }

    public interface SHOOTER {
        final int
        SHOOTER_PICH_SPARK = 15,
        SHOOTER_LAUNCHER_LEADER = 16,
        SHOOTER_LAUNCHER_FOLLOWER = 17;
    }

}
