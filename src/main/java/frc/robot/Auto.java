/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 
 */
public class Auto {
    private D_Swerve swerve;
    private Shooter shooter;
    private Gyro gyro;
    private Intake intake;
    
   

    public Auto(){
    D_Swerve.getInstance();
    Shooter.getInstance();
    Gyro.getInstance();
    Intake.getInstance();

    }

    public void autoInit(){
      gyro.reset();
    }



    private boolean crossWhiteLine(){
      
      double YPosition = gyro.getDisplacementY();
      SmartDashboard.getNumber("Yposition", YPosition);
      
      if(YPosition < Parameters.CROSS_WHITE_LINE_DISTANCE_IN_METERS) {
        swerve.setSpeed(Parameters.AUTO_SWERVE_TRACTION_SPEED);
        swerve.setSpin(0.0);
        swerve.travelTowards(180);
        return false;
        
        }
        else{
          swerve.setSpeed(0.0);
          swerve.setSpin(0.0);
          return true;
      
          
        }
    }


    //for mode4 only 
    private void moveBack(){
      double YPosition = gyro.getDisplacementY();
      if(YPosition < Parameters.MOVE_BACK_DISTANCE){
        swerve.setSpeed(Parameters.AUTO_SWERVE_ROTATION_SPEED);
        swerve.setSpin(0.0);
        swerve.travelTowards(180);
      }
      else{
        swerve.setSpeed(0.0);
        swerve.setSpin(0.0);
      }
       
    }



    private boolean drivingToPorts(double distance){
      double Xposition = gyro.getDisplacementX();
      SmartDashboard.getNumber("Xposition", Xposition);
      if(Xposition < distance){
      swerve.setSpin(90.0);
      swerve.setSpeed(Parameters.AUTO_SWERVE_TRACTION_SPEED);
      swerve.travelTowards(270);// ask if the value is right 
      return false;
    }
      else{
      swerve.setSpeed(0.0);
      swerve.setSpin(0.0);
      return true;
    }

    }


    private void alignAndShoot(){
      shooter.shootAlign();

    }

    private boolean getBallsFromTrench(double distance){
      double YPosition = gyro.getDisplacementY();
      if(YPosition< distance){
        swerve.setSpin(0.0);
        swerve.setSpeed(Parameters.AUTO_SWERVE_ROTATION_SPEED);
        swerve.travelTowards(180);
        intake.succ();
        return false;
      }
      else{
        swerve.setSpeed(0.0);
        intake.stop();
        return true;
      }
     
      

    }


    // mode1: start from the right 
    public void mode1(){
      if(getBallsFromTrench(Parameters.DRIVE_TO_TRENCH_DISTANCE)){
        if(drivingToPorts(Parameters.STARTING_DISTANCE_FROM_RIGHT)){
          alignAndShoot();
        }
      }
      
      
      

    }
    // mode2: start in front of the ports
    public void mode2(){

      if(crossWhiteLine()){
        if(drivingToPorts(Parameters.STARTING_DISTANCE_FROM_MIDDLE)){
          alignAndShoot();
        }  
      }
    }

    // mode3: start from the left
    public void mode3(){
      if(getBallsFromTrench(Parameters.DRIVE_TO_TRENCH_DISTANCE)){
        if(drivingToPorts(Parameters.STARTING_DISTANCE_FROM_LEFT)){
          alignAndShoot();
        }
      }
    }

    // mode4: start 
    public void mode4(){
      if(crossWhiteLine()){
        //moveBack();
        swerve.setSpeed(0.0);
      } 
    }

}
