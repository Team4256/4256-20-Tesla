package frc.robot;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import com.fasterxml.jackson.databind.ObjectMapper.DefaultTypeResolverBuilder;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class JoystickControl {
    // Constants
    private SwerveModule moduleA = new SwerveModule(Parameters.ROTATION_MOTOR_A_ID,3, true, Parameters.TRACTION_MOTOR_A_ID, false, 0);
    private Xbox driver = new Xbox(0);
    private Xbox gunner = new Xbox(1);
    // private D_Swerve swerve = new D_Swerve(moduleA = new SwerveModule(Parameters.ROTATION_MOTOR_A_ID, true, Parameters.TRACTION_MOTOR_A_ID, false, 0));
    //         moduleB = new SwerveModule(0, true, 0, false, 0), moduleC = new SwerveModule(0, true, 0, true, 0),
    //         moduleD = new SwerveModule(0, true, 0, false, 0));
    

    private ClimbingPrep climbPrep = new ClimbingPrep(Parameters.SOLENOID_Up_CHANNEL, Parameters.SOLENOID_Down_CHANNEL);
    private ClimbingControl climbCont = new ClimbingControl(Parameters.R_CLIMBER_MOTOR_ID,Parameters.L_CLIMBER_MOTOR_ID);

    private Intake succer = new Intake();
    private Shooter cellShooter = new Shooter();
    private boolean rotationMode = false;
    private double angle = 0;
    private double spin = 0.5 * driver.getDeadbandedAxis(Xbox.AXIS_RIGHT_X);
    private double direction = driver.getCurrentAngle(Xbox.STICK_LEFT, true);
    private double speed = driver.getCurrentRadius(Xbox.STICK_LEFT, true);

    // Swerve Periodic
    
    public void swervePeriodic() {
        
        // if (driver.getRawButtonPressed(driver.BUTTON_A)) {
        //     moduleA.getTractionMotor().set(.3);
        // }
        // if (driver.getRawButtonPressed(driver.BUTTON_X)) {
        //     rotationMode = true;
        //     angle = 360;
        // }
        // if (driver.getRawButtonPressed(driver.BUTTON_Y)) {
        //     rotationMode = true;
        //     angle = 10;
        // }
        if (true) {
            rotationMode = true;
            angle = 0;
        }
        if (rotationMode) {
           // moduleA.getRotationMotor().SetAngle(angle);
            moduleA.getRotationMotor().SetAngle(driver.getCurrentAngle(Xbox.STICK_RIGHT, true));
            moduleA.getTractionMotor().set(driver.getCurrentRadius(Xbox.STICK_LEFT, true));
        }
        
        
        
        // if (driver.getRawButtonPressed(driver.BUTTON_START)) {

        //     swerve.setAllModulesToZero();

        // } else {
             

        //     swerve.completeLoopUpdate();
        //     swerve.setSpin(spin);
        //     swerve.setSpeed(speed);
        //     swerve.travelTowards(direction);
        // }
    }
    //Shooter Periodic
    public void shooterPeriodic() {

        if (gunner.getRawButtonPressed(gunner.BUTTON_RB)) {

            cellShooter.shoot();

        }
        if (gunner.getRawButtonReleased(gunner.BUTTON_RB)) {
            cellShooter.stop();
        }
    }
    //Intake Periodic
    public void intakePeriodic() {
        if (gunner.getRawButtonPressed(gunner.BUTTON_LB)) {

            succer.succ();
            
        }
        if (gunner.getRawButtonReleased(gunner.BUTTON_LB)) {
            succer.stop();
/*
        if (gunner.getRawButtonPressed(gunner.BUTTON_RB)) {

            succer.spew();
            
        }
        if (gunner.getRawButtonReleased(gunner.BUTTON_RB)) {
            succer.stop();
*/
        }
    }
    //probably different buttons for both Control and Prep

   
    public void ClimbingPeriodic(){
        if (driver.getRawButtonPressed(driver.BUTTON_A)){
            climbPrep.rotateArmUp();
            climbCont.extendPole();
        }
        if (driver.getRawButtonPressed(driver.BUTTON_B)){

            climbCont.retractPole();
            climbPrep.rotateArmDown();
        }
        final double LTAxis = driver.getRawAxis(driver.AXIS_LT);
        final double RTAxis = driver.getRawAxis(driver.AXIS_RT);
        if (LTAxis >= 0.1){
            climbCont.retractPoleLeft(LTAxis);
        }
        if (RTAxis >= 0.1){
            climbCont.retractPoleRight(RTAxis);
        }

    }
    /*public void colorPeriodic(){
        if (driver.getRawButtonPressed(driver.BUTTON_X)){
            ColorWheelPrep.rotateArmDown();

        }
    }*/
}