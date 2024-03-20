package frc.robot.commands.autonomous;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.DriverStation.Alliance;
import frc.robot.subsystems.DashboardSubsystem;
import frc.robot.subsystems.FlipperPistonSubsystem;
import frc.robot.subsystems.TankSubsystem;

public class ScoreAmp extends AutonomousCommandBase
    {
    /* Subsystem */
    private FlipperPistonSubsystem flipperPistonSubsystem;

    /* Auto Command State */
    private enum AutoCommandState
        {
        RESET_ENCODERS_1, ACCELERATE, DRIVE_1, BRAKE_1, RESET_ENCODERS_2, REVERSE, BRAKE_2, RESET_ENCODERS_3, PIVOT, RESET_ENCODERS_4, DRIVE_2, BRAKE_3, START_TIMER, CHECK_TIMER, FLIP_UP, END
        }

    private AutoCommandState autoCommandState = AutoCommandState.ACCELERATE;

    /*
     * Drive Forward 1 Distance
     */
    public static int driveForwardDistance1 = 51;

    /**
     * Drive Rerverse Distance
     */

    public static int driveReverseDistance = -21;
    /**
     * either
     * 
     * Anyways, it gets information from the DS (Driver Station) & FMS (Field
     * Management System) and if it is blue, it pivots left, otherwise it pivots
     * right
     */
    public static int pivotAmount = DriverStation.getAlliance()
            .get() == Alliance.Blue ? -75 : 75;

    /*
     * Drive Forward 2 Distance
     */
    public static int driveForwardDistance2 = 17;

    private Timer pistonDelayTimer = new Timer();
    private double pistonDelayTime = 1.0;

    /**
     * Constructor
     * 
     * <p>
     * Sets {@link TankSubsystem}, {@link DashboardSubsystem}, and
     * {@link FlipperPistonSubsystem}
     * </p>
     */
    public ScoreAmp(TankSubsystem tankSubsystem,
            DashboardSubsystem dashboardSubsystem,
            FlipperPistonSubsystem flipperPistonSubsystem)
        {
            super(tankSubsystem, dashboardSubsystem);
            this.flipperPistonSubsystem = flipperPistonSubsystem;

            addRequirements(flipperPistonSubsystem);
        }

    public void executeAutonomous()
    {
        switch (autoCommandState)
            {
            case RESET_ENCODERS_1:
                tankSubsystem.resetEncoders();

                autoCommandState = AutoCommandState.ACCELERATE;
            case ACCELERATE:
                if (tankSubsystem.accelerate(this.autonomousSpeed) == true)
                    {
                    autoCommandState = AutoCommandState.DRIVE_1;
                    }
                break;
            case DRIVE_1:
                if (tankSubsystem.driveStraightInches(driveForwardDistance1,
                        this.autonomousSpeed, false, true) == true)
                    {
                    autoCommandState = AutoCommandState.RESET_ENCODERS_2;
                    }
                break;
            case BRAKE_1:
                // TODO: Fix Braking
                if (tankSubsystem.brake(this.autonomousSpeed) == true)
                    {
                    autoCommandState = AutoCommandState.RESET_ENCODERS_2;
                    }
                break;
            case RESET_ENCODERS_2:
                tankSubsystem.resetEncoders();

                autoCommandState = AutoCommandState.REVERSE;
                break;
            case REVERSE:
                if (tankSubsystem.driveStraightInches(driveReverseDistance,
                        -this.autonomousSpeed, false, true) == true)
                    {
                    autoCommandState = AutoCommandState.RESET_ENCODERS_3;
                    }
                break;
            case BRAKE_2:
                // TODO: Fix Braking
                if (tankSubsystem.brake(this.autonomousSpeed) == true)
                    {
                    autoCommandState = AutoCommandState.RESET_ENCODERS_3;
                    }
                break;
            case RESET_ENCODERS_3:
                tankSubsystem.resetEncoders();

                autoCommandState = AutoCommandState.PIVOT;
                break;
            case PIVOT:
                if (tankSubsystem.pivotDegrees(pivotAmount,
                        this.autonomousSpeed) == true)
                    {
                    autoCommandState = AutoCommandState.RESET_ENCODERS_4;
                    System.out.println("LEncode = " + tankSubsystem.getLeftEncoder().getDistance());
                    System.out.println("REncode = " + tankSubsystem.getRightEncoder().getDistance());
                    }
                break;
            case RESET_ENCODERS_4:
                tankSubsystem.resetEncoders();

                autoCommandState = AutoCommandState.DRIVE_2;
                break;
            case DRIVE_2:
                if (tankSubsystem.driveStraightInches(driveForwardDistance2,
                        this.autonomousSpeed - 0.1, false, true) == true)
                    {
                    autoCommandState = AutoCommandState.START_TIMER;
                    }
                break;
            case BRAKE_3:
                // TODO: Fix Braking
                if (tankSubsystem.brake(this.autonomousSpeed) == true)
                    {
                    autoCommandState = AutoCommandState.START_TIMER;
                    pistonDelayTimer.reset();
                    }
                break;
            case START_TIMER:
                pistonDelayTimer.start();
                autoCommandState = AutoCommandState.CHECK_TIMER;
                break;
            case CHECK_TIMER:
                if (pistonDelayTimer.hasElapsed(pistonDelayTime) == true)
                    {
                    autoCommandState = AutoCommandState.FLIP_UP;
                    }
                break;
            case FLIP_UP:
                flipperPistonSubsystem.flipUp();
                autoCommandState = AutoCommandState.END;
                break;
            case END:
                /* Motor Safety - ensure that motors stay at 0 */
                tankSubsystem.drive(0, 0);
                break;
            }
    }
    }
