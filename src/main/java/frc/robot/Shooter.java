package frc.robot;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import com.ctre.phoenix.motorcontrol.TalonFXControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
//import com.cyborgcats.reusable.phoenix.Talon;
//import com.cyborgcats.reusable.spark.SparkMaxNeo;


public class Shooter {

  // CONSTANTS
  private static double motorSpeed = .5; // we don't know yet what the engineers are going to do
  // private static final double UPPER_PORT_SPEED = 0.0; //same as above

  // INSTANCE
  private final WPI_TalonFX shooterMotor1;
  private final WPI_TalonFX shooterMotor2;

  // private final WPI_TalonFX shooterMotor3;
  // private CANEncoder shooterMotorEncoder1;
  // private CANEncoder shooterMotorEncoder2;

  //Constructor
  public Shooter(int deviceNumber1, int deviceNumber2) {
    shooterMotor1 = new WPI_TalonFX(deviceNumber1);
    shooterMotor2 = new WPI_TalonFX(deviceNumber2);

    
    // shooterMotorEncoder1 = new CANEncoder(shooterMotor1);
    // shooterMotorEncoder2 = new CANEncoder(shooterMotor2);

  }
  //Shoot Method
  public void shoot() {

      shooterMotor1.set(TalonFXControlMode.PercentOutput, motorSpeed);
      //shooterMotor1.set(0.5);
      shooterMotor2.set(TalonFXControlMode.PercentOutput, motorSpeed);
      SmartDashboard.putString("Alive", "Is alive");
      SmartDashboard.putNumber("shooterSpeed(RPM)",
      shooterMotor1.getSensorCollection().getIntegratedSensorVelocity() / 2048 * 600);
      // shooterMotor2.set(TalonFXControlMode.Follower, shooterMotor1.getDeviceID());
    }
  public void stop() {
    shooterMotor1.set(0.0);
    shooterMotor2.set(0.0);
  }

  }

