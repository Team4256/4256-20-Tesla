/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;


import edu.wpi.first.wpilibj.DoubleSolenoid;

public class ClimbingPrep {
    private DoubleSolenoid armRotationSolenoid;
    public ClimbingPrep(){
        
        armRotationSolenoid = new DoubleSolenoid(Parameters.SOLENOID_Up_CHANNEL,Parameters.SOLENOID_Down_CHANNEL);
    }
    public void rotateArmUp(){
        armRotationSolenoid.set(DoubleSolenoid.Value.kReverse);
    }

    public void rotateArmDown() {
        armRotationSolenoid.set(DoubleSolenoid.Value.kForward);
    }
}