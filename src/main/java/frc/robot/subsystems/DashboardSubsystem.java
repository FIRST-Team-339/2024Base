package frc.robot.subsystems;

import java.util.Arrays;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.wpilibj.smartdashboard.Field2d;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.enums.*;

public class DashboardSubsystem extends SubsystemBase
    {
    /* Field2d */
    private Field2d field = new Field2d();

    private SendableChooser<Integer> autoChooser = new SendableChooser<>();

    interface IdSupplier
        {
        public int getId();
        }

    /**
     * Constructor
     */
    public DashboardSubsystem()
        {
            /* Field Data */
            SmartDashboard.putData("Field", field);

            /* Autonomous Enabled */
            SmartDashboard.putBoolean("Kilroy.Auto.Enabled",
                    getAutonomousEnabled());
            SmartDashboard.setPersistent("Kilroy.Auto.Enabled");

            /* Autonomous Delay */
            SmartDashboard.putNumber("Kilroy.Auto.Delay", getAutonomousDelay());
            SmartDashboard.setPersistent("Kilroy.Auto.Delay");

            /* Demo Enabled */
            SmartDashboard.putBoolean("Kilroy.Demo.Enabled",
                    getDemoModeEnabled());
            SmartDashboard.setPersistent("Kilroy.Demo.Enabled");
        }

    /**
     * @return if autonomous functions are enabled
     */
    public boolean getAutonomousEnabled()
    {
        return SmartDashboard.getBoolean("Kilroy.Auto.Enabled", true);
    }

    public AutonomousModes getAutonomousMode()
    {
        return AutonomousModes.getFromId(autoChooser.getSelected());
    }

    public AutonomousModeOptions getAutonomousModeOptions()
    {
        throw new UnsupportedOperationException("Not implemented");
    }

    /**
     * @return the autonomous delay in seconds
     */
    public double getAutonomousDelay()
    {
        return SmartDashboard.getNumber("Kilroy.Auto.Delay", 0.0);
    }

    /**
     * @return if demo mode is enabled
     */
    public boolean getDemoModeEnabled()
    {
        return SmartDashboard.getBoolean("Kilroy.Demo.Enabled", false);
    }

    /**
     * Set the autonomous mode choices
     * 
     * <p>
     * Uses the first choice as the default value
     * 
     * @param <T>
     *            the enum type parameter
     * @param choices
     *            the choices that the user can pick
     * @param defaultChoice
     *            the defualt choice
     */
    public <T extends Enum<T> & IdSupplier> void setAutoModeChoices(
            final T[] choices)
    {
        if (choices.length > 0)
            setAutoModeChoices(choices, choices[0]);
    }

    /**
     * Set the autonomous mode choices
     * 
     * @param <T>
     *            the enum type parameter
     * @param choices
     *            the choices that the user can pick
     * @param defaultChoice
     *            the defualt choice
     */
    public <T extends Enum<T> & IdSupplier> void setAutoModeChoices(
            final T[] choices, final T defaultChoice)
    {
        setAutoModeChoices(
                (String[]) Arrays.stream(choices)
                        .map(choice -> choice.toString()).toArray(),
                defaultChoice.toString(), defaultChoice.getId());
    }

    /**
     * Set the autonomous mode choices
     * 
     * <p>
     * Uses the first choice as the default value
     * 
     * @param choices
     *            the choices that the user can pick
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
     *            the choices that the user can pick
     * @param defaultName
     *            the name of the default choice
     * @param defaultValue
     *            the value of the default choice
     */
    public void setAutoModeChoices(final String[] choices,
            final String defaultName, final int defaultValue)
    {
        autoChooser.close();

        autoChooser = new SendableChooser<>();

        int i = 0;
        for (String choice : choices)
            {
            autoChooser.addOption(choice, i);
            i++;
            }
        autoChooser.setDefaultOption(defaultName, defaultValue);

        SmartDashboard.putData("/Kilroy/Auto/Selection", autoChooser);
    }

    public void updatePose(final Pose2d newPose)
    {
        field.setRobotPose(newPose);
    }
    }
