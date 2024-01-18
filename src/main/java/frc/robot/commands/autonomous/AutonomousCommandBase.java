package frc.robot.commands.autonomous;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.TankSubsystem;

/**
 * The {@code AutonomousBase} Class is used as the base class for Autonomous
 * Commands
 * 
 * <p>
 * It's purpose is to handle the Delay Potentiometer before running
 * </p>
 */
public abstract class AutonomousCommandBase extends CommandBase
    {

    /* Subsystems */
    protected TankSubsystem tankSubsystem;

    protected AutonomousCommandBase(TankSubsystem tankSubsystem)
        {
            this.tankSubsystem = tankSubsystem;
        }

    @Override
    public final void initialize()
    {
        // Do something at the start of every autonomous, such as resetting
        // pistons
    }

    @Override
    public final void execute()
    {
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

    }
