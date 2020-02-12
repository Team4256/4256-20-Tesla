package frc.robot;

import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.util.Color;
import com.revrobotics.ColorSensorV3;
import com.revrobotics.ColorMatchResult;
import com.revrobotics.ColorMatch;



public class Color_Sensor {
  private final I2C.Port i2cPort = I2C.Port.kOnboard;

  private ColorSensorV3 colorSensor = new ColorSensorV3(i2cPort);
  final ColorMatch colorMatcher = new ColorMatch();
  final int colorThreshold = 140;
  

  public Color_Sensor(){
    colorMatcher.addColorMatch(Parameters.myBlue);
    colorMatcher.addColorMatch(Parameters.myGreen);
    colorMatcher.addColorMatch(Parameters.myRed);
    colorMatcher.addColorMatch(Parameters.myYellow);
  }
    
  

  public Color getCurrentColor() {
    int distance = colorSensor.getProximity();
    Color detectedColor = colorSensor.getColor();
    ColorMatchResult match = colorMatcher.matchClosestColor(detectedColor);
    {
      if (distance >= colorThreshold) {

        if (match.color == Parameters.myBlue || match.color == Parameters.myGreen || match.color == Parameters.myRed || match.color == Parameters.myYellow ) {

          return match.color;

        } else {

          return null;
        }
      } else {

        return null;
      }
    }
  }
  
  
}
