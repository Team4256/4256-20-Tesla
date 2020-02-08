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
  //hopper motor and feeder motor
  private final WPI_TalonFX hopperMotor;
  private final WPI_TalonFX feederMotor;

  // private final WPI_TalonFX shooterMotor3;
  // private CANEncoder shooterMotorEncoder1;
  // private CANEncoder shooterMotorEncoder2;

  //Constructor
  public Shooter() {
    shooterMotor1 = new WPI_TalonFX(Parameters.ShooterMotor_1_ID);
    shooterMotor2 = new WPI_TalonFX(Parameters.ShooterMoror_2_ID);
    hopperMotor = new WPI_TalonFX(Parameters.HopperMotor_ID);
    feederMotor = new WPI_TalonFX(Parameters.FeederMotor_ID);

    
    // shooterMotorEncoder1 = new CANEncoder(shooterMotor1);
    // shooterMotorEncoder2 = new CANEncoder(shooterMotor2);

  }
  //Hopper and Feeder Motors
  
  //Shoot Method
  public void shoot() {

      shooterMotor1.set(TalonFXControlMode.PercentOutput, motorSpeed);
      //shooterMotor1.set(0.5);
      shooterMotor2.set(.5);
      hopperMotor.set(TalonFXControlMode.PercentOutput, motorSpeed);
      feederMotor.set(.5);
      SmartDashboard.putString("Alive", "Is alive");
      SmartDashboard.putNumber("shooterSpeed(RPM)",
      shooterMotor1.getSensorCollection().getIntegratedSensorVelocity() / 2048 * 600);
      // shooterMotor2.set(TalonFXControlMode.Follower, shooterMotor1.getDeviceID());
    }
  public void stop() {
    shooterMotor1.set(0.0);
    shooterMotor2.set(0.0);
    hopperMotor.set(0.0);
    feederMotor.set(0.0);
  }

  }

