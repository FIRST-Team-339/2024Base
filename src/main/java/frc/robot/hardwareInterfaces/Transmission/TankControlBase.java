package frc.robot.hardwareInterfaces.Transmission;

import edu.wpi.first.wpilibj.motorcontrol.MotorController;

/**
 * Contains necessary functions that must be included in each transmission type
 * class created.
 *
 * @author Ryan McGee
 * @written 7/17/2017
 */
public class TankControlBase
    {

    /**
     * Creates the TankControl object for a single side of the tank. The
     * MotorController here is MotorControllerGroup which may contain any number
     * of motors (from 1 up). All motors in this group MUST be on the same side
     * of the Tank.
     *
     * @param controllingMotors
     */
    public TankControlBase(MotorController controllingMotors)
        {
            this.motors = new MotorController[1];
            this.motors[0] = controllingMotors;
        } // end constructor

    /**
     * Creates the TankControl object. This represents 2 motors controlling a
     * single side of the robot but NOT in a ControllerGroup.
     *
     * @param rearMotor
     * @param frontMotor
     */
    public TankControlBase(MotorController rearMotor,
            MotorController frontMotor)
        {
            this.motors = new MotorController[2];
            this.motors[0] = rearMotor;
            this.motors[1] = frontMotor;
        } // end constructor

    /**
     * Turns off the deadband for use in auto.
     */
    public void disableDeadband()
    {
        currentJoystickDeadband = 0;
    } // end disableDeadband()

    /**
     * Removes one from the current gear of the robot, allowing the user to
     * drive slower.
     */
    public void downShift()
    {
        if (currentGear > 0)
            currentGear--;
    } // end downShift()

    /**
     * Drives the transmission based on a Tank drive system, but without gear
     * ratios or joystick deadbands. Use for autonomous purposes.
     *
     * @param motorSpeed
     *            The value of the motors, in percentage (-1.0 to 1.0)
     */
    public void driveRaw(double motorSpeed)
    {
        this.motors[0].set(motorSpeed);
    } // end driveRaw() - overloaded

    /**
     * Drives the transmission based on a Tank drive system, but without gear
     * ratios or joystick deadbands. This represents 2 motors controlling a
     * single side of the robot but NOT in a ControllerGroup.
     *
     * @param rearVal
     *            The left value of the robot, in percentage (-1.0 to 1.0)
     * @param frontVal
     *            The right value of the robot, in percentage (-1.0 to 1.0)
     */
    public void driveRaw(double rearVal, double frontVal)
    {
        for (int i = 0; i < this.motors.length; i++)
            if (i % 2 == 0)
                this.motors[i].set(rearVal);
            else
                this.motors[i].set(frontVal);
    } // end driveRaw() - overloaded

    /**
     * Drives the robot based on raw inputs, for autonomous uses. Also, can use
     * a correction PID loop for rotation, if that is enabled. (Functionality
     * overridden in MecanumTransmission class)
     *
     * @param magnitude
     *            How fast the robot will travel (0.0 to 1.0)
     * @param direction
     *            In which direction, laterally, will the robot travel (degrees,
     *            -180 to 180. 0 is forward.)
     * @param rotation
     *            How much the robot should be turning (left,(-1.0) to
     *            right,(1.0)
     */
    public void driveRaw(double magnitude, double direction, double rotation)
    {
        this.stop();
        // If this object is an omni-directional drive and this method is
        // called,
        // it will be overridden by the superclass. This prevents a tank style
        // transmission from forcing the motors against each other, as it will
        // by default to do nothing.
    } // end driveRaw() - overloaded

    /**
     * Turns on the deadband for use in teleop.
     */
    public void enableDeadband()
    {
        currentJoystickDeadband = inputJoystickDeadband;
    } // end enableDeadband()

    /**
     * @return all speed controllers attached to this transmission, as an array.
     */
    public MotorController[] getAllMotorControllers()
    {
        return this.motors;
    } // end getAllMotorControllers()

    /**
     * @return The gear number that is active
     */
    public int getCurrentGear()
    {
        return this.currentGear;
    } // end getCurrentGear()

    /**
     * @return The percentage corresponding to the current gear
     */
    public double getCurrentGearRatio()
    {
        return gearRatios[currentGear];
    } // end getCurrentGearRatio()

    /**
     * @param position
     *            which corner the motor is in
     * @return the motor controller object
     */
    public MotorController getMotorController(MotorPosition position)
    {
        switch (position)
            {
            // return the motor on this side of the tank
            // OR if NOT in a MotorControllerGroup, then
            // the rear designated motor
            case MOTOR:
            case MOTOR_REAR:
                return this.motors[0];
            // if NOT in a MotorControllerGroup, then
            // the front designated motor
            case MOTOR_FRONT:
                if (this.motors.length > 1)
                    return this.motors[1];
                else
                    return null;
            default:
                return null;
            } // end switch

    } // end getMotorController()

    /**
     * Uses the formula for mapping one set of values to the other: y = mx + b
     *
     * m = 1 / (1 - deadband) b = deadband * -m x = joystick input y = motor
     * output
     *
     * Therefore, motor output = (1 / (1 - deadband)) * joystick input + (1 - (1
     * / (1 - deadband)))
     *
     * If this equation does not make much sense, try graphing it first as the
     * original x = y, and then the scaled output starting at the deadband, and
     * use the slope formula.
     *
     * @param input
     * @return The scaled value, if between -1 and -deadband or deadband and 1,
     *         or 0 if between -deadband and deadband.
     */
    public double scaleJoystickForDeadband(double input)
    {
        double deadbandSlope = 1.0 / (1.0 - currentJoystickDeadband);
        double constant = -this.currentJoystickDeadband * deadbandSlope;

        if (input > this.currentJoystickDeadband)
            return (deadbandSlope * input) + constant;
        else
            if (input < -this.currentJoystickDeadband)
                return -((-deadbandSlope * input) + constant);

        // Set to 0 if it is between the deadbands.
        return 0.0;
    } // end scaleJoystickForDeadband()

    /**
     * Sets every gear ratio. Make sure that the lowest gear starts at 0, and
     * the highest gear is at the max, to make sure the up-shifting and
     * down-shifting works properly.
     *
     * @param ratios
     *            Percent multiplied by the transmission.drive functions
     */
    public void setAllGearPercentages(double... ratios)
    {
        this.gearRatios = ratios;
    } // end setAllGearPercentages()

    /**
     * TODO Test gear system Sets the current gear for the robot. This will
     * change the maximum speed of the robot for precise aiming/driving.
     *
     * @param gear
     *            The requested gear number. If outside the range, it will do
     *            nothing.
     */
    public void setGear(int gear)
    {
        if (gear >= 0 && gear < gearRatios.length)
            this.currentGear = gear;
    } // end setGear()

    /**
     * Sets the percent multiplied by Transmission.
     *
     * @param gear
     *            Which gear should be changed: 0 is lowest, increasing.
     * @param value
     *            Percent decimal form: between 0.0 and less than or equal to
     *            1.0
     */
    public void setGearPercentage(int gear, double value)
    {
        if (value <= 1.0 && value >= 0.0 && gear < gearRatios.length
                && gear >= 0)
            {
            gearRatios[gear] = value;
            }
    } // end setGearPercentage()

    /**
     * TODO test deadbands Sets the minimum value the joysticks must output in
     * order for the robot to start moving.
     *
     * @param deadband
     *            Percentage value, ranging from 0.0 to 1.0, in decimals.
     */
    public void setJoystickDeadband(double deadband)
    {
        this.inputJoystickDeadband = deadband;
        this.enableDeadband();
    } // end setJoystickDeadband()

    /**
     * Sets the maximum gear to the value input.
     *
     * @param value
     *            Percent (0.0 to 1.0)
     */
    public void setMaxGearPercentage(double value)
    {
        this.gearRatios[gearRatios.length - 1] = value;
    } // end setMaxGearPercentage()

    /**
     * Sets the robot to the maximum gear available
     *
     */
    public void setToMaxGear()
    {
        this.currentGear = gearRatios.length - 1;
    } // end setToMaxGear()

    /**
     * Shift gears using a up-shift and down-shift button. Also makes sure that
     * holding the button will not trigger multiple shifts.
     *
     * @param upShiftButton
     *            The button that should change to the next higher gear
     * @param downShiftButton
     *            The button that should change to the next lowest gear
     */
    public void shiftGears(boolean upShiftButton, boolean downShiftButton)
    {
        // Makes sure that if the button is held down, it doesn't constantly
        // cycle through gears.
        if (downShiftButton && !downShiftButtonStatus)
            {
            downShift();
            } // if
        else
            if (upShiftButton && !upShiftButtonStatus)
                {
                upShift();
                } // if

        upShiftButtonStatus = upShiftButton;
        downShiftButtonStatus = downShiftButton;
    } // end shiftGears()

    /**
     * Tells the robot to cut all power to the motors.
     */
    public void stop()
    {
        for (MotorController sc : motors)
            sc.set(0);
    } // end stop()

    /**
     * Adds one to the current gear of the robot, allowing the user to drive
     * faster.
     */

    /*
     * ====================================================================== To
     * (Hopefully) fix McGee's code, uncomment the gearArrayInit method. Comment
     * out the declaration that declares gearRatios with set values, and
     * uncomment the declaration with no set values. THIS HAS NOT BEEN TESTED!!!
     * There is a workaround in teleopDrive method, but this is a possible
     * permanent fix.
     * =====================================================================
     */
    // public void gearArrayInit(int numberOfGears)
    // {
    // double[] gearRatios;
    // gearRatios = new double[numberOfGears];
    // }

    /**
     * Tells the robot to move up one gear.
     */
    public void upShift()
    {
        if (currentGear < gearRatios.length - 1)
            currentGear++;
    } // end upShift()

    // =========================================

    /**
     * Describes which corner a motor is in when identifying it.
     *
     * @author Ryan McGee
     */
    public enum MotorPosition
        {
        /**
         * the motor on this side
         */
        MOTOR,
        // ========================
        // If there are 2 motors per side
        // and these are NOT in a MotorControllerGroup
        // then the rear designated motor
        // ========================
        MOTOR_REAR,
        // ========================
        // If there are 2 motors per side
        // and these are NOT in a MotorControllerGroup
        // then the front designated motor
        // ========================
        MOTOR_FRONT
        } // end enum MotorPosition

    // ================VARIABLES================
    private double[] gearRatios =
        { .6, .8, 1 };
    // public static double[] gearRatios;

    private final MotorController motors[];

    // Will default to the highest gear available
    private int currentGear = 0;

    // private int numberOfGears = 2;

    // private int maxGears = 2;

    private boolean upShiftButtonStatus = false;

    private boolean downShiftButtonStatus = false;

    private double inputJoystickDeadband = DEFAULT_JOYSTICK_DEADBAND;

    // The motors will start turning only once the joystick is past this
    // deadband.
    private double currentJoystickDeadband = inputJoystickDeadband;

    // ================CONSTANTS================
    /** the default deadband applied to all joysticks used in drive methods */
    public static final double DEFAULT_JOYSTICK_DEADBAND = .2;
    // =========================================
    } // end class
