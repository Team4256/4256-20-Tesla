package frc.robot;

public class SubsystemControl {
    // Constants
    private SwerveModule moduleA, moduleB, moduleC, moduleD;
    private Xbox driver = new Xbox(0);
    private D_Swerve swerve = new D_Swerve(moduleA = new SwerveModule(32, true, 31, false, 0),
            moduleB = new SwerveModule(0, true, 0, false, 0), moduleC = new SwerveModule(0, true, 0, true, 0),
            moduleD = new SwerveModule(0, true, 0, false, 0));
    private Intake succer = new Intake(0);
    private Shooter cellShooter = new Shooter(32);

    // Swerve Periodic
    public void swervePeriodic() {

        if (driver.getRawButtonPressed(driver.BUTTON_X)) {
            swerve.formX();

        }
        if (driver.getRawButtonPressed(driver.BUTTON_START)) {

            swerve.setAllModulesToZero();

        } else {

            double direction = driver.getCurrentAngle(Xbox.STICK_LEFT, true);

            double speed = driver.getCurrentRadius(Xbox.STICK_LEFT, true);

            double spin = 0.5 * driver.getDeadbandedAxis(Xbox.AXIS_RIGHT_X);

            swerve.completeLoopUpdate();
            swerve.setSpin(spin);
            swerve.setSpeed(speed);
            swerve.travelTowards(direction);
        }
    }
    //Shooter Periodic
    public void shooterPeriodic() {

        if (driver.getRawButtonPressed(driver.BUTTON_LB)) {

            cellShooter.shoot();

        }

    }
    //Intake Periodic
    public void intakePeriodic() {
        if (driver.getRawButtonPressed(driver.BUTTON_RB)) {

            succer.succ();
        }
        if (driver.getRawButtonPressed(driver.BUTTON_LB)) {
            succer.spew();
        }

    }

}
