package frc.robot.constants;

import frc.robot.enums.DriveGears;

/**
 * The {@code CurrentConstants} class is to store all constants to be used with
 * the robot for the current season
 * 
 * <p>
 * Anything declared here should be prefaced with {@code public static final}
 * </p>
 */
public final class CurrentConstants
    {
    public static final class DriveConstants
        {
        /* MOTOR IDs */
        public static final int FRONT_LEFT_MOTOR_ID = 10;
        public static final int REAR_LEFT_MOTOR_ID = 11;
        public static final int FRONT_RIGHT_MOTOR_ID = 12;
        public static final int REAR_RIGHT_MOTOR_ID = 13;

        /* Encoder */
        // public static final double DISTANCE_PER_PULSE = (6.0 * Math.PI) /
        // 8.45;
        public static final double DISTANCE_PER_PULSE = 2.5;
        public static final double DRIVE_STRAIGHT_CORRECTION_DELTA = 0.1;

        /* MOTOR CONTROLLER GROUPS */
        public static final boolean[] MOTOR_CONTROLLER_GROUPS_INVERTED =
            { false, true };

        /* JOYSTICK DEADBAND */
        public static final double JOYSTICK_DEADBAND = 0.25;

        /* Gears */
        public static final DriveGears DEFAULT_GEAR = DriveGears.GEAR1;
        public static final int GEAR_DOWN_BUTTON_ID = 1;
        public static final int GEAR_UP_BUTTON_ID = 1;

        /* Rate Limits */
        public static final double ACCELERATION_RATE_LIMIT = 1.0;
        public static final double BRAKE_RATE_LIMIT = 0.8;

        /* Turning */
        public static final double TURN_DEGREES_FUDGE_FACTOR = 5.0;
        public static final double TURN_RADIUS = 16.75;
        }

    public static final class AutonomousConstants
        {
        /* Autonomous Hardware IDs */
        public static final int USE_AUTO_SWITCH_ID = 10;
        public static final int[] AUTO_SIX_POSITION_SWITCH_IDS =
            { 15, 13, 14, 18, 16, 17 };
        public static final int AUTO_DELAY_POT_ID = 2;

        /* Autonomous Delay */
        public static final double AUTO_MIN_DELAY_SECONDS = 0.0;
        public static final double AUTO_MAX_DELAY_SECONDS = 5.0;

        /* Autonomous Drive Constants */
        public static final double AUTO_MAX_DRIVE_SPEED = 0.3;

        /* Autonomous Mode */
        public static final int DEFAULT_AUTONOMOUS_MODE = 0;
        public static final boolean CHECK_MODE_FROM_DASHBOARD = true;
        }

    public static final class JoystickConstants
        {
        /* JOYSTICK IDs */
        public static final int LEFT_DRIVER_JOYSTICK_ID = 0;
        public static final int RIGHT_DRIVER_JOYSTICK_ID = 1;
        public static final int LEFT_OPERATOR_JOYSTICK_ID = 2;
        public static final int RIGHT_OPERATOR_JOYSTICK_ID = 3;
        }

    public static final class CameraConstants
        {
        /* SOFTWARE PROPERTIES */
        public static final boolean CAMERA_ENABLED = true;
        public static final boolean APRIL_TAGS_ENABLED = false;
        public static final boolean USING_TWO_CAMERAS = false;

        /* BUTTON IDS */
        public static final int SWITCH_CAMERA_BUTTON_ID = 10;

        /* CAMERA PROPERTIES */
        public static final int[] RESOLUTION =
            { 1080, 720 };
        public static final int FRAMES_PER_SECOND = 20;
        public static final int COMPRESSION = 60;
        public static final int BRIGHTNESS = 0;
        }

    public static final class FlipperPistonConstants
        {
        /* DOUBLE SOLENOID PORTS */
        public static final int LEFT_PISTON_FWD_PORT = 2;
        public static final int LEFT_PISTON_REV_PORT = 3;
        public static final int RIGHT_PISTON_FWD_PORT = 6;
        public static final int RIGHT_PISTON_REV_PORT = 7;

        /* BUTTON IDS */
        public static final int FLIP_UP_BUTTON_ID = 1;
        public static final int FLIP_DOWN_BUTTON_ID = 1;

        /* Default State */
        public static final boolean FORWARD_BY_DEFAULT = true;
        }

    public static final class DashboardConstants
        {
        /* Battery Level */
        public static final double LOW_BATTERY_LEVEL = 11.5;

        /* Default Values */
        public static final boolean AUTONOMOUS_ENABLED_DEFAULT = true;
        public static final double AUTONOMOUS_DELAY_DEFAULT = 0.0;
        public static final boolean DEMO_ENABLED_DEFAULT = false;
        }
    }
