package frc.robot.commands.teleop;

import edu.wpi.first.wpilibj2.command.Command;
import java.util.function.DoubleSupplier;
import frc.robot.hardwareInterfaces.Transmission.oldTankTransmission;
import frc.robot.hardwareInterfaces.drive.oldDrive;
import com.playingwithfusion.CANVenom;
import com.playingwithfusion.CANVenom.BrakeCoastMode;
import frc.robot.Constants.*;
import frc.robot.hardwareInterfaces.drive.oldDrive;
import frc.robot.hardwareInterfaces.KilroyEncoder;

import frc.robot.subsystems.TankSubsystem;

public class Drive extends Command
        {
        /* Subsystems */
        /* Left + Right Drive */
        private DoubleSupplier leftDrive;
        private DoubleSupplier rightDrive;

        private TankSubsystem tankSubsystem;

        // // Remove if follow works - RAB
        // /* Motors on the Left Side */
        // private CANVenom frontLeftMotorDrive = new CANVenom(
        // DriveConstants.FRONT_LEFT_MOTOR_ID);
        // private CANVenom rearLeftMotorDrive = new CANVenom(
        // DriveConstants.REAR_LEFT_MOTOR_ID);

        // /* Motors on the Right Side */
        // private CANVenom frontRightMotorDrive = new CANVenom(
        // DriveConstants.FRONT_RIGHT_MOTOR_ID);
        // private CANVenom rearRightMotorDrive = new CANVenom(
        // DriveConstants.REAR_RIGHT_MOTOR_ID);

        // /* transmission */
        // public oldTankTransmission transmission = new oldTankTransmission(
        // rearLeftMotorDrive, rearRightMotorDrive,
        // frontLeftMotorDrive, frontRightMotorDrive);

        // /* The Drive Encoder on the Left Side */
        // private KilroyEncoder leftEncoder = new KilroyEncoder(
        // frontLeftMotorDrive);

        // /* The Drive Encoder on the Left Side */
        // private KilroyEncoder rightEncoder = new KilroyEncoder(
        // frontRightMotorDrive);

        // public oldDrive drive = new oldDrive(transmission, leftEncoder,
        // rightEncoder, tankSubsystem.getGyro());
        // // end follow - RAB

        /**
         * Constructor
         * 
         * Sets {@link TankSubsystem} and the two drive {@link DoubleSupplier}'s
         */
        public Drive(TankSubsystem tankSubsystem, DoubleSupplier leftDrive,
                        DoubleSupplier rightDrive)
                {
                        addRequirements(tankSubsystem);
                        this.tankSubsystem = tankSubsystem;

                        this.leftDrive = leftDrive;
                        this.rightDrive = rightDrive;
                }

        @Override
        public void execute()
        {
                double leftSpeed = -leftDrive.getAsDouble();
                double rightSpeed = -rightDrive.getAsDouble();

                tankSubsystem.drive(leftSpeed, rightSpeed);
                // Remove if follow works - RAB
                // drive.drive(leftSpeed, rightSpeed);
                // end follow - RAB
        }

        }
