package frc.robot;

import edu.wpi.first.wpilibj2.command.button.CommandJoystick;
import frc.robot.Constants.*;
import frc.robot.commands.teleop.*;
import frc.robot.commands.teleop.FlipperPiston.FlipperPistonUpOrDown;
import frc.robot.commands.autonomous.*;
import frc.robot.commands.teleop.GearShift.GearUpOrDown;
import frc.robot.enums.AutonomousModes;
import frc.robot.subsystems.CameraSubsystem;
import frc.robot.subsystems.DashboardSubsystem;
import frc.robot.subsystems.FlipperPistonSubsystem;
import frc.robot.subsystems.TankSubsystem;
import frc.robot.subsystems.DashboardSubsystem.AutonomousModeOptionSupplier;

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

        /* Camera */
        public final CameraSubsystem cameraSubsystem = new CameraSubsystem();
        private final Camera cameraCommand = new Camera(cameraSubsystem);

        /* Dashboard Subsystem */
        public final DashboardSubsystem dashboardSubsystem = new DashboardSubsystem(
                        cameraSubsystem);

        /* Teleop Drive & Tank Subsystem w/ gears */
        public final TankSubsystem tankSubsystem = new TankSubsystem();
        private final Drive teleopDriveCommand = new Drive(tankSubsystem,
                        () -> leftDriverJoystick.getY(),
                        () -> rightDriverJoystick.getY());
        private final GearShift gearUpCommand = new GearShift(tankSubsystem, dashboardSubsystem,
                        GearUpOrDown.UP);
        private final GearShift gearDownCommand = new GearShift(tankSubsystem, dashboardSubsystem,
                        GearUpOrDown.DOWN);

        /* Flipper Piston Subsytem w/ Commands */
        public final FlipperPistonSubsystem flipperPistonSubsystem = new FlipperPistonSubsystem();
        private final FlipperPiston flipPistonUpCommand = new FlipperPiston(
                        flipperPistonSubsystem, FlipperPistonUpOrDown.UP);
        private final FlipperPiston flipPistonDownCommand = new FlipperPiston(
                        flipperPistonSubsystem, FlipperPistonUpOrDown.DOWN);

        public RobotContainer()
                {
                        /* Initialize Teleop Drive & Tank Subsystem w/ gears */
                        tankSubsystem.setDefaultCommand(teleopDriveCommand);

                        /* Configure Button Bindings */
                        configureButtonBindings();

                        /* Autonomous Modes */
                        populateAutonomousModes();
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

        private void populateAutonomousModes()
        {
                AutonomousModes[] autonomousModes = AutonomousModes.values();
                String[] autonomousModeStrings = new String[autonomousModes.length];

                for (int i = 0; i < autonomousModes.length; i++)
                        {
                        autonomousModeStrings[i] = autonomousModes[i]
                                        .toString(); // Convert enum to string
                        }

                AutonomousModes defaultAutonomousMode = AutonomousModes.PASS_START_LINE;
                dashboardSubsystem.setAutoModeChoices(autonomousModeStrings,
                                defaultAutonomousMode.toString(),
                                defaultAutonomousMode.getId());

                this.onAutonomousModeUpdate(dashboardSubsystem.getAutonomousMode().getId());

                dashboardSubsystem.setListener(
                                DashboardSubsystem.ListenerType.AutonomousMode,
                                this::onAutonomousModeUpdate);
        }

        private void onAutonomousModeUpdate(final int newAutonomousModeId)
        {
                AutonomousModes newAutonomousMode = AutonomousModes
                                .getFromId(newAutonomousModeId);

                AutonomousModeOptionSupplier[] autonomousModeOptions = null;

                switch (newAutonomousMode)
                        {
                        case PASS_START_LINE:
                                System.out.println("SELECTED AUTONOMOUS MODE: 'Pass Start Line'");
                                autonomousModeOptions = PassStartLine
                                .getAutonomousOptions();
                                break;
                        case SCORE_AMP:
                                System.out.println("SELECTED AUTONOMOUS MODE: 'Score Amp'");
                                autonomousModeOptions = ScoreAmp
                                                .getAutonomousOptions();
                                break;
                        default:
                                System.err.println("INVALID AUTONOMOUS MODE");
                                break;
                        }

                if (autonomousModeOptions != null)
                        {
                        String[] autonomousModeOptionStrings = new String[autonomousModeOptions.length];

                        for (int i = 0; i < autonomousModeOptions.length; i++)
                                {
                                autonomousModeOptionStrings[i] = autonomousModeOptions[i]
                                                .toString(); // Convert to
                                                             // string
                                }

                        AutonomousModeOptionSupplier defaultAutonomousModeOption = autonomousModeOptions[0];

                        dashboardSubsystem.setAutoModeOptionsChoices(
                                        autonomousModeOptionStrings,
                                        defaultAutonomousModeOption.toString(),
                                        defaultAutonomousModeOption.getId());

                        }
                else
                        dashboardSubsystem
                                        .setAutoModeOptionsChoices(new String[]
                                        { "None" }, "None", -1);
        }

        public AutonomousCommandBase getAutonomousCommand()
        {
                AutonomousCommandBase autonomousCommand = null;

                if (dashboardSubsystem.getAutonomousEnabled())
                        {
                        switch (dashboardSubsystem.getAutonomousMode())
                                {
                                case PASS_START_LINE:
                                        System.out.println("SELECTED AUTONOMOUS MODE: 'Pass Start Line'");
                                        autonomousCommand = new PassStartLine(tankSubsystem, dashboardSubsystem);
                                        break;
                                case SCORE_AMP:
                                        System.out.println("SELECTED AUTONOMOUS MODE: 'Score Amp'");
                                        autonomousCommand = new ScoreAmp(tankSubsystem, dashboardSubsystem, flipperPistonSubsystem);
                                        break;
                                default:
                                        System.err.println(
                                                        "INVALID AUTONOMOUS MODE");
                                        break;
                                }
                        }
                else
                        System.out.println("AUTONOMOUS MODE DISABLED");

                return autonomousCommand;
        }
        }