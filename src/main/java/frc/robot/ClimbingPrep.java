
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
    public ClimbingPrep(int forwardChannel, int reverseChannel){

        armRotationSolenoid = new DoubleSolenoid(forwardChannel, reverseChannel);
    }
    public void rotateArmUp(){
        armRotationSolenoid.set(DoubleSolenoid.Value.kReverse);
    }   

    public void rotateArmDown() {
        armRotationSolenoid.set(DoubleSolenoid.Value.kForward);
    }
}