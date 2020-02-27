/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.controller.PIDController;

/**
 * Add your docs here.
 */
public class Aligner {
    private PIDController orientationPID = new PIDController(0, 0, 0);
    private PIDController positionPID = new PIDController(0, 0, 0);
    private static D_Swerve swerveSystem;
    public static Aligner instance = null;
    Limelight camera = Limelight.getInstance();
    Gyro gyro = Gyro.getInstance();
    public Aligner (D_Swerve swerve) {
        orientationPID.setTolerance(Parameters.POSITION_TOLERANCE, Parameters.VELOCITY_TOLERANCE);
        swerveSystem = D_Swerve.getInstance();
    }



    public synchronized static Aligner getInstance() {
        if (instance == null) {
          instance = new Aligner(swerveSystem);
        }
        return instance;
    }

    

    public boolean getIsAtTarget() {
        return orientationPID.atSetpoint();
    }
    public double getDistanceToTarget(){
        return camera.getTargetOffsetDegrees();
    }
//something wrong with this 
    public double getDirectionCommand() {
        return positionPID.calculate(camera.getTargetOffsetDegrees(), 0);

    }

    public double getOrientationCommand() {
        return orientationPID.calculate(camera.getTargetOffsetDegrees(), 0);
    }

    public boolean alignRobotToTarget(){
        camera.turnLEDOn();
        swerveSystem.setSpin(getOrientationCommand());
        return getIsAtTarget();
        
    }

    public void turnLEDOff() {
        camera.turnLEDOff();
    }
    
}   
    
