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

    public static enum ScoreAmpCommandOptions implements DashboardSubsystem.AutonomousModeOptionSupplier {
        REGULAR(0, "Regular"), GO_FARTHER(1, "Back it up! (by 1 foot)");

        private int id;
        private String friendlyName;

        private ScoreAmpCommandOptions(int id, String friendlyName)
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
         * @return An {@link ScoreAmpCommandOptions} enum
         */
        public static ScoreAmpCommandOptions getFromId(final int id)
        {
            for (ScoreAmpCommandOptions autonomousModeOption : values())
                {
                if (autonomousModeOption.id == id)
                    {
                    return autonomousModeOption;
                    }
                }
            return null;
        }
    } 
    public ScoreAmpCommandOptions commandOptionState = ScoreAmpCommandOptions.REGULAR;

    private AutoCommandState autoCommandState = AutoCommandState.ACCELERATE;

    /*
     * Drive Forward 1 Distance
     */
    public static int driveForwardDistance1 = 49;

    /**
     * Drive Rerverse Distance
     */

    public static int driveReverseDistance = -24;
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
                if (tankSubsystem.driveStraightInches(this.commandOptionState == ScoreAmpCommandOptions.GO_FARTHER ? driveForwardDistance1 + 12 : driveForwardDistance1,
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
                        this.autonomousSpeed, false, true) == true)
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
                tankSubsystem.drive(0.275, 0.275, true);
                autoCommandState = AutoCommandState.END;
                break;
            case END:
                tankSubsystem.drive(0.275, 0.275, true);
                break;
            }
    }

    public static DashboardSubsystem.AutonomousModeOptionSupplier[] getAutonomousOptions() {
        return ScoreAmpCommandOptions.values();
    }

    public void updateCommandOption(final int commandOptionId)
    {
        this.commandOptionState = ScoreAmpCommandOptions.getFromId(commandOptionId);
    }
    }
