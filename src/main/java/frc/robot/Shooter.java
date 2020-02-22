package frc.robot;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.TalonFXControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import edu.wpi.first.wpilibj.DoubleSolenoid;
//import com.cyborgcats.reusable.phoenix.Talon;
//import com.cyborgcats.reusable.spark.SparkMaxNeo;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;


public class Shooter {

  // CONSTANTS
  
  // private static final double UPPER_PORT_SPEED = 0.0; //same as above

  // INSTANCE
  private final WPI_TalonFX shooterMotor1;
  private final WPI_TalonFX shooterMotor2;
  //hopper motor and feeder motor
  private final TalonSRX stirrerMotor;
  private final TalonSRX feederMotor;
  private DoubleSolenoid shroudSolenoid;
  private Aligner aligner;
  boolean SpinUp = false;

  
  // private CANEncoder shooterMotorEncoder1;
  // private CANEncoder shooterMotorEncoder2;
  public enum ShootingStates{
    SPINUP,
    SHOOTNOALIGN,
    SHOOTALIGN,
    SHOOTERRANGE,
    OFF;
  }
  public enum FeederStates{

  }
  private ShootingStates currentShootingState = ShootingStates.OFF;
  private ShootingStates currentFeederStates = ShootingStates.OFF;
  private Aligner shooterAligner;
  //Constructor
  public void SpinShooterPrep(){
    currentShootingState = ShootingStates.SPINUP;
  }
  public void ShootNoAlign(){
    currentShootingState = ShootingStates.SHOOTNOALIGN;
  }
  public void ShootAlign(){
    currentShootingState = ShootingStates.SHOOTALIGN;
  }
  public void ShooterRange(){
    currentShootingState = ShootingStates.SHOOTERRANGE;
  }
  public void STOP(){
    currentShootingState = ShootingStates.OFF;
  }
  public Shooter(Aligner aligner, int shootermotorID1, int shooterMotorID2, int hopperMotorID, int feederMotorID, int shroudForwardChannel, int shroudReverseChannel) {
    shooterMotor1 = new WPI_TalonFX(shootermotorID1);
    shooterMotor2 = new WPI_TalonFX(shooterMotorID2);
    stirrerMotor = new TalonSRX(hopperMotorID);
    feederMotor = new TalonSRX(feederMotorID);
    shroudSolenoid = new DoubleSolenoid(shroudForwardChannel, shroudReverseChannel);
    shooterAligner = aligner;

    // shooterMotorEncoder1 = new CANEncoder(shooterMotor1);
    // shooterMotorEncoder2 = new CANEncoder(shooterMotor2);

  }
  public void periodic(){
    switch(currentShootingState){
      case SPINUP:
        spinShooterMotors(Parameters.MOTORSPEEDMEDIUM);
        break;
      case SHOOTNOALIGN:
        shootUnAligned();
        break;
      case SHOOTALIGN:
        shootAlign();
        break;
      case SHOOTERRANGE:
        range();
        break;
      case OFF:
        stop();
        break;
    }
  }


  //Hopper and Feeder Motors
  
  /*
  CHANGE THIS METHOD: when we are pressing the shoot button, we want to shoot all the balls we have. 
  Make sure we have the target, make sure the motor is above a certain speed. 

*/

public void spinShooterMotors(double speed){
  shooterMotor1.set(TalonFXControlMode.PercentOutput, speed);
    shooterMotor2.set(TalonFXControlMode.PercentOutput, speed);
    SpinUp = true;
  }


  public void shootAlign() {
    if (shooterAligner.getIsAtTarget()) {
      
        shooterAligner.alignRobotToTarget();
        
        // spinShooterMotors(speed);
        // stirrerMotor.set(ControlMode.PercentOutput, Parameters.MOTORSPEEDMEDIUM);
        // feederMotor.set(ControlMode.PercentOutput, 0.5);
        // SmartDashboard.putString("Alive", "Is alive");
        // SmartDashboard.putNumber("shooterSpeed(RPM)",
        // shooterMotor1.getSensorCollection().getIntegratedSensorVelocity() / 2048 * 600);
      
    }
    /*
     * if (target is found && motorSpeed >= a certain number){ addjust the speed of
     * the shooterwheel according to the data we receive from limelight move to the
     * correct position hopperMotor.set(ControlMode.PercentOutput, 0.5);
     * feederMotor.set(ControlMode.PercentOutput, 0.5);
     * 
     * } else if(target is found && motorSpeed !>= certain number){
     * shooterMotorSpeed +=0.1; } else if(target is not found && motorSpeed >= a
     * certain number){ look for target } else{ look for target shooterMotorspeed +=
     * 0.1; }
     * 
     * shooterMotor1.set(TalonFXControlMode.PercentOutput,
     * Parameters.motorSpeedMedium); //shooterMotor1.set(0.5);
     * shooterMotor2.set(.5); stirrerMotor.set(ControlMode.PercentOutput, 0.5);
     * feederMotor.set(ControlMode.PercentOutput, 0.5);
     * SmartDashboard.putString("Alive", "Is alive");
     * SmartDashboard.putNumber("shooterSpeed(RPM)",
     * shooterMotor1.getSensorCollection().getIntegratedSensorVelocity() / 2048 *
     * 600); // shooterMotor2.set(TalonFXControlMode.Follower,
     * shooterMotor1.getDeviceID());
     * 
     */
  }

  public void shootUnAligned() {
    spinShooterMotors(Parameters.MOTORSPEEDMEDIUM);
    stirrerMotor.set(ControlMode.PercentOutput, Parameters.MOTORSPEEDMEDIUM);
    feederMotor.set(ControlMode.PercentOutput, Parameters.MOTORSPEEDMEDIUM);
      SmartDashboard.putString("Alive", "Is alive");
      SmartDashboard.putNumber("shooterSpeed(RPM)",
      shooterMotor1.getSensorCollection().getIntegratedSensorVelocity() / 2048 * 600);
      // shooterMotor2.set(TalonFXControlMode.Follower, shooterMotor1.getDeviceID());
      
   }

    public void range(){
      shroudSolenoid.set(Value.kForward);
    }
    // public double distanceSpeed(){
    //   double distance = shooterAligner.DistanceToTarget();
    //   if (distance >= Parameters.DISTANCE_LOW_MIN && distance <= Parameters.DISTANCE_LOW_MAX) {
    //     return Parameters.MOTORSPEEDLOW;
    //   }
    //   else if(distance >= Parameters.DISTANCE_MED_MIN && distance <= Parameters.DISTANCE_MED_MAX){
    //     return Parameters.MOTORSPEEDMEDIUM;
    //   }
    //   else if(distance >= Parameters.DISTANCE_HIGH_MIN && distance <= Parameters.DISTANCE_HIGH_MAX){
    //     return Parameters.MOTORSPEEDHIGH;
    //   }
    //   else {
    //     return 0;
    //   }
    // }
  public void stop() {
    shooterMotor1.set(0.0);
    shooterMotor2.set(0.0);
    stirrerMotor.set(ControlMode.PercentOutput,0.0);
    feederMotor.set(ControlMode.PercentOutput,0.0);
    aligner.turnLEDOff();
  }

  }

