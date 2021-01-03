package frc.robot;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.TalonFXControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

public class Hopper {
  // hopper motor and feeder motor
  private final Victor stirrerMotor;
  private final TalonSRX feederMotor;
  private static Hopper instance = null;
  private HopperStates previousHopperState = HopperStates.OFF;

  // Constuctor
  private Hopper(int hopperMotorID, int feederMotorID) {
    stirrerMotor = new Victor(hopperMotorID, ControlMode.PercentOutput);
    feederMotor = new TalonSRX(feederMotorID);
    aligner = Aligner.getInstance();
  }

  public synchronized static Hopper getInstance() {
    if (instance == null) {
      instance = new Hopper(Parameters.STIRRERMOTOR_ID, Parameters.FEEDERMOTOR_ID);
    }
    return instance;
  }

  public enum HopperStates {
    ALIGN, SHOOTALIGN, SHOOTUNALIGNED, REVERSE, OFF
  }

  private HopperStates currentHopperStates = HopperStates.OFF;
  private Aligner aligner;

  public void ShootNoAlign() {
    currentHopperStates = HopperStates.SHOOTUNALIGNED;
  }

  public HopperStates getcurrentHopperStates() {
    return currentHopperStates;
  }

  public void StopHopper() {
    currentHopperStates = HopperStates.OFF;
  }

  public void ShootAlign() {
    currentHopperStates = currentHopperStates.SHOOTALIGN;
  }

  public void ReverseHopper() {
    currentHopperStates = currentHopperStates.REVERSE;
  }

  public void Align() {
    currentHopperStates = currentHopperStates.ALIGN;
  }

  public void spinHopperMotors() {
    stirrerMotor.set(ControlMode.PercentOutput, Parameters.STIRRER_MOTOR_SPEED);
    feederMotor.set(ControlMode.PercentOutput, Parameters.FEEDER_STIRRER_MOTOR_SPEED);
  }

  public void reverseHopper() {
    stirrerMotor.set(ControlMode.PercentOutput, -Parameters.STIRRER_MOTOR_SPEED);
    feederMotor.set(ControlMode.PercentOutput, -Parameters.FEEDER_STIRRER_MOTOR_SPEED);
  }

  public void shootAlign() {
    aligner.alignRobotToTarget();
    if (aligner.getIsAtTarget(3)) {
      spinHopperMotors();
    }
  }

  public void shootUnAligned() {
    stirrerMotor.quickSet(Parameters.STIRRER_MOTOR_SPEED);
    feederMotor.set(ControlMode.PercentOutput, Parameters.FEEDER_STIRRER_MOTOR_SPEED);
  }

  public void stopHopper() {
    stirrerMotor.set(ControlMode.PercentOutput, 0.0);
    feederMotor.set(ControlMode.PercentOutput, 0.0);
  }

  public void hopperPeriodic() {
    switch (currentHopperStates) {
    case SHOOTALIGN:
      shootAlign();
      break;
    case SHOOTUNALIGNED:
      spinHopperMotors();
      break;
    case ALIGN:
      aligner.alignRobotToTarget();
      break;
    case REVERSE:
      reverseHopper();
      break;
    case OFF:
      stopHopper();
      break;
    }
    previousHopperState = currentHopperStates;
  }

}