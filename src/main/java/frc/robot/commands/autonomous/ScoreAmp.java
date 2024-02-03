package frc.robot.commands.autonomous;

import frc.robot.subsystems.TankSubsystem;

public class ScoreAmp extends AutonomousCommandBase
    {
    /* Auto Command State */
    private enum AutoCommandState
        {
        ACCELERATE, DRIVE, BRAKE_1, RESET_ENCODERS_1, REVERSE, BRAKE_2, RESET_ENCODERS_2, PIVOT, END
        }

    private AutoCommandState autoCommandState = AutoCommandState.ACCELERATE;

    /*
     * Drive Distance, supposed to be 46 inches but it is shaved off a little to
     * account for braking
     */
    public static int driveForwardDistance = 40;

    /**
     * Rerverse Distance
     * 
     * @param tankSubsystem
     */
    public static int reverseDistance = -4;

    /**
     * Constructor
     * 
     * <p>
     * Sets {@link TankSubsystem}
     * </p>
     */
    public ScoreAmp(TankSubsystem tankSubsystem)
        {
            super(tankSubsystem);
        }

    public void executeAutonomous()
    {
        switch (autoCommandState)
            {
            case ACCELERATE:
                if (tankSubsystem.accelerate(this.autonomousSpeed) == true)
                    {
                    autoCommandState = AutoCommandState.DRIVE;
                    }
                break;
            case DRIVE:
                if (tankSubsystem.driveStraightInches(driveForwardDistance,
                        this.autonomousSpeed, false) == true)
                    {
                    autoCommandState = AutoCommandState.BRAKE_1;
                    }
                break;
            case BRAKE_1:
                if (tankSubsystem.brake(this.autonomousSpeed) == true)
                    {
                    autoCommandState = AutoCommandState.RESET_ENCODERS_1;
                    }
                break;
            case RESET_ENCODERS_1:
                tankSubsystem.resetEncoders();

                autoCommandState = AutoCommandState.REVERSE;
                break;
            case REVERSE:
                if (tankSubsystem.driveStraightInches(reverseDistance,
                        -this.autonomousSpeed, false) == true)
                    {
                    autoCommandState = AutoCommandState.BRAKE_2;
                    }
                break;
            case BRAKE_2:
                if (tankSubsystem.brake(this.autonomousSpeed) == true)
                    {
                    autoCommandState = AutoCommandState.RESET_ENCODERS_2;
                    }
                break;
            case RESET_ENCODERS_2:
                tankSubsystem.resetEncoders();

                autoCommandState = AutoCommandState.PIVOT;
                break;
            case PIVOT:
                if (tankSubsystem.pivotDegrees(90,
                        this.autonomousSpeed) == true)
                    {
                    autoCommandState = AutoCommandState.END;
                    }
                break;
            case END:
                /* Motor Safety - ensure that motors stay at 0 */
                tankSubsystem.drive(0, 0);
                break;
            }
    }
    }
