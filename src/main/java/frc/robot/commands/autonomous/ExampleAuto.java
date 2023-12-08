package frc.robot.commands.autonomous;

import frc.robot.subsystems.TankSubsystem;

public class ExampleAuto extends AutonomousCommandBase
    {
    /* Auto Command State */
    private enum AutoCommandState
        {
        START, END
        }

    private AutoCommandState autoCommandState = AutoCommandState.START;

    /**
     * Constructor
     * 
     * <p>
     * Sets {@link TankSubsystem}
     * </p>
     */
    public ExampleAuto(TankSubsystem tankSubsystem)
        {
            super(tankSubsystem);
        }

    public void executeAutonomous()
    {
        switch (autoCommandState)
            {
            case START:
                System.out.println("Starting autonomous!");
                autoCommandState = AutoCommandState.END;
                break;
            case END:
                break;
            }
    }
    }
