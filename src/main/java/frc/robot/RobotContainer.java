package frc.robot;

import edu.wpi.first.wpilibj2.command.button.CommandJoystick;
import frc.robot.Constants.*;
// import frc.robot.commands.*;

public class RobotContainer
    {
    /* Joysticks */
    private CommandJoystick leftDriverJoystick = new CommandJoystick(
            JoystickConstants.LEFT_DRIVER_JOYSTICK_ID);
    private CommandJoystick rightDriverJoystick = new CommandJoystick(
            JoystickConstants.RIGHT_DRIVER_JOYSTICK_ID);
    private CommandJoystick leftOperatorJoystick = new CommandJoystick(
            JoystickConstants.LEFT_OPERATOR_JOYSTICK_ID);
    private CommandJoystick rightOperatorJoystick = new CommandJoystick(
            JoystickConstants.RIGHT_OPERATOR_JOYSTICK_ID);

    /* Autonomous Hardware */

    public RobotContainer()
        {
        }

    }