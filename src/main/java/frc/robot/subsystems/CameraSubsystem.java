package frc.robot.subsystems;

import edu.wpi.first.cscore.UsbCamera;
import edu.wpi.first.cscore.VideoSink;
import edu.wpi.first.cscore.VideoSource;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.*;

public class CameraSubsystem extends SubsystemBase {
    private UsbCamera camera;
    private VideoSource source;

    /* No Constructor */
    public CameraSubsystem() {
    }

    /**
     * The {@code setCameraValues} method is to update the camera values such
     * as:
     * <ul>
     * <li>Resolution</li>
     * <li>Frames per Second (FPS)</li>
     * <li>Compression rate</li>
     * <li>Brightness</li>
     * </ul>
     * 
     * The values can all be changed and set under {@link CameraConstants}
     * 
     * @param usbCamera
     *                  The {@link UsbCamera} to set the values for
     * @param server
     *                  The camera server ({@link VideoSink}) to update the value(s)
     *                  for
     */
    public void setCameraValues(UsbCamera usbCamera, VideoSink server) {
        usbCamera.setResolution(CameraConstants.RESOLUTION[0],
                CameraConstants.RESOLUTION[1]);
        usbCamera.setFPS(CameraConstants.FRAMES_PER_SECOND);
        server.getProperty("compression").set(CameraConstants.COMPRESSION);
        usbCamera.setBrightness(CameraConstants.BRIGHTNESS);

        this.source = server.getSource();
    }

    /**
     * The {@code switchCamera} method is to set the source between one of the
     * two {@link UsbCamera}'s that can be used on the robot
     * 
     * It will only attempt to change the source if
     * {@link CameraConstants#USING_TWO_CAMERAS} is <b>true</b>.
     * 
     * @param server
     *                      The camera server ({@link VideoSink}) to switch the
     *                      source
     * @param desiredSource
     *                      The desired source ({@link UsbCamera}) to display
     */
    public void switchCamera(VideoSink server, UsbCamera desiredSource) {
        if (CameraConstants.USING_TWO_CAMERAS) {
            server.setSource(desiredSource);
        }
    }

    public UsbCamera getCamera() {
        return this.camera;
    }

    public VideoSource getVideoSource() {
        return this.source;
    }

}
