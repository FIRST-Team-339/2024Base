package frc.robot.hardwareInterfaces.Transmission;

import edu.wpi.first.wpilibj.motorcontrol.MotorController;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
import frc.robot.hardwareInterfaces.Transmission.TankControlBase.MotorPosition;

/**
 * Like the traction drive system, except with four motors, usually all as
 * omni-wheels. Or, tank drive with paired motors. Each joystick controls one
 * side of the robot.
 * 
 * @author Ryan McGee
 * @written 7/21/17
 */
public class oldTankTransmission
        {
        /**
         * Creates the Transmission object.
         * 
         * @param leftSide
         *                the grouped motor controllers on the left side of the
         *                robot
         * 
         * @param rightSide
         *                the grouped motor controllers on the right side of the
         *                robot
         */
        public oldTankTransmission(MotorControllerGroup leftSide,
                        MotorControllerGroup rightSide)
                {
                        this.leftSide = new TankControlBase(leftSide);
                        this.rightSide = new TankControlBase(rightSide);
                } // end constructor

        /**
         * Creates the Transmission object.
         * 
         * @param leftSide
         *                the grouped motor controllers on the left side of the
         *                robot
         * 
         * @param rightSide
         *                the grouped motor controllers on the right side of the
         *                robot
         */
        public oldTankTransmission(MotorController leftSideRear,
                        MotorController rightSideRear,
                        MotorController leftSideFront,
                        MotorController rightSideFront)
                {
                        this.leftSide = new TankControlBase(leftSideRear,
                                        leftSideFront);
                        this.rightSide = new TankControlBase(rightSideRear,
                                        rightSideFront);
                } // end constructor

        /**
         * Turns off the deadband for use in auto.
         */
        public void disableDeadband()
        {
                this.leftSide.disableDeadband();
                this.rightSide.disableDeadband();
        } // end disableDeadband()

        /**
         * Removes one from the current gear of the robot, allowing the user to
         * drive slower.
         */
        public void downShift()
        {
                this.leftSide.downShift();
                this.rightSide.downShift();
        } // end downShift()

        /**
         * Drives the transmission based on a Tank drive system, but without
         * gear ratios or joystick deadbands. Use for autonomous purposes.
         *
         * @param motorSpeed
         *                The value of the motors, in percentage (-1.0 to 1.0)
         */
        public void driveRaw(double motorSpeed)
        {
                this.leftSide.driveRaw(motorSpeed);
                this.rightSide.driveRaw(motorSpeed);
        } // end driveRaw() - overloaded

        /**
         * Drives the transmission based on a Tank drive system, but without
         * gear ratios or joystick deadbands. This represents 2 motors
         * controlling a single side of the robot but NOT in a ControllerGroup.
         *
         * @param rearVal
         *                The left value of the robot, in percentage (-1.0 to
         *                1.0)
         * @param frontVal
         *                The right value of the robot, in percentage (-1.0 to
         *                1.0)
         */
        public void driveRaw(double rearVal, double frontVal)
        {
                this.leftSide.driveRaw(rearVal, frontVal);
                this.rightSide.driveRaw(rearVal, frontVal);
        } // end driveRaw() - overloaded

        /**
         * Drives the transmission based on a Tank drive system, but without
         * gear ratios or joystick deadbands. Use for autonomous purposes.
         *
         * @param motorSpeed
         *                The value of the motors, in percentage (-1.0 to 1.0)
         */
        public void driveRaw(double magnitude, double direction,
                        double rotation)
        {
                this.leftSide.driveRaw(magnitude, direction, rotation);
                this.rightSide.driveRaw(magnitude, direction, rotation);
        } // end driveRaw() - overloaded

        /**
         * Drives the transmission based on a Tank drive system, where left
         * controls the left wheels, and right controls the right wheels.
         * 
         * Uses joystick deadbands and gear ratios.
         * 
         * @param leftSideVal
         *                Percentage, (-1.0 to 1.0)
         * @param rightSideVal
         *                Percentage, (-1.0 to 1.0)
         */
        public void drive(double leftSideVal, double rightSideVal)
        {
                double leftOut = leftSide.scaleJoystickForDeadband(leftSideVal)
                                * leftSide.getCurrentGearRatio();
                leftSide.driveRaw(leftOut);
                double rightOut = rightSide.scaleJoystickForDeadband(
                                rightSideVal) * rightSide.getCurrentGearRatio();
                rightSide.driveRaw(rightOut);
        } // end drive()

        /**
         * Turns on the deadband for use in teleop.
         */
        public void enableDeadband()
        {
                this.leftSide.enableDeadband();
                this.rightSide.enableDeadband();
        } // end enableDeadband()

        /**
         * @return The gear number that is active as seen from the leftSide
         */
        public int getCurrentGear()
        {
                return this.leftSide.getCurrentGear();
        } // end getCurrentGear()

        /**
         * @return The percentage corresponding to the current gear as seen from
         *         the leftSide
         */
        public double getCurrentGearRatio()
        {
                return this.leftSide.getCurrentGearRatio();
        } // end getCurrentGearRatio()

        /**
         * @param position
         *                which corner the motor is in
         * @return the motor controller object
         */
        public MotorController getMotorController(Motors position)
        {
                switch (position)
                        {
                        // return the motor on this side of the tank
                        // OR if NOT in a MotorControllerGroup, then
                        // the rear designated motor
                        case LEFT:
                                return this.leftSide.getMotorController(
                                                MotorPosition.MOTOR);
                        case LEFT_REAR:
                                return this.leftSide.getMotorController(
                                                MotorPosition.MOTOR_REAR);
                        case LEFT_FRONT:
                                return this.leftSide.getMotorController(
                                                MotorPosition.MOTOR_FRONT);
                        // if NOT in a MotorControllerGroup, then
                        // the front designated motor
                        case RIGHT:
                                return this.rightSide.getMotorController(
                                                MotorPosition.MOTOR);
                        case RIGHT_REAR:
                                return this.rightSide.getMotorController(
                                                MotorPosition.MOTOR_REAR);
                        case RIGHT_FRONT:
                                return this.rightSide.getMotorController(
                                                MotorPosition.MOTOR_FRONT);
                        default:
                                return null;
                        } // end switch
        } // end getMotorController()

        /**
         * @return The type of transmission of a class extending TankControl.
         */
        public TransmissionType getType()
        {
                return type;
        } // end getType()

        /**
         * Performs scaling for the Joysticks. See the TankControl class for an
         * explanation of what is going on.
         *
         * @param input
         * @return The scaled value, if between -1 and -deadband or deadband and
         *         1, or 0 if between -deadband and deadband for the leftSide of
         *         the robot
         */
        public double scaleJoystickForDeadband(double input)
        {
                this.rightSide.scaleJoystickForDeadband(input);
                return (this.leftSide.scaleJoystickForDeadband(input));
        } // end scaleJoystickForDeadband()

        /**
         * Sets every gear ratio. Make sure that the lowest gear starts at 0,
         * and the highest gear is at the max, to make sure the up-shifting and
         * down-shifting works properly.
         *
         * @param ratios
         *                Percent multiplied by the transmission.drive functions
         */
        public void setAllGearPercentages(double... ratios)
        {
                this.leftSide.setAllGearPercentages(ratios);
                this.rightSide.setAllGearPercentages(ratios);
        } // end setAllGearPercentages()

        /**
         * TODO Test gear system Sets the current gear for the robot. This will
         * change the maximum speed of the robot for precise aiming/driving.
         *
         * @param gear
         *                The requested gear number. If outside the range, it
         *                will do nothing.
         */
        public void setGear(int gear)
        {
                this.leftSide.setGear(gear);
                this.rightSide.setGear(gear);
        } // end setGear()

        /**
         * Sets the percent multiplied by Transmission.
         *
         * @param gear
         *                Which gear should be changed: 0 is lowest, increasing.
         * @param value
         *                Percent decimal form: between 0.0 and less than or
         *                equal to 1.0
         */
        public void setGearPercentage(int gear, double value)
        {
                this.leftSide.setGearPercentage(gear, value);
                this.rightSide.setGearPercentage(gear, value);
        } // end setGearPercentage()

        /**
         * TODO test deadbands Sets the minimum value the joysticks must output
         * in order for the robot to start moving.
         *
         * @param deadband
         *                Percentage value, ranging from 0.0 to 1.0, in
         *                decimals.
         */
        public void setJoystickDeadband(double deadband)
        {
                this.leftSide.setJoystickDeadband(deadband);
                this.rightSide.setJoystickDeadband(deadband);
        } // end setJoystickDeadband()

        /**
         * Sets the maximum gear to the value input.
         *
         * @param value
         *                Percent (0.0 to 1.0)
         */
        public void setMaxGearPercentage(double value)
        {
                this.leftSide.setMaxGearPercentage(value);
                this.rightSide.setMaxGearPercentage(value);
        } // end setMaxGearPercentage()

        /**
         * Sets the robot to the maximum gear available
         *
         */
        public void setToMaxGear()
        {
                this.leftSide.setToMaxGear();
                this.rightSide.setToMaxGear();
        } // end setToMaxGear()

        /**
         * Shift gears using a up-shift and down-shift button. Also makes sure
         * that holding the button will not trigger multiple shifts.
         *
         * @param upShiftButton
         *                The button that should change to the next higher gear
         * @param downShiftButton
         *                The button that should change to the next lowest gear
         */
        public void shiftGears(boolean upShiftButton, boolean downShiftButton)
        {
                this.leftSide.shiftGears(upShiftButton, downShiftButton);
                this.rightSide.shiftGears(upShiftButton, downShiftButton);
        } // end shiftGears()

        /**
         * Tells the robot to cut all power to the motors.
         */
        public void stop()
        {
                this.leftSide.stop();
                this.rightSide.stop();
        } // end stop()

        /**
         * Tells the robot to move up one gear.
         */
        public void upShift()
        {
                this.leftSide.upShift();
                this.rightSide.upShift();
        } // end upShift()

        // =========================================

        public enum Motors
                {
                /**
                 * the left side (if two wheel drive) or left rear (if four
                 * wheel drive)
                 */
                LEFT,
                /**
                 * the right side (if two wheel drive) or right rear (if four
                 * wheel drive)
                 */
                RIGHT,
                /** the left front wheel */
                LEFT_FRONT,
                /** the left rear wheel */
                LEFT_REAR,
                /** the right front wheel */
                RIGHT_FRONT,
                /** the right rear wheel */
                RIGHT_REAR,
                /** all motor positions, not used in getMotorController. */
                ALL
                } // end enum Motors

        /**
         * The current types of transmissions available.
         *
         * @author Ryan McGee
         *
         */
        public enum TransmissionType
                {
                /**
                 * Tank-style drive system with a left drive, and a right drive.
                 */
                TANK
                } // end enum TransissionType

        // ==============================
        // The two individual TankControls representing
        // the left and right side of the tank drive
        // ==============================
        private TankControlBase leftSide;
        private TankControlBase rightSide;

        // The current stored transmission type
        TransmissionType type = TransmissionType.TANK;
        } // end class
