/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import com.ctre.phoenix.motorcontrol.TalonFXSensorCollection;				

public class ClimbingControl {
    //Will need to set speed to something else 
    private double speed = 0.5;
    public double climbingSpeed;
   

    
  private WPI_TalonFX climbMotorRight;
    private WPI_TalonFX climbMotorLeft;
    //need to add device numbers based on excel sheet
    public ClimbingControl( int RMotorID, int LMotorID){
        climbMotorRight = new WPI_TalonFX(RMotorID); 
        climbMotorLeft = new WPI_TalonFX(LMotorID);
        climbMotorRight.getSensorCollection().setIntegratedSensorPosition(0,0);

    }
    public void extendPole(){
        climbMotorRight.set(speed);
        climbMotorLeft.set(speed);
    }
    public boolean extendPoles(double height)
    {
        if  (climbMotorRight.getSensorCollection().getIntegratedSensorPosition() >= height)
        { 
            climbMotorRight.set(0);
            climbMotorLeft.set(0);
            return false;

        } 
    
        else 
        {
          climbMotorRight.set(.3);
          climbMotorLeft.set(.3);
          return true;
        }
    }   
    public void retractPole(){
        climbMotorRight.set(-speed);
        climbMotorLeft.set(-speed);
    }
    public void retractPoleRight(double climbingSpeed){
        this.climbingSpeed = climbingSpeed;
        climbMotorRight.set(-climbingSpeed);
    }
    public void retractPoleLeft(double climbingSpeed){
        this.climbingSpeed = climbingSpeed;
        climbMotorLeft.set(-climbingSpeed);
    }
   // add extracts (thumsticks)
}