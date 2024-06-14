package frc.robot.commands.teleop;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.geometry.Pose3d;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.modules.LimelightHelpers;
import frc.robot.subsystems.TankSubsystem;

public class LimelightGo extends Command {
    private TankSubsystem tankSubsystem;
    private PIDController pid = new PIDController(0.02, 0, 0);

    public LimelightGo(TankSubsystem tankSubsystem)
    {
        this.tankSubsystem = tankSubsystem;
        // Shuffleboard.getTab("PID").add(pid);
    }

    @Override
    public void execute() {
        // shutup, joysticks
        // this.tankSubsystem.getDefaultCommand().cancel();

    //    Pose3d blueAmpPose = LimelightHelpers.getTargetPose3d_RobotSpace("limelight");

       double targetOffsetAngle_Vertical = LimelightHelpers.getTY("limelight");
        // how many degrees back is your limelight rotated from perfectly vertical?
        double limelightMountAngleDegrees = 34.0;

        // distance from the center of the Limelight lens to the floor
        double limelightLensHeightInches = 12.5; 

        // distance from the target to the floor
        double goalHeightInches = 42.5; 

        double angleToGoalDegrees = limelightMountAngleDegrees + targetOffsetAngle_Vertical;
        double angleToGoalRadians = angleToGoalDegrees * (Math.PI / 180.0);

        //calculate distance
        double distanceFromLimelightToGoalInches = (goalHeightInches - limelightLensHeightInches) / Math.tan(angleToGoalRadians);
        var a = pid.calculate(distanceFromLimelightToGoalInches, 20);
        System.out.println("distance " + distanceFromLimelightToGoalInches);
        // System.out.println("vroomvroom " + a);
        // tankSubsystem.drive(a, a);
    }
}
