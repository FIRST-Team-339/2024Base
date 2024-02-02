package frc.robot.commands.teleop;

import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.cscore.UsbCamera;
import edu.wpi.first.cscore.VideoSink;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants.*;
import frc.robot.subsystems.CameraSubsystem;

/**
 * Camera Command
 * 
 * This is the command that handles the Camera Server and Camera Switching
 * 
 * @author Jacob Fisher
 * @created 24 January 2024
 */
public class Camera extends CommandBase
    {
    /* Camera Subsystem */
    private CameraSubsystem cameraSubsystem;

    /* USB Cameras */
    private UsbCamera camera0;
    private UsbCamera camera1;

    /* USB Camera Server */
    private VideoSink server;

    /**
     * Constructor
     * 
     * Sets the {@link CameraSubsystem}
     */
    public Camera(CameraSubsystem cameraSubsystem)
        {
            if (CameraConstants.CAMERA_ENABLED)
                {
                addRequirements(cameraSubsystem);
                this.cameraSubsystem = cameraSubsystem;

                this.camera0 = CameraServer.startAutomaticCapture("Camera", 0);
                this.server = CameraServer.getServer("serve_Camera");
                cameraSubsystem.setCameraValues(camera0, server);

                // If `usingTwoCameras` is true, then start up the second server
                if (CameraConstants.USING_TWO_CAMERAS == true)
                    {
                    this.camera1 = new UsbCamera("Camera2", 1);
                    cameraSubsystem.setCameraValues(camera1, server);
                    }
                }

        }

    @Override
    public void execute()
    {
        /*
         * Checks if cameras are being used, if so, continue to the next check
         * 
         * Otherwise, finish the command
         */
        if (CameraConstants.CAMERA_ENABLED)
            {
            /*
             * Checks if camera 0 is enabled, and if so, switch the camera
             * server to display the second camera
             * 
             * Otherwise, switch the camera server to display the first camera
             */
            if (camera0.isEnabled() == true)
                {
                cameraSubsystem.switchCamera(server, camera1);
                }
            else
                {
                cameraSubsystem.switchCamera(server, camera0);
                }
            }

        // Cancels the command as it has ran
        cancel();
    }
    }
