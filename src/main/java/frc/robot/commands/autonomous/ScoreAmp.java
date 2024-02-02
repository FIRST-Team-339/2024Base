package frc.robot.commands.autonomous;

import frc.robot.subsystems.TankSubsystem;

public class ScoreAmp extends AutonomousCommandBase
    {
    /* Auto Command State */
    private enum AutoCommandState
        {
        RESET_ENCODERS_1, ACCELERATE, DRIVE, BRAKE, RESET_ENCODERS_2, REVERSE, END
        }

    private AutoCommandState autoCommandState = AutoCommandState.RESET_ENCODERS_1;

    /*
     * Drive Distance, supposed to be 46 inches but it is shaved off a little to
     * account for braking
     */
    public static int driveForwardDistance = 40;

    /**
     * Rerverse Distance, supposed to be x inches but it is shaved off a little
     * to account for braking
     * 
     * @param tankSubsystem
     */
    public static int reverseDistance = 40;

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
            case RESET_ENCODERS_1:
                tankSubsystem.resetEncoders();

                autoCommandState = AutoCommandState.ACCELERATE;
                break;
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
                    autoCommandState = AutoCommandState.BRAKE;
                    }
                break;
            case BRAKE:
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
                // if (tankSubsystem.driveStraightInches(reverseDistance, 0.5,
                // false) == true)
                // {
                // autoCommandState = AutoCommandState.BRAKE;
                // }
                tankSubsystem.turnDegrees(90, autonomousSpeed, autonomousSpeed);
            case END:
                /* Motor Safety - ensure that motors stay at 0 */
                tankSubsystem.drive(0, 0);
                break;
            }
    }
    }
