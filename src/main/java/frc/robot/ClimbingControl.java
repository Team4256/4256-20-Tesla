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

public class ClimbingControl {
    
    public double climbingSpeed;
    private WPI_TalonFX climbMotorRight;
    private WPI_TalonFX climbMotorLeft;
    private DoubleSolenoid armRotationSolenoid;
    private double targetHeight;
    public double retractingSpeedMotorLeft;
    public double retractingSpeedMotorRight;
    
    public enum ClimbingStates{
        ROTATEARMSUP,
        ROTATEARMSDOWN,
        EXTENDPOLES,
        CLIMB,
        STOP;
    }
    public ClimbingStates currentState = ClimbingStates.STOP;

    public void climberArmUp(){
        currentState = ClimbingStates.ROTATEARMSUP;
    }
    public void climberArmDown(){
        currentState = ClimbingStates.ROTATEARMSDOWN;
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
        currentState = ClimbingStates.CLIMB;
        retractingSpeedMotorLeft = Parameters.CLIMBER_MOTOR_SPEED_DPAD;
        retractingSpeedMotorRight = Parameters.CLIMBER_MOTOR_SPEED_DPAD;
    }
    public void retractIndividualClimberPole(double leftMotorSpeed, double rightMotorSpeed){
        if(leftMotorSpeed !=0.0 || rightMotorSpeed != 0.0){
            currentState = ClimbingStates.CLIMB;
        } 
        retractingSpeedMotorLeft = leftMotorSpeed*Parameters.CLIMBER_MOTOR_SPEED_INDIVIDUAL;
        retractingSpeedMotorRight = rightMotorSpeed*Parameters.CLIMBER_MOTOR_SPEED_INDIVIDUAL; 
    }
    public void stop(){
        currentState = ClimbingStates.STOP;
    }


    public void periodic(){
        switch(currentState){
            case ROTATEARMSUP:
              rotateArmUp();
            break;
            case ROTATEARMSDOWN:
              rotateArmDown();
            break;
            case EXTENDPOLES:
              extendPoles();
            break;
            case CLIMB:
              climb();
              SmartDashboard.putNumber("test3", 12123213);
            break;
            case STOP:
            break;
        }

    }



    
    //need to add device numbers based on excel sheet
    public ClimbingControl( int LMotorID){
        climbMotorRight = new WPI_TalonFX(Parameters.R_CLIMBER_MOTOR_ID); 
        climbMotorLeft = new WPI_TalonFX(Parameters.L_CLIMBER_MOTOR_ID);
        climbMotorLeft.setNeutralMode(NeutralMode.Brake);
        climbMotorRight.setNeutralMode(NeutralMode.Brake);
        armRotationSolenoid = new DoubleSolenoid(Parameters.CLIMBER_FORWARD_CHANNEL, Parameters.CLIMBER_REVERSE_CHANNEL);
        climbMotorRight.getSensorCollection().setIntegratedSensorPosition(0,0);



    }
    public void rotateArmUp(){
        armRotationSolenoid.set(DoubleSolenoid.Value.kReverse);
    } 



    public void rotateArmDown() {
        armRotationSolenoid.set(DoubleSolenoid.Value.kForward);
    }

    
    
    public void extendPoles()
    {
        // if  (climbMotorRight.getSensorCollection().getIntegratedSensorPosition() < targetHeight)
        // { 
        //     //climbMotorRight.set(Parameters.CLIMBER_MOTOR_SPEED_DPAD);
        //     climbMotorLeft.set(Parameters.CLIMBER_MOTOR_SPEED_DPAD);
        // } 
        // else 
        // {
        //   //climbMotorRight.set(0.0);
        //   climbMotorLeft.set(0.0);
         
        // }
    }  
    
    


    public void climb(){ 
        climbMotorLeft.set(-retractingSpeedMotorLeft);
       climbMotorRight.set(-retractingSpeedMotorRight);
        
    }
}