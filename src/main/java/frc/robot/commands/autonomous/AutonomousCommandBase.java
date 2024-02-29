package frc.robot.commands.autonomous;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.DashboardSubsystem;
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

    /* Subsystems */
    protected TankSubsystem tankSubsystem;

    /**
     * Autonomous Speed (can be changed) but comes with a default value of `0.5`
     */
    protected double autonomousSpeed = 0.5;

    protected AutonomousCommandBase(TankSubsystem tankSubsystem)
        {
            this.tankSubsystem = tankSubsystem;

            addRequirements(tankSubsystem);
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
        executeAutonomous();
    }

    public void endAutonomous()
    {
        // Do stuff before autonomous is ended
        cancel();
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
     */
    public void updateCommandOption(final int commandOptionId)
    {
    }
    }
