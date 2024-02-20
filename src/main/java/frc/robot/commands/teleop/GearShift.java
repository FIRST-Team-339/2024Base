package frc.robot.commands.teleop;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.TankSubsystem;

public class GearShift extends Command
    {
    /* Gear Shift State */
    public static enum GearUpOrDown
        {
        UP, DOWN
        }

    private TankSubsystem tankSubsystem;
    private GearUpOrDown gearState;

    public GearShift(TankSubsystem tankSubsystem, GearUpOrDown gearState)
        {
            addRequirements(tankSubsystem);
            this.tankSubsystem = tankSubsystem;
            this.gearState = gearState;
        }

    @Override
    public void execute()
    {
        /**
         * If the `gearState` value is set to UP, tell the Tank Subsystem to
         * shift up a gear
         * 
         * Otherwise, tell the Tank Subsystem to shift down a gear
         */
        switch (gearState)
            {
            case UP:
                tankSubsystem.shiftGearUp();
                break;
            case DOWN:
                tankSubsystem.shiftGearDown();
                break;
            default:
                break;
            }

        // Cancels the command as it has ran
        cancel();
    }

    }
