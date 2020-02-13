package frc.robot;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import com.fasterxml.jackson.databind.ObjectMapper.DefaultTypeResolverBuilder;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class JoystickControl {
    // Constants
    
    private Xbox driver = new Xbox(0);
    private Xbox gunner = new Xbox(1);
    private D_Swerve swerve = new D_Swerve();
    //private SwerveModule moduleAB = new SwerveModule(Parameters.ROTATION_MOTOR_A_ID,0, true, Parameters.TRACTION_MOTOR_A_ID, false, 0);
    
    //private ClimbingPrep climbPrep = new ClimbingPrep(Parameters.SOLENOID_Up_CHANNEL,Parameters.SOLENOID_Down_CHANNEL);
    private ClimbingControl climbCont = new ClimbingControl(0, 0);

    private Intake succer = new Intake();
    private Shooter cellShooter = new Shooter(Parameters.ShooterMotor_1_ID, Parameters.ShooterMoror_2_ID, Parameters.HopperMotor_ID, Parameters.FeederMotor_ID, Parameters.ShroudForwardChannel, Parameters.ShroudReverseChannel);
    private boolean rotationMode = false;
    private double angle = 0;
    double spin;
    private double direction;
    private double speed;

    // Swerve Periodic

    public void swervePeriodic() {
        speed  = driver.getCurrentRadius(Xbox.STICK_LEFT, true);
        direction = driver.getCurrentAngle(Xbox.STICK_LEFT, true);
        spin = 0.5 * driver.getDeadbandedAxis(Xbox.AXIS_RIGHT_X);// normal mode
        
            //if (driver.getRawButtonPressed(driver.BUTTON_A)) {
            //moduleAB.getTractionMotor().set(.3);
            //moduleAB.getRotationMotor().SetAngle(90);
        //}
        // if (driver.getRawButtonPressed(driver.BUTTON_X)) {
        //     rotationMode = true;
        //     angle = 360;
        // }
        if (true) {
            rotationMode = true;
            
        }
        if (rotationMode == true) {
            //moduleAB.getRotationMotor().SetAngle(driver.getCurrentAngle(Xbox.STICK_RIGHT, true));
            //moduleAB.getTractionMotor().set(driver.getCurrentRadius(Xbox.STICK_LEFT, true));
            
            swerve.modules[0].getRotationMotor().SetAngle(driver.getCurrentAngle(Xbox.STICK_RIGHT, true));
            swerve.modules[0].getTractionMotor().set(driver.getCurrentRadius(Xbox.STICK_LEFT, true));

            // spin *= spin * Math.signum(spin);
            // swerve.setSpeed(speed);
            // swerve.setSpin(spin);
            // swerve.travelTowards(direction);
            // swerve.completeLoopUpdate();
            // SmartDashboard.putNumber("Swervespin", spin);
            // SmartDashboard.putNumber("SwervesDirection", direction);
            // SmartDashboard.putNumber("Swervespeed", speed);
            }
        }
        








        // if (driver.getRawButtonPressed(driver.BUTTON_START)) {

        //     swerve.setAllModulesToZero();

        // } else {
             

        //     swerve.completeLoopUpdate();
        //     swerve.setSpin(spin);
        //     swerve.setSpeed(speed);
        //     swerve.travelTowards(direction);
        // }
    //}
    //Shooter Periodic
    //create a button that tells the shooter wheel to spin, when the shoot mode started, the shooter wheel continue to spin. 
    public void shooterPeriodic() {

    //     if (gunner.getRawButtonPressed(gunner.BUTTON_RB)) {

    //         cellShooter.shoot();

    //     }
    //     if (gunner.getRawButtonReleased(gunner.BUTTON_RB)) {
    //         cellShooter.stop();
    //     }
    // }
    // //Intake Periodic
    // public void intakePeriodic() {
    //     if (gunner.getRawButtonPressed(gunner.BUTTON_LB)) {

    //         succer.succ();
            
    //     }
    //     if (gunner.getRawButtonReleased(gunner.BUTTON_LB)) {
    //         succer.stop();
/*
        if (gunner.getRawButtonPressed(gunner.BUTTON_RB)) {

            succer.spew();
            
        }
        if (gunner.getRawButtonReleased(gunner.BUTTON_RB)) {
            succer.stop();
*/
        }
    
    //probably different buttons for both Control and Prep

   
    public void ClimbingPeriodic(){
        // if (gunner.getRawButtonPressed(Xbox.DPAD_NORTH)){
        //     climbPrep.rotateArmUp();
        // }
        // if (driver.getRawButtonPressed(driver.BUTTON_B)){
        //     climbCont.retractPole();
            
        // }
        // if (gunner.getRawButtonPressed(Xbox.DPAD_WEST)){
        //    while (climbCont.extendPoles(ClimbingControl.MED_HEIGHT_COUNT));
        // }
        // if (gunner.getRawButtonPressed(Xbox.DPAD_EAST)){
        //   while (climbCont.extendPoles(ClimbingControl.MAX_HEIGHT_COUNT));
        // }

    }
    public void colorPeriodic(){
        // if (gunner.getRawButtonPressed(Xbox.BUTTON_A)){
        //     cwPrep.wheelDownCW();

        // }
        // if (gunner.getRawButtonPressed(Xbox.BUTTON_Y)){
        //     cwPrep.wheelUpCW();

        }
        if(gunner.getRawButtonPressed(Xbox.BUTTON_X)){
            controlPanel.spinControlPanel3Revs();
        }
        if( gunner.getRawButtonPressed(Xbox.BUTTON_B)){
            controlPanel.spinToTheTargetColor();
        }
    }
}