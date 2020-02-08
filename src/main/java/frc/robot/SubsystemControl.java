package frc.robot;

public class SubsystemControl {
    // Constants
    private SwerveModule moduleA = new SwerveModule(Parameters.ROTATION_MOTOR_A_ID, 0, true, Parameters.TRACTION_MOTOR_A_ID, false, 0);
    private Xbox driver = new Xbox(0);
    // private D_Swerve swerve = new D_Swerve(moduleA = new SwerveModule(Parameters.ROTATION_MOTOR_A_ID, true, Parameters.TRACTION_MOTOR_A_ID, false, 0));
    //         moduleB = new SwerveModule(0, true, 0, false, 0), moduleC = new SwerveModule(0, true, 0, true, 0),
    //         moduleD = new SwerveModule(0, true, 0, false, 0));
    
     private Intake succer = new Intake(0);
    private Shooter cellShooter = new Shooter();
    private ClimbingPrep climbPrep = new ClimbingPrep();
    private ClimbingControl climbCont = new ClimbingControl();

    // Swerve Periodic
    public void swervePeriodic() {

        if (driver.getRawButtonPressed(driver.BUTTON_X)) {
            moduleA.getRotationMotor().SetAngle(45);
            moduleA.getTractionMotor().set(.5);
        }
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
/*
        if (driver.getRawButtonPressed(driver.BUTTON_RB)) {

            succer.spew();
            
        }
        if (driver.getRawButtonReleased(driver.BUTTON_RB)) {
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
        public void colorPeriodic(){
            if (driver.getRawButtonPressed(driver.BUTTON_X)){
                ColorWheelPrep.rotateArmDown();

            }
        }
    }
}
