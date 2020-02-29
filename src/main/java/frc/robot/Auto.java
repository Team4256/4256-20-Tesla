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
    swerve = D_Swerve.getInstance();
    shooter = Shooter.getInstance();
    gyro = Gyro.getInstance();
    intake = Intake.getInstance();

    }

    public void autoInit(){
      gyro.reset();
      swerve.resetEncoderPosition();
    }



    private boolean crossWhiteLine(){
      double distanceTravelled = Math.abs(swerve.getAverageIntegratedSensorPosition());
      
      SmartDashboard.putNumber("Rotation Counts", distanceTravelled);
      
      if(distanceTravelled < Parameters.CROSS_WHITE_LINE_DISTANCE_IN_INCHES) {
        swerve.setSpeed(Parameters.AUTO_SWERVE_TRACTION_SPEED);
        swerve.setSpin(0.0);
        //swerve.setSpin(Parameters.AUTO_SWERVE_TRACTION_SPEED);
        swerve.travelTowards(180);
        return false;
        
        }
      else{
        swerve.resetEncoderPosition();
        swerve.setSpeed(0.0);
        swerve.setSpin(0.0);
        return true;
      
          
        }
    }


    //for mode4 only 
    private void moveBack(){
      double distanceTravelled = swerve.getAverageIntegratedSensorPosition();
      
      if(distanceTravelled < Parameters.MOVE_BACK_DISTANCE_IN_INCHES){
        swerve.setSpeed(Parameters.AUTO_SWERVE_TRACTION_SPEED);
        swerve.setSpin(0.0);
        swerve.travelTowards(180);
      }
      else{
        swerve.resetEncoderPosition();
        swerve.setSpeed(0.0);
        swerve.setSpin(0.0);
      }
       
    }



    private boolean drivingToPorts(double distance){
      double distanceTravelled = Math.abs(swerve.getAverageIntegratedSensorPosition());
      //SmartDashboard.putNumber("Xposition", Xposition);
      if(distanceTravelled < distance){
      swerve.setSpin(90.0);
      swerve.setSpeed(Parameters.AUTO_SWERVE_TRACTION_SPEED);
      swerve.travelTowards(270);// ask if the value is right 
      return false;
    }
      else{
      swerve.resetEncoderPosition();
      swerve.setSpeed(0.0);
      swerve.setSpin(0.0);
      return true;
    }
  }


    private void alignAndShoot(){
      shooter.shootAlign();
    }




    private boolean getBallsFromTrench(double distance){
      double distanceTravelled = Math.abs(swerve.getAverageIntegratedSensorPosition());
      if(distanceTravelled < distance){
        swerve.setSpin(0.0);
        swerve.setSpeed(Parameters.AUTO_SWERVE_TRACTION_SPEED);
        swerve.travelTowards(180);
        intake.succ();
        return false;
      }
      else{
        swerve.resetEncoderPosition();
        swerve.setSpeed(0.0);
        intake.stop();
        return true;
      }
     
      

    }


    // mode1: start from the right 
    //might take out the driving to ports function
    public void mode1(){
      if(getBallsFromTrench(Parameters.DRIVE_TO_TRENCH_DISTANCE_IN_INCHES)){
        if(drivingToPorts(Parameters.STARTING_DISTANCE_FROM_RIGHT_IN_INCHES)){
          alignAndShoot();
        }
      }
      
      
      

    }
    // mode2: start in front of the ports
    public void mode2(){

      if(crossWhiteLine()){
        //if(drivingToPorts(Parameters.STARTING_DISTANCE_FROM_MIDDLE)){
          alignAndShoot();
        //}  
      }
    }

    // mode3: start from the left
    public void mode3(){
      if(getBallsFromTrench(Parameters.DRIVE_TO_TRENCH_DISTANCE_IN_INCHES)){
        //if(drivingToPorts(Parameters.STARTING_DISTANCE_FROM_LEFT)){
          alignAndShoot();
        //}
      }
    }

    // mode4: start 
    public void mode4(){
      // if(crossWhiteLine()){
      //   moveBack();
       
      // } 
      crossWhiteLine();
    }

}
