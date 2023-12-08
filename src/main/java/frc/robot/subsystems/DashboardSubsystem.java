package frc.robot.subsystems;

import edu.wpi.first.wpilibj.RobotController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.*;
import frc.robot.enums.DriveGears;

public class DashboardSubsystem extends SubsystemBase
    {
    public static enum AutonomousMode
        {
        Init, Teleop, Disabled, Completed, Mode1
        }

    public DashboardSubsystem()
        {
            /* Initialize Drive Gears */
            updateGearIndicator();

            /* Update Autonomous Mode */
            updateAutoIndicator(AutonomousMode.Init);

            /* Update Utilities */
            updateEBrakeEngagedIndicator(false);
            updateReplaceBatteryIndicator();
            updateClawClosedIndicator(false);
        }

    public void updateGearIndicator()
    {
        SmartDashboard.putBoolean("Gear3", false);
        SmartDashboard.putBoolean("Gear2", false);
        SmartDashboard.putBoolean("Gear1", false);
        SmartDashboard.putBoolean("GearR", false);
    }

    public void updateGearIndicator(DriveGears gear, boolean inReverse)
    {
        if (inReverse == true)
            {
            SmartDashboard.putBoolean("Gear3", false);
            SmartDashboard.putBoolean("Gear2", false);
            SmartDashboard.putBoolean("Gear1", false);
            SmartDashboard.putBoolean("GearR", true);

            return;
            }

        switch (gear)
            {
            case GEAR2:
                SmartDashboard.putBoolean("Gear3", false);
                SmartDashboard.putBoolean("Gear2", true);
                SmartDashboard.putBoolean("Gear1", false);
                SmartDashboard.putBoolean("GearR", false);
                break;
            case GEAR1:
                SmartDashboard.putBoolean("Gear3", false);
                SmartDashboard.putBoolean("Gear2", false);
                SmartDashboard.putBoolean("Gear1", true);
                SmartDashboard.putBoolean("GearR", false);
            default:
                break;
            }
    }

    public void updateAutoIndicator(AutonomousMode autonomousMode)
    {
        switch (autonomousMode)
            {
            case Init:
                SmartDashboard.putString("AutoMode", "");

                SmartDashboard.putBoolean("AutoMode0", true);
                SmartDashboard.putBoolean("AutoMode1", false);
                SmartDashboard.putBoolean("AutoMode2", false);
                SmartDashboard.putBoolean("AutoMode3", false);
                SmartDashboard.putBoolean("AutoMode4", false);
                SmartDashboard.putBoolean("AutoMode5", false);
                SmartDashboard.putBoolean("AutoMode6", false);
                break;

            case Teleop:
                SmartDashboard.putString("AutoMode", "N/A (Teleop Enabled)");

                SmartDashboard.putBoolean("AutoMode0", true);
                SmartDashboard.putBoolean("AutoMode1", false);
                SmartDashboard.putBoolean("AutoMode2", false);
                SmartDashboard.putBoolean("AutoMode3", false);
                SmartDashboard.putBoolean("AutoMode4", false);
                SmartDashboard.putBoolean("AutoMode5", false);
                SmartDashboard.putBoolean("AutoMode6", false);
                break;

            case Disabled:
                SmartDashboard.putString("AutoMode",
                        "N/A (Auto Switch set to `Disabled`)");

                SmartDashboard.putBoolean("AutoMode0", true);
                SmartDashboard.putBoolean("AutoMode1", false);
                SmartDashboard.putBoolean("AutoMode2", false);
                SmartDashboard.putBoolean("AutoMode3", false);
                SmartDashboard.putBoolean("AutoMode4", false);
                SmartDashboard.putBoolean("AutoMode5", false);
                SmartDashboard.putBoolean("AutoMode6", false);
                break;

            case Completed:
                SmartDashboard.putString("AutoMode",
                        "N/A (Autonomous Completed)");

                SmartDashboard.putBoolean("AutoMode0", true);
                SmartDashboard.putBoolean("AutoMode1", false);
                SmartDashboard.putBoolean("AutoMode2", false);
                SmartDashboard.putBoolean("AutoMode3", false);
                SmartDashboard.putBoolean("AutoMode4", false);
                SmartDashboard.putBoolean("AutoMode5", false);
                SmartDashboard.putBoolean("AutoMode6", false);
                break;

            case Mode1:
                SmartDashboard.putString("AutoMode", "Auto Example");

                SmartDashboard.putBoolean("AutoMode0", false);
                SmartDashboard.putBoolean("AutoMode1", true);
                SmartDashboard.putBoolean("AutoMode2", false);
                SmartDashboard.putBoolean("AutoMode3", false);
                SmartDashboard.putBoolean("AutoMode4", false);
                SmartDashboard.putBoolean("AutoMode5", false);
                SmartDashboard.putBoolean("AutoMode6", false);
                break;
            }
    }

    public void updateEBrakeEngagedIndicator(boolean eBrakeEngaged)
    {
        SmartDashboard.putBoolean("EBrakeEngaged", eBrakeEngaged);
    }

    public void updateReplaceBatteryIndicator()
    {
        double batteryLevel = RobotController.getBatteryVoltage();

        if (batteryLevel < DashboardConstants.LOW_BATTERY_LEVEL)
            {
            SmartDashboard.putBoolean("ReplaceBattery", true);
            }
        else
            {
            SmartDashboard.putBoolean("ReplaceBattery", false);
            }
    }

    public void updateClawClosedIndicator(boolean clawClosed)
    {
        SmartDashboard.putBoolean("ClawClosed", clawClosed);
    }

    }
