package frc.robot;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import com.revrobotics.EncoderType;
import com.ctre.phoenix.motorcontrol.TalonFXControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
//import com.cyborgcats.reusable.phoenix.Talon;
//import com.cyborgcats.reusable.spark.SparkMaxNeo;
import com.revrobotics.CANEncoder;

public class Shooter{

  //CONSTANTS
  private static final double LOWER_PORT_SPEED = 0.0; // we don't know yet what the engineers are going to do
  private static final double UPPER_PORT_SPEED = 0.0; //same as above

  //INSTANCE
  private WPI_TalonFX shooterMotor1;
 // private final WPI_TalonFX shooterMotor2;
  //private final WPI_TalonFX shooterMotor3;
  //private CANEncoder shooterMotorEncoder1;
  //private CANEncoder shooterMotorEncoder2;


  public Shooter(int deviceNumber1){
    shooterMotor1 = new WPI_TalonFX(33);
    //shooterMotor2 = new WPI_TalonFX(deviceNumber2);
    //shooterMotor3 = new WPI_TalonFX(deviceNumber3);

  }

  public void shoot(){
    
    if (shooterMotor1.isAlive()) {
    shooterMotor1.set(TalonFXControlMode.PercentOutput, 0.5);
    SmartDashboard.putString("Alive", "Is alive");
    //shooterMotor2.set(TalonFXControlMode.Follower, shooterMotor1.getDeviceID());
  } else {
          SmartDashboard.putString("Is not Alive", "Is not alive");
  }
  
  }
    
    //shooterMotorEncoder1 = new CANEncoder(shooterMotor1);
    //shooterMotorEncoder2 = new CANEncoder(shooterMotor2);



    public void smartdashboard(){
      SmartDashboard.getNumber("speed", 0.0);
        
    }


}