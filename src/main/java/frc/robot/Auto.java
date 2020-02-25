/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

/**
 * Add your docs here.
 */
public class Auto {
    private D_Swerve swerve = D_Swerve.getInstance();
    private Shooter shooter = Shooter.getInstance();
    private Gyro gyro = Gyro.getInstance();
    private double Xposition = gyro.getDisplacementX();
    private double YPosition = gyro.getDisplacementY();

    public Auto(){

    }
    

    public void crossWhiteLine(){
        if(YPosition > Parameters.CROSS_WHITE_LINE_DISTANCE_IN_METERS) {
          swerve.setSpeed(Parameters.AUTO_SWERVE_TRACTION_SPEED);
          swerve.setSpin(0.0);
        }
    }








}
