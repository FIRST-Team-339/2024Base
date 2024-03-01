package frc.robot.commands.autonomous;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.DashboardSubsystem;
import frc.robot.enums.DriveGears;
import frc.robot.subsystems.TankSubsystem;

/**
 * The {@code AutonomousBase} Class is used as the base class for Autonomous
 * Commands
 * 
 * <p>
 * It's purpose is to handle the Delay Potentiometer before running
 * </p>
 */
public abstract class AutonomousCommandBase extends Command
    {
    /* Auto Delay Timer */
    private Timer autoDelayTimer = new Timer();
    private boolean autoDelayTimerStarted = false;

    /* Subsystems */
    protected TankSubsystem tankSubsystem;
    protected DashboardSubsystem dashboardSubsystem;

    /**
     * Autonomous Speed (can be changed) but comes with a default value of `0.5`
     */
    protected double autonomousSpeed = 0.5;

    protected AutonomousCommandBase(TankSubsystem tankSubsystem, DashboardSubsystem dashboardSubsystem)
        {
            this.tankSubsystem = tankSubsystem;
            this.dashboardSubsystem = dashboardSubsystem;

            addRequirements(tankSubsystem, dashboardSubsystem);
        }

    @Override
    public final void initialize()
    {
        // Do something at the start of every autonomous, such as resetting
        tankSubsystem.setMaxOutput(1.0);
        tankSubsystem.resetEncoders();
    }

    @Override
    public final void execute()
    {
        if (autoDelayTimerStarted == false) {
            autoDelayTimer.start();
        } else {
            if (autoDelayTimer.hasElapsed(dashboardSubsystem.getAutonomousDelay())) {
                executeAutonomous();
            } else {
                tankSubsystem.drive(0, 0);
            }
        }
    }

    public void endAutonomous()
    {
        // Do stuff before autonomous is ended
        cancel();
        tankSubsystem.setGear(DriveGears.GEAR1);
    }

    /**
     * The main body of an autonomous command. Called repeatedly while the
     * command is scheduled once the {@link #autoDelayTimer} has elappsed the
     * set time.
     */
    public abstract void executeAutonomous();

    public static DashboardSubsystem.AutonomousModeOptionSupplier[] getAutonomousOptions()
    {
        return null;
    }

    /**
     * Method that is called when the selected command option is updated
     * 
     * It has a default implementation of doing ABSOLUTELY NOTHING, just so
     * WPILib doesn't go kaboom
     * 
     * If you need to use this method, implement it yourself in the command
     */
    public void updateCommandOption(final int commandOptionId)
    {
    }
    }
