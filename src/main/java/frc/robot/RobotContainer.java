package frc.robot;

import java.util.function.Function;

import edu.wpi.first.wpilibj2.command.button.CommandJoystick;
import frc.robot.Constants.*;
import frc.robot.commands.teleop.*;
import frc.robot.commands.teleop.FlipperPiston.FlipperPistonUpOrDown;
import frc.robot.commands.autonomous.*;
import frc.robot.commands.teleop.GearShift.GearUpOrDown;
import frc.robot.subsystems.CameraSubsystem;
import frc.robot.subsystems.DashboardSubsystem;
import frc.robot.subsystems.FlipperPistonSubsystem;
import frc.robot.subsystems.TankSubsystem;

public class RobotContainer
        {
        /* Joysticks */
        private final CommandJoystick leftDriverJoystick = new CommandJoystick(
                        JoystickConstants.LEFT_DRIVER_JOYSTICK_ID);
        private final CommandJoystick rightDriverJoystick = new CommandJoystick(
                        JoystickConstants.RIGHT_DRIVER_JOYSTICK_ID);
        private final CommandJoystick leftOperatorJoystick = new CommandJoystick(
                        JoystickConstants.LEFT_OPERATOR_JOYSTICK_ID);
        private final CommandJoystick rightOperatorJoystick = new CommandJoystick(
                        JoystickConstants.RIGHT_OPERATOR_JOYSTICK_ID);

        /* Dashboard Subsystem */
        public final DashboardSubsystem dashboardSubsystem = new DashboardSubsystem();

        /* Camera */
        public final CameraSubsystem cameraSubsystem = new CameraSubsystem();
        private final Camera cameraCommand = new Camera(cameraSubsystem);

        /* Teleop Drive & Tank Subsystem w/ gears */
        public TankSubsystem tankSubsystem = new TankSubsystem();
        private Drive teleopDriveCommand = new Drive(tankSubsystem,
                        dashboardSubsystem, () -> leftDriverJoystick.getY(),
                        () -> rightDriverJoystick.getY());
        private GearShift gearUpCommand = new GearShift(tankSubsystem,
                        GearUpOrDown.UP);
        private GearShift gearDownCommand = new GearShift(tankSubsystem,
                        GearUpOrDown.DOWN);

        /* Flipper Piston Subsytem w/ Commands */
        private FlipperPistonSubsystem flipperPistonSubsystem = new FlipperPistonSubsystem();
        private FlipperPiston flipPistonUpCommand = new FlipperPiston(
                        flipperPistonSubsystem, FlipperPistonUpOrDown.UP);
        private FlipperPiston flipPistonDownCommand = new FlipperPiston(
                        flipperPistonSubsystem, FlipperPistonUpOrDown.DOWN);

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

                /* Configure Flipper Piston Buttons */
                leftOperatorJoystick.button(
                                FlipperPistonConstants.FLIP_DOWN_BUTTON_ID)
                                .onTrue(flipPistonDownCommand);
                rightOperatorJoystick.button(
                                FlipperPistonConstants.FLIP_UP_BUTTON_ID)
                                .onTrue(flipPistonUpCommand);
        }

        public AutonomousCommandBase getAutonomousCommand()
        {
                Function<TankSubsystem, AutonomousCommandBase> autonomousCommandConstructor = null;
                AutonomousCommandBase autonomousCommand = null;

                if (dashboardSubsystem.getAutonomousEnabled())
                        {
                        switch (dashboardSubsystem.getAutonomousMode())
                                {
                                case PASS_START_LINE:
                                        autonomousCommandConstructor = PassStartLine::new;
                                        break;
                                case SCORE_AMP:
                                        autonomousCommandConstructor = ScoreAmp::new;
                                        break;
                                default:
                                        System.out.println(
                                                        "INVALID AUTONOMOUS MODE");
                                        break;
                                }

                        if (autonomousCommandConstructor != null)
                                {
                                autonomousCommand = autonomousCommandConstructor
                                                .apply(tankSubsystem);

                                autonomousCommand
                                                .addRequirements(tankSubsystem);
                                }
                        }
                else
                        System.out.println("AUTONOMOUS MODE DISABLED");

                return autonomousCommand;
        }

        }