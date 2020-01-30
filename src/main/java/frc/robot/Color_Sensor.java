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

  final Color myBlue = ColorMatch.makeColor(0.128174, 0.431152, 0.440674);
  final Color myGreen = ColorMatch.makeColor(0.172363, 0.579834, 0.247803);
  final Color myRed = ColorMatch.makeColor(0.513428, 0.351807, 0.134766);
  final Color myYellow = ColorMatch.makeColor(0.320557, 0.556641, 0.122803);
  final Color detectedColor = colorSensor.getColor();
  final ColorMatchResult match = colorMatcher.matchClosestColor(detectedColor);
  final int colorThreshold = 140;
  final Color targetColor = myBlue;
  int distance = colorSensor.getProximity();

  public void init() {
    colorMatcher.addColorMatch(myBlue);
    colorMatcher.addColorMatch(myGreen);
    colorMatcher.addColorMatch(myRed);
    colorMatcher.addColorMatch(myYellow);
  }

  public void outputColor() {
    String colorString;
    {
      if (distance >= colorThreshold) {

        if (match.color == myBlue) {

          colorString = "Blue";
        } else if (match.color == myGreen) {
          colorString = "Green";

        } else if (match.color == myRed) {
          colorString = "Red";
        } else if (match.color == myYellow) {
          colorString = "Yellow";
        } else {
          colorString = "Unknown";
        }
      } else {

        colorString = "Color not found";
      }
    }
  }
}
