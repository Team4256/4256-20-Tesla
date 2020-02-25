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
    private static Aligner instance = null;
    private Gyro gyro = Gyro.getInstance();

    private Aligner () {
        orientationPID.setTolerance(Parameters.POSITION_TOLERANCE, Parameters.VELOCITY_TOLERANCE);
        swerveSystem = D_Swerve.getInstance();
    }



    public synchronized static Aligner getInstance() {
        if (instance == null) {
          instance = new Aligner();
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

    public void alignRobotToTarget(){
        camera.turnLEDOn();
        swerveSystem.setSpin(getOrientationCommand());
        
    }

    public void turnLEDOff() {
        camera.turnLEDOff();
    }
    
}   
    
