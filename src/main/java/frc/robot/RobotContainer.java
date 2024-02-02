package frc.robot;

import edu.wpi.first.wpilibj2.command.button.CommandJoystick;
import frc.robot.Constants.*;
import frc.robot.commands.teleop.*;
import frc.robot.commands.autonomous.*;
import frc.robot.commands.teleop.GearShift.GearUpOrDown;
import frc.robot.subsystems.CameraSubsystem;
import frc.robot.subsystems.DashboardSubsystem;
import frc.robot.subsystems.TankSubsystem;

public class RobotContainer
        {
        /* Joysticks */
        private CommandJoystick leftDriverJoystick = new CommandJoystick(
                        JoystickConstants.LEFT_DRIVER_JOYSTICK_ID);
        private CommandJoystick rightDriverJoystick = new CommandJoystick(
                        JoystickConstants.RIGHT_DRIVER_JOYSTICK_ID);
        private CommandJoystick leftOperatorJoystick = new CommandJoystick(
                        JoystickConstants.LEFT_OPERATOR_JOYSTICK_ID);
        private CommandJoystick rightOperatorJoystick = new CommandJoystick(
                        JoystickConstants.RIGHT_OPERATOR_JOYSTICK_ID);

        /* Dashboard Subsystem */
        public DashboardSubsystem dashboardSubsystem = new DashboardSubsystem();

        /* Camera */
        private CameraSubsystem cameraSubsystem = new CameraSubsystem();
        private Camera cameraCommand = new Camera(cameraSubsystem);

        /* Teleop Drive & Tank Subsystem w/ gears */
        public TankSubsystem tankSubsystem = new TankSubsystem();
        private Drive teleopDriveCommand = new Drive(tankSubsystem,
                        dashboardSubsystem, () -> leftDriverJoystick.getY(),
                        () -> rightDriverJoystick.getY());
        private GearShift gearUpCommand = new GearShift(tankSubsystem,
                        GearUpOrDown.UP);
        private GearShift gearDownCommand = new GearShift(tankSubsystem,
                        GearUpOrDown.DOWN);

        public RobotContainer()
                {
                        /* Initialize Teleop Drive & Tank Subsystem w/ gears */
                        tankSubsystem.setDefaultCommand(teleopDriveCommand);

                        /* Configure Button Bindings */
                        configureButtonBindings();
                }

        private void configureButtonBindings()
        {
                /* Configure Gear Buttons */
                rightDriverJoystick.button(DriveConstants.GEAR_UP_BUTTON_ID)
                                .onTrue(gearUpCommand);
                leftDriverJoystick.button(DriveConstants.GEAR_DOWN_BUTTON_ID)
                                .onTrue(gearDownCommand);

                /* Configure Camera Buttons */
                rightOperatorJoystick
                                .button(CameraConstants.SWITCH_CAMERA_BUTTON_ID)
                                .onTrue(cameraCommand);
        }

        public AutonomousCommandBase getAutonomousCommand()
        {
                // TODO: Check if autonomous is enabled
                // TODO: Get autonomous command from dashboard
                var test = new PassStartLine(tankSubsystem);
                test.addRequirements(tankSubsystem);

                return test;
        }

        }