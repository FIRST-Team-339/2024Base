package frc.robot.commands.teleop;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.ShooterSubsystem;
import frc.robot.subsystems.ShooterSubsystem.ShooterState;

public class ShooterIntake extends Command {
    
    private ShooterSubsystem shooterSubsystem;
    public ShooterIntake(ShooterSubsystem shooterSubsystem) {
        this.shooterSubsystem = shooterSubsystem;
        addRequirements(shooterSubsystem);
    }

    @Override
    public void execute() {
        if (this.shooterSubsystem.getState() == ShooterState.OFF) this.shooterSubsystem.setState(ShooterState.INTAKING);
    }
}
