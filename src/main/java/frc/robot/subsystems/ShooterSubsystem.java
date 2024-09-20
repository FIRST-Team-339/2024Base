package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.TalonSRXControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.FlipperPistonConstants;
import frc.robot.Constants.ShooterConstants;

public class ShooterSubsystem extends SubsystemBase {
    private TalonSRX topMotor;
    private TalonSRX bottomMotor;
    private DoubleSolenoid piston;
    private ShooterState state;

    public static enum ShooterState { //the states of shooting 
        OFF,
        SHOOTING,
        REVVING,
        INTAKING
    }

    public ShooterSubsystem() {
        topMotor = new TalonSRX(ShooterConstants.TOP_MOTOR_ID);
        bottomMotor = new TalonSRX(ShooterConstants.BOTTOM_MOTOR_ID);
        piston = new DoubleSolenoid(PneumaticsModuleType.CTREPCM, FlipperPistonConstants.RIGHT_PISTON_FWD_PORT,
        FlipperPistonConstants.RIGHT_PISTON_REV_PORT);
    }

    public ShooterState getState() {
        return this.state;
    }

    public void setState(ShooterState newState) {
        this.state = newState;
    }

    public void periodic() {
        if (this.state == ShooterState.OFF) { //if it's off
            this.topMotor.set(TalonSRXControlMode.PercentOutput, 0);
            this.bottomMotor.set(TalonSRXControlMode.PercentOutput, 0);
            this.piston.set(Value.kReverse); //contract the pistons
        } else if (this.state == ShooterState.REVVING) { //if it's revving
            this.topMotor.set(TalonSRXControlMode.PercentOutput, ShooterConstants.OUTTAKE_SPEED); //top motor spins outtake
            this.bottomMotor.set(TalonSRXControlMode.PercentOutput, -ShooterConstants.OUTTAKE_SPEED); //bottom motor spins reverse outtake
        } else if (this.state == ShooterState.SHOOTING) { //if it's shooting
            this.piston.set(Value.kForward);
        } else if (this.state == ShooterState.INTAKING) { //if it's intaking
            this.topMotor.set(TalonSRXControlMode.PercentOutput, -ShooterConstants.INTAKE_SPEED); //top motor spins intake
            this.bottomMotor.set(TalonSRXControlMode.PercentOutput, ShooterConstants.INTAKE_SPEED); //bottom motor spins reverse intake
        }
    }
}
