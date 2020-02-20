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
    private D_Swerve swerveSystem;
    Limelight camera = Limelight.getInstance();
    Gyro gyro = new Gyro(Parameters.GYRO_UPDATE_HZ);
    public Aligner (D_Swerve swerve) {
        orientationPID.setTolerance(Parameters.POSITION_TOLERANCE, Parameters.VELOCITY_TOLERANCE);
        swerveSystem = swerve;
    }

    public boolean getIsAtTarget() {
        return orientationPID.atSetpoint();
    }
    public double getDistanceToTarget(){
        return camera.getTargetOffsetDegrees();
    }

    public double getDirectionCommand() {
        return positionPID.calculate(camera.getTargetOffsetDegrees(), 0);

    }

    public double getOrientationCommand() {
        return orientationPID.calculate(camera.getTargetOffsetDegrees(), 0);
    }

    public void alignRobotToTarget(){
        camera.turnLEDOn();
        swerveSystem.setSpin(getOrientationCommand());
        
    }

    public void turnLEDOff() {
        camera.turnLEDOff();
    }
    
}   
    
