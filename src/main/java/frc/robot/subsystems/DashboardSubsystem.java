package frc.robot.subsystems;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.networktables.*;
import edu.wpi.first.wpilibj.smartdashboard.Field2d;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.enums.AutonomousModes;

public class DashboardSubsystem extends SubsystemBase
    {
    /* Field2d */
    private Field2d field = new Field2d();

    /* Network Tables */
    private NetworkTableInstance instance = NetworkTableInstance.getDefault();
    private NetworkTable table = instance.getTable("Kilroy");
    private BooleanSubscriber autonomousEnabled = table
            .getBooleanTopic("/Auto/Enabled").subscribe(false);
    // TODO: populate options
    private StringArraySubscriber autonomousMode = table
            .getStringArrayTopic("/Auto/Selection").subscribe(new String[]
            { "" });
    // TODO: populate options
    private StringArraySubscriber autonomousModeOptions = table
            .getStringArrayTopic("/Auto/CommandOptions").subscribe(new String[]
            { "" });
    private DoubleSubscriber autonomousDelay = table
            .getDoubleTopic("/Auto/Delay").subscribe(0);
    private BooleanSubscriber demoEnabled = table
            .getBooleanTopic("/Demo/Enabled").subscribe(false);

    /**
     * Constructor
     */
    public DashboardSubsystem()
        {
            SmartDashboard.putData("Field", field);
        }

    public boolean getAutonomousEnabled()
    {
        throw new UnsupportedOperationException("Not implemented");
    }

    public AutonomousModes getAutonomousMode()
    {
        throw new UnsupportedOperationException("Not implemented");
    }

    public double getAutonomousDelay()
    {
        throw new UnsupportedOperationException("Not implemented");
    }

    public boolean getDemoModeEnabled()
    {
        throw new UnsupportedOperationException("Not implemented");
    }

    public void updatePose(final Pose2d newPose)
    {
        field.setRobotPose(newPose);
    }
    }
