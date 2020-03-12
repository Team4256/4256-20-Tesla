/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import com.fasterxml.jackson.databind.deser.impl.CreatorCandidate.Param;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.TalonFXSensorCollection;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;	
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.DigitalInput;			

public class ClimbingControl {
    
    public double climbingSpeed;
    private WPI_TalonFX climbMotorRight;
    private WPI_TalonFX climbMotorLeft;
    private DoubleSolenoid armRotationSolenoid;
    private DoubleSolenoid climberLock;
    private double targetHeight;
    public double retractingSpeedMotorLeft;
    public double retractingSpeedMotorRight;
    public DigitalInput limitSwitchLeft;
    public DigitalInput limitSwitchRight;
    public static final int kTimeoutMS = 10;

    private static ClimbingControl instance = null;
    
    public enum ClimbingStates{
        EXTENDPOLES,
        RETRACTPOLES,
        STOP;
    }
    
    public ClimbingStates currentState = ClimbingStates.STOP;

    public void climberArmUp(){
        rotateArmUp();
    }
    public void climberArmDown(){
        rotateArmDown();
    }
    public void extendClimberPolesHigh(){

        currentState = ClimbingStates.EXTENDPOLES;

        targetHeight = Parameters.MAX_HEIGHT_COUNT;

    }

    public void extendClimberPolesMedium(){

        currentState = ClimbingStates.EXTENDPOLES;

        targetHeight = Parameters.MED_HEIGHT_COUNT;

    }

    public void retractClimberPoles(){
        currentState = ClimbingStates.RETRACTPOLES;
        retractingSpeedMotorLeft = Parameters.CLIMBER_MOTOR_SPEED_DPAD;
        retractingSpeedMotorRight = Parameters.CLIMBER_MOTOR_SPEED_DPAD;
    }
    public void engageLock() {
        lockEngage();
    }
    public void disngageLock() {
        lockDisengage();
    }
    public void retractIndividualClimberPoleRight(double leftMotorSpeed, double rightMotorSpeed){
        if(leftMotorSpeed !=0.0 || rightMotorSpeed != 0.0){
            currentState = ClimbingStates.RETRACTPOLES;
        } 
        retractingSpeedMotorLeft = leftMotorSpeed*Parameters.CLIMBER_MOTOR_SPEED_INDIVIDUAL;
        retractingSpeedMotorRight = rightMotorSpeed*Parameters.CLIMBER_MOTOR_SPEED_INDIVIDUAL; 
    }
    public void stopClimb(){
        currentState = ClimbingStates.STOP;
        climbMotorLeft.set(0.0);
        climbMotorRight.set(0.0);
    }


    public void periodic(){
        double leftEncoderPosition = -climbMotorLeft.getSensorCollection().getIntegratedSensorPosition();
        double rightEncoderPosition = climbMotorRight.getSensorCollection().getIntegratedSensorPosition(); 
        SmartDashboard.putNumber("leftEncoderPosition", leftEncoderPosition);
        SmartDashboard.putNumber("RightEncoderPosition", rightEncoderPosition);
        switch(currentState){
    
            case EXTENDPOLES:
              extendPoles();
            break;
            case RETRACTPOLES:
              retractPoles();
            break;
            case STOP:
              stopClimb();
            break;
        }
        
        

    }



    
    //need to add device numbers based on excel sheet
    private ClimbingControl(){
        climbMotorRight = new WPI_TalonFX(Parameters.R_CLIMBER_MOTOR_ID); 
        climbMotorLeft = new WPI_TalonFX(Parameters.L_CLIMBER_MOTOR_ID);
        climbMotorLeft.setNeutralMode(NeutralMode.Brake);
        climbMotorRight.setNeutralMode(NeutralMode.Brake);
        armRotationSolenoid = new DoubleSolenoid(Parameters.CLIMBER_FORWARD_CHANNEL, Parameters.CLIMBER_REVERSE_CHANNEL);
        climberLock = new DoubleSolenoid(Parameters.CLIMBER_LOCK_FORWARD_CHANNEL, Parameters.CLIMBER_LOCK_REVERSE_CHANNEL);
        climbMotorRight.getSensorCollection().setIntegratedSensorPosition(0,0);
        limitSwitchLeft = new DigitalInput(Parameters.CLIMBER_LEFT_RETRACT_LIMIT_SWITCH_ID);
        limitSwitchRight = new DigitalInput(Parameters.CLIMBER_RIGHT_RETRACT_LIMIT_SWITCH_ID);

    }

    public void climberInit(){ //resets both encoder position
        climbMotorLeft.getSensorCollection().setIntegratedSensorPosition(0.0, kTimeoutMS);
        climbMotorRight.getSensorCollection().setIntegratedSensorPosition(0.0, kTimeoutMS);
    }

    public synchronized static ClimbingControl getInstance() {
        if (instance == null) {
          instance = new ClimbingControl();
        }
        return instance;
          
        }
    
    public void rotateArmUp(){
        armRotationSolenoid.set(Value.kForward);
    } 



    public void rotateArmDown() {
        armRotationSolenoid.set(Value.kReverse);
    }
    

    public void extendPoles(){
        if  (true || climbMotorRight.getSensorCollection().getIntegratedSensorPosition() < targetHeight){
            lockDisengage();
            climbMotorRight.set(Parameters.CLIMBER_MOTOR_SPEED_DPAD);
            climbMotorLeft.set(-Parameters.CLIMBER_MOTOR_SPEED_DPAD);
        } 
        else 
        {
          climbMotorRight.set(0.0);
          climbMotorLeft.set(0.0);
        }
    
    
    }  
    


    public void retractPoles() {
        double leftEncoderPosition = -climbMotorLeft.getSensorCollection().getIntegratedSensorPosition();
        double rightEncoderPosition = climbMotorRight.getSensorCollection().getIntegratedSensorPosition(); 
       
        if(leftEncoderPosition <= 0){
            retractingSpeedMotorLeft = 0.0;
        }
        if(rightEncoderPosition <= 0){
            retractingSpeedMotorRight = 0.0;
        }
        
        // if(!limitSwitchLeft.get()){ // when the boolean is false, it senses
        //     retractingSpeedMotorLeft = 0.0; //might need to switch sign
        // }
        
        // if(!limitSwitchRight.get()){
        //     retractingSpeedMotorRight = 0.0;
        // }
        lockDisengage();
        climbMotorLeft.set(retractingSpeedMotorLeft); //might need to switch sign
        climbMotorRight.set(-retractingSpeedMotorRight);
        
    }
    
    public void lockEngage() {
        climberLock.set(DoubleSolenoid.Value.kForward);
    }

    public void lockDisengage() {
        climberLock.set(DoubleSolenoid.Value.kReverse);
    }
}