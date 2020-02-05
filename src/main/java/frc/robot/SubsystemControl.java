package frc.robot;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class SubsystemControl {
    // Constants
    private SwerveModule moduleA = new SwerveModule(Parameters.ROTATION_MOTOR_A_ID,3, true, Parameters.TRACTION_MOTOR_A_ID, false, 0);
    private Xbox driver = new Xbox(0);
    TractionControl tractionControl = new TractionControl(45);

    // private D_Swerve swerve = new D_Swerve(moduleA = new SwerveModule(Parameters.ROTATION_MOTOR_A_ID, true, Parameters.TRACTION_MOTOR_A_ID, false, 0));
    //         moduleB = new SwerveModule(0, true, 0, false, 0), moduleC = new SwerveModule(0, true, 0, true, 0),
    //         moduleD = new SwerveModule(0, true, 0, false, 0));
    
     private Intake succer = new Intake(0);
    private Shooter cellShooter = new Shooter(32,0);
    private Motor testMotor = MotorFactory.createTractionMotor(45);
    // Swerve Periodic
    
    public void swervePeriodic() {
        
        double angle = SmartDashboard.getNumber("Swerve Angle", 0.0);
        if (driver.getRawButtonPressed(driver.BUTTON_A)) {
            moduleA.getTractionMotor().set(.1);
        }
        if (driver.getRawButtonReleased(driver.BUTTON_X)) {
           
            moduleA.getRotationMotor().SetAngle(angle);
           
        }
        // if (driver.getRawButtonPressed(driver.BUTTON_START)) {

        //     swerve.setAllModulesToZero();

        // } else {

        //     double direction = driver.getCurrentAngle(Xbox.STICK_LEFT, true);

        //     double speed = driver.getCurrentRadius(Xbox.STICK_LEFT, true);

        //     double spin = 0.5 * driver.getDeadbandedAxis(Xbox.AXIS_RIGHT_X);

        //     swerve.completeLoopUpdate();
        //     swerve.setSpin(spin);
        //     swerve.setSpeed(speed);
        //     swerve.travelTowards(direction);
        // }
    }
    //Shooter Periodic
    public void shooterPeriodic() {

        if (driver.getRawButtonPressed(driver.BUTTON_RB)) {

            cellShooter.shoot();

        }
        if (driver.getRawButtonReleased(driver.BUTTON_RB)) {
            cellShooter.stop();
        }

    }
    //Intake Periodic
    public void intakePeriodic() {
        if (driver.getRawButtonPressed(driver.BUTTON_LB)) {

            succer.succ();
            
        }
        if (driver.getRawButtonReleased(driver.BUTTON_LB)) {
            succer.stop();
        }
    }

}
