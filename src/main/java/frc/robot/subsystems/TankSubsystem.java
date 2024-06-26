package frc.robot.subsystems;

import com.playingwithfusion.CANVenom;
import com.playingwithfusion.CANVenom.BrakeCoastMode;

import edu.wpi.first.math.filter.SlewRateLimiter;
import edu.wpi.first.math.kinematics.DifferentialDriveKinematics;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.math.estimator.DifferentialDrivePoseEstimator;
import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.*;
import frc.robot.enums.DriveGears;
import frc.robot.hardwareInterfaces.KilroyEncoder;

public class TankSubsystem extends SubsystemBase
	{
	/* Motors on the Left Side */
	private CANVenom frontLeftMotor = new CANVenom(
			DriveConstants.FRONT_LEFT_MOTOR_ID);
	private CANVenom rearLeftMotor = new CANVenom(
			DriveConstants.REAR_LEFT_MOTOR_ID);

	/* Motors on the Right Side */
	private CANVenom frontRightMotor = new CANVenom(
			DriveConstants.FRONT_RIGHT_MOTOR_ID);
	private CANVenom rearRightMotor = new CANVenom(
			DriveConstants.REAR_RIGHT_MOTOR_ID);

	/* The Drive Encoder on the Left Side */
	private KilroyEncoder leftEncoder = new KilroyEncoder(frontLeftMotor);

	/* The Drive Encoder on the Left Side */
	private KilroyEncoder rightEncoder = new KilroyEncoder(frontRightMotor);

	/* Current Gear */
	private DriveGears currentGear;

	/* Odometry & Gyro */
	private ADXRS450_Gyro gyro = new ADXRS450_Gyro();

	/* Limiters */
	SlewRateLimiter accelerationLimiter = new SlewRateLimiter(
			DriveConstants.ACCELERATION_RATE_LIMIT);
	public boolean accelerationLimiterReset = true;
	SlewRateLimiter brakingLimiter = new SlewRateLimiter(
			DriveConstants.BRAKE_RATE_LIMIT);
	public boolean brakeLimiterReset = true;

	/* Max Output */
	private double maxOutput = 1.0;

	/* Pose Estimation (please help) */
	// private DifferentialDriveKinematics kinematics = new DifferentialDriveKinematics(Units.inchesToMeters(22));
	// private DifferentialDrivePoseEstimator estimatedPose = new DifferentialDrivePoseEstimator(kinematics, gyro.getRotation2d(), );

	public TankSubsystem()
		{
			/* Set the currentGear value to the passed startingGear value */
			currentGear = DriveGears.GEAR1;
			setGear(currentGear);

			/* Set the inversion value for the motor controller groups */
			frontLeftMotor.setInverted(
					DriveConstants.MOTOR_CONTROLLER_GROUPS_INVERTED[0]);
			frontRightMotor.setInverted(
					DriveConstants.MOTOR_CONTROLLER_GROUPS_INVERTED[1]);
			rearLeftMotor.setInverted(
					DriveConstants.MOTOR_CONTROLLER_GROUPS_INVERTED[0]);
			rearRightMotor.setInverted(
					DriveConstants.MOTOR_CONTROLLER_GROUPS_INVERTED[1]);

			/* Reset Encoders */
			leftEncoder.setReverseDirection(frontLeftMotor.getInverted());
			rightEncoder.setReverseDirection(frontRightMotor.getInverted());
			leftEncoder.setDistancePerPulse(DriveConstants.DISTANCE_PER_PULSE);
			rightEncoder.setDistancePerPulse(DriveConstants.DISTANCE_PER_PULSE);
			leftEncoder.reset();
			rightEncoder.reset();

			/* Set Braking */
			frontLeftMotor.setBrakeCoastMode(BrakeCoastMode.Brake);
			frontRightMotor.setBrakeCoastMode(BrakeCoastMode.Brake);
			rearLeftMotor.setBrakeCoastMode(BrakeCoastMode.Brake);
			rearRightMotor.setBrakeCoastMode(BrakeCoastMode.Brake);

			/* Initialize Gyro */
			gyro.calibrate();
		}

	/**
	 * Get the Left Encoder
	 * 
	 * @return The Left Encoder
	 */
	public KilroyEncoder getLeftEncoder()
	{
		return leftEncoder;
	}

	/**
	 * Get the Right Encoder
	 * 
	 * @return The Right Encoder
	 */
	public KilroyEncoder getRightEncoder()
	{
		return rightEncoder;
	}

	public CANVenom getLeftRearMotor()
	{
		return rearLeftMotor;
	}

	public CANVenom getRightRearMotor()
	{
		return rearRightMotor;
	}

	/**
	 * Checks if the {@code encoder} has passed the {@code distance} provided
	 * 
	 * @param distance
	 *            The distance to check
	 * @return If any of the {@code encoder} has passed the {@code distance}
	 *         provided
	 */
	public boolean encoderHasPassedDistance(final KilroyEncoder encoder,
			final double distance)
	{
		return distance > 0 ? encoder.getDistance() >= distance
				: encoder.getDistance() <= distance;
	}

	/**
	 * Checks if either encoders (left or right) have passed the
	 * {@code distance} provided
	 * 
	 * @param distance
	 *            The distance to check
	 * @return If any of the encoders (left or right) have passed the
	 *         {@code distance} provided
	 */
	public boolean anyEncoderHasPassedDistance(final double distance)
	{
		return encoderHasPassedDistance(leftEncoder, distance)
				|| encoderHasPassedDistance(rightEncoder, distance);
	}

	/**
	 * Drives the robot straight
	 * 
	 * @param leftSpeed
	 *            The robot's left side speed along the X axis [-1.0..1.0].
	 *            Forward is positive.
	 * @param rightSpeed
	 *            The robot's right side speed along the X axis [-1.0..1.0].
	 *            Forward is positive.
	 */
	public void drive(final double leftSpeed, final double rightSpeed)
	{
		boolean leftAboveDeadbandPos = leftSpeed > 0 && leftSpeed > DriveConstants.JOYSTICK_DEADBAND;
		boolean leftBelowDeadbandNeg = leftSpeed < 0 && leftSpeed < DriveConstants.JOYSTICK_DEADBAND;
		boolean rightAboveDeadbandPos = rightSpeed > 0 && rightSpeed > DriveConstants.JOYSTICK_DEADBAND;
		boolean rightBelowDeadbandNeg = rightSpeed < 0 && rightSpeed < DriveConstants.JOYSTICK_DEADBAND;
		
		double checkedLeftSpeed = leftAboveDeadbandPos || leftBelowDeadbandNeg ? leftSpeed : 0.0;
		double checkedRightSpeed = rightAboveDeadbandPos || rightBelowDeadbandNeg ? rightSpeed : 0.0;

		this.frontLeftMotor.set(checkedLeftSpeed * this.maxOutput);
		this.rearLeftMotor.set(checkedLeftSpeed * this.maxOutput);
		this.frontRightMotor.set(checkedRightSpeed * this.maxOutput);
		this.rearRightMotor.set(checkedRightSpeed * this.maxOutput);
	}

	public void drive(final double leftSpeed, final double rightSpeed, final boolean overrideDeadband) {
		if (overrideDeadband) {
			this.frontLeftMotor.set(leftSpeed * this.maxOutput);
			this.rearLeftMotor.set(leftSpeed * this.maxOutput);
			this.frontRightMotor.set(rightSpeed * this.maxOutput);
			this.rearRightMotor.set(rightSpeed * this.maxOutput);
		} else {
			drive(leftSpeed, rightSpeed);
		}
	}

	/**
	 * Drives the robot straight with the {@link DifferentialDrive#tankDrive}
	 * method
	 * 
	 * @param speed
	 *            The robot's speed along the X axis [-1.0..1.0]. Forward is
	 *            positive.
	 */
	public void driveStraight(final double speed, final boolean usingGyro)
	{
		// int delta = leftEncoder.get() - rightEncoder.get();

		// double straightLeftSpeed = 0.0;
		// double straightRightSpeed = 0.0;

		// if (usingGyro == true)
		// {
		// straightLeftSpeed = speed - (Math.signum(gyro.getAngle())
		// * DriveConstants.DRIVE_STRAIGHT_CORRECTION_DELTA);
		// straightRightSpeed = speed + (Math.signum(gyro.getAngle())
		// * DriveConstants.DRIVE_STRAIGHT_CORRECTION_DELTA);
		// }
		// else
		// {
		// straightLeftSpeed = speed + ((Math.signum(delta)
		// * DriveConstants.DRIVE_STRAIGHT_CORRECTION_DELTA));
		// straightRightSpeed = speed - ((Math.signum(delta)
		// * DriveConstants.DRIVE_STRAIGHT_CORRECTION_DELTA));
		// }

		// /* Only send the new power to the side lagging behind */
		// if (straightLeftSpeed > straightRightSpeed)
		// {
		// straightRightSpeed = speed;
		// }
		// else
		// {
		// straightLeftSpeed = speed;
		// }
		drive(speed, speed);
	}

	/**
	 * Reset the encoders
	 */
	public void resetEncoders()
	{
		leftEncoder.reset();
		rightEncoder.reset();
	}

	/**
	 * Determines how many inches an encoder must read to have completed a turn
	 *
	 * @param degrees
	 *            How many degrees the robot should turn
	 * @param pivot
	 *            Whether or not the robot is pivoting on a wheel: Effectively
	 *            doubles the turning radius
	 * @return The calculated value in inches.
	 */
	public double degreesToEncoderInches(final double degrees,
			final boolean pivot)
	{
		if (pivot == false)
			return DriveConstants.TURN_RADIUS
					* Math.toRadians(Math.abs(degrees));

		return (DriveConstants.TURN_RADIUS / 2)
				* Math.toRadians(Math.abs(degrees));
	}

	/**
	 * @return the encoder distance average
	 */
	public double getEncoderDistanceAverage()
	{
		return (Math.abs(leftEncoder.getDistance())
				+ Math.abs(rightEncoder.getDistance())) / 2;
	}

	/**
	 * Drives the robot straight a certain number of inches
	 * 
	 * @param distance
	 *            The distance you want the robot to travel
	 * @param speed
	 *            The robot's speed along the X axis [-1.0..1.0]. Forward is
	 *            positive.
	 * @param resetEncoders
	 *            If you want to reset the encoders (usually this would be done
	 *            once per Command)
	 * @return If any of the encoders (left or right) have passed the
	 *         {@code distance} provided
	 */
	public boolean driveStraightInches(double distance, double speed,
			boolean resetEncoders, boolean useGyro)
	{
		if (resetEncoders == true)
			{
			resetEncoders();
			}

		if (anyEncoderHasPassedDistance(distance) == true)
			{
			return true;
			}
		else
			{
			driveStraight(distance < 0 ? -speed : speed, useGyro);
			return false;
			}
	}

	/**
	 * Turns the robot on the spot a number of degrees
	 *
	 * @param degrees
	 *            How far the robot should turn. Positive for clockwise,
	 *            negative for counter-clockwise
	 * @param speed
	 *            How fast the robot should pivot, from 0.0 to 1.0.
	 * @return Whether or not the robot has finished turning the requested
	 *         number of degrees, used in a state machine.
	 */
	public boolean pivotDegrees(final int degrees, final double speed)
	{
		/*
		 * If either sensor has reached the target position, then stop motors
		 * and return true.
		 */
		// System.out.println("Encoder Distance Average: "
		// + this.getEncoderDistanceAverage());
		// System.out
		// .println("Degrees to Encoder Inches: " + degreesToEncoderInches(
		// Math.abs(degrees)
		// + DriveConstants.TURN_DEGREES_FUDGE_FACTOR,
		// true));

		// if (Math.abs(this.getEncoderDistanceAverage()) > degreesToEncoderInches(
		// 		Math.abs(degrees) + DriveConstants.TURN_DEGREES_FUDGE_FACTOR,
		// 		true))
		// 	{
		// 	this.drive(0, 0);
		// 	return true;
		// 	}
		if (gyro.getAngle() - Math.abs(degrees) < 1)
			{
			this.drive(0, 0);
			return true;
			}

		// If degrees is positive, then turn left. If not, then turn right.
		if (degrees > 0)
			{
			this.drive(speed, -speed);
			}
		else
			{
			this.drive(-speed, speed);
			}
		return false;
	}

	/**
	 * Smoothly accelerate the robot
	 * 
	 * @param desiredSpeed
	 *            The desired speed to accelerate to
	 * @return
	 */
	public boolean accelerate(final double desiredSpeed)
	{
		double accelerationSpeed = accelerationLimiter
				.calculate((accelerationLimiterReset) ? 0.0 : desiredSpeed);
		accelerationLimiterReset = false;

		if (accelerationSpeed == desiredSpeed)
			{
			accelerationLimiterReset = true;
			return true;
			}
		else
			{
			drive(accelerationSpeed, accelerationSpeed);
			return false;
			}
	}

	/**
	 * Slow down the robot
	 * 
	 * @param startingSpeed
	 *            The desired starting speed
	 * @return If the bot is finished braking
	 */
	public boolean brake(final double startingSpeed)
	{
		double brakeSpeed = brakingLimiter
				.calculate((brakeLimiterReset) ? startingSpeed : 0.0);
		brakeLimiterReset = false;

		if (brakeSpeed == 0.0)
			{
			brakeLimiterReset = true;
			return true;
			}
		else
			{
			driveStraight(brakeSpeed, false);
			return false;
			}
	}

	/**
	 * Slow down the robot with a default starting speed of 1.0
	 * 
	 * @return If the bot is finished braking
	 */
	public boolean brake()
	{
		return brake(1.0);
	}

	/**
	 * Get the current gear of the {@link TankSubsystem}
	 * 
	 * @return A {@link DriveGears} enum
	 */
	public DriveGears getCurrentGear()
	{
		return currentGear;
	}

	/**
	 * Get the current gear ratio (max output) of the {@link TankSubsystem}
	 * 
	 * @return The current gear ratio
	 */
	public double getCurrentGearRatio()
	{
		return currentGear.getRatio();
	}

	/**
	 * Set the gear (the  max output)
	 * 
	 * @param gear
	 *            The desired gear to set the max output as
	 */
	public void setGear(final DriveGears gear)
	{
		this.maxOutput = gear.getRatio();
	}

	/**
	 * Manually set the max output (usually for autonomous )
	 * 
	 * @param maxOutput
	 *            The desired max output
	 */
	public void setMaxOutput(final double maxOutput)
	{
		this.maxOutput = maxOutput;
	}

	/**
	 * Shift the gear up or down
	 * 
	 * @param shiftBy
	 *            How much you want to shift by (example: +1)
	 * @return If the gear successfully shifted (will return false if you
	 *         shifted to a gear that doesn't exist)
	 */
	public boolean shiftGearBy(final int shiftBy)
	{
		DriveGears newGear = DriveGears
				.getFromId(currentGear.getId() + shiftBy);

		if (newGear != null)
			{
			setGear(newGear);
			currentGear = newGear;
			return true;
			}
		return false;
	}

	/**
	 * Shift the gear up by 1
	 * 
	 * @return If the gear successfully shifted (will return false if you
	 *         shifted to a gear that doesn't exist)
	 */
	public boolean shiftGearUp()
	{
		return shiftGearBy(1);
	}

	/**
	 * Shift the gear down by 1
	 * 
	 * @return If the gear successfully shifted (will return false if you
	 *         shifted to a gear that doesn't exist)
	 */
	public boolean shiftGearDown()
	{
		if (getCurrentGear().getId() == 0)
			{
			return false;
			}
		return shiftGearBy(-1);
	}

	/**
	 * Shift to the lowest (first) gear
	 */
	public void shiftLowestGear()
	{
		DriveGears lowestGear = DriveGears.getFromId(0);
		setGear(lowestGear);
	}

	/**
	 * Shift to the highest gear
	 */
	public void shiftHighestGear()
	{
		DriveGears highestGear = DriveGears.getFromId(-1);
		setGear(highestGear);
	}

	/**
	 * Get the gyro
	 * 
	 * @return ADXRS450_Gyro
	 */
	public ADXRS450_Gyro getGyro()
	{
		return this.gyro;
	}

	}