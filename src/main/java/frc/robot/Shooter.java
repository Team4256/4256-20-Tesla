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
  private static double motorSpeedLow = 0.1;
  private static double motorSpeedMedium = .5; 
  private static double motorSpeedHigh = 1.0;// we don't know yet what the engineers are going to do
  // private static final double UPPER_PORT_SPEED = 0.0; //same as above

  // INSTANCE
  private final WPI_TalonFX shooterMotor1;
  private final WPI_TalonFX shooterMotor2;
  //hopper motor and feeder motor
  private final TalonSRX hopperMotor;
  private final TalonSRX feederMotor;
  private DoubleSolenoid shroudSolenoid;

  // private final WPI_TalonFX shooterMotor3;
  // private CANEncoder shooterMotorEncoder1;
  // private CANEncoder shooterMotorEncoder2;

  //Constructor
  public Shooter(int shootermotorID1, int shooterMotorID2, int hopperMotorID, int feederMotorID, int shroudForwardChannel, int shroudReverseChannel) {
    shooterMotor1 = new WPI_TalonFX(shootermotorID1);
    shooterMotor2 = new WPI_TalonFX(shooterMotorID2);
    hopperMotor = new TalonSRX(hopperMotorID);
    feederMotor = new TalonSRX(feederMotorID);
    shroudSolenoid = new DoubleSolenoid(shroudForwardChannel, shroudReverseChannel);

    
    // shooterMotorEncoder1 = new CANEncoder(shooterMotor1);
    // shooterMotorEncoder2 = new CANEncoder(shooterMotor2);

  }
  //Hopper and Feeder Motors
  
  /*
  CHANGE THIS METHOD: when we are pressing the shoot button, we want to shoot all the balls we have. 
  Make sure we have the target, make sure the motor is above a certain speed. 

*/

public void spinShooterMotors(){
  shooterMotor1.set(TalonFXControlMode.PercentOutput, motorSpeedMedium);
  shooterMotor2.set(TalonFXControlMode.PercentOutput, motorSpeedMedium);
}


  public void shoot() {
   /* if (target is found && motorSpeed >= a certain number){
      addjust the speed of the shooterwheel according to the data we receive from limelight
      move to the correct position 
       hopperMotor.set(ControlMode.PercentOutput, 0.5);
       feederMotor.set(ControlMode.PercentOutput, 0.5);

   }
   else if(target is found && motorSpeed !>= certain number){
      shooterMotorSpeed +=0.1;
   }
   else if(target is not found && motorSpeed >= a certain number){
      look for target
   }
   else{
     look for target 
     shooterMotorspeed += 0.1;
   }

*/
      shooterMotor1.set(TalonFXControlMode.PercentOutput, motorSpeedMedium);
      //shooterMotor1.set(0.5);
      shooterMotor2.set(.5);
      hopperMotor.set(ControlMode.PercentOutput, 0.5);
      feederMotor.set(ControlMode.PercentOutput, 0.5);
      SmartDashboard.putString("Alive", "Is alive");
      SmartDashboard.putNumber("shooterSpeed(RPM)",
      shooterMotor1.getSensorCollection().getIntegratedSensorVelocity() / 2048 * 600);
      // shooterMotor2.set(TalonFXControlMode.Follower, shooterMotor1.getDeviceID());
      
    }

    

    public void passBall(){
      shroudSolenoid.set(Value.kForward);
    }

    
  public void stop() {
    shooterMotor1.set(0.0);
    shooterMotor2.set(0.0);
    hopperMotor.set(ControlMode.PercentOutput,0.0);
    feederMotor.set(ControlMode.PercentOutput,0.0);
  }

  }

