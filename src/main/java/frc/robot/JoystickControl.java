
package frc.robot;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class JoystickControl {
    // Constants

    private Xbox driver = new Xbox(0);
    private Xbox gunner = new Xbox(1);
    private D_Swerve swerve = D_Swerve.getInstance();

    private ClimbingControl climber = ClimbingControl.getInstance();

    private Intake intake = Intake.getInstance();
    private Aligner aligner = Aligner.getInstance();
    private Shooter shooter = Shooter.getInstance();
    private Hopper hopper = Hopper.getInstance();
    private Shroud shroud = Shroud.getInstance();
    private Limelight camera = Limelight.getInstance();
    private Gyro gyro = Gyro.getInstance();
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
    private boolean leftTriggerTurnsMotorOn = true;
    private boolean leftTriggerPrevPressed = false;

    public JoystickControl() {
        nt = NetworkTableInstance.getDefault();
        zeus = nt.getTable("Zeus");
    }

    public void setSwerveToZero() {
        swerve.setAllModulesToZero();
    }

    public void displaySwerveAngles() {
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
        speed = driver.getCurrentRadius(Xbox.STICK_LEFT, true);
        direction = driver.getCurrentAngle((Xbox.STICK_LEFT), true);
        spin = 0.8 * driver.getDeadbandedAxis(Xbox.AXIS_RIGHT_X);// normal mode
        final boolean turbo = driver.getRawButton(Xbox.BUTTON_STICK_LEFT);

        if (true) {
            if (driver.getRawButtonPressed(Xbox.BUTTON_START)) {
                swerve.faceTo(90); // gyro align to a position set in the parameters of this method
            }
            SmartDashboard.putNumber("dist. to target", camera.getDistanceToTarget());
            aligner.camera.turnLEDOn();

            if (aligner.camera.hasTarget()) {
                SmartDashboard.putNumber("TargetOffset", aligner.camera.getTargetOffsetDegrees());
            }

            if (driver.getRawButtonPressed(driver.BUTTON_B)) {
                gyro.reset();
            }

            else {
                if (!turbo) {
                    speed *= 0.6;
                }

                spin *= spin * Math.signum(spin);
                swerve.setSpeed(speed);
                swerve.setSpin(spin);
                swerve.travelTowards(direction);
                swerve.completeLoopUpdate();
                speed *= speed;
            }
        }

    }

    // Shooter Periodic
    Shooter Periodic;

    public void shooterPeriodic() {
        if (driver.getPOV() == (Xbox.DPAD_EAST)) {
            shooter.setShooterSpeed(.9);
        }
        if (driver.getPOV() == (Xbox.DPAD_NORTH)) {
            shooter.setShooterSpeed(.79);
        }
        if (driver.getPOV() == (Xbox.DPAD_WEST)) {
            shooter.setShooterSpeed(.7);
        }
        if (driver.getPOV() == (Xbox.DPAD_SOUTH)) {
            shooter.setShooterSpeed(1);
        }
        if (gunner.getRawButtonPressed(Xbox.BUTTON_RB)) { // Hold and release
            hopper.ShootNoAlign();
        }
        if (gunner.getRawButtonReleased(Xbox.BUTTON_RB)) {
            hopper.StopHopper();
        }

        if (gunner.getDeadbandedAxis(Xbox.AXIS_RT) > .5) { // Hold and release
            hopper.ShootAlign();

        } else {
            if (hopper.getcurrentHopperStates() == Hopper.HopperStates.SHOOTALIGN)
                hopper.StopHopper();

        }

        if (gunner.isTriggerPressed(Xbox.AXIS_LT)) { // Toggle
            shooter.SpinShooterPrep();
            // aligner.alignRobotToTarget();
        }
        gunner.isTriggerReleased(Xbox.AXIS_LT); // clears for next time above is called
        if (gunner.getRawButtonPressed(Xbox.BUTTON_LB)) { // Hold and release

            hopper.ReverseHopper();
        }
        if (gunner.getRawButtonReleased(Xbox.BUTTON_LB)) {
            hopper.StopHopper();
        }

        if (driver.getRawButtonPressed(Xbox.BUTTON_RB)) { // Toggle
            shroud.shroudToggle();
        }
        shooter.periodic();

    }

    // Intake Periodic
    public void intakePeriodic() {
        if (driver.isTriggerPressed(Xbox.AXIS_LT)) {
            intake.succ();
        }
        if (driver.isTriggerReleased(Xbox.AXIS_LT)) {
            intake.stop();
        }

        if (driver.getRawButtonPressed(Xbox.BUTTON_LB)) {
            intake.spew();
        }
        if (driver.getRawButtonReleased(Xbox.BUTTON_LB)) {
            intake.stop();
        }
        if (driver.getRawButtonPressed(Xbox.BUTTON_A)) {
            intake.intakeDown();

        }
        if (driver.getRawButtonReleased(Xbox.BUTTON_Y)) {
            intake.intakeUp();
        }
    }

    public void ClimbingPeriodic() {
        climber.stopClimb();
        if (gunner.getRawButtonPressed(Xbox.BUTTON_Y)) {
            climber.rotateArmUp();
            climber.lockDisengage();

        }

        if (gunner.getRawButtonPressed(Xbox.BUTTON_A)) {
            climber.rotateArmDown();
        }

        if (gunner.getRawButtonPressed(Xbox.BUTTON_X)) {
            climber.lockEngage();
        }

        if (gunner.getRawButtonPressed(Xbox.BUTTON_B)) {
            climber.lockDisengage();
        }

        if (gunner.getPOV() == (Xbox.DPAD_WEST)) {
            climber.extendClimberPolesMedium();
        }
        if (gunner.getPOV() == (Xbox.DPAD_NORTH)) {
            climber.extendClimberPolesHigh();
        }

        if (gunner.getPOV() == (Xbox.DPAD_SOUTH)) {
            climber.retractClimberPoles(); // both poles at the same time

        } else {
            climber.retractIndividualClimberPoleRight(gunner.getDeadbandedAxis(Xbox.AXIS_LEFT_Y),
                    gunner.getDeadbandedAxis(Xbox.AXIS_RIGHT_Y));

        }

        climber.periodic();
    }
}
