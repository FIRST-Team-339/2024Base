package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;

import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.motorcontrol.MotorController;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.*;
import frc.robot.enums.DriveGears;
import frc.robot.hardwareInterfaces.KilroyEncoder;

public class TankSubsystem extends SubsystemBase
	{
	/* Motors on the Left Side */
	private MotorController motorForLeftEncoder = new WPI_TalonFX(
			DriveConstants.BOTTOM_LEFT_MOTOR_ID);
	private MotorControllerGroup leftMotorControllerGroup = new MotorControllerGroup(
			(MotorController) new WPI_TalonFX(DriveConstants.TOP_LEFT_MOTOR_ID),
			motorForLeftEncoder);

	/* Motors on the Right Side */
	private MotorController motorForRightEncoder = new WPI_TalonFX(
			DriveConstants.BOTTOM_RIGHT_MOTOR_ID);
	private MotorControllerGroup rightMotorControllerGroup = new MotorControllerGroup(
			(MotorController) new WPI_TalonFX(
					DriveConstants.TOP_RIGHT_MOTOR_ID),
			motorForRightEncoder);

	/* The Robot's Drive */
	private DifferentialDrive differentialDrive = new DifferentialDrive(
			leftMotorControllerGroup, rightMotorControllerGroup);

	/* The Drive Encoder on the Left Side */
	private KilroyEncoder leftEncoder = new KilroyEncoder(
			(WPI_TalonFX) motorForLeftEncoder);

	/* The Drive Encoder on the Left Side */
	private KilroyEncoder rightEncoder = new KilroyEncoder(
			(WPI_TalonFX) motorForRightEncoder);

	/* Current Gear */
	private DriveGears currentGear;

	/* Gyro */

	/* Limiters */

	public TankSubsystem()
		{
			/* Set the Joystick Deadband */
			differentialDrive.setDeadband(DriveConstants.JOYSTICK_DEADBAND);

			/* Set the inversion value for the motor controller groups */
			leftMotorControllerGroup.setInverted(
					DriveConstants.MOTOR_CONTROLLER_GROUPS_INVERTED[0]);
			rightMotorControllerGroup.setInverted(
					DriveConstants.MOTOR_CONTROLLER_GROUPS_INVERTED[1]);

			/* Reset Encoders */
			leftEncoder.setDistancePerPulse(DriveConstants.DISTANCE_PER_PULSE);
			rightEncoder.setDistancePerPulse(DriveConstants.DISTANCE_PER_PULSE);
			leftEncoder.reset();
			rightEncoder.reset();

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

	/**
	 * Checks if the {@code encoder} has passed the {@code distance} provided
	 * 
	 * @param distance
	 *            The distance to check
	 * @return If any of the {@code encoder} has passed the {@code distance}
	 *         provided
	 */
	public boolean encoderHasPassedDistance(KilroyEncoder encoder,
			double distance)
	{
		return encoder.getDistance() >= distance;
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
	public boolean anyEncoderHasPassedDistance(double distance)
	{
		return encoderHasPassedDistance(leftEncoder, distance)
				|| encoderHasPassedDistance(rightEncoder, distance);
	}

	/**
	 * Drives the robot straight with the {@link DifferentialDrive#tankDrive}
	 * method
	 * 
	 * @param leftSpeed
	 *            The robot's left side speed along the X axis [-1.0..1.0].
	 *            Forward is positive.
	 * @param rightSpeed
	 *            The robot's right side speed along the X axis [-1.0..1.0].
	 *            Forward is positive.
	 */
	public void drive(double leftSpeed, double rightSpeed)
	{
		differentialDrive.tankDrive(leftSpeed, rightSpeed);
	}

	/**
	 * Drives the robot straight with the {@link DifferentialDrive#tankDrive}
	 * method
	 * 
	 * @param speed
	 *            The robot's speed along the X axis [-1.0..1.0]. Forward is
	 *            positive.
	 */

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
	 * Set the gear (the {@link DifferentialDrive}'s max output)
	 * 
	 * @param gear
	 *            The desired gear to set the max output as
	 */
	public void setGear(DriveGears gear)
	{
		differentialDrive.setMaxOutput(gear.getRatio());
	}

	/**
	 * Shift the gear up or down
	 * 
	 * @param shiftBy
	 *            How much you want to shift by (example: +1)
	 * @return If the gear successfully shifted (will return false if you
	 *         shifted to a gear that doesn't exist)
	 */
	public boolean shiftGearBy(int shiftBy)
	{
		DriveGears newGear = DriveGears
				.getFromId(currentGear.getId() + shiftBy);

		setGear(newGear);
		currentGear = newGear;
		return true;
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

	}
