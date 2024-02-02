package frc.robot.commands.autonomous;

import frc.robot.subsystems.TankSubsystem;

public class PassStartLine extends AutonomousCommandBase
    {
    /* Auto Command State */
    private enum AutoCommandState
        {
        RESET_ENCODERS, ACCELERATE, DRIVE, BRAKE, END
        }

    private AutoCommandState autoCommandState = AutoCommandState.RESET_ENCODERS;

    /*
     * Drive Distance, supposed to be 46 inches but it is shaved off a little to
     * account for braking
     */
    public static int driveDistance = 40;

    /**
     * Constructor
     * 
     * <p>
     * Sets {@link TankSubsystem}
     * </p>
     */
    public PassStartLine(TankSubsystem tankSubsystem)
        {
            super(tankSubsystem);
        }

    public void executeAutonomous()
    {
        switch (autoCommandState)
            {
            case RESET_ENCODERS:
                tankSubsystem.resetEncoders();

                autoCommandState = AutoCommandState.ACCELERATE;
                break;
            case ACCELERATE:
                if (tankSubsystem.accelerate(0.5) == true)
                    {
                    autoCommandState = AutoCommandState.DRIVE;
                    }
                break;
            case DRIVE:
                if (tankSubsystem.driveStraightInches(driveDistance, 0.5,
                        false) == true)
                    {
                    autoCommandState = AutoCommandState.BRAKE;
                    }
                break;
            case BRAKE:
                if (tankSubsystem.brake(0.5) == true)
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