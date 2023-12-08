package frc.robot.commands.autonomous;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.hardwareInterfaces.Potentiometer;
import frc.robot.subsystems.TankSubsystem;
import frc.robot.Constants.*;

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
    private static Potentiometer autoDelayPotentiometer = new Potentiometer(
            AutonomousConstants.AUTO_DELAY_POT_ID);
    private static Timer autoDelayTimer = new Timer();
    private static boolean autoDelayTimerStarted = false;

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
        /* Handle Delay Potentiometer */
        if (autoDelayTimerStarted == false)
            {
            System.out.println("Start auto delay timer");
            autoDelayTimer.start();
            autoDelayTimerStarted = true;
            this.tankSubsystem.drive(0, 0);
            }
        else
            {
            if (autoDelayTimer.hasElapsed(autoDelayPotentiometer.get(
                    AutonomousConstants.AUTO_MIN_DELAY_SECONDS,
                    AutonomousConstants.AUTO_MAX_DELAY_SECONDS)))
                {
                // System.out.println(
                // "Auto delay has elappsed, calling executeAutonomous()");
                executeAutonomous();
                }
            else
                {
                // This is for motor safety purposes
                this.tankSubsystem.drive(0, 0);
                }
            }
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
