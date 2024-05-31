package frc.robot.commands.teleop;

import edu.wpi.first.math.geometry.Pose3d;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.modules.LimelightHelpers;
import frc.robot.subsystems.TankSubsystem;

public class LimelightGo extends Command {
    private TankSubsystem tankSubsystem;

    public LimelightGo(TankSubsystem tankSubsystem)
    {
        this.tankSubsystem = tankSubsystem;
    }

    @Override
    public void execute() {
        // shutup, joysticks
        // this.tankSubsystem.getDefaultCommand().cancel();

       Pose3d blueAmpPose = LimelightHelpers.getTargetPose3d_RobotSpace("limelight");

       double targetOffsetAngle_Vertical = LimelightHelpers.getTY("limelight");
        // how many degrees back is your limelight rotated from perfectly vertical?
        double limelightMountAngleDegrees = 34.0;

        // distance from the center of the Limelight lens to the floor
        double limelightLensHeightInches = 14.0; 

        // distance from the target to the floor
        double goalHeightInches = 40.0; 

        double angleToGoalDegrees = limelightMountAngleDegrees + targetOffsetAngle_Vertical;
        double angleToGoalRadians = angleToGoalDegrees * (Math.PI / 180.0);

        //calculate distance
        double distanceFromLimelightToGoalInches = (goalHeightInches - limelightLensHeightInches) / Math.tan(angleToGoalRadians);
        System.out.println(distanceFromLimelightToGoalInches);
    }
}
