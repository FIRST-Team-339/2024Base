package frc.robot.subsystems;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.*;
import frc.robot.hardwareInterfaces.DoubleSolenoidGroup;

public class FlipperPistonSubsystem extends SubsystemBase
  {
  private DoubleSolenoidGroup doubleSolenoidGroup;

  public FlipperPistonSubsystem()
    {
      DoubleSolenoid[] doubleSolenoids =
        { new DoubleSolenoid(PneumaticsModuleType.CTREPCM,
            FlipperPistonConstants.LEFT_PISTON_FWD_PORT,
            FlipperPistonConstants.LEFT_PISTON_REV_PORT),
            new DoubleSolenoid(PneumaticsModuleType.CTREPCM,
                FlipperPistonConstants.RIGHT_PISTON_FWD_PORT,
                FlipperPistonConstants.RIGHT_PISTON_REV_PORT) };

      this.doubleSolenoidGroup = new DoubleSolenoidGroup(doubleSolenoids, true);
    }

  /**
   * Flip the flipper piston up
   */
  public void flipUp()
  {
    this.doubleSolenoidGroup.setReverse();
  }

  /**
   * Flip the flipper piston down
   */
  public void flipDown()
  {
    this.doubleSolenoidGroup.setForward();
  }
  }
