// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import frc.robot.commands.autonomous.AutonomousCommandBase;
import frc.robot.constants.CurrentConstants;
import frc.robot.modules.AprilTagModule;
import frc.robot.modules.LimelightHelpers;
import frc.robot.subsystems.DashboardSubsystem;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot
  {
  private RobotContainer robotContainer;
  private AutonomousCommandBase autonomousCommand = null;
  private Thread aprilTagThread;
  private TalonSRX ShooterMotorTop = new TalonSRX(CurrentConstants.DriveConstants.SHOOTER_TOP_MOTOR_ID); 
  private TalonSRX ShooterMotorBottom = new TalonSRX(CurrentConstants.DriveConstants.SHOOTER_BOTTOM_MOTOR_ID);

  /**
   * This function is run when the robot is first started up and should be used
   * for any initialization code.
   */
  @Override
  public void robotInit()
  {
    /*
     * Instantiate our RobotContainer. This will perform all our button
     * bindings, and put our autonomous chooser on the dashboard.
     */
    robotContainer = new RobotContainer();

    /* Autonomous Mode Option Updates */
    robotContainer.dashboardSubsystem.setListener(
        DashboardSubsystem.ListenerType.AutonomousModeOption,
        this::onAutonomousModeOptionUpdate);

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
  public void robotPeriodic()
  {
    // Runs the Scheduler. This is responsible for polling buttons, adding
    // newly-scheduled
    // commands, running already-scheduled commands, removing finished or
    // interrupted commands,
    // and running subsystem periodic() methods. This must be called from the
    // robot's periodic
    // block in order for anything in the Command-based framework to work.
    CommandScheduler.getInstance().run();        

    int[] validIDs = {6};
    LimelightHelpers.SetFiducialIDFiltersOverride("limelight", validIDs);
    // System.out.println(this.robotContainer.tankSubsystem.getGyro().getAngle());
    LimelightHelpers.SetRobotOrientation("limelight", this.robotContainer.tankSubsystem.getGyro().getAngle(),0,0,0,0,0);
  }

  /** This function is called once each time the robot enters Disabled mode. */
  @Override
  public void disabledInit()
  {
  }

  @Override
  public void disabledPeriodic()
  {
  }

  public void onAutonomousModeOptionUpdate(final int newAutonomousModeOptionId)
  {
    // -1 signifies NONE, so ignore that
    // Also check that there is an autonomous command
    if (newAutonomousModeOptionId != -1 && autonomousCommand != null)
      {
      autonomousCommand.updateCommandOption(newAutonomousModeOptionId);
      }
  }

  /**
   * This autonomous runs the autonomous command selected by your
   * {@link RobotContainer} class.
   */
  @Override
  public void autonomousInit()
  {
    /* Initial States */
    robotContainer.flipperPistonSubsystem.flipDown();
    autonomousCommand = robotContainer.getAutonomousCommand();

    /* Schedule the autonomous command */
    if (autonomousCommand != null)
      {
      autonomousCommand.schedule();
      autonomousCommand.updateCommandOption(this.robotContainer.dashboardSubsystem.getAutonomousModeOption());
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
  public void autonomousPeriodic()
  {
  }

  @Override
  public void teleopInit()
  {
    /*
     * This makes sure that the autonomous stops running when teleop starts
     * running. If you want the autonomous to continue until interrupted by
     * another command, remove this line or comment it out.
     */
    if (autonomousCommand != null)
      {
      autonomousCommand.endAutonomous();
      autonomousCommand = null;
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
  public void teleopPeriodic()
  {
  }

  @Override
  public void testInit()
  {
    // Cancels all running commands at the start of test mode.
    CommandScheduler.getInstance().cancelAll();
  }

  /** This function is called periodically during test mode. */
  @Override
  public void testPeriodic()
  {
  }

  /** This function is called once when the robot is first started up. */
  @Override
  public void simulationInit()
  {
  }

  /** This function is called periodically whilst in simulation. */
  @Override
  public void simulationPeriodic()
  {
  }
  }
