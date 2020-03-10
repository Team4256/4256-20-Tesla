/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import com.fasterxml.jackson.databind.deser.impl.CreatorCandidate.Param;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 
 */
public class Auto {
    private D_Swerve swerve;
    private Shooter shooter;
    private Gyro gyro;
    private Intake intake;
    private boolean stageOneFinished;
    private boolean stageTwoFinished;
    private boolean stageThreeFinished;
    private boolean stageFourFinished;
    private double elapsedTime;
    private StopWatch stopWatch = StopWatch.getInstance();
    private Shooter cellshooter = Shooter.getInstance();
   private Intake intakae = Intake.getInstance();
   private Limelight limelight = Limelight.getInstance();
    
   

    public Auto(){
    swerve = D_Swerve.getInstance();
    shooter = Shooter.getInstance();
    gyro = Gyro.getInstance();
    intake = Intake.getInstance();

    }

    public void autoInit(){
      gyro.reset();
      swerve.resetEncoderPosition();
    stageOneFinished = false;
    stageTwoFinished = false;
    stageThreeFinished = false;
    stageFourFinished = false;
    elapsedTime = 0.0;
    }



    private boolean crossWhiteLine(){
      double distanceTravelled = Math.abs(swerve.getAverageIntegratedSensorPosition());
      
      SmartDashboard.putNumber("distance travelled", distanceTravelled);
      
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
        stageOneFinished = true;
        return true;
      
          
        }
    }


    //for mode4 only 
    private boolean moveBack(double distance){
      double distanceTravelled = Math.abs(swerve.getAverageIntegratedSensorPosition());
      
      if(distanceTravelled < distance){
        swerve.faceTo(0.0);
        swerve.setSpeed(Parameters.AUTO_SWERVE_TRACTION_SPEED);
        swerve.travelTowards(180.0);
        return false;
      }
      else{
        swerve.resetEncoderPosition();
        swerve.setSpeed(0.0);
        swerve.setSpin(0.0);
        return true;
      }
       
    }



    private boolean drivingToPorts(double distance){
      double distanceTravelled = Math.abs(swerve.getAverageIntegratedSensorPosition());
      SmartDashboard.putNumber("To Ports Distance", distanceTravelled);
      if(distanceTravelled < distance){
      // swerve.setSpin(.30);
      swerve.setSpeed(Parameters.AUTO_SWERVE_TRACTION_SPEED);
      swerve.travelTowards(270);// ask if the value is right 
      return false;
    }
      else{
      swerve.resetEncoderPosition();
      swerve.setSpeed(0.0);
      swerve.setSpin(0.0);
      stageTwoFinished = true;
      return true;
    }
  }


    private void alignAndShoot(double shooterSpeed){
      shooter.shootAlign(shooterSpeed);
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
        stageThreeFinished = true;
        return true;
      }
     
      

    }
    
    public static enum PhaseStates {
      NOT_SARTED, STARTED, ENDED
    }
  private PhaseStates phase1 = PhaseStates.NOT_SARTED;
  private PhaseStates phase2 = PhaseStates.NOT_SARTED;
  private PhaseStates phase3 = PhaseStates.NOT_SARTED;
  private PhaseStates phase4 = PhaseStates.NOT_SARTED;
  private PhaseStates phase5 = PhaseStates.NOT_SARTED;

    // mode1: start from the right 
    //might take out the driving to ports function
    public void mode1(){
      SmartDashboard.putNumber("distance travelled", Math.abs(swerve.getAverageIntegratedSensorPosition()));
      SmartDashboard.putNumber("timer", stopWatch.getElapsedTime());
      SmartDashboard.putNumber("distance to target" , limelight.getDistanceToTarget());
      //SmartDashboard.putString("Autonomus phase","Phase 0");
      if(phase1 == PhaseStates.NOT_SARTED){ // Starts the shooter motors
        phase1 = PhaseStates.STARTED;
        shooter.spinShooterMotors(Parameters.SHOOTER_MOTOR_SPEED_CLOSE);
      }
      if(phase1 == PhaseStates.STARTED){
        if(true||shooter.isSpunUp()){//need to be changed 
          phase1 = PhaseStates.ENDED; 
        }
       else{
        SmartDashboard.putString("Autonomus phase","Phase 1");
          return;
        }
      }
      if(phase2 == PhaseStates.NOT_SARTED){ // Aligns and shoots pre loaded power cells
        phase2 = PhaseStates.STARTED;
        stopWatch.resetTimer();
      }
      if(phase2 == PhaseStates.STARTED){
        alignAndShoot(Parameters.SHOOTER_MOTOR_SPEED_CLOSE);
        if(stopWatch.getElapsedTime() > 5){ //make sure the time is right
          phase2 = PhaseStates.ENDED;
        }
        else {
          SmartDashboard.putString("Autonomus phase","Phase 2");
          return;
        }
      }
        if(phase3 == PhaseStates.NOT_SARTED){ // Turns towards target
          phase3 = PhaseStates.STARTED;
          stopWatch.resetTimer();
        }
        if(phase3 == PhaseStates.STARTED){
          swerve.faceTo(0.0); //make sure this is right 
          shooter.stopHopper();
          if(stopWatch.getElapsedTime() > 0.5){
            phase3 = PhaseStates.ENDED;
          }
          else {
            SmartDashboard.putString("Autonomus phase","Phase 3");
            return;
      }
    }
      if(phase4 == PhaseStates.NOT_SARTED){ // Drives to trench and intakes power cells
        swerve.resetEncoderPosition();
        phase4 = PhaseStates.STARTED;
      }
      if(phase4 == PhaseStates.STARTED){
        intakae.succ();
        double distanceTravelled = Math.abs(swerve.getAverageIntegratedSensorPosition());
      if(distanceTravelled >= Parameters.MOVE_BACK_RIGHT_TRENCH_DISTANCE_IN_INCHES){
          phase4 = PhaseStates.ENDED;
          swerve.setSpeed(0.0);
        }
        else {
          swerve.faceTo(0.0);
          swerve.setSpeed(Parameters.AUTO_SWERVE_TRACTION_SPEED);
          swerve.travelTowards(180);
          SmartDashboard.putString("Autonomus phase","Phase 4");
          return;
       } 
      }
       if(phase5 == PhaseStates.NOT_SARTED){ //Shoots previously gathered power cells
        phase5 = PhaseStates.STARTED;
        stopWatch.resetTimer();
        limelight.setPipeline(2);
        //SmartDashboard.putNumber("limelight distance", limelight.getDistanceToTarget());
      }

      if(phase5 == PhaseStates.STARTED){
        
        alignAndShoot(Parameters.SHOOTER_MOTOR_SPEED_FAR);
        if(stopWatch.getElapsedTime() > 5){
          phase5 = PhaseStates.ENDED;
          shooter.stopHopper();
          shooter.stopShooterWheel();
          limelight.turnLEDOff();
        }
        else {
          SmartDashboard.putString("Autonomus phase","Phase 5" );
          return;
        }
      }
    }
  
      
      

        
    


      // if(getBallsFromTrench(Parameters.DRIVE_TO_TRENCH_DISTANCE_IN_INCHES)){
      //   if(drivingToPorts(Parameters.STARTING_DISTANCE_FROM_RIGHT_IN_INCHES)){
      //     alignAndShoot();
      //   }
      // }
      
      
      

    
    // mode2: start in front of the ports
    public void mode2(){
      SmartDashboard.putNumber("distance travelled", Math.abs(swerve.getAverageIntegratedSensorPosition()));
      SmartDashboard.putNumber("timer", stopWatch.getElapsedTime());
      SmartDashboard.putNumber("distance to target" , limelight.getDistanceToTarget());
      if(phase1 == PhaseStates.NOT_SARTED){ // Starts the shooter motors
        phase1 = PhaseStates.STARTED;
        //intake.succ();
      }
      if(phase1 == PhaseStates.STARTED){
        double distanceTravelled = Math.abs(swerve.getAverageIntegratedSensorPosition());
        if(distanceTravelled> Parameters.MOVE_BACK_LEFT_TRENCH_DISTANCE_IN_INCHES){
          swerve.setSpeed(0.0);
          phase1 = PhaseStates.ENDED; 
        }
        else{
          moveBack(Parameters.MOVE_BACK_LEFT_TRENCH_DISTANCE_IN_INCHES);
          SmartDashboard.putString("Autonomus phase","Phase 1");
          return;
        }
       
      }
      
      if(phase2 == PhaseStates.NOT_SARTED){ // Aligns and shoots pre loaded power cells
        phase2 = PhaseStates.STARTED;
        stopWatch.resetTimer();
        swerve.resetEncoderPosition();
        // shooter.spinHopperMotors();
        // shooter.spinShooterMotors(Parameters.SHOOTER_MOTOR_SPEED_CLOSE);
      }
      if(phase2 == PhaseStates.STARTED){
    
        double distanceTravelled = Math.abs(swerve.getAverageIntegratedSensorPosition());
        if(distanceTravelled > Parameters.DRIVE_FROM_TRENCH_TO_PORT_MODE2){
          swerve.setSpeed(0.0);
          phase2 = PhaseStates.ENDED;
          
        }
        else {
          swerve.travelTowards(75.0);//make sure the angle is right
          swerve.setSpeed(Parameters.AUTO_SWERVE_TRACTION_SPEED);
          SmartDashboard.putString("Autonomus phase","Phase 2");
          return;
        }
      }
        if(phase3 == PhaseStates.NOT_SARTED){ // Turns towards target
          phase3 = PhaseStates.STARTED;
          stopWatch.resetTimer();
          //swerve.faceTo(45.0);


        }
        if(phase3 == PhaseStates.STARTED){
          alignAndShoot(Parameters.SHOOTER_MOTOR_SPEED_CLOSE);
          if(stopWatch.getElapsedTime() > 3){
            phase3 = PhaseStates.ENDED;
          }
          else {
            SmartDashboard.putString("Autonomus phase","Phase 3");
            return;
      }
    }
      // if(phase4 == PhaseStates.NOT_SARTED){ // Drives to trench and intakes power cells
      //   swerve.resetEncoderPosition();
      //   phase4 = PhaseStates.STARTED;
      // }
      // if(phase4 == PhaseStates.STARTED){
      //   intakae.succ();
      //   double distanceTravelled = Math.abs(swerve.getAverageIntegratedSensorPosition());
      // if(distanceTravelled >= 186){
      //     phase4 = PhaseStates.ENDED;
      //     swerve.setSpeed(0.0);
      //   }
      //   else {
      //     swerve.setSpeed(Parameters.AUTO_SWERVE_TRACTION_SPEED);
      //     swerve.setSpin(0.0);
      //     swerve.travelTowards(180);
      //     SmartDashboard.putString("Autonomus phase","Phase 4");
      //     return;
      //  } 
      // }
      //  if(phase5 == PhaseStates.NOT_SARTED){ //Shoots previously gathered power cells
      //   phase5 = PhaseStates.STARTED;
      //   stopWatch.resetTimer();
      // }
      // if(phase5 == PhaseStates.STARTED){
      //   alignAndShoot();
      //   if(stopWatch.getElapsedTime() > 3){
      //     phase5 = PhaseStates.ENDED;
      //     shooter.stopStirrerMotors();
      //   }
      //   else {
      //     SmartDashboard.putString("Autonomus phase","Phase 5" );
      //     return;
      //   }
      // }
    }
      // SmartDashboard.putNumber("gyroHeading", gyro.getCurrentAngle());
      // swerve.getRPM();

      // crossWhiteLine();

      // if(crossWhiteLine()){
      //   //if(drivingToPorts(Parameters.STARTING_DISTANCE_FROM_MIDDLE)){
      //     alignAndShoot();
      //   //}  
      // }
    

    // mode3: start from the left
    public void mode3(){
        SmartDashboard.putNumber("distance travelled", Math.abs(swerve.getAverageIntegratedSensorPosition()));
        SmartDashboard.putNumber("timer", stopWatch.getElapsedTime());
        SmartDashboard.putNumber("distance to target" , limelight.getDistanceToTarget());
        if(phase1 == PhaseStates.NOT_SARTED){ // Starts the shooter motors
          phase1 = PhaseStates.STARTED;
          intake.succ();
        }
        if(phase1 == PhaseStates.STARTED){
          double distanceTravelled = Math.abs(swerve.getAverageIntegratedSensorPosition());
          if(distanceTravelled> Parameters.MOVE_BACK_LEFT_TRENCH_DISTANCE_IN_INCHES){
            swerve.setSpeed(0.0);
            phase1 = PhaseStates.ENDED; 
          }
          else{
            moveBack(Parameters.MOVE_BACK_LEFT_TRENCH_DISTANCE_IN_INCHES);
            SmartDashboard.putString("Autonomus phase","Phase 1");
            return;
          }
         
        }
        
        if(phase2 == PhaseStates.NOT_SARTED){ // Aligns and shoots pre loaded power cells
          phase2 = PhaseStates.STARTED;
          stopWatch.resetTimer();
          swerve.resetEncoderPosition();
          //shooter.spinStirrerMotors();
          //shooter.spinShooterMotors(Parameters.SHOOTER_MOTOR_SPEED_CLOSE);
        }
        if(phase2 == PhaseStates.STARTED){
      
          double distanceTravelled = Math.abs(swerve.getAverageIntegratedSensorPosition());
          if(distanceTravelled > Parameters.DRIVE_FROM_TRENCH_TO_PORT_MODE2){
            swerve.setSpeed(0.0);
            phase2 = PhaseStates.ENDED;
            
          }
          else {
            swerve.travelTowards(45.0); //angle might be different
            swerve.setSpeed(Parameters.AUTO_SWERVE_TRACTION_SPEED);
            SmartDashboard.putString("Autonomus phase","Phase 2");
            return;
          }
        }
          if(phase3 == PhaseStates.NOT_SARTED){ // Turns towards target
            phase3 = PhaseStates.STARTED;
            stopWatch.resetTimer();
  
          }
          if(phase3 == PhaseStates.STARTED){
            alignAndShoot(Parameters.SHOOTER_MOTOR_SPEED_CLOSE);
            if(stopWatch.getElapsedTime() > 3){
              phase3 = PhaseStates.ENDED;
            }
            else {
              SmartDashboard.putString("Autonomus phase","Phase 3");
              return;
        }
      }
      }
    
      // if(getBallsFromTrench(Parameters.DRIVE_TO_TRENCH_DISTANCE_IN_INCHES)){
      //   //if(drivingToPorts(Parameters.STARTING_DISTANCE_FROM_LEFT)){
      //     alignAndShoot();
      //   //}
    //   }
    // }

    // mode4: start right in front of the ports 
    public void mode4(){
        SmartDashboard.putNumber("distance travelled", Math.abs(swerve.getAverageIntegratedSensorPosition()));
        SmartDashboard.putNumber("timer", stopWatch.getElapsedTime());
        SmartDashboard.putNumber("distance to target" , limelight.getDistanceToTarget());
        if(phase1 == PhaseStates.NOT_SARTED){ // Starts the shooter motors
          phase1 = PhaseStates.STARTED;
          //intake.succ();
        }
        if(phase1 == PhaseStates.STARTED){
          double distanceTravelled = Math.abs(swerve.getAverageIntegratedSensorPosition());
          if(distanceTravelled> Parameters.MOVE_BACK_LEFT_TRENCH_DISTANCE_IN_INCHES){
            swerve.setSpeed(0.0);
            phase1 = PhaseStates.ENDED; 
          }
          else{
            moveBack(Parameters.MOVE_BACK_LEFT_TRENCH_DISTANCE_IN_INCHES);
            SmartDashboard.putString("Autonomus phase","Phase 1");
            return;
          }
         
        }
        
        if(phase2 == PhaseStates.NOT_SARTED){ // Aligns and shoots pre loaded power cells
          phase2 = PhaseStates.STARTED;
          stopWatch.resetTimer();
          swerve.resetEncoderPosition();
          //shooter.spinStirrerMotors();
          //shooter.spinShooterMotors(Parameters.SHOOTER_MOTOR_SPEED_CLOSE);
        }
        if(phase2 == PhaseStates.STARTED){
      
          double distanceTravelled = Math.abs(swerve.getAverageIntegratedSensorPosition());
          if(distanceTravelled > Parameters.DRIVE_FROM_TRENCH_TO_PORT_MODE2){
            swerve.setSpeed(0.0);
            phase2 = PhaseStates.ENDED;
            
          }
          else {
            swerve.travelTowards(45.0);
            swerve.setSpeed(Parameters.AUTO_SWERVE_TRACTION_SPEED);
            SmartDashboard.putString("Autonomus phase","Phase 2");
            return;
          }
        }
          if(phase3 == PhaseStates.NOT_SARTED){ // Turns towards target
            phase3 = PhaseStates.STARTED;
            stopWatch.resetTimer();
  
          }
          if(phase3 == PhaseStates.STARTED){
            alignAndShoot(Parameters.SHOOTER_MOTOR_SPEED_CLOSE);
            if(stopWatch.getElapsedTime() > 3){
              phase3 = PhaseStates.ENDED;
            }
            else {
              SmartDashboard.putString("Autonomus phase","Phase 3");
              return;
        }
      }
      // if(crossWhiteLine()){
      //   moveBack();
       
      // } 


      // if(!stageOneFinished){
      //   crossWhiteLine();
      // }
      // else if(!stageTwoFinished){
      //   drivingToPorts(Parameters.DRIVE_TO_TRENCH_DISTANCE_IN_INCHES);
      // }
      // else{
      //   alignAndShoot();
      // }
    //   elapsedTime +=0.02;

    //   //spin shooter motors --> align and shoot
    //   alignAndShoot();
    //   if(!stageOneFinished){
    //     crossWhiteLine();
    //   }
    //   else if(!stageTwoFinished){
    //     drivingToPorts(Parameters.MOVE_BACK_RIGHT_TRENCH_DISTANCE_IN_INCHES);
    //   }
    //   else{
    //     alignAndShoot();
    //   }
      
     }
     public void mode5(){
       swerve.setSpeed(Parameters.AUTO_SWERVE_TRACTION_SPEED);
       swerve.travelTowards(180.0);
     }

}
