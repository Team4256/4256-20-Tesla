/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.controller.PIDController;

public class Aligner {

    Limelight camera = Limelight.getInstance();
    Gyro gyro = Gyro.getInstance();
    private final PIDController orientationPID = new PIDController(-.0242, 0, 0);
    private static D_Swerve swerveSystem;
    public static Aligner instance = null;

    private Aligner() {
        orientationPID.setTolerance(Parameters.POSITION_TOLERANCE, Parameters.VELOCITY_TOLERANCE);
        swerveSystem = D_Swerve.getInstance();
    }

    public synchronized static Aligner getInstance() {
        if (instance == null) {
            instance = new Aligner();
        }
        return instance;
    }

    // Check if crosshair is at the target
    public boolean getIsAtTarget(final double threshold) {
        return camera.getTargetOffsetDegrees() <= threshold;

    }

    // Get distance from crosshair to target
    public double getDistanceToTarget() {
        return camera.getTargetOffsetDegrees();
    }

    // Get the amount to move to align to target
    public double getOrientationCommand() {
        double spinningSpeed = orientationPID.calculate(camera.getTargetOffsetDegrees(), 0);
        spinningSpeed = Math.max(-.2, Math.min(spinningSpeed, .2));
        return spinningSpeed;
    }

    // Set the robot to face the limelight target
    public void alignRobotToTarget() {
        // camera.turnLEDOn();
        swerveSystem.setSpin(getOrientationCommand());

    }

    // Turn limelight LED off
    public void turnLEDOff() {
        camera.turnLEDOff();
    }

}
