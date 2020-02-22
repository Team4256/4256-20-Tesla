
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
    
    private ClimbingControl climber = new ClimbingControl();

    private Intake succer = new Intake();
    private Aligner aligner = new Aligner(swerve);
    private Shooter cellShooter = new Shooter(aligner, Parameters.SHOOTERMOTOR_L_ID, Parameters.SHOOTERMOTOR_R_ID, Parameters.STIRRERMOTOR_ID, Parameters.FEEDERMOTOR_ID, Parameters.SHROUD_UP_CHANNEL, Parameters.SHROUD_DOWN_CHANNEL);
   // private ControlPanelSystem controlPanel = new ControlPanelSystem(Parameters.WHEEL_ARM_UP_SOLENOID_CHANNEL, Parameters.WHEEL_ARM_DOWN_SOLENOID_CHANNEL, Parameters.WHEEL_ARM_MOTOR_ID);
    private boolean rotationMode = false;
    private double angle = 0;
    double spin;
    private double direction;
    private double speed;
    private Limelight blindei = Limelight.getInstance();

    // Swerve Periodic

    public void swervePeriodic() {
        speed  = driver.getCurrentRadius(Xbox.STICK_LEFT, true);
        direction = driver.getCurrentAngle(Xbox.STICK_LEFT, true);
        spin = 0.5 * driver.getDeadbandedAxis(Xbox.AXIS_RIGHT_X);// normal mode
        
        //     if (driver.getRawButtonPressed(driver.BUTTON_A)) {
        //     moduleAB.getTractionMotor().set(.3);
        //     moduleAB.getRotationMotor().SetAngle(90);
        // }
        // if (driver.getRawButtonPressed(driver.BUTTON_X)) {
        //     rotationMode = true;

        //     angle = 360;
        // }
        if (true) {
            rotationMode = true;
            
        }
        if (rotationMode == true) {
            // moduleAB.getRotationMotor().SetAngle(driver.getCurrentAngle(Xbox.STICK_RIGHT, true));
            // moduleAB.getTractionMotor().set(driver.getCurrentRadius(Xbox.STICK_LEFT, true));

            // swerve.modules[0].getRotationMotor().SetAngle(driver.getCurrentAngle(Xbox.STICK_RIGHT, true));
            // swerve.modules[0].getTractionMotor().set(driver.getCurrentRadius(Xbox.STICK_LEFT, true));
            
            spin *= spin * Math.signum(spin);
            swerve.setSpeed(speed);
            swerve.setSpin(spin);
            swerve.travelTowards(direction);
            swerve.completeLoopUpdate();
            SmartDashboard.putNumber("Swervespin", spin);
            SmartDashboard.putNumber("SwervesDirection", direction);
            SmartDashboard.putNumber("Swervespeed", speed);
            }
        
        








        if (driver.getRawButtonPressed(driver.BUTTON_START)) {

            swerve.setAllModulesToZero();

        } else {
             

            swerve.completeLoopUpdate();
            swerve.setSpin(spin);
            swerve.setSpeed(speed);
            swerve.travelTowards(direction);
            SmartDashboard.putNumber("tx", blindei.getCommandedDirection());

        }
    }
    Shooter Periodic;
   // create a button that tells the shooter wheel to spin, when the shoot mode started, the shooter wheel continue to spin. 
    public void shooterPeriodic() {

        if (driver.getAxisPress(Xbox.AXIS_RT, 0.5)) {
        
             cellShooter.ShootAlign();

         }
         if (driver.getRawButtonPressed(Xbox.BUTTON_RB)) {
             cellShooter.ShootNoAlign();
         }
        if (gunner.getAxisPress(Xbox.AXIS_LT, 0.5)){
            cellShooter.SpinShooterPrep();
        }
        if (gunner.getAxisPress(Xbox.AXIS_RT, 0.5)){
            cellShooter.ShooterRange();
        }
        cellShooter.periodic();
        
     }
    //Intake Periodic
    public void intakePeriodic() {
         if (gunner.getRawButtonPressed(Xbox.BUTTON_LB)) {
             succer.succ();
         }
         if (gunner.getRawButtonReleased(Xbox.BUTTON_LB)) {
             succer.stop();
        }
        if (gunner.getRawButtonPressed(Xbox.BUTTON_RB)) {
            succer.spew();  
        }
        if (gunner.getRawButtonReleased(Xbox.BUTTON_RB)) {
            succer.stop();
        }
    }
    //probably different buttons for both Control and Prep

   
    public void ClimbingPeriodic(){
        if (gunner.getPOV() ==(Xbox.DPAD_NORTH)){
            climber.climberArmUp();
        }
        if (gunner.getPOV() == (Xbox.DPAD_WEST)){
            climber.extendClimberPolesMedium();
        }
        if (gunner.getPOV() ==(Xbox.DPAD_EAST)){
            climber.extendClimberPolesHigh();
        }
        if (gunner.getPOV() == (Xbox.DPAD_SOUTH)){
            climber.retractClimberPoles(); //both poles at the same time
            
        }
        else{
            climber.retractIndividualClimberPole(gunner.getDeadbandedAxis(Xbox.AXIS_LEFT_Y), gunner.getDeadbandedAxis(Xbox.AXIS_RIGHT_Y));
            
        }
        
        climber.periodic();
    }




    // public void colorPeriodic(){
    //     if (gunner.getRawButtonPressed(Xbox.BUTTON_Y)){
    //         controlPanel.ArmDown();
    //     }

    //     if (gunner.getRawButtonPressed(Xbox.BUTTON_A)){
    //         controlPanel.ArmUp();
    //     }
    //     if(gunner.getRawButtonPressed(Xbox.BUTTON_X)){
    //         controlPanel.spin3Revs();
    //     }
    //     if( gunner.getRawButtonPressed(Xbox.BUTTON_B)){
    //         controlPanel.spinToAColor();
    //     }
    //     controlPanel.periodic();
    //     }   
        }
