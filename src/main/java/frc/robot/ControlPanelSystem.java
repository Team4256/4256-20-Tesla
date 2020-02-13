package frc.robot;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.util.Color;
import edu.wpi.first.wpilibj.DriverStation;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;

public class ControlPanelSystem {
    private WPI_TalonFX colorMotor;
    private DoubleSolenoid wheelArmSolenoid;
    private Color previousColor;
    private Color targetColor;
    private int colorWedgeCount = 0;
    private boolean hasStartedSpinning = false;
    String gameData; 
    private Color_Sensor colorSensor = new Color_Sensor();
    private ControlPanelSystemStates currentControlPanelSystemStates = ControlPanelSystemStates.STOP;


    public enum ControlPanelSystemStates{
      ARMUP,
      ARMDOWN,
      SPINREVS,
      SPINTOACOLOR,
      STOP;

    }
    
    
  

    public ControlPanelSystem() {
        wheelArmSolenoid = new DoubleSolenoid(Parameters.WHEEL_ARM_UP_SOLENOID_CHANNEL, Parameters.WHEEL_ARM_DOWN_SOLENOID_CHANNEL); 
        colorMotor = new WPI_TalonFX(Parameters.WHEEL_ARM_MOTOR_ID);
    }
        
    public void wheelUpCW(){
        wheelArmSolenoid.set(DoubleSolenoid.Value.kReverse);
        currentControlPanelSystemStates = ControlPanelSystemStates.ARMUP;
        }
    public void wheelDownCW() {
        wheelArmSolenoid.set(DoubleSolenoid.Value.kForward);
        currentControlPanelSystemStates = ControlPanelSystemStates.ARMDOWN;
        }


    private void spinCountInit(){
      if(!hasStartedSpinning){
        previousColor = colorSensor.getCurrentColor();
        colorWedgeCount =0;
        hasStartedSpinning = true;
      }
    }



   private void checkColorChanged(){
    Color currentColor = colorSensor.getCurrentColor();
     if(currentColor == null) return;
     if(currentColor != previousColor){
       ++colorWedgeCount;
       previousColor = currentColor;
     }  
     
   }


    public void spinControlPanel3Revs(){
      //check if the wheel is really down
      spinCountInit();
      checkColorChanged();
      if(colorWedgeCount< Parameters.TARGET_WEDGE_COUNT){
        colorMotor.set(Parameters.COLOR_MOTOR_SPEED);
        currentControlPanelSystemStates = ControlPanelSystemStates.SPINREVS;
      }
      else{
        colorMotor.set(0.0);
        hasStartedSpinning = false;
        currentControlPanelSystemStates = ControlPanelSystemStates.STOP;
      }
      
    }



    public void getTargetColor(){
      gameData = DriverStation.getInstance().getGameSpecificMessage();
      if(gameData.length() > 0)
      {
        switch (gameData.charAt(0))
        {
          case 'B' :
            targetColor = Parameters.myBlue;
            break;
          case 'G' :
          targetColor = Parameters.myGreen;
            break;
          case 'R' :
          targetColor = Parameters.myRed;
            break;
          case 'Y' :
          targetColor = Parameters.myYellow;
            break;
          default :
            targetColor = null;
            break;
        }
      } else {
        targetColor = null;
      }
  }



    public void spinToTheTargetColor(){
      Color currentColor = colorSensor.getCurrentColor();
      getTargetColor();
      if(targetColor != null && currentColor != targetColor){
        colorMotor.set(Parameters.COLOR_MOTOR_SPEED);
        currentControlPanelSystemStates = ControlPanelSystemStates.SPINTOACOLOR;
      }
      else{
         colorMotor.set(0.0);
         currentControlPanelSystemStates = ControlPanelSystemStates.STOP;         
      }
      

    }
    
    
}

