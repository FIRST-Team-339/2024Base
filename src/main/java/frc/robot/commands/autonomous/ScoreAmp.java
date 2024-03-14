package frc.robot.commands.autonomous;

import com.ctre.phoenix.platform.can.AutocacheState;

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
    public static int driveForwardDistance1 = 54;

    /**
     * Drive Rerverse Distance
     */
    public static int driveReverseDistance = -24;

    /**
     * The delay between stopping and flipping the pizza box up
     */
    public static double pistonDelayTime = 3.0;

    private Timer pistonDelayTimer = new Timer();

    /**
     * It is NOT supposed to turn 75 degrees, but rather 90 degrees,
     * unfortunately I don't know what is wrong with that and I don't have time
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
    public static int driveForwardDistance2 = 27;

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
                        this.autonomousSpeed, false) == true)
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
                        -this.autonomousSpeed, false) == true)
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
                    }
                break;
            case RESET_ENCODERS_4:
                tankSubsystem.resetEncoders();

                autoCommandState = AutoCommandState.DRIVE_2;
                break;
            case DRIVE_2:
                if (tankSubsystem.driveStraightInches(driveForwardDistance2,
                        this.autonomousSpeed, false) == true)
                    {
                    autoCommandState = AutoCommandState.START_TIMER;
                    }
                break;
            case BRAKE_3:
                // TODO: Fix Braking
                if (tankSubsystem.brake(this.autonomousSpeed) == true)
                    {
                    autoCommandState = AutoCommandState.START_TIMER;
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
