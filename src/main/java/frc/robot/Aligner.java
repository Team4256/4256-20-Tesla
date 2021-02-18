/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Add your docs here.
 */
public class Aligner {
    private PIDController orientationPID = new PIDController(-.0242, 0, 0); //Values must be negative
    private PIDController gyroOrientationPID = new PIDController(-.035, 0, -.003); //Values must be negative
    private PIDController positionPID = new PIDController(0, 0, 0);
    private static D_Swerve swerveSystem;
    public static Aligner instance = null;
    Limelight camera = Limelight.getInstance();
    Gyro gyro = Gyro.getInstance();
    private double target;
    private Aligner () {
        orientationPID.setTolerance(Parameters.POSITION_TOLERANCE, Parameters.VELOCITY_TOLERANCE);
        gyroOrientationPID.setTolerance(Parameters.POSITION_TOLERANCE, Parameters.VELOCITY_TOLERANCE);
        swerveSystem = D_Swerve.getInstance();
    }



    public synchronized static Aligner getInstance() {
        if (instance == null) {
          instance = new Aligner();
        }
        return instance;
    }

    

    public boolean getIsAtTarget(double threshold) {
        return camera.getTargetOffsetDegrees() <= threshold;
        
    }
    public double getDistanceToTarget(){
        return camera.getTargetOffsetDegrees();
    }

    public double getOrientationCommand() {
         double spinningSpeed =  orientationPID.calculate(camera.getTargetOffsetDegrees(), 0);
         spinningSpeed = Math.max(-.2, Math.min(spinningSpeed, .2));
         return spinningSpeed;
    }



    public double getSpinOrentationCommand() {
        double error = target - gyro.getCurrentAngle();

        while (error > 180.0) {
            error -= 360.0;
        }
        while (error < -180.0) {
           error += 360.0;
        }
        SmartDashboard.putNumber("SwerveError", error);
        SmartDashboard.putNumber("SwerveTarget", target);
        SmartDashboard.putNumber("PIDPositonError", gyroOrientationPID.getPositionError());
        SmartDashboard.putNumber("PIDVelocityError", gyroOrientationPID.getVelocityError());
        double spinSpeed = gyroOrientationPID.calculate(error, 0.0);
        if (gyroOrientationPID.atSetpoint()) {
            spinSpeed = 0;
        } else {
            spinSpeed = Math.max(-.4, Math.min(spinSpeed, .4));
        }
        
        return spinSpeed;
    }

    public void setGyroSnapshot() {
        gyroOrientationPID.reset();
        target = gyro.getCurrentAngle();
    }


    public void alignRobotToTarget(){
        //camera.turnLEDOn();
            swerveSystem.setSpin(getOrientationCommand());
        //SmartDashboard.putNumber("Offset", camera.putTxToDashboard());
        
    }

    public void turnLEDOff() {
        camera.turnLEDOff();
    }
    
}   
    
