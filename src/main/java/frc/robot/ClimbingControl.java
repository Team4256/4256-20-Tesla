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

public class ClimbingControl {
    
    public double climbingSpeed;
    private WPI_TalonFX climbMotorRight;
    private WPI_TalonFX climbMotorLeft;
    private DoubleSolenoid armRotationSolenoid;
    
    public enum ClimbingStates{
        ROTATEARMSUP,
        ROTATEARMSDOWN,
        EXTENDPOLES,
        CLIMB,
        STOP;
    }
    public ClimbingStates currentStates = ClimbingStates.STOP;

    
    //need to add device numbers based on excel sheet
    public ClimbingControl( int RMotorID, int LMotorID){
        climbMotorRight = new WPI_TalonFX(RMotorID); 
        climbMotorLeft = new WPI_TalonFX(LMotorID);
        climbMotorLeft.setNeutralMode(NeutralMode.Brake);
        climbMotorRight.setNeutralMode(NeutralMode.Brake);
        armRotationSolenoid = new DoubleSolenoid(Parameters.CLIMBER_FORWARD_CHANNEL, Parameters.CLIMBER_REVERSE_CHANNEL);
        climbMotorRight.getSensorCollection().setIntegratedSensorPosition(0,0);



    }
    public void periodic(){
        switch(currentStates){
          case ROTATEARMSUP:
            rotateArmUp();
            break;
          case ROTATEARMSDOWN:
            rotateArmDown();
            break;
          case EXTENDPOLES:
            ;
            break;
          case CLIMB:
            ;
            break;
          case STOP:
    
            break;
        }
    }
    public void rotateArmUp(){
        armRotationSolenoid.set(DoubleSolenoid.Value.kReverse);
    } 



    public void rotateArmDown() {
        armRotationSolenoid.set(DoubleSolenoid.Value.kForward);
    }

    
    
    public void extendPoles(double height)
    {
        if  (climbMotorRight.getSensorCollection().getIntegratedSensorPosition() < height)
        { 
            climbMotorRight.set(Parameters.CLIMBING_SPEED_LOW);
            climbMotorLeft.set(Parameters.CLIMBING_SPEED_LOW);
        } 
        else 
        {
          climbMotorRight.set(0.0);
          climbMotorLeft.set(0.0);
         
        }
    }  
    
    


    public void retractPole(){
        
        climbMotorRight.set(Parameters.CLIMBING_SPEED_LOW);
        climbMotorLeft.set(Parameters.CLIMBING_SPEED_LOW);
    }
    public void movePoleRight(int speed){
        climbMotorRight.set(-speed);
    }
    public void movePoleLeft(int speed){
        climbMotorLeft.set(-speed);
    }
   // add extracts (thumsticks)
}