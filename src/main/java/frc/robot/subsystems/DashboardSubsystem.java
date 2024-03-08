package frc.robot.subsystems;

import java.util.Map;
import java.util.function.Consumer;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.networktables.GenericEntry;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj.shuffleboard.ComplexWidget;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj.smartdashboard.Field2d;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import frc.robot.Constants.*;
import frc.robot.enums.*;

public class DashboardSubsystem extends SubsystemBase
        {
        /* Field2d */
        private Field2d field = new Field2d();

        /* Shuffleboard */
        private ShuffleboardTab tab = Shuffleboard.getTab("Kilroy");
        private GenericEntry autonomousEnabled = tab.addPersistent(
                        "Auto Enabled",
                        DashboardConstants.AUTONOMOUS_ENABLED_DEFAULT)
                        .withWidget(BuiltInWidgets.kToggleSwitch).withSize(2, 2)
                        .withPosition(8, 0).getEntry();
        @SuppressWarnings("unused")
        private ComplexWidget autonomousMode;
        private SendableChooser<Integer> autonomousModeChooser = new SendableChooser<>();
        @SuppressWarnings("unused")
        private ComplexWidget autonomousModeOptions;
        private SendableChooser<Integer> autonomousModeOptionsChooser = new SendableChooser<>();
        private GenericEntry autonomousDelay = tab.addPersistent("Auto Delay",
                        DashboardConstants.AUTONOMOUS_DELAY_DEFAULT)
                        .withWidget(BuiltInWidgets.kNumberSlider).withSize(2, 2)
                        .withPosition(8, 2).withProperties(Map.of("min", 0.0,
                                        "max", 5.0, "block increment", 0.1))
                        .getEntry();
        private GenericEntry gearModeIndicator = tab.add("Drive Gear", DriveConstants.DEFAULT_GEAR.getId() + 1)
                        .withWidget(BuiltInWidgets.kDial)
                        .withSize(2, 2)
                        .withPosition(10, 2)
                        .withProperties(Map.of("min", 1.0, "max", 3.0, "show value", true))
                        .getEntry();
        private GenericEntry demoModeEnabled = tab
                        .addPersistent("Demo Enabled",
                                        DashboardConstants.DEMO_ENABLED_DEFAULT)
                        .withWidget(BuiltInWidgets.kToggleSwitch).withSize(2, 2)
                        .withPosition(12, 2).getEntry();

        private static class SimplePersistentNTValues
                {
                private static NetworkTableInstance instance = NetworkTableInstance
                                .getDefault();
                private static NetworkTable table = instance
                                .getTable("KilroyPersist");
                private static NetworkTableEntry autonomousMode = table
                                .getEntry("/AutonomousMode");
                private static NetworkTableEntry autonomousModeOption = table
                                .getEntry("/AutonomousModeOption");
                }

        public static interface AutonomousModeOptionSupplier
                {
                public int getId();

                public String toString();
                }

        /**
         * Constructor
         */
        public DashboardSubsystem(final CameraSubsystem cameraSubsystem)
                {
                        if (CameraConstants.CAMERA_ENABLED)
                                {
                                tab.add(cameraSubsystem.getVideoSource())
                                                .withWidget(BuiltInWidgets.kCameraStream)
                                                .withSize(8, 5)
                                                .withPosition(0, 0)
                                                .withProperties(Map.of(
                                                                "rotation",
                                                                "NONE",
                                                                "show controls",
                                                                false, "show crosshair", false));
                                }
                        else
                                {
                                tab.add("Camera Disabled", false).withWidget(
                                                BuiltInWidgets.kBooleanBox)
                                                .withSize(8, 5)
                                                .withPosition(0, 0);
                                }

                        /* Field Data */
                        SmartDashboard.putData("Field", field);
                }

        public ShuffleboardTab getTab()
        {
                return tab;
        }

        /**
         * @return if autonomous functions are enabled
         */
        public boolean getAutonomousEnabled()
        {
                return autonomousEnabled.getBoolean(
                                DashboardConstants.AUTONOMOUS_ENABLED_DEFAULT);
        }

        public AutonomousModes getAutonomousMode()
        {
                return AutonomousModes
                                .getFromId(autonomousModeChooser.getSelected());
        }

        /**
         * @return the autonomous delay in seconds
         */
        public double getAutonomousDelay()
        {
                return autonomousDelay.getDouble(
                                DashboardConstants.AUTONOMOUS_DELAY_DEFAULT);
        }

        /**
         * @return if demo mode is enabled
         */
        public boolean getDemoModeEnabled()
        {
                return demoModeEnabled.getBoolean(
                                DashboardConstants.DEMO_ENABLED_DEFAULT);
        }

        /**
         * Set the autonomous mode choices
         * 
         * <p>
         * Uses the first choice as the default value
         * 
         * @param choices
         *                the choices that the user can pick
         */
        public void setAutoModeChoices(final String[] choices)
        {
                if (choices.length > 0)
                        setAutoModeChoices(choices, choices[0], 0);
        }

        /**
         * Set the autonomous mode choices
         * 
         * @param choices
         *                the choices that the user can pick
         * @param defaultName
         *                the name of the default choice
         * @param defaultValue
         *                the value of the default choice
         */
        public void setAutoModeChoices(final String[] choices,
                        final String defaultName, final int defaultValue)
        {
                setChoices(SendableChooserType.AutonomousMode, choices,
                                defaultName, defaultValue);

                autonomousMode = tab
                                .add("Autonomous Mode", autonomousModeChooser)
                                .withWidget(BuiltInWidgets.kComboBoxChooser)
                                .withSize(3, 2).withPosition(10, 0);
        }

        /**
         * Set the autonomous mode choices
         * 
         * <p>
         * Uses the first choice as the default value
         * 
         * @param choices
         *                the choices that the user can pick
         */
        public void setAutoModeOptionsChoices(final String[] choices)
        {
                if (choices.length > 0)
                        setAutoModeChoices(choices, choices[0], 0);
        }

        /**
         * Set the autonomous mode choices
         * 
         * @param choices
         *                the choices that the user can pick
         * @param defaultName
         *                the name of the default choice
         * @param defaultValue
         *                the value of the default choice
         */
        public void setAutoModeOptionsChoices(final String[] choices,
                        final String defaultName, final int defaultValue)
        {
                setChoices(SendableChooserType.AutonomousModeOptions, choices,
                                defaultName, defaultValue);

                autonomousModeOptions = tab
                                .add("Auto Mode Options",
                                                autonomousModeOptionsChooser)
                                .withWidget(BuiltInWidgets.kComboBoxChooser)
                                .withSize(3, 2).withPosition(13, 0);
        }

        private static enum SendableChooserType
                {
                AutonomousMode, AutonomousModeOptions
                }

        /**
         * Sets choices of a sendable chooser
         * 
         * @param sendableChooser
         *                the sendable chooser to use
         * @param choices
         *                the choices that the user can pick
         * @param defaultName
         *                the name of the default choice
         * @param defaultValue
         *                the value of the default choice
         */
        private void setChoices(SendableChooserType sendableChooserType,
                        final String[] choices, final String defaultName,
                        final int defaultValue)
        {
                SendableChooser<Integer> sendableChooser = sendableChooserType == SendableChooserType.AutonomousMode
                                ? autonomousModeChooser
                                : autonomousModeOptionsChooser;
                sendableChooser.close();

                sendableChooser = new SendableChooser<>();

                int i = 0;
                for (String choice : choices)
                        {
                        sendableChooser.addOption(choice, i);
                        i++;
                        }
                sendableChooser.setDefaultOption(defaultName, defaultValue);

                if (sendableChooserType == SendableChooserType.AutonomousMode)
                        {
                        autonomousModeChooser = sendableChooser;
                        }
                        {
                        autonomousModeOptionsChooser = sendableChooser;
                        }
        }

        public void updatePose(final Pose2d newPose)
        {
                field.setRobotPose(newPose);
        }

        public static enum ListenerType
                {
                AutonomousMode, AutonomousModeOption
                }

        public void setListener(final ListenerType listenerType,
                        Consumer<Integer> listener)
        {
                switch (listenerType)
                        {
                        case AutonomousMode:
                                if (autonomousModeChooser != null)
                                        autonomousModeChooser.onChange(
                                                        (Integer updated) ->
                                                                {
                                                                SimplePersistentNTValues.autonomousMode
                                                                                .setInteger(updated);

                                                                listener.accept(updated);
                                                                });
                                break;
                        case AutonomousModeOption:
                                if (autonomousModeOptionsChooser != null)
                                        autonomousModeOptionsChooser.onChange(
                                                        (Integer updated) ->
                                                                {
                                                                SimplePersistentNTValues.autonomousModeOption
                                                                                .setInteger(updated);

                                                                listener.accept(updated);
                                                                });
                                break;
                        }
        }

        /**
         * Set the drive gear in the dashboard
         * 
         * @param driveGear the gear to set in the dashboard
         */
        public void setDriveGear(DriveGears driveGear) {
                setDriveGear(driveGear.getId() + 1);
        }

        /**
         * Set the drive gear in the dashboard
         * 
         * @param driveGear the gear to set in the dashboard
         */
        public void setDriveGear(int driveGear) {
                gearModeIndicator.setInteger(driveGear);
        }
        }
