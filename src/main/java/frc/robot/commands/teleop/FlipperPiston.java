package frc.robot.commands.teleop;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.FlipperPistonSubsystem;

public class FlipperPiston extends CommandBase
    {
    /* Gear Shift State */
    public static enum FlipperPistonUpOrDown
        {
        UP, DOWN
        }

    private FlipperPistonSubsystem flipperPistonSubsystem;
    private FlipperPistonUpOrDown flipperPistonState;

    public FlipperPiston(final FlipperPistonSubsystem flipperPistonSubsystem,
            final FlipperPistonUpOrDown flipperPistonState)
        {
            addRequirements(flipperPistonSubsystem);
            this.flipperPistonSubsystem = flipperPistonSubsystem;
            this.flipperPistonState = flipperPistonState;
        }

    @Override
    public void execute()
    {
        /**
         * If the `flipperPistonState` value is set to UP, tell the Flipper
         * Piston Subsystem to flip up
         * 
         * Otherwise, tell the Flipper Piston Subsystem flip down
         */
        switch (flipperPistonState)
            {
            case UP:
                flipperPistonSubsystem.flipUp();
                break;
            case DOWN:
                flipperPistonSubsystem.flipDown();
                break;
            default:
                break;
            }

        // Cancels the command as it has ran
        cancel();
    }

    }
