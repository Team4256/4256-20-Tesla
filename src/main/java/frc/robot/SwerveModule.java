package frc.robot;

import java.util.logging.Logger;

import com.revrobotics.CANSparkMax.IdleMode;

public final class SwerveModule {
	public static final double ROTATOR_GEAR_RATIO = 1.0;
	public static final double TRACTION_GEAR_RATIO = 52.0 / 9.0;// updated 2019
	public static final double TRACTION_WHEEL_CIRCUMFERENCE = 4.0 * Math.PI;// inches
	// private final Talon rotation;
	private final SparkMaxNeo rotation;
	private final SparkMaxNeo traction;
	private final double tareAngle;

	private double decapitated = 1.0;
	private double tractionDeltaPathLength = 0.0;
	private double tractionPreviousPathLength = 0.0;

	// This constructor is intended for use with the module which has an encoder on
	// the traction motor.
	public SwerveModule(final int rotatorID, final boolean flippedSensor, final int tractionID,
			final boolean isTractionInverted, final double tareAngle) {
		// rotation = new Talon(rotatorID, ROTATOR_GEAR_RATIO, Talon.position,
		// Encoder.ANALOG, flippedSensor);
		rotation = new SparkMaxNeo(31, IdleMode.kCoast, false);
		traction = new SparkMaxNeo(tractionID, isTractionInverted);
		this.tareAngle = tareAngle;
	}

	/**
	 * This function prepares each motor individually, including setting PID values
	 * for the rotator.
	 **/
	public void init() {

		setTareAngle(tareAngle);

		// rotation.setNeutralMode(Talon.coast);
		rotation.setIdleMode(IdleMode.kCoast);

		traction.init();
	}

	/**
	 * This sets the tare angle. Positive means clockwise and negative means
	 * counter-clockwise.
	 **/
	public void setTareAngle(final double tareAngle) {
		setTareAngle(tareAngle, false);
	}

	/**
	 * This sets the tare angle. Positive means clockwise and negative means
	 * counter-clockwise. If relativeReference is true, tareAngle will be
	 * incremented rather than set.
	 **/
	public void setTareAngle(double tareAngle, final boolean relativeReference) {
		if (relativeReference)
			tareAngle += rotation.compass.getTareAngle();
		rotation.compass.setTareAngle(tareAngle);
	}

	/**
	 * Use wheel_chassisAngle to specify the wheel's orientation relative to the
	 * robot in degrees.
	 **/
	// public void swivelTo(final double wheel_chassisAngle) {
	// rotation.quickSet(decapitateAngle(wheel_chassisAngle), true);

	// }
	public void swivelTo(double targetAngle) {
		rotation.setAngle(targetAngle);
	}

	/**
	 * Use wheel_fieldAngle to specify the wheel's orientation relative to the field
	 * in degrees.
	 **/
	public void swivelWith(final double wheel_fieldAngle, final double chassis_fieldAngle) {
		swivelTo(convertToRobot(wheel_fieldAngle, chassis_fieldAngle));
	}

	/**
	 * This function sets the master and slave traction motors to the specified
	 * speed, from -1 to 1. It also makes sure that they turn in the correct
	 * direction, regardless of decapitated state.
	 **/
	public void set(final double speed) {
		traction.set(speed * decapitated);
	}

	public void checkTractionEncoder() {
		final double currentPathLength = tractionPathLength();
		tractionDeltaPathLength = currentPathLength - tractionPreviousPathLength;
		tractionPreviousPathLength = currentPathLength;
	}

	/**
	 * A shortcut to call completeLoopUpdate on all the Talons in the module.
	 **/
	public void completeLoopUpdate() {
		rotation.completeLoopUpdate();
		traction.completeLoopUpdate();
	}

	/**
	 * Threshold should be specified in degrees. If the rotator is within that many
	 * degrees of its target, this function returns true.
	 **/
	public boolean isThere(final double threshold) {
		return Math.abs(rotation.getCurrentAngle()) <= threshold;
	}

	/**
	 * This function makes sure the module rotates no more than 90 degrees from its
	 * current position. It should be used every time a new angle is being set to
	 * ensure quick rotation.
	 **/
	public double decapitateAngle(final double endAngle) {
		decapitated = Math.abs(rotation.pathTo(endAngle)) > 90 ? -1 : 1;
		return decapitated == -1 ? Compass.validate(endAngle + 180) : Compass.validate(endAngle);
	}

	public double tractionSpeed() {
		return TRACTION_WHEEL_CIRCUMFERENCE * traction.getRPS();// returns in/sec
	}

	public double tractionPathLength() {
		return traction.getPosition() * TRACTION_WHEEL_CIRCUMFERENCE / 12.0;
	}

	public double deltaDistance() {
		return tractionDeltaPathLength;
	}

	public double deltaXDistance() {
		return tractionDeltaPathLength
				* Math.sin(convertToField(rotation.getCurrentAngle(), Robot.gyroHeading) * Math.PI / 180.0);
	}

	public double deltaYDistance() {
		return tractionDeltaPathLength
				* Math.cos(convertToField(rotation.getCurrentAngle(), Robot.gyroHeading) * Math.PI / 180.0);
	}

	public SparkMaxNeo getRotationMotor() {
		return rotation;
	}

	public SparkMaxNeo getTractionMotor() {
		return traction;
	}

	public double getDecapitated() {
		return decapitated;
	}

	public void setParentLogger(final Logger logger) {
		rotation.setParentLogger(logger);
		traction.setParentLogger(logger);
	}

	/**
	 * This function translates angles from the robot's perspective to the field's
	 * orientation. It requires an angle and input from the gyro.
	 **/
	public static double convertToField(final double wheel_robotAngle, final double chassis_fieldAngle) {
		return Compass.validate(wheel_robotAngle + chassis_fieldAngle);
	}

	/**
	 * This function translates angles from the field's orientation to the robot's
	 * perspective. It requires an angle and input from the gyro.
	 **/
	public static double convertToRobot(final double wheel_fieldAngle, final double chassis_fieldAngle) {
		return Compass.validate(wheel_fieldAngle - chassis_fieldAngle);
	}
}