package frc.robot.modules;

import java.util.HashSet;

import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;

import edu.wpi.first.apriltag.AprilTagDetection;
import edu.wpi.first.apriltag.AprilTagDetector;
import edu.wpi.first.apriltag.AprilTagDetector.Config;
import edu.wpi.first.apriltag.AprilTagDetector.QuadThresholdParameters;
import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.cscore.CvSink;
import edu.wpi.first.cscore.CvSource;
import edu.wpi.first.wpilibj.Timer;
import frc.robot.Constants.CameraConstants;
import frc.robot.enums.AprilTagLocations;
import frc.robot.subsystems.CameraSubsystem;

public class AprilTagModule
    {
    private static AprilTagLocations currentLocation = null;

    /**
     * Code sourced from <a href=
     * "https://www.chiefdelphi.com/t/how-to-detect-apriltags-in-java-using-a-logitech-webcam-sample-code-pls/421638">ChiefDelphi</a>
     * 
     * @param cameraSubsystem
     * @return
     */
    public static Runnable getDetectorRunnable(CameraSubsystem cameraSubsystem)
    {
        return () ->
            {
            CvSink cvSink = CameraServer.getVideo();
            CvSource outputStream = CameraServer.putVideo("AprilTagTest",
                    CameraConstants.RESOLUTION[0],
                    CameraConstants.RESOLUTION[1]);

            Mat mat = new Mat();
            Mat grayMat = new Mat();

            Point pt0 = new Point();
            Point pt1 = new Point();
            Point pt2 = new Point();
            Point pt3 = new Point();
            Point center = new Point();
            Scalar red = new Scalar(0, 0, 255);
            Scalar green = new Scalar(0, 255, 0);

            AprilTagDetector aprilTagDetector = new AprilTagDetector();

            Config config = aprilTagDetector.getConfig();
            config.quadSigma = 0.8f;
            aprilTagDetector.setConfig(config);

            QuadThresholdParameters quadThreshParams = aprilTagDetector
                    .getQuadThresholdParameters();
            quadThreshParams.minClusterPixels = 250;
            quadThreshParams.criticalAngle *= 5; // default is 10
            quadThreshParams.maxLineFitMSE *= 1.5;
            aprilTagDetector.setQuadThresholdParameters(quadThreshParams);

            aprilTagDetector.addFamily("tag36h11");

            Timer timer = new Timer();
            timer.start();

            while (!Thread.interrupted())
                {
                if (cvSink.grabFrame(mat) == 0)
                    {
                    outputStream.notifyError(cvSink.getError());
                    continue;
                    }

                Imgproc.cvtColor(mat, grayMat, Imgproc.COLOR_RGB2GRAY);

                AprilTagDetection[] results = aprilTagDetector.detect(grayMat);

                HashSet<Integer> set = new HashSet<Integer>();

                for (var result : results)
                    {
                    pt0.x = result.getCornerX(0);
                    pt1.x = result.getCornerX(1);
                    pt2.x = result.getCornerX(2);
                    pt3.x = result.getCornerX(3);

                    pt0.y = result.getCornerY(0);
                    pt1.y = result.getCornerY(1);
                    pt2.y = result.getCornerY(2);
                    pt3.y = result.getCornerY(3);

                    center.x = result.getCenterX();
                    center.y = result.getCenterY();

                    set.add(result.getId());

                    Imgproc.line(mat, pt0, pt1, red, 5);
                    Imgproc.line(mat, pt1, pt2, red, 5);
                    Imgproc.line(mat, pt2, pt3, red, 5);
                    Imgproc.line(mat, pt3, pt0, red, 5);

                    Imgproc.circle(mat, center, 4, green);
                    Imgproc.putText(mat, String.valueOf(result.getId()), pt2,
                            Imgproc.FONT_HERSHEY_SIMPLEX, 2, green, 7);

                    }
                ;

                for (int id : set)
                    {
                    setCurrentLocation(AprilTagLocations.getFromId(id));
                    }

                outputStream.putFrame(mat);
                }
            aprilTagDetector.close();
            };
    }

    public static AprilTagLocations getCurrentLocation()
    {
        return currentLocation;
    }

    public static void setCurrentLocation(final AprilTagLocations newLocation)
    {
        currentLocation = newLocation;
    }
    }
