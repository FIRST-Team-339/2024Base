package frc.robot.commands.teleop;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.ShooterSubsystem;
import frc.robot.subsystems.ShooterSubsystem.ShooterState;

public class ShooterIntake extends Command {
    
    private ShooterSubsystem shooterSubsystem;
    private boolean intakeToggle = false;

    public ShooterIntake(ShooterSubsystem shooterSubsystem) {
        this.shooterSubsystem = shooterSubsystem;
        addRequirements(shooterSubsystem);
    }

    @Override
    public void execute() {
        this.intakeToggle = !this.intakeToggle;

        this.shooterSubsystem.setState(this.intakeToggle ? ShooterState.INTAKING : ShooterState.OFF);
        
        //  if (this.shooterSubsystem.getState() == ShooterState.OFF && intakeToggle == false) 
        //  {
        //      intakeToggle = true;
        //      this.shooterSubsystem.setState(ShooterState.INTAKING);
        //  }else 
        //  {
        //      intakeToggle = false;
        //      this.shooterSubsystem.setState(ShooterState.OFF);
        //  }
    }
}
