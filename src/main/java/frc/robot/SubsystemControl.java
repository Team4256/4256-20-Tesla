package frc.robot;

public class SubsystemControl {
    // Constants
    private SwerveModule moduleA, moduleB, moduleC, moduleD;
    private Xbox driver = new Xbox(0);
    private D_Swerve swerve = new D_Swerve(moduleA, moduleB, moduleC, moduleD);
    private Shooter cellShooter = new Shooter(0);

    // Swerve Periodic
    public void swervePeriodic() {
        
            moduleA = new SwerveModule(32, true, 31, false, 0);// PRACTICE BOT
            moduleB = new SwerveModule(0, true, 0, false, 0);// PRACTICE BOT
            moduleC = new SwerveModule(0, true, 0, true, 0);// PRACTICE BOT
            moduleC = new SwerveModule(0, true, 0, false, 0);// PRACTICE BOT

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


    public void shooterPeriodic() {

            if (driver.getRawButtonPressed(driver.AXIS_RT)) {

                    cellShooter.shoot();

            }



    }
}
