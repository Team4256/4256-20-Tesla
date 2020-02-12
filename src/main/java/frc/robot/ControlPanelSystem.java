package frc.robot;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.util.Color;
import edu.wpi.first.wpilibj.DriverStation;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;

public class ControlPanelSystem {
    private double colorMotorSpeed = 0.5;
    private WPI_TalonFX colorMotor;
    private DoubleSolenoid wheelArmSolenoid;
    private Color previousColor;
    private Color targetColor;
    String gameData; 

public void gameFieldData(){
    gameData = DriverStation.getInstance().getGameSpecificMessage();
if(gameData.length() > 0)
{
  switch (gameData.charAt(0))
  {
    case 'B' :
      //Blue case code
      break;
    case 'G' :
      //Green case code
      break;
    case 'R' :
      //Red case code
      break;
    case 'Y' :
      //Yellow case code
      break;
    default :
      //This is corrupt data
      break;
  }
} else {
  //Code for no data received yet
}
}
  

    public ControlPanelSystem() {
        wheelArmSolenoid = new DoubleSolenoid(Parameters.WHEEL_ARM_UP_SOLENOID_CHANNEL, Parameters.WHEEL_ARM_DOWN_SOLENOID_CHANNEL); 
        colorMotor = new WPI_TalonFX(Parameters.WHEEL_ARM_MOTOR_ID);
    }
        
    public void wheelUpCW(){
        wheelArmSolenoid.set(DoubleSolenoid.Value.kReverse);
        }
    public void wheelDownCW() {
        wheelArmSolenoid.set(DoubleSolenoid.Value.kForward);
        }
    public void spinControlPenal3Revs(){

        colorMotor.set(colorMotorSpeed);


    }
    
    
}

