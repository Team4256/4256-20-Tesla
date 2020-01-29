package frc.robot;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import com.revrobotics.EncoderType;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.TalonFXControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
//import com.cyborgcats.reusable.phoenix.Talon;
//import com.cyborgcats.reusable.spark.SparkMaxNeo;
import com.revrobotics.CANEncoder;

public class Shooter{

  //CONSTANTS
  private static double motorSpeed; // we don't know yet what the engineers are going to do
  //private static final double UPPER_PORT_SPEED = 0.0; //same as above

  //INSTANCE
  private final WPI_TalonFX shooterMotor1;
  private final WPI_TalonFX shooterMotor2;
  
  //private final WPI_TalonFX shooterMotor3;
  //private CANEncoder shooterMotorEncoder1;
  //private CANEncoder shooterMotorEncoder2;


  public Shooter(int deviceNumber1,int deviceNumber2){
    shooterMotor1 = new WPI_TalonFX(deviceNumber1);
    shooterMotor2 = new WPI_TalonFX(deviceNumber2);
    double velocity = shooterMotor1.getSensorCollection().getIntegratedSensorVelocity()/2048*600;
    
    //shooterMotor3 = new WPI_TalonFX(deviceNumber3);
     //shooterMotorEncoder1 = new CANEncoder(shooterMotor1);
    //shooterMotorEncoder2 = new CANEncoder(shooterMotor2);

  }

  public void shoot(){
    
    if (shooterMotor1.isAlive() && shooterMotor2.isAlive()) {
    shooterMotor1.set(TalonFXControlMode.PercentOutput, motorSpeed);
    shooterMotor2.set(TalonFXControlMode.PercentOutput, motorSpeed);
    SmartDashboard.putString("Alive", "Is alive");
    //shooterMotor2.set(TalonFXControlMode.Follower, shooterMotor1.getDeviceID());
  } else {
          SmartDashboard.putString("Is not Alive", "Is not alive");
  }
  
  }
    
   



}