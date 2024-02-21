package frc.robot;

import frc.robot.constants.*;
import frc.robot.enums.DriveGears;
import frc.robot.enums.Season;

/**
 * The {@code Constants} class is to store all constants to be used with the
 * robot
 * 
 * <p>
 * Anything declared here should be prefaced with {@code public static}
 * </p>
 */
public final class Constants
  {
  public static Season season = Season.CurrentSeason;

  public static final class DriveConstants
    {
    /* MOTOR IDs */
    public static int FRONT_LEFT_MOTOR_ID;
    public static int REAR_LEFT_MOTOR_ID;
    public static int FRONT_RIGHT_MOTOR_ID;
    public static int REAR_RIGHT_MOTOR_ID;

    /* Encoder */
    public static double DISTANCE_PER_PULSE;
    public static double DRIVE_STRAIGHT_CORRECTION_DELTA;

    /* MOTOR CONTROLLER GROUPS */
    public static boolean[] MOTOR_CONTROLLER_GROUPS_INVERTED;

    /* JOYSTICK DEADBAND */
    public static double JOYSTICK_DEADBAND;

    /* Gears */
    public static DriveGears DEFAULT_GEAR;
    public static int GEAR_DOWN_BUTTON_ID;
    public static int GEAR_UP_BUTTON_ID;

    /* Rate Limits */
    public static double ACCELERATION_RATE_LIMIT;
    public static double BRAKE_RATE_LIMIT;

    /* Turning */
    public static double TURN_DEGREES_FUDGE_FACTOR;
    public static double TURN_RADIUS;
    }

  public static final class CameraConstants
    {
    /* SOFTWARE PROPERTIES */
    public static boolean CAMERA_ENABLED;
    public static boolean USING_TWO_CAMERAS;

    /* BUTTON IDS */
    public static int SWITCH_CAMERA_BUTTON_ID;

    /* CAMERA PROPERTIES */
    public static int[] RESOLUTION;
    public static int FRAMES_PER_SECOND;
    public static int COMPRESSION;
    public static int BRIGHTNESS;
    }

  public static final class AutonomousConstants
    {
    /* Autonomous Hardware IDs */

    /* Autonomous Delay */

    /* Autonomous Drive Constants */

    /* Autonomous Mode */
    }

  public static final class JoystickConstants
    {
    /* JOYSTICK IDs */
    public static int LEFT_DRIVER_JOYSTICK_ID;
    public static int RIGHT_DRIVER_JOYSTICK_ID;
    public static int LEFT_OPERATOR_JOYSTICK_ID;
    public static int RIGHT_OPERATOR_JOYSTICK_ID;
    }

  public static final class FlipperPistonConstants
    {
    /* DOUBLE SOLENOID PORTS */
    public static int LEFT_PISTON_FWD_PORT;
    public static int LEFT_PISTON_REV_PORT;
    public static int RIGHT_PISTON_FWD_PORT;
    public static int RIGHT_PISTON_REV_PORT;

    /* BUTTON IDS */
    public static int FLIP_UP_BUTTON_ID;
    public static int FLIP_DOWN_BUTTON_ID;
    }

  public static final class DashboardConstants
    {
    }

  public static void initialize()
  {
    if (season == Season.CurrentSeason)
      {
      DriveConstants.FRONT_LEFT_MOTOR_ID = CurrentConstants.DriveConstants.FRONT_LEFT_MOTOR_ID;
      DriveConstants.FRONT_RIGHT_MOTOR_ID = CurrentConstants.DriveConstants.FRONT_RIGHT_MOTOR_ID;
      DriveConstants.REAR_LEFT_MOTOR_ID = CurrentConstants.DriveConstants.REAR_LEFT_MOTOR_ID;
      DriveConstants.REAR_RIGHT_MOTOR_ID = CurrentConstants.DriveConstants.REAR_RIGHT_MOTOR_ID;
      DriveConstants.DISTANCE_PER_PULSE = CurrentConstants.DriveConstants.DISTANCE_PER_PULSE;
      DriveConstants.DRIVE_STRAIGHT_CORRECTION_DELTA = CurrentConstants.DriveConstants.DRIVE_STRAIGHT_CORRECTION_DELTA;
      DriveConstants.MOTOR_CONTROLLER_GROUPS_INVERTED = CurrentConstants.DriveConstants.MOTOR_CONTROLLER_GROUPS_INVERTED;
      DriveConstants.JOYSTICK_DEADBAND = CurrentConstants.DriveConstants.JOYSTICK_DEADBAND;
      DriveConstants.DEFAULT_GEAR = CurrentConstants.DriveConstants.DEFAULT_GEAR;
      DriveConstants.GEAR_UP_BUTTON_ID = CurrentConstants.DriveConstants.GEAR_UP_BUTTON_ID;
      DriveConstants.GEAR_DOWN_BUTTON_ID = CurrentConstants.DriveConstants.GEAR_DOWN_BUTTON_ID;
      DriveConstants.ACCELERATION_RATE_LIMIT = CurrentConstants.DriveConstants.ACCELERATION_RATE_LIMIT;
      DriveConstants.BRAKE_RATE_LIMIT = CurrentConstants.DriveConstants.ACCELERATION_RATE_LIMIT;
      DriveConstants.TURN_DEGREES_FUDGE_FACTOR = CurrentConstants.DriveConstants.TURN_DEGREES_FUDGE_FACTOR;
      DriveConstants.TURN_RADIUS = CurrentConstants.DriveConstants.TURN_RADIUS;

      JoystickConstants.LEFT_DRIVER_JOYSTICK_ID = CurrentConstants.JoystickConstants.LEFT_DRIVER_JOYSTICK_ID;
      JoystickConstants.RIGHT_DRIVER_JOYSTICK_ID = CurrentConstants.JoystickConstants.RIGHT_DRIVER_JOYSTICK_ID;
      JoystickConstants.LEFT_OPERATOR_JOYSTICK_ID = CurrentConstants.JoystickConstants.LEFT_OPERATOR_JOYSTICK_ID;
      JoystickConstants.RIGHT_OPERATOR_JOYSTICK_ID = CurrentConstants.JoystickConstants.RIGHT_OPERATOR_JOYSTICK_ID;

      CameraConstants.CAMERA_ENABLED = CurrentConstants.CameraConstants.CAMERA_ENABLED;
      CameraConstants.SWITCH_CAMERA_BUTTON_ID = CurrentConstants.CameraConstants.SWITCH_CAMERA_BUTTON_ID;
      CameraConstants.USING_TWO_CAMERAS = CurrentConstants.CameraConstants.USING_TWO_CAMERAS;
      CameraConstants.RESOLUTION = CurrentConstants.CameraConstants.RESOLUTION;
      CameraConstants.FRAMES_PER_SECOND = CurrentConstants.CameraConstants.FRAMES_PER_SECOND;
      CameraConstants.COMPRESSION = CurrentConstants.CameraConstants.COMPRESSION;
      CameraConstants.BRIGHTNESS = CurrentConstants.CameraConstants.BRIGHTNESS;

      FlipperPistonConstants.LEFT_PISTON_FWD_PORT = CurrentConstants.FlipperPistonConstants.LEFT_PISTON_FWD_PORT;
      FlipperPistonConstants.LEFT_PISTON_REV_PORT = CurrentConstants.FlipperPistonConstants.LEFT_PISTON_REV_PORT;
      FlipperPistonConstants.RIGHT_PISTON_FWD_PORT = CurrentConstants.FlipperPistonConstants.RIGHT_PISTON_FWD_PORT;
      FlipperPistonConstants.RIGHT_PISTON_REV_PORT = CurrentConstants.FlipperPistonConstants.RIGHT_PISTON_REV_PORT;
      FlipperPistonConstants.FLIP_UP_BUTTON_ID = CurrentConstants.FlipperPistonConstants.FLIP_UP_BUTTON_ID;
      FlipperPistonConstants.FLIP_DOWN_BUTTON_ID = CurrentConstants.FlipperPistonConstants.FLIP_DOWN_BUTTON_ID;
      }
    else
      {
      DriveConstants.FRONT_LEFT_MOTOR_ID = PreviousConstants.DriveConstants.FRONT_LEFT_MOTOR_ID;
      DriveConstants.FRONT_RIGHT_MOTOR_ID = PreviousConstants.DriveConstants.FRONT_RIGHT_MOTOR_ID;
      DriveConstants.REAR_LEFT_MOTOR_ID = PreviousConstants.DriveConstants.REAR_LEFT_MOTOR_ID;
      DriveConstants.REAR_RIGHT_MOTOR_ID = PreviousConstants.DriveConstants.REAR_RIGHT_MOTOR_ID;
      DriveConstants.DISTANCE_PER_PULSE = PreviousConstants.DriveConstants.DISTANCE_PER_PULSE;
      DriveConstants.DRIVE_STRAIGHT_CORRECTION_DELTA = PreviousConstants.DriveConstants.DRIVE_STRAIGHT_CORRECTION_DELTA;
      DriveConstants.MOTOR_CONTROLLER_GROUPS_INVERTED = PreviousConstants.DriveConstants.MOTOR_CONTROLLER_GROUPS_INVERTED;
      DriveConstants.JOYSTICK_DEADBAND = PreviousConstants.DriveConstants.JOYSTICK_DEADBAND;
      DriveConstants.DEFAULT_GEAR = PreviousConstants.DriveConstants.DEFAULT_GEAR;
      DriveConstants.GEAR_UP_BUTTON_ID = PreviousConstants.DriveConstants.GEAR_UP_BUTTON_ID;
      DriveConstants.GEAR_DOWN_BUTTON_ID = PreviousConstants.DriveConstants.GEAR_DOWN_BUTTON_ID;
      DriveConstants.ACCELERATION_RATE_LIMIT = PreviousConstants.DriveConstants.ACCELERATION_RATE_LIMIT;
      DriveConstants.BRAKE_RATE_LIMIT = PreviousConstants.DriveConstants.ACCELERATION_RATE_LIMIT;
      DriveConstants.TURN_DEGREES_FUDGE_FACTOR = PreviousConstants.DriveConstants.TURN_DEGREES_FUDGE_FACTOR;
      DriveConstants.TURN_RADIUS = PreviousConstants.DriveConstants.TURN_RADIUS;

      JoystickConstants.LEFT_DRIVER_JOYSTICK_ID = PreviousConstants.JoystickConstants.LEFT_DRIVER_JOYSTICK_ID;
      JoystickConstants.RIGHT_DRIVER_JOYSTICK_ID = PreviousConstants.JoystickConstants.RIGHT_DRIVER_JOYSTICK_ID;
      JoystickConstants.LEFT_OPERATOR_JOYSTICK_ID = PreviousConstants.JoystickConstants.LEFT_OPERATOR_JOYSTICK_ID;
      JoystickConstants.RIGHT_OPERATOR_JOYSTICK_ID = PreviousConstants.JoystickConstants.RIGHT_OPERATOR_JOYSTICK_ID;

      CameraConstants.CAMERA_ENABLED = PreviousConstants.CameraConstants.CAMERA_ENABLED;
      CameraConstants.SWITCH_CAMERA_BUTTON_ID = PreviousConstants.CameraConstants.SWITCH_CAMERA_BUTTON_ID;
      CameraConstants.USING_TWO_CAMERAS = PreviousConstants.CameraConstants.USING_TWO_CAMERAS;
      CameraConstants.RESOLUTION = PreviousConstants.CameraConstants.RESOLUTION;
      CameraConstants.FRAMES_PER_SECOND = PreviousConstants.CameraConstants.FRAMES_PER_SECOND;
      CameraConstants.COMPRESSION = PreviousConstants.CameraConstants.COMPRESSION;
      CameraConstants.BRIGHTNESS = PreviousConstants.CameraConstants.BRIGHTNESS;

      FlipperPistonConstants.LEFT_PISTON_FWD_PORT = PreviousConstants.FlipperPistonConstants.LEFT_PISTON_FWD_PORT;
      FlipperPistonConstants.LEFT_PISTON_REV_PORT = PreviousConstants.FlipperPistonConstants.LEFT_PISTON_REV_PORT;
      FlipperPistonConstants.RIGHT_PISTON_FWD_PORT = PreviousConstants.FlipperPistonConstants.RIGHT_PISTON_FWD_PORT;
      FlipperPistonConstants.RIGHT_PISTON_REV_PORT = PreviousConstants.FlipperPistonConstants.RIGHT_PISTON_REV_PORT;
      FlipperPistonConstants.FLIP_UP_BUTTON_ID = PreviousConstants.FlipperPistonConstants.FLIP_UP_BUTTON_ID;
      FlipperPistonConstants.FLIP_DOWN_BUTTON_ID = PreviousConstants.FlipperPistonConstants.FLIP_DOWN_BUTTON_ID;
      }
  }
  }
