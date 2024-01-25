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
    public static int FRONT_LEFT_MOTOR_ID;
    public static int REAR_LEFT_MOTOR_ID;
    public static int FRONT_RIGHT_MOTOR_ID;
    public static int REAR_RIGHT_MOTOR_ID;

    /* Encoder */
    public static double DISTANCE_PER_PULSE;

    /* MOTOR CONTROLLER GROUPS */
    public static boolean[] MOTOR_CONTROLLER_GROUPS_INVERTED;

    /* JOYSTICK DEADBAND */
    public static double JOYSTICK_DEADBAND;

    /* Gears */

    /* Rate Limits */
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
      DriveConstants.MOTOR_CONTROLLER_GROUPS_INVERTED = CurrentConstants.DriveConstants.MOTOR_CONTROLLER_GROUPS_INVERTED;
      DriveConstants.JOYSTICK_DEADBAND = CurrentConstants.DriveConstants.JOYSTICK_DEADBAND;

      JoystickConstants.LEFT_DRIVER_JOYSTICK_ID = CurrentConstants.JoystickConstants.LEFT_DRIVER_JOYSTICK_ID;
      JoystickConstants.RIGHT_DRIVER_JOYSTICK_ID = CurrentConstants.JoystickConstants.RIGHT_DRIVER_JOYSTICK_ID;
      JoystickConstants.LEFT_OPERATOR_JOYSTICK_ID = CurrentConstants.JoystickConstants.LEFT_OPERATOR_JOYSTICK_ID;
      JoystickConstants.RIGHT_OPERATOR_JOYSTICK_ID = CurrentConstants.JoystickConstants.RIGHT_OPERATOR_JOYSTICK_ID;

      }
    else
      {
      DriveConstants.FRONT_LEFT_MOTOR_ID = PreviousConstants.DriveConstants.FRONT_LEFT_MOTOR_ID;
      DriveConstants.FRONT_RIGHT_MOTOR_ID = PreviousConstants.DriveConstants.FRONT_RIGHT_MOTOR_ID;
      DriveConstants.REAR_LEFT_MOTOR_ID = PreviousConstants.DriveConstants.REAR_LEFT_MOTOR_ID;
      DriveConstants.REAR_RIGHT_MOTOR_ID = PreviousConstants.DriveConstants.REAR_RIGHT_MOTOR_ID;
      DriveConstants.DISTANCE_PER_PULSE = PreviousConstants.DriveConstants.DISTANCE_PER_PULSE;
      DriveConstants.MOTOR_CONTROLLER_GROUPS_INVERTED = PreviousConstants.DriveConstants.MOTOR_CONTROLLER_GROUPS_INVERTED;
      DriveConstants.JOYSTICK_DEADBAND = PreviousConstants.DriveConstants.JOYSTICK_DEADBAND;
      JoystickConstants.LEFT_DRIVER_JOYSTICK_ID = PreviousConstants.JoystickConstants.LEFT_DRIVER_JOYSTICK_ID;
      JoystickConstants.RIGHT_DRIVER_JOYSTICK_ID = PreviousConstants.JoystickConstants.RIGHT_DRIVER_JOYSTICK_ID;
      JoystickConstants.LEFT_OPERATOR_JOYSTICK_ID = PreviousConstants.JoystickConstants.LEFT_OPERATOR_JOYSTICK_ID;
      JoystickConstants.RIGHT_OPERATOR_JOYSTICK_ID = PreviousConstants.JoystickConstants.RIGHT_OPERATOR_JOYSTICK_ID;

      }
  }
  }
