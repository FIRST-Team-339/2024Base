package frc.robot.commands.teleop;

import edu.wpi.first.wpilibj2.command.Command;
import java.util.function.DoubleSupplier;

import frc.robot.subsystems.TankSubsystem;

public class Drive extends Command
        {
        /* Subsystems */
        private TankSubsystem tankSubsystem;

        /* Left + Right Drive */
        private DoubleSupplier leftDrive;
        private DoubleSupplier rightDrive;

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
        }

        }
