package frc.robot;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import com.ctre.phoenix.motorcontrol.TalonFXControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;

public class Shooter {

  private final WPI_TalonFX shooterMotor1;
  private final WPI_TalonFX shooterMotor2;
  private static Shooter instance = null;
  private ShootingWheelStates previousShootingState = ShootingWheelStates.OFF;
  private double previousEncoderVelocity;
  private double currentEncoderVelocity;
  private double shooterSpeed = Parameters.SHOOTER_MOTOR_SPEED_LINE;
  private ShootingWheelStates currentShootingState = ShootingWheelStates.OFF;
  private Limelight limelight;
  boolean SpinUp = false;

  // Constuctor
  private Shooter(int shootermotorID1, int shooterMotorID2, int shroudReverseChannel, int shroudForwardChannel) {
    shooterMotor1 = new WPI_TalonFX(shootermotorID1);
    shooterMotor2 = new WPI_TalonFX(shooterMotorID2);
    limelight = Limelight.getInstance();
  }

  public synchronized static Shooter getInstance() {
    if (instance == null) {
      instance = new Shooter(Parameters.SHOOTERMOTOR_L_ID, Parameters.SHOOTERMOTOR_R_ID, Parameters.SHROUD_UP_CHANNEL,
          Parameters.SHROUD_DOWN_CHANNEL);
    }
    return instance;
  }

  public enum ShootingWheelStates {
    SPINUP, OFF
  }

  public double getShooterRPM() {
    double rpm = shooterMotor1.getSensorCollection().getIntegratedSensorVelocity();
    return rpm;
  }

  public boolean isAtTargetRPM(double targetRPM) {
    double currentRPM = getShooterRPM();
    if (currentRPM >= targetRPM) {
      return true;
    } else {
      return false;
    }

  }

  public void setShooterSpeed(double speed) {
    shooterSpeed = speed;
  }

  public void spinShooterMotors(double speed) {

    if (previousShootingState != ShootingWheelStates.SPINUP) {
      // limelight.turnLEDOff();
      previousEncoderVelocity = 0.0;
      currentEncoderVelocity = 0.0;
    }

    shooterMotor1.set(TalonFXControlMode.PercentOutput, speed);
    shooterMotor2.set(TalonFXControlMode.PercentOutput, speed);
    SpinUp = true;
    SmartDashboard.putNumber("Shoot Speed", speed);
  }

  public boolean isSpunUp() {
    currentEncoderVelocity = shooterMotor1.getSensorCollection().getIntegratedSensorVelocity();

    if (currentEncoderVelocity != 0 && previousEncoderVelocity / currentEncoderVelocity > 0.98) {
      return true;
    } else {
      previousEncoderVelocity = currentEncoderVelocity;
      return false;
    }

  }

  public void stopShooterWheel() {
    limelight.turnLEDOff();
    shooterMotor1.set(0.0);
    shooterMotor2.set(0.0);
  }

  public void SpinShooterPrep() {
    if (currentShootingState == ShootingWheelStates.SPINUP) {
      // limelight.turnLEDOff();
      currentShootingState = ShootingWheelStates.OFF;
    } else {
      // limelight.turnLEDOn();
      currentShootingState = ShootingWheelStates.SPINUP;
    }
  }

  public void periodic() {
    // double shooterSpeedTest = SmartDashboard.getNumber("ShooterSpeed", 0.5);
    switch (currentShootingState) {
    case SPINUP:
      spinShooterMotors(shooterSpeed);
      break;
    case OFF:
      stopShooterWheel();
      break;
    }
    previousShootingState = currentShootingState;
  }

}
