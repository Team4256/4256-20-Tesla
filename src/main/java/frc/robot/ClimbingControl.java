/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;

public class ClimbingControl {
    //Will need to set speed to something else 
    private double speed = 0.5;
    public double climbingSpeed;
    private WPI_TalonFX climbMotorRight;
    private WPI_TalonFX climbMotorLeft;
    //need to add device numbers based on excel sheet
    public ClimbingControl(){
        climbMotorRight = new WPI_TalonFX(Parameters.R_CLIMBER_MOTOR_ID); 
    }
    public void extendPole(){
        climbMotorRight.set(speed);
        climbMotorLeft.set(speed);
    }
    public void retractPole(){
        climbMotorRight.set(-speed);
        climbMotorLeft.set(-speed);
    }
    public void retractPoleRight(double climbingSpeed){
        climbMotorRight.set(-climbingSpeed);
    }
    public void retractPoleLeft(double climbingSpeed){
        climbMotorLeft.set(-climbingSpeed);
    }
}