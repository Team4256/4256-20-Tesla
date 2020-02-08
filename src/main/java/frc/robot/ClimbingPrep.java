/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;

public class ClimbingPrep {
    private DoubleSolenoid LarmRotationSolenoid;
    private DoubleSolenoid RarmRotationSolenoid;
    public ClimbingPrep( int forwardChannel, int reverseChannel ){
        //need numbers for Forward Channel and Reverse Channel by Engineering team
        LarmRotationSolenoid = new DoubleSolenoid( forwardChannel, reverseChannel);
        RarmRotationSolenoid = new DoubleSolenoid( forwardChannel, reverseChannel);
    }
    public void rotateArmUp(){
        LarmRotationSolenoid.set(DoubleSolenoid.Value.kReverse);
    }

    public void rotateArmDown() {
        RarmRotationSolenoid.set(DoubleSolenoid.Value.kForward);
    }
}