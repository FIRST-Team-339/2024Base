package frc.robot;

import frc.robot.constants.*;
import frc.robot.enums.*;

/**
 * The {@code Constants} class is to store all constants to be used with the
 * robot
 * 
 * <p>
 * Anything declared here should be prefaced with {@code public static final}
 * </p>
 */
public final class Constants
  {
  public static Season season = Season.PreviousSeason;

  public static final class DriveConstants
    {
    /* MOTOR IDs */
    public static int TOP_LEFT_MOTOR_ID;
    public static int BOTTOM_LEFT_MOTOR_ID;
    public static int TOP_RIGHT_MOTOR_ID;
    public static int BOTTOM_RIGHT_MOTOR_ID;

    /* Encoder */
    public static double DISTANCE_PER_PULSE;

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
    public static double DRIVE_STRAIGHT_CORRECTION_DELTA;
    }

  public static final class AutonomousConstants
    {
    /* Autonomous Hardware IDs */
    public static int USE_AUTO_SWITCH_ID;
    public static int[] AUTO_SIX_POSITION_SWITCH_IDS;
    public static int AUTO_DELAY_POT_ID;

    /* Autonomous Delay */
    public static double AUTO_MIN_DELAY_SECONDS;
    public static double AUTO_MAX_DELAY_SECONDS;

    /* Autonomous Drive Constants */
    public static double AUTO_MAX_DRIVE_SPEED;

    /* Autonomous Mode */
    public static int DEFAULT_AUTONOMOUS_MODE;
    public static boolean CHECK_MODE_FROM_DASHBOARD;
    }

  public static final class JoystickConstants
    {
    /* JOYSTICK IDs */
    public static int LEFT_DRIVER_JOYSTICK_ID;
    public static int RIGHT_DRIVER_JOYSTICK_ID;
    public static int LEFT_OPERATOR_JOYSTICK_ID;
    public static int RIGHT_OPERATOR_JOYSTICK_ID;
    }

  public static final class CameraConstants
    {
    /* SOFTWARE PROPERTIES */
    public static boolean USING_TWO_CAMERAS;

    /* BUTTON IDS */
    public static int SWITCH_CAMERA_BUTTON_ID;

    /* CAMERA PROPERTIES */
    public static int[] RESOLUTION;
    public static int FRAMES_PER_SECOND;
    public static int COMPRESSION;
    }

  public static final class DashboardConstants
    {
    /* Battery Level */
    public static double LOW_BATTERY_LEVEL;
    }

  public static void initialize()
  {
    if (season == Season.CurrentSeason)
      {
      DriveConstants.TOP_LEFT_MOTOR_ID = CurrentConstants.DriveConstants.TOP_LEFT_MOTOR_ID;
      DriveConstants.TOP_RIGHT_MOTOR_ID = CurrentConstants.DriveConstants.TOP_RIGHT_MOTOR_ID;
      DriveConstants.BOTTOM_LEFT_MOTOR_ID = CurrentConstants.DriveConstants.BOTTOM_LEFT_MOTOR_ID;
      DriveConstants.BOTTOM_RIGHT_MOTOR_ID = CurrentConstants.DriveConstants.BOTTOM_RIGHT_MOTOR_ID;
      DriveConstants.DISTANCE_PER_PULSE = CurrentConstants.DriveConstants.DISTANCE_PER_PULSE;
      DriveConstants.MOTOR_CONTROLLER_GROUPS_INVERTED = CurrentConstants.DriveConstants.MOTOR_CONTROLLER_GROUPS_INVERTED;
      DriveConstants.JOYSTICK_DEADBAND = CurrentConstants.DriveConstants.JOYSTICK_DEADBAND;
      DriveConstants.DEFAULT_GEAR = CurrentConstants.DriveConstants.DEFAULT_GEAR;
      DriveConstants.GEAR_DOWN_BUTTON_ID = CurrentConstants.DriveConstants.GEAR_DOWN_BUTTON_ID;
      DriveConstants.GEAR_UP_BUTTON_ID = CurrentConstants.DriveConstants.GEAR_UP_BUTTON_ID;
      DriveConstants.ACCELERATION_RATE_LIMIT = CurrentConstants.DriveConstants.ACCELERATION_RATE_LIMIT;
      DriveConstants.BRAKE_RATE_LIMIT = CurrentConstants.DriveConstants.BRAKE_RATE_LIMIT;
      DriveConstants.DRIVE_STRAIGHT_CORRECTION_DELTA = CurrentConstants.DriveConstants.DRIVE_STRAIGHT_CORRECTION_DELTA;

      AutonomousConstants.USE_AUTO_SWITCH_ID = CurrentConstants.AutonomousConstants.USE_AUTO_SWITCH_ID;
      AutonomousConstants.AUTO_SIX_POSITION_SWITCH_IDS = CurrentConstants.AutonomousConstants.AUTO_SIX_POSITION_SWITCH_IDS;
      AutonomousConstants.AUTO_DELAY_POT_ID = CurrentConstants.AutonomousConstants.AUTO_DELAY_POT_ID;
      AutonomousConstants.AUTO_MIN_DELAY_SECONDS = CurrentConstants.AutonomousConstants.AUTO_MIN_DELAY_SECONDS;
      AutonomousConstants.AUTO_MAX_DELAY_SECONDS = CurrentConstants.AutonomousConstants.AUTO_MAX_DELAY_SECONDS;
      AutonomousConstants.AUTO_MAX_DRIVE_SPEED = CurrentConstants.AutonomousConstants.AUTO_MAX_DRIVE_SPEED;
      AutonomousConstants.DEFAULT_AUTONOMOUS_MODE = CurrentConstants.AutonomousConstants.DEFAULT_AUTONOMOUS_MODE;
      AutonomousConstants.CHECK_MODE_FROM_DASHBOARD = CurrentConstants.AutonomousConstants.CHECK_MODE_FROM_DASHBOARD;

      JoystickConstants.LEFT_DRIVER_JOYSTICK_ID = CurrentConstants.JoystickConstants.LEFT_DRIVER_JOYSTICK_ID;
      JoystickConstants.RIGHT_DRIVER_JOYSTICK_ID = CurrentConstants.JoystickConstants.RIGHT_DRIVER_JOYSTICK_ID;
      JoystickConstants.LEFT_OPERATOR_JOYSTICK_ID = CurrentConstants.JoystickConstants.LEFT_OPERATOR_JOYSTICK_ID;
      JoystickConstants.RIGHT_OPERATOR_JOYSTICK_ID = CurrentConstants.JoystickConstants.RIGHT_OPERATOR_JOYSTICK_ID;

      CameraConstants.USING_TWO_CAMERAS = CurrentConstants.CameraConstants.USING_TWO_CAMERAS;
      CameraConstants.SWITCH_CAMERA_BUTTON_ID = CurrentConstants.CameraConstants.SWITCH_CAMERA_BUTTON_ID;
      CameraConstants.RESOLUTION = CurrentConstants.CameraConstants.RESOLUTION;
      CameraConstants.FRAMES_PER_SECOND = CurrentConstants.CameraConstants.FRAMES_PER_SECOND;
      CameraConstants.COMPRESSION = CurrentConstants.CameraConstants.COMPRESSION;

      DashboardConstants.LOW_BATTERY_LEVEL = CurrentConstants.DashboardConstants.LOW_BATTERY_LEVEL;
      }
    else
      {
      DriveConstants.TOP_LEFT_MOTOR_ID = PreviousConstants.DriveConstants.TOP_LEFT_MOTOR_ID;
      DriveConstants.TOP_RIGHT_MOTOR_ID = PreviousConstants.DriveConstants.TOP_RIGHT_MOTOR_ID;
      DriveConstants.BOTTOM_LEFT_MOTOR_ID = PreviousConstants.DriveConstants.BOTTOM_LEFT_MOTOR_ID;
      DriveConstants.BOTTOM_RIGHT_MOTOR_ID = PreviousConstants.DriveConstants.BOTTOM_RIGHT_MOTOR_ID;
      DriveConstants.DISTANCE_PER_PULSE = PreviousConstants.DriveConstants.DISTANCE_PER_PULSE;
      DriveConstants.MOTOR_CONTROLLER_GROUPS_INVERTED = PreviousConstants.DriveConstants.MOTOR_CONTROLLER_GROUPS_INVERTED;
      DriveConstants.JOYSTICK_DEADBAND = PreviousConstants.DriveConstants.JOYSTICK_DEADBAND;
      DriveConstants.DEFAULT_GEAR = PreviousConstants.DriveConstants.DEFAULT_GEAR;
      DriveConstants.GEAR_DOWN_BUTTON_ID = PreviousConstants.DriveConstants.GEAR_DOWN_BUTTON_ID;
      DriveConstants.GEAR_UP_BUTTON_ID = PreviousConstants.DriveConstants.GEAR_UP_BUTTON_ID;
      DriveConstants.ACCELERATION_RATE_LIMIT = PreviousConstants.DriveConstants.ACCELERATION_RATE_LIMIT;
      DriveConstants.BRAKE_RATE_LIMIT = PreviousConstants.DriveConstants.BRAKE_RATE_LIMIT;
      DriveConstants.DRIVE_STRAIGHT_CORRECTION_DELTA = PreviousConstants.DriveConstants.DRIVE_STRAIGHT_CORRECTION_DELTA;

      AutonomousConstants.USE_AUTO_SWITCH_ID = CurrentConstants.AutonomousConstants.USE_AUTO_SWITCH_ID;
      AutonomousConstants.AUTO_SIX_POSITION_SWITCH_IDS = CurrentConstants.AutonomousConstants.AUTO_SIX_POSITION_SWITCH_IDS;
      AutonomousConstants.AUTO_DELAY_POT_ID = CurrentConstants.AutonomousConstants.AUTO_DELAY_POT_ID;
      AutonomousConstants.AUTO_MIN_DELAY_SECONDS = CurrentConstants.AutonomousConstants.AUTO_MIN_DELAY_SECONDS;
      AutonomousConstants.AUTO_MAX_DELAY_SECONDS = CurrentConstants.AutonomousConstants.AUTO_MAX_DELAY_SECONDS;
      AutonomousConstants.AUTO_MAX_DRIVE_SPEED = CurrentConstants.AutonomousConstants.AUTO_MAX_DRIVE_SPEED;
      AutonomousConstants.DEFAULT_AUTONOMOUS_MODE = CurrentConstants.AutonomousConstants.DEFAULT_AUTONOMOUS_MODE;
      AutonomousConstants.CHECK_MODE_FROM_DASHBOARD = CurrentConstants.AutonomousConstants.CHECK_MODE_FROM_DASHBOARD;

      JoystickConstants.LEFT_DRIVER_JOYSTICK_ID = PreviousConstants.JoystickConstants.LEFT_DRIVER_JOYSTICK_ID;
      JoystickConstants.RIGHT_DRIVER_JOYSTICK_ID = PreviousConstants.JoystickConstants.RIGHT_DRIVER_JOYSTICK_ID;
      JoystickConstants.LEFT_OPERATOR_JOYSTICK_ID = PreviousConstants.JoystickConstants.LEFT_OPERATOR_JOYSTICK_ID;
      JoystickConstants.RIGHT_OPERATOR_JOYSTICK_ID = PreviousConstants.JoystickConstants.RIGHT_OPERATOR_JOYSTICK_ID;

      CameraConstants.USING_TWO_CAMERAS = PreviousConstants.CameraConstants.USING_TWO_CAMERAS;
      CameraConstants.SWITCH_CAMERA_BUTTON_ID = PreviousConstants.CameraConstants.SWITCH_CAMERA_BUTTON_ID;
      CameraConstants.RESOLUTION = PreviousConstants.CameraConstants.RESOLUTION;
      CameraConstants.FRAMES_PER_SECOND = PreviousConstants.CameraConstants.FRAMES_PER_SECOND;
      CameraConstants.COMPRESSION = PreviousConstants.CameraConstants.COMPRESSION;

      DashboardConstants.LOW_BATTERY_LEVEL = PreviousConstants.DashboardConstants.LOW_BATTERY_LEVEL;
      }
  }
  }
