// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import frc.robot.commands.autonomous.AutonomousCommandBase;
import frc.robot.modules.AprilTagModule;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {
  private RobotContainer robotContainer;
  private AutonomousCommandBase autonomousCommand = null;
  private Thread aprilTagThread;

  /**
   * This function is run when the robot is first started up and should be used
   * for any initialization code.
   */
  @Override
  public void robotInit() {
    /*
     * Instantiate our RobotContainer. This will perform all our button
     * bindings, and put our autonomous chooser on the dashboard.
     */
    robotContainer = new RobotContainer();

    /* Initialize the Pose2d */
    robotContainer.dashboardSubsystem
        .updatePose(robotContainer.tankSubsystem.getPose());

    /* Initialize the AprilTag Detector */
    aprilTagThread = new Thread(
        AprilTagModule.getDetectorRunnable(robotContainer.cameraSubsystem));
    aprilTagThread.setDaemon(true);
    aprilTagThread.start();

    System.out.println("|*************************|");
    System.out.println("| Kilroy 2024 Has Started |");
    System.out.println("|*************************|");
  }

  /**
   * This function is called every 20 ms, no matter the mode. Use this for items
   * like diagnostics that you want ran during disabled, autonomous,
   * teleoperated and test.
   *
   * <p>
   * This runs after the mode specific periodic functions, but before LiveWindow
   * and SmartDashboard integrated updating.
   */
  @Override
  public void robotPeriodic() {
    // Runs the Scheduler. This is responsible for polling buttons, adding
    // newly-scheduled
    // commands, running already-scheduled commands, removing finished or
    // interrupted commands,
    // and running subsystem periodic() methods. This must be called from the
    // robot's periodic
    // block in order for anything in the Command-based framework to work.
    CommandScheduler.getInstance().run();

    /* Update the Pose2d */
    robotContainer.dashboardSubsystem
        .updatePose(robotContainer.tankSubsystem.getPose());
  }

  /** This function is called once each time the robot enters Disabled mode. */
  @Override
  public void disabledInit() {
  }

  @Override
  public void disabledPeriodic() {
  }

  /**
   * This autonomous runs the autonomous command selected by your
   * {@link RobotContainer} class.
   */
  @Override
  public void autonomousInit() {
    autonomousCommand = robotContainer.getAutonomousCommand();

    /* Schedule the autonomous command */
    if (autonomousCommand != null) {
      autonomousCommand.schedule();
    }
    // ==============================
    // All user code goes above here
    // ==============================
    System.out.println("|************************************|");
    System.out.println("| Kilroy 2024 Autonomous Has Started |");
    System.out.println("|************************************|");
  }

  /** This function is called periodically during autonomous. */
  @Override
  public void autonomousPeriodic() {
  }

  @Override
  public void teleopInit() {
    /*
     * This makes sure that the autonomous stops running when teleop starts
     * running. If you want the autonomous to continue until interrupted by
     * another command, remove this line or comment it out.
     */
    if (autonomousCommand != null) {
      autonomousCommand.endAutonomous();
    }

    // ==============================
    // All user code goes above here
    // ==============================
    System.out.println("|********************************|");
    System.out.println("| Kilroy 2024 Teleop Has Started |");
    System.out.println("|********************************|");
  }

  /** This function is called periodically during operator control. */
  @Override
  public void teleopPeriodic() {
  }

  @Override
  public void testInit() {
    // Cancels all running commands at the start of test mode.
    CommandScheduler.getInstance().cancelAll();
  }

  /** This function is called periodically during test mode. */
  @Override
  public void testPeriodic() {
  }

  /** This function is called once when the robot is first started up. */
  @Override
  public void simulationInit() {
  }

  /** This function is called periodically whilst in simulation. */
  @Override
  public void simulationPeriodic() {
  }
}
