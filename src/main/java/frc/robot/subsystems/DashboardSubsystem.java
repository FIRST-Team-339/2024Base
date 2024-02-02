package frc.robot.subsystems;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.wpilibj.smartdashboard.Field2d;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class DashboardSubsystem extends SubsystemBase
    {
    /* Field2d */
    private Field2d field = new Field2d();

    /**
     * Constructor
     */
    public DashboardSubsystem()
        {
            SmartDashboard.putData("Field", field);
        }

    public void updatePose(final Pose2d newPose)
    {
        field.setRobotPose(newPose);
    }
    }
