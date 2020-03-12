package frc.robot;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.TalonFXControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import edu.wpi.first.wpilibj.DoubleSolenoid;
//import com.cyborgcats.reusable.phoenix.Talon;
//import com.cyborgcats.reusable.spark.SparkMaxNeo;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;

public class Shooter {

  // CONSTANTS

  // private static final double UPPER_PORT_SPEED = 0.0; //same as above

  // INSTANCE
  public static Shooter Instance = null;
  private final WPI_TalonFX shooterMotor1;
  private final WPI_TalonFX shooterMotor2;
  // hopper motor and feeder motor
  private final Victor stirrerMotor;
  private final TalonSRX feederMotor;
  private DoubleSolenoid shroudSolenoid;
  private D_Swerve swerve;
  boolean SpinUp = false;
  private static Shooter instance = null;
  private ShootingWheelStates previousShootingState = ShootingWheelStates.OFF;
  private HopperStates previousHopperState = HopperStates.OFF;
  private double previousEncoderVelocity;
  private double currentEncoderVelocity;
  private double shooterSpeed = Parameters.SHOOTER_MOTOR_SPEED_LINE;
  

  public enum ShootingWheelStates {
    SPINUP, OFF
  }
  public enum HopperStates {
    ALIGN, SHOOTALIGN, SHOOTUNALIGNED, REVERSE, OFF
  }

  private ShootingWheelStates currentShootingState = ShootingWheelStates.OFF;
  private HopperStates currentHopperStates = HopperStates.OFF;
  private Aligner shooterAligner;
  private Limelight limelight;

  // Constructor
  public void ShootNoAlign() {
    currentHopperStates = HopperStates.SHOOTUNALIGNED;
    // if (currentHopperStates == HopperStates.SHOOTUNALIGNED) {
    //   currentHopperStates = HopperStates.OFF;
    // } else {
    //   currentHopperStates = HopperStates.SHOOTUNALIGNED;
    //   SmartDashboard.putNumber("isNoAlign", 1000);

    // }
    
    // currentHopperStates = HopperStates.SHOOTUNALIGNED; 
    // //currentShootingState = ShootingWheelStates.SPINUP;
  }
  public HopperStates getcurrentHopperStates(){
    return currentHopperStates;
  }

  public void stopHOPPER () {
    currentHopperStates = HopperStates.OFF;
  }
  public void ShootAlign() {
     //currentShootingState = currentShootingState.SPINUP;
     currentHopperStates = currentHopperStates.SHOOTALIGN;
  }
  
  public void ReverseHopper() {
    currentHopperStates = currentHopperStates.REVERSE;
  }
  public void Align(){
currentHopperStates = currentHopperStates.ALIGN;
  }

  public void shroudToggle() {
    range();
  }
  
  public void SpinShooterPrep() {
    if (currentShootingState == ShootingWheelStates.SPINUP) {
      //limelight.turnLEDOff();
      currentShootingState = ShootingWheelStates.OFF;
    } else {
     // limelight.turnLEDOn();
      currentShootingState = ShootingWheelStates.SPINUP;
    }
  }

  private Shooter(int shootermotorID1, int shooterMotorID2, int hopperMotorID, int feederMotorID,
      int shroudReverseChannel, int shroudForwardChannel) {
    shooterMotor1 = new WPI_TalonFX(shootermotorID1);
    shooterMotor2 = new WPI_TalonFX(shooterMotorID2);
    stirrerMotor = new Victor(hopperMotorID, ControlMode.PercentOutput);
    feederMotor = new TalonSRX(feederMotorID);
    shroudSolenoid = new DoubleSolenoid(shroudReverseChannel, shroudForwardChannel);
    shooterAligner = Aligner.getInstance();
    limelight = Limelight.getInstance();
      }

  // shooterMotorEncoder1 = new CANEncoder(shooterMotor1);
  // shooterMotorEncoder2 = new CANEncoder(shooterMotor2);

  public synchronized static Shooter getInstance() {
    if (instance == null) {
      instance = new Shooter(Parameters.SHOOTERMOTOR_L_ID, Parameters.SHOOTERMOTOR_R_ID, Parameters.STIRRERMOTOR_ID,
          Parameters.FEEDERMOTOR_ID, Parameters.SHROUD_UP_CHANNEL, Parameters.SHROUD_DOWN_CHANNEL);
    }
    return instance;
  }

  public void periodic() {
    //double shooterSpeedTest = SmartDashboard.getNumber("ShooterSpeed", 0.5);
    switch (currentShootingState) {
    case SPINUP:
      spinShooterMotors(shooterSpeed);
      break;
    case OFF:
      stopShooterWheel();
      break;
    }
    previousShootingState = currentShootingState;
    hopperPeriodic();
  }

  public void hopperPeriodic() {  
  //double shooterSpeedTest = SmartDashboard.getNumber("ShooterSpeed", 0.5);
  switch (currentHopperStates) {
  case SHOOTALIGN:
    shootAlign(shooterSpeed);
    break;
  case SHOOTUNALIGNED:
    spinHopperMotors();
    break;
    case ALIGN:
    shooterAligner.alignRobotToTarget();
    break;
    case REVERSE:
    reverseHopper();
  case OFF:
    stopHopper();
    break;
  }
  previousHopperState = currentHopperStates;
}
  // Hopper and Feeder Motors

  /*
   * CHANGE THIS METHOD: when we are pressing the shoot button, we want to shoot
   * all the balls we have. Make sure we have the target, make sure the motor is
   * above a certain speed.
   * 
   */
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
    
    if(previousShootingState != ShootingWheelStates.SPINUP){
      limelight.turnLEDOff();
      previousEncoderVelocity = 0.0;
      currentEncoderVelocity = 0.0;
    }
    else{
      limelight.turnLEDOn();
    }
    shooterMotor1.set(TalonFXControlMode.PercentOutput, speed);
    shooterMotor2.set(TalonFXControlMode.PercentOutput, speed);
    SpinUp = true;
  }



  public void spinHopperMotors() {
    stirrerMotor.set(ControlMode.PercentOutput, Parameters.STIRRER_MOTOR_SPEED);
    feederMotor.set(ControlMode.PercentOutput, Parameters.FEEDER_STIRRER_MOTOR_SPEED);
  }

  public void reverseHopper () {
    stirrerMotor.set(ControlMode.PercentOutput, -Parameters.STIRRER_MOTOR_SPEED);
    feederMotor.set(ControlMode.PercentOutput, -Parameters.FEEDER_STIRRER_MOTOR_SPEED);
  }

  // public void stopStirrerMotors() {
  //   stirrerMotor.quickSet(0.0);
  // }

  public void shootAlign(double shooterSpeed) {
      shooterAligner.alignRobotToTarget();
      if (shooterAligner.getIsAtTarget(3)) {
        spinShooterMotors(shooterSpeed);
        spinHopperMotors();
      }
  }

  public void shootUnAligned() {
    // spinShooterMotors(-Parameters.SHOOTER_MOTOR_SPEED);
    stirrerMotor.quickSet(Parameters.STIRRER_MOTOR_SPEED);
    feederMotor.set(ControlMode.PercentOutput, Parameters.FEEDER_STIRRER_MOTOR_SPEED);
    // SmartDashboard.putString("Alive", "Is alive");
    // SmartDashboard.putNumber("shooterSpeed(RPM)",
    //     shooterMotor1.getSensorCollection().getIntegratedSensorVelocity() / 2048 * 600);
    // shooterMotor2.set(TalonFXControlMode.Follower, shooterMotor1.getDeviceID());
  }

  public void range() {
    if (shroudSolenoid.get() == Value.kForward) {
      shroudSolenoid.set(Value.kReverse);
    } else {
      shroudSolenoid.set(Value.kForward);
    }
  }

  public void shroudDown () {
    shroudSolenoid.set(Value.kReverse);
  }


  public boolean isSpunUp(){
    currentEncoderVelocity = shooterMotor1.getSensorCollection().getIntegratedSensorVelocity();

    if(currentEncoderVelocity !=0 && previousEncoderVelocity/currentEncoderVelocity > 0.98){
      return true;
    }
    else{
      previousEncoderVelocity = currentEncoderVelocity;
      return false;
    }
    
  }
  // public double distanceSpeed(){
  // double distance = shooterAligner.DistanceToTarget();
  // if (distance >= Parameters.DISTANCE_LOW_MIN && distance <=
  // Parameters.DISTANCE_LOW_MAX) {
  // return Parameters.MOTORSPEEDLOW;
  // }
  // else if(distance >= Parameters.DISTANCE_MED_MIN && distance <=
  // Parameters.DISTANCE_MED_MAX){
  // return Parameters.MOTORSPEEDMEDIUM;
  // }
  // else if(distance >= Parameters.DISTANCE_HIGH_MIN && distance <=
  // Parameters.DISTANCE_HIGH_MAX){
  // return Parameters.MOTORSPEEDHIGH;
  // }
  // else {
  // return 0;
  // }
  // }
  public void stopShooterWheel() {
    limelight.turnLEDOff();
    shooterMotor1.set(0.0);
    shooterMotor2.set(0.0);
  }

  public void stopHopper() {
    stirrerMotor.set(ControlMode.PercentOutput, 0.0);
    feederMotor.set(ControlMode.PercentOutput, 0.0);
  }

}
