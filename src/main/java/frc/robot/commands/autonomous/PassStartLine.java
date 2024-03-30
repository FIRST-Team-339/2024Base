package frc.robot.commands.autonomous;

import frc.robot.subsystems.DashboardSubsystem;
import frc.robot.subsystems.TankSubsystem;

public class PassStartLine extends AutonomousCommandBase
    {
    /* Auto Command State */
    private enum AutoCommandState
        {
        RESET_ENCODERS, ACCELERATE, DRIVE, BRAKE, REVERSE, END
        }

    public static enum PassStartLineCommandOptions implements DashboardSubsystem.AutonomousModeOptionSupplier {
        REGULAR(0, "Regular"), GO_FARTHER(1, "Go the Extra Mile"), GO_FARTHER_BACKUP(2, "Go farther and back up");

        private int id;
        private String friendlyName;

        private PassStartLineCommandOptions(int id, String friendlyName)
            {
                this.id = id;
                this.friendlyName = friendlyName;
            }

        public int getId()
        {
            return this.id;
        }

        @Override
        public String toString()
        {
            return friendlyName;
        }

        /**
         * Get the autonomous mode option based on the ID
         * 
         * @return An {@link PassStartLineCommandOptions} enum
         */
        public static PassStartLineCommandOptions getFromId(final int id)
        {
            for (PassStartLineCommandOptions autonomousModeOption : values())
                {
                if (autonomousModeOption.id == id)
                    {
                    return autonomousModeOption;
                    }
                }
            return null;
        }
    } 
    public PassStartLineCommandOptions commandOptionState = PassStartLineCommandOptions.REGULAR;

    private AutoCommandState autoCommandState = AutoCommandState.RESET_ENCODERS;

    /**
     * Default Drive Distance (50in)
     */
    public static int defaultDriveDistance = 50;

    /**
     * Farther Drive Distance (65in)
     */
    public static int fartherDriveDistance = 65;

    /**
     * Reverse Distance
     */
    public static int reverseDistance = -12;

    /**
     * Constructor
     * 
     * <p>
     * Sets {@link TankSubsystem} and {@link DashboardSubsystem}
     * </p>
     */
    public PassStartLine(TankSubsystem tankSubsystem, DashboardSubsystem dashboardSubsystem)
        {
            super(tankSubsystem, dashboardSubsystem);
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
                if (tankSubsystem.accelerate(this.autonomousSpeed) == true)
                    {
                    autoCommandState = AutoCommandState.DRIVE;
                    }
                break;
            case DRIVE:
                switch (commandOptionState) {
                    case REGULAR:
                        if (tankSubsystem.driveStraightInches(defaultDriveDistance,
                            this.autonomousSpeed, false, true) == true)
                        {
                        autoCommandState = AutoCommandState.END;
                        }
                        break;
                    case GO_FARTHER:
                        if (tankSubsystem.driveStraightInches(fartherDriveDistance,
                            this.autonomousSpeed, false, true) == true)
                        {
                        autoCommandState = AutoCommandState.END;
                        }
                        break;
                    case GO_FARTHER_BACKUP:
                        if (tankSubsystem.driveStraightInches(fartherDriveDistance,
                            this.autonomousSpeed, false, true) == true)
                        {
                        autoCommandState = AutoCommandState.REVERSE;
                        }
                        break;
                }
                break;
            case BRAKE:
                //TODO: Fix Braking
                if (tankSubsystem.brake(this.autonomousSpeed) == true)
                    {
                    autoCommandState = AutoCommandState.END;
                    }
                break;
            case REVERSE:
                if (tankSubsystem.driveStraightInches(reverseDistance,
                    this.autonomousSpeed, false, true) == true)
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

    public static DashboardSubsystem.AutonomousModeOptionSupplier[] getAutonomousOptions() {
        return PassStartLineCommandOptions.values();
    }

    public void updateCommandOption(final int commandOptionId)
    {
        this.commandOptionState = PassStartLineCommandOptions.getFromId(commandOptionId);
    }
    }
