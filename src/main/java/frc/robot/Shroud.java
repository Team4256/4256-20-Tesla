package frc.robot;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;

public class Shroud {
  private DoubleSolenoid shroudSolenoid;
  private static Shroud instance = null;

  private Shroud(int shroudReverseChannel, int shroudForwardChannel) {
    shroudSolenoid = new DoubleSolenoid(shroudReverseChannel, shroudForwardChannel);

  }

  public synchronized static Shroud getInstance() {
    if (instance == null) {
      instance = new Shroud(Parameters.SHROUD_UP_CHANNEL, Parameters.SHROUD_DOWN_CHANNEL);
    }
    return instance;
  }

  public void shroudDown() {
    shroudSolenoid.set(Value.kReverse);
  }

  public void shroudUp() {
    shroudSolenoid.set(Value.kForward);
  }

  public void shroudToggle() {
    if (shroudSolenoid.get() == Value.kForward) {
      shroudSolenoid.set(Value.kReverse);
    } else {
      shroudSolenoid.set(Value.kForward);
    }
  }
}