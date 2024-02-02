package frc.robot.commands.teleop;

import edu.wpi.first.wpilibj2.command.CommandBase;
import java.util.function.DoubleSupplier;

import frc.robot.subsystems.DashboardSubsystem;
import frc.robot.subsystems.TankSubsystem;

public class Drive extends CommandBase
    {
    /* Subsystems */
    private TankSubsystem tankSubsystem;
    private DashboardSubsystem dashboardSubsystem;

    /* Left + Right Drive */
    private DoubleSupplier leftDrive;
    private DoubleSupplier rightDrive;

    /**
     * Constructor
     * 
     * Sets {@link TankSubsystem}, {@link DashboardSubsystem} and the two drive
     * {@link DoubleSupplier}'s
     */
    public Drive(TankSubsystem tankSubsystem,
            DashboardSubsystem dashboardSubsystem, DoubleSupplier leftDrive,
            DoubleSupplier rightDrive)
        {
            addRequirements(tankSubsystem, dashboardSubsystem);
            this.tankSubsystem = tankSubsystem;
            this.dashboardSubsystem = dashboardSubsystem;

            this.leftDrive = leftDrive;
            this.rightDrive = rightDrive;
        }

    @Override
    public void initialize()
    {
        // TODO: Dashboard stuff
    }

    @Override
    public void execute()
    {
        double leftSpeed = -leftDrive.getAsDouble();
        double rightSpeed = -rightDrive.getAsDouble();

        tankSubsystem.drive(leftSpeed, rightSpeed);
    }

    }
