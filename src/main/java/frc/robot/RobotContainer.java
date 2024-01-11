package frc.robot;

import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.button.CommandJoystick;
import frc.robot.Constants.*;
// import frc.robot.commands.*;
import frc.robot.commands.autonomous.*;
import frc.robot.enums.*;
import frc.robot.hardwareInterfaces.*;
import frc.robot.subsystems.*;
import frc.robot.subsystems.DashboardSubsystem.AutonomousMode;

public class RobotContainer {
  /* Joysticks */
  private CommandJoystick leftDriverJoystick = new CommandJoystick(
      JoystickConstants.LEFT_DRIVER_JOYSTICK_ID);
  private CommandJoystick rightDriverJoystick = new CommandJoystick(
      JoystickConstants.RIGHT_DRIVER_JOYSTICK_ID);
  private CommandJoystick leftOperatorJoystick = new CommandJoystick(
      JoystickConstants.LEFT_OPERATOR_JOYSTICK_ID);
  private CommandJoystick rightOperatorJoystick = new CommandJoystick(
      JoystickConstants.RIGHT_OPERATOR_JOYSTICK_ID);

  /* Autonomous Software */
  // TODO: Have some way in dashboard to check
  private boolean autonomousEnabled = true;
  private SendableChooser<Integer> autonomousChooser = new SendableChooser<Integer>();

  /* Dashboard Subsystem */
  private DashboardSubsystem dashboardSubsystem = new DashboardSubsystem();

  /* Teleop Drive & Tank Subsystem w/ gears */
  private TankSubsystem tankSubsystem = new TankSubsystem();

  public RobotContainer() {
    /* Autonomous Mode on Dashboard */
    for (AutonomousModes autonomousMode : AutonomousModes.values()) {
      int autonomousModeId = autonomousMode.getId();

      if (autonomousModeId == 0) {
        autonomousChooser.setDefaultOption(autonomousMode.toString(),
            autonomousModeId);
      } else {
        autonomousChooser.addOption(autonomousMode.toString(),
            autonomousModeId);
      }
    }
    SmartDashboard.putData("Auto Selector", autonomousChooser);

    /* Configure Button Bindings */
    configureButtonBindings();
  }

  private void configureButtonBindings() {

  }

  public AutonomousCommandBase getAutonomousCommand() {
    AutonomousCommandBase autonomousCommand = null;

    if (autonomousEnabled) {
      int autonomousMode = autonomousChooser.getSelected();

      switch (autonomousMode) {
        /*
         * Position 1
         * 
         * When the robot is in the shorter length of the community, it will be
         * placed 4 inches away from the community line and drive forward 140
         * inches, then stop 16 inches away from the game piece.
         * 
         * Then stop.
         */
        case 0:
          dashboardSubsystem.updateAutoIndicator(AutonomousMode.Mode1);
          autonomousCommand = new ExampleAuto(tankSubsystem);
          break;
        default:
          System.out.println("No or Invalid Autonomous Selected");
          break;
      }
    }

    if (autonomousCommand != null) {
      autonomousCommand.addRequirements(tankSubsystem);
      tankSubsystem.setDefaultCommand(autonomousCommand);
    }
    return autonomousCommand;
  }
}