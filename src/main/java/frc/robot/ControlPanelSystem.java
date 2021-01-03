package frc.robot;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.util.Color;
import edu.wpi.first.wpilibj.DriverStation;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;

public class ControlPanelSystem {
  public enum ControlPanelSystemStates {
    ARMDOWN, ARMUP, SPIN3REVS, SPINTOACOLOR, STOP;

  }

  private ControlPanelSystemStates currentState = ControlPanelSystemStates.STOP;
  private WPI_TalonFX colorMotor;
  private DoubleSolenoid wheelArmSolenoid;
  private Color previousColor;
  private Color targetColor;
  private int colorWedgeCount = 0;
  private boolean hasStartedSpinning = false;
  String gameData;
  private Color_Sensor colorSensor = new Color_Sensor();

  public void ArmDown() {
    currentState = ControlPanelSystemStates.ARMDOWN;
  }

  public void ArmUp() {
    currentState = ControlPanelSystemStates.ARMUP;
  }

  public void spin3Revs() {
    currentState = ControlPanelSystemStates.SPIN3REVS;
  }

  public void spinToAColor() {
    currentState = ControlPanelSystemStates.SPINTOACOLOR;
  }

  public void stop() {
    currentState = ControlPanelSystemStates.STOP;
  }

  public void periodic() {
    switch (currentState) {
    case ARMDOWN:
      moveArmSolenoidDown();
      break;
    case ARMUP:
      moveArmSolenoidUp();
      break;
    case SPIN3REVS:
      spinControlPanel3Revs();
      break;
    case SPINTOACOLOR:
      spinToTheTargetColor();
      break;
    case STOP:

      break;

    }

  }

  public ControlPanelSystem(int upChannel, int downChannel, int colorMotorID) {
    wheelArmSolenoid = new DoubleSolenoid(upChannel, downChannel);
    colorMotor = new WPI_TalonFX(colorMotorID);
  }

  public void moveArmSolenoidUp() {
    wheelArmSolenoid.set(DoubleSolenoid.Value.kReverse);

  }

  public void moveArmSolenoidDown() {
    wheelArmSolenoid.set(DoubleSolenoid.Value.kForward);

  }

  private void spinCountInit() {
    if (!hasStartedSpinning) {
      previousColor = colorSensor.getCurrentColor();
      colorWedgeCount = 0;
      hasStartedSpinning = true;
    }
  }

  private void checkColorChanged() {
    Color currentColor = colorSensor.getCurrentColor();
    if (currentColor == null)
      return;
    if (currentColor != previousColor) {
      ++colorWedgeCount;
      previousColor = currentColor;
    }

  }

  public void spinControlPanel3Revs() {
    // check if the wheel is really down
    spinCountInit();
    checkColorChanged();
    if (colorWedgeCount < Parameters.TARGET_WEDGE_COUNT) {
      colorMotor.set(Parameters.COLOR_MOTOR_SPEED);

    } else {
      colorMotor.set(0.0);
      hasStartedSpinning = false;
      currentState = ControlPanelSystemStates.STOP;

    }

  }

  public void getTargetColor() {
    gameData = DriverStation.getInstance().getGameSpecificMessage();
    if (gameData.length() > 0) {
      switch (gameData.charAt(0)) {
      case 'B':
        targetColor = Parameters.myBlue;
        break;
      case 'G':
        targetColor = Parameters.myGreen;
        break;
      case 'R':
        targetColor = Parameters.myRed;
        break;
      case 'Y':
        targetColor = Parameters.myYellow;
        break;
      default:
        targetColor = null;
        break;
      }
    } else {
      targetColor = null;
    }
  }

  public void spinToTheTargetColor() {
    Color currentColor = colorSensor.getCurrentColor();
    getTargetColor();
    if (targetColor != null && currentColor != targetColor) {
      colorMotor.set(Parameters.COLOR_MOTOR_SPEED);

    } else {
      colorMotor.set(0.0);
      currentState = ControlPanelSystemStates.STOP;

    }

  }

}
