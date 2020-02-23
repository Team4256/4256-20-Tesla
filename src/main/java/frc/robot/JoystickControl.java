
package frc.robot;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import com.fasterxml.jackson.databind.ObjectMapper.DefaultTypeResolverBuilder;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class JoystickControl {
    // Constants
    
    private Xbox driver = new Xbox(0);
    private Xbox gunner = new Xbox(1);
    public D_Swerve swerve = new D_Swerve();
    
    private ClimbingControl climber = new ClimbingControl();

    private Intake intake = new Intake();
    private Aligner aligner = new Aligner(swerve);
    private Shooter cellShooter = new Shooter(aligner, Parameters.SHOOTERMOTOR_L_ID, Parameters.SHOOTERMOTOR_R_ID, Parameters.STIRRERMOTOR_ID, Parameters.FEEDERMOTOR_ID, Parameters.SHROUD_UP_CHANNEL, Parameters.SHROUD_DOWN_CHANNEL);
   // private ControlPanelSystem controlPanel = new ControlPanelSystem(Parameters.WHEEL_ARM_UP_SOLENOID_CHANNEL, Parameters.WHEEL_ARM_DOWN_SOLENOID_CHANNEL, Parameters.WHEEL_ARM_MOTOR_ID);
    private boolean rotationMode = false;
    private double angle = 0;
    private double spin;
    private double direction;
    private double speed;
    private boolean isShooting;
    private static NetworkTableInstance nt;
    private static NetworkTable zeus;
    private double modAMax = 0;
    private double modBMax = 0;
    private double modCMax = 0;
    private double modDMax = 0;
    private double modAMin = 10;
    private double modBMin = 10;
    private double modCMin = 10;
    private double modDMin = 10;



    public JoystickControl() {
        nt = NetworkTableInstance.getDefault();
        zeus = nt.getTable("Zeus");
    }

    public void displaySwerveAngles () {
    double modA = swerve.getSwerveModules()[0].getRotationMotor().getEncoderVoltage();
    double modB = swerve.getSwerveModules()[1].getRotationMotor().getEncoderVoltage();
    double modC = swerve.getSwerveModules()[2].getRotationMotor().getEncoderVoltage();
    double modD = swerve.getSwerveModules()[3].getRotationMotor().getEncoderVoltage();
    
    modAMax = Math.max(modAMax, modA);
    modBMax = Math.max(modBMax, modB);
    modCMax = Math.max(modCMax, modC);
    modDMax = Math.max(modDMax, modD);
    modAMin = Math.min(modAMin, modA);
    modBMin = Math.min(modBMin, modB);
    modCMin = Math.min(modCMin, modC);
    modDMin = Math.min(modDMin, modD);
    
    


    zeus.getEntry("ModuleA Angle").setNumber(swerve.getSwerveModules()[0].getRotationMotor().getCurrentAngle());
    zeus.getEntry("ModuleB Angle").setNumber(swerve.getSwerveModules()[1].getRotationMotor().getCurrentAngle());
    zeus.getEntry("ModuleC Angle").setNumber(swerve.getSwerveModules()[2].getRotationMotor().getCurrentAngle());
    zeus.getEntry("ModuleD Angle").setNumber(swerve.getSwerveModules()[3].getRotationMotor().getCurrentAngle());
    
    zeus.getEntry("ModuleA Tare").setNumber(modA);
    zeus.getEntry("ModuleB Tare").setNumber(modB);
    zeus.getEntry("ModuleC Tare").setNumber(modC);
    zeus.getEntry("ModuleD Tare").setNumber(modD);
    
    zeus.getEntry("ModuleA Max").setNumber(modAMax);
    zeus.getEntry("ModuleB Max").setNumber(modBMax);
    zeus.getEntry("ModuleC Max").setNumber(modCMax);
    zeus.getEntry("ModuleD Max").setNumber(modDMax);
    
    zeus.getEntry("ModuleA Min").setNumber(modAMin);
    zeus.getEntry("ModuleB Min").setNumber(modBMin);
    zeus.getEntry("ModuleC Min").setNumber(modCMin);
    zeus.getEntry("ModuleD Min").setNumber(modDMin);
    }
    
    
    // Swerve Periodic

    public void swervePeriodic() {
        speed  = driver.getCurrentRadius(Xbox.STICK_LEFT, true);
        direction = driver.getCurrentAngle((Xbox.STICK_LEFT), true);
        spin = 0.5 * driver.getDeadbandedAxis(Xbox.AXIS_RIGHT_X);// normal mode
        final boolean turbo = driver.getRawButton(Xbox.BUTTON_STICK_LEFT);

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
            swerve.travelTowards(-direction);
            swerve.completeLoopUpdate();
            SmartDashboard.putNumber("Swervespin", spin);
            SmartDashboard.putNumber("SwervesDirection", direction);
            SmartDashboard.putNumber("Swervespeed", speed);

            speed *= speed;
        if (!turbo) {
            speed *= 0.6;
        }
            }
             else {
            swerve.completeLoopUpdate();
            swerve.setSpin(spin);
            swerve.setSpeed(speed);
            swerve.travelTowards(direction);
           
        }
    }   
    //Shooter Periodic
    Shooter Periodic; 
    public void shooterPeriodic() {

        if (driver.getAxisPress(Xbox.AXIS_RT, 0.5)) {
             cellShooter.ShootAlign();
         }
         if (driver.getRawButtonPressed(Xbox.BUTTON_RB)) {
             isShooting = true;
            cellShooter.ShootNoAlign();
         }
         if (driver.getRawButtonReleased(Xbox.BUTTON_RB)) {
             cellShooter.STOP();
         }
        if (gunner.getRawButtonPressed(Xbox.AXIS_LT)){
            cellShooter.SpinShooterPrep();
        }
        if (gunner.getAxisPress(Xbox.AXIS_RT, 0.5)){
            cellShooter.ShooterRange();
        }
        if (driver.getRawButtonPressed(Xbox.BUTTON_B)) {
            cellShooter.spinStirrerMotors();
        }
        
        
        
        cellShooter.periodic();
        
     }
    //Intake Periodic
    public void intakePeriodic() {
         if (gunner.getRawButtonPressed(Xbox.BUTTON_LB)) {
            
            intake.spew();
            
         }
         if (gunner.getRawButtonReleased(Xbox.BUTTON_LB)) {
             intake.stop();
        }
        if (gunner.getRawButtonPressed(Xbox.BUTTON_RB)) {

            intake.succ();
            
        }
        if (gunner.getRawButtonReleased(Xbox.BUTTON_RB)) {
            intake.stop();

        } 
        if (driver.getRawButtonPressed(Xbox.BUTTON_A)) {

            intake.intakeDown();

        }
        if (driver.getRawButtonReleased(Xbox.BUTTON_Y)) {

            intake.intakeUp();

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
