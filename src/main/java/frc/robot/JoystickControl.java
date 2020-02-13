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
    

    private ClimbingPrep climbPrep = new ClimbingPrep(Parameters.ClimberForwardChannel, Parameters.ClimberReverseChannel);
    private ClimbingControl climbCont = new ClimbingControl(Parameters.R_CLIMBER_MOTOR_ID,Parameters.L_CLIMBER_MOTOR_ID);
    private ControlPanelSystem cwPrep = new ControlPanelSystem(Parameters.WHEEL_ARM_UP_SOLENOID_CHANNEL, Parameters.WHEEL_ARM_DOWN_SOLENOID_CHANNEL,Parameters.WHEEL_ARM_MOTOR_ID);
    private Intake succer = new Intake();
    private Shooter cellShooter = new Shooter(Parameters.ShooterMotor_1_ID, Parameters.ShooterMoror_2_ID, Parameters.HopperMotor_ID, Parameters.FeederMotor_ID, Parameters.ShroudForwardChannel, Parameters.ShroudReverseChannel);
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
    //create a button that tells the shooter wheel to spin, when the shoot mode started, the shooter wheel continue to spin. 
    public void shooterPeriodic() {

        if (gunner.getRawButtonPressed(Xbox.BUTTON_RB)) {
              /* if (target is found && motorSpeed >= a certain number){
      cellshooter.shoot();
   }
   else if(target is found && motorSpeed !>= certain number){
      increase the motorspeed
   }
   else if(target is not found && motorSpeed >= a certain number){
      look for target
   }
   else{
     look for target 
     increase the motorspeed
   }

*/

            cellShooter.shoot();

        }
        if (gunner.getRawButton(Xbox.BUTTON_LB)){

            cellShooter.passBall();
        }
        if (gunner.getRawButtonReleased(gunner.BUTTON_RB)) {
            cellShooter.stop();
        }
    }
    //Intake Periodic
    public void intakePeriodic() {
        if (gunner.getRawButtonPressed(Xbox.BUTTON_LB)) {

            succer.succ();
            
        }
        if (gunner.getRawButtonReleased(Xbox.BUTTON_LB)) {
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
        if (gunner.getRawButtonPressed(Xbox.DPAD_NORTH)){
            climbPrep.rotateArmUp();
        }
        if (gunner.getRawButtonPressed(Xbox.DPAD_SOUTH)){
            climbCont.retractPole();
            
        }
        if (gunner.getRawButtonPressed(Xbox.DPAD_WEST)){
           while (climbCont.extendPoles(Parameters.MED_HEIGHT_COUNT));
        }
        if (gunner.getRawButtonPressed(Xbox.DPAD_EAST)){
          while (climbCont.extendPoles(Parameters.MAX_HEIGHT_COUNT));
        }
        if(gunner.getAxisActivity(Xbox.AXIS_LEFT_Y)){
        }
        if (gunner.getAxisActivity(Xbox.AXIS_RIGHT_Y)){
            climbCont.retractPoleRight(Parameters.ClimbingSpeed);
        }
    }
    public void colorPeriodic(){
        if (gunner.getRawButtonPressed(Xbox.BUTTON_A)){
            cwPrep.wheelDownCW();

        }
        if (gunner.getRawButtonPressed(Xbox.BUTTON_Y)){
            cwPrep.wheelUpCW();

        }
    }
}