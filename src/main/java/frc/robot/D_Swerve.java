package frc.robot;

import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public final class D_Swerve implements Drivetrain {

	public static enum SwerveMode {
		FIELD_CENTRIC, ROBOT_CENTRIC
	}

	private static D_Swerve instance = null;

	private static final double PIVOT_TO_FRONT_X = 12.75, // inches, pivot point to front wheel tip, x
			PIVOT_TO_FRONT_Y = 11.425, // inches, pivot point to front wheel tip, y
			PIVOT_TO_AFT_X = 12.75, // inches, pivot point to aft wheel tip, x
			PIVOT_TO_AFT_Y = 11.425;// inches, pivot point to aft wheel tip, y
	private static final double PIVOT_TO_FRONT = Math.hypot(PIVOT_TO_FRONT_X, PIVOT_TO_FRONT_Y),
			PIVOT_TO_AFT = Math.hypot(PIVOT_TO_AFT_X, PIVOT_TO_AFT_Y);

	private final SwerveModule moduleA, moduleB, moduleC, moduleD;
	private final SwerveModule[] modules;

	private double moduleD_maxSpeed = 70.0;// always put max slightly higher than max observed
	private double moduleD_previousAngle = 0.0;
	private double previousSpin = 0.0;
	private PIDController pid = new PIDController(0.008, 0.0, 0.0); 
	private Gyro gyro = Gyro.getInstance();
	private double direction = 0.0, speed = 0.0, spin = 0.0;

	private SwerveMode currentSwerveMode = SwerveMode.FIELD_CENTRIC;

	private D_Swerve() {
		moduleA = new SwerveModule(Parameters.ROTATION_MOTOR_A_ID, 0, true, Parameters.TRACTION_MOTOR_A_ID, false, 0);
		moduleB = new SwerveModule(Parameters.ROTATION_MOTOR_B_ID, 1, true, Parameters.TRACTION_MOTOR_B_ID, false, 0);
		moduleC = new SwerveModule(Parameters.ROTATION_MOTOR_C_ID, 2, true, Parameters.TRACTION_MOTOR_C_ID, false, 0);
		moduleD = new SwerveModule(Parameters.ROTATION_MOTOR_D_ID, 3, true, Parameters.TRACTION_MOTOR_D_ID, false, 0);

		this.modules = new SwerveModule[] { moduleA, moduleB, moduleC, moduleD };
	}

	public synchronized static D_Swerve getInstance() {
		if (instance == null) {
			instance = new D_Swerve();
		}
		return instance;

	}

	/**
	 * This function prepares each swerve module individually.
	 **/
	@Override
	public void init() {
		moduleA.init();
		moduleB.init();
		moduleC.init();
		moduleD.init();
	}

	public void resetEncoderPosition(){
		moduleA.resetEncoderValue();
		moduleB.resetEncoderValue();
		moduleC.resetEncoderValue();
		moduleD.resetEncoderValue();

	}

	public double getAverageIntegratedSensorPosition(){
		double encoderCountsA = Math.abs(moduleA.getIntegratedSensorENcoderCounts());
		double encoderCountsB = Math.abs(moduleB.getIntegratedSensorENcoderCounts());
		double encoderCountsC = Math.abs(moduleC.getIntegratedSensorENcoderCounts());
		double encoderCountsD = Math.abs(moduleD.getIntegratedSensorENcoderCounts());

		double averageEncoderCounts = (encoderCountsA + encoderCountsB + encoderCountsC + encoderCountsD)/4;
		double averageDistanceTraveled = ((averageEncoderCounts/2048)/8.3121)*12.56637;

		return averageDistanceTraveled;
	}

	public void getEncoderValues() {
		double encoderValueA = moduleA.getIntegratedSensorENcoderCounts();
		double encoderValueB = moduleB.getIntegratedSensorENcoderCounts();
		double encoderValueC = moduleC.getIntegratedSensorENcoderCounts();
		double encoderValueD = moduleD.getIntegratedSensorENcoderCounts();

		// SmartDashboard.putNumber("Swerve A Value", encoderValueA);
		// SmartDashboard.putNumber("Swerve B Value", encoderValueB);
		// SmartDashboard.putNumber("Swerve C Value", encoderValueC);
		// SmartDashboard.putNumber("Swerve D Value", encoderValueD);

	}
	public void getRPM() {
		double encoderVelocityA = moduleA.getRPM();
		double encoderVelocityB = moduleB.getRPM();
		double encoderVelocityC = moduleC.getRPM();
		double encoderVelocityD = moduleD.getRPM();
		
		SmartDashboard.putNumber("encoderVelocityA", encoderVelocityA);
		SmartDashboard.putNumber("encoderVelocityB", encoderVelocityB);
		SmartDashboard.putNumber("encoderVelocityC", encoderVelocityC);
		SmartDashboard.putNumber("encoderVelocityD", encoderVelocityD);

		
	}

	public void getCurrentSwerveAngles() {

		double encoderAngleA = moduleA.getAngle();
		double encoderAngleB = moduleB.getAngle();
		double encoderAngleC = moduleC.getAngle();
		double encoderAngleD = moduleD.getAngle();

		// SmartDashboard.putNumber("encoderAngleA", encoderAngleA);
		// SmartDashboard.putNumber("encoderAngleB", encoderAngleB);
		// SmartDashboard.putNumber("encoderAngleC", encoderAngleC);
		// SmartDashboard.putNumber("encoderAngleD", encoderAngleD);
	}

	


	private void holonomic_encoderIgnorant(final double direction, double speed, final double spin) {
		// {PREPARE VARIABLES}

		speed = Math.abs(speed);
		final double chassis_fieldAngle = Robot.gyroHeading;
		double forward;
		double strafe;
		if (getSwerveMode() == SwerveMode.ROBOT_CENTRIC) {
			forward = speed * Math.cos(Math.toRadians(direction));
			strafe = speed * Math.sin(Math.toRadians(direction));
		} else {
			forward = speed * Math.cos(Math.toRadians(SwerveModule.convertToRobot(direction, chassis_fieldAngle)));
			strafe = speed * Math.sin(Math.toRadians(SwerveModule.convertToRobot(direction, chassis_fieldAngle)));
		}

		final double[] comps_desired = computeComponents(strafe, forward, spin);
		final boolean bad = speed == 0.0 && spin == 0.0;
		// {CONTROL MOTORS, computing outputs as needed}
		if (!bad) {
			final double[] angles_final = computeAngles(comps_desired);
			for (int i = 0; i < 4; i++)
				modules[i].swivelTo(-angles_final[i]);// negative sign because when spining, the angles were flipped
			// for (int i = 0; i < 4; i++) modules[i].swivelTo(45);//control rotation if
			// driver input
		}

		if (!bad && isThere(0.0)) {
			final double[] speeds_final = computeSpeeds(comps_desired);
			// SmartDashboard.putNumber("speedCommand A", speeds_final[0]);
			// SmartDashboard.putNumber("speedCommandB", speeds_final[1]);
			// SmartDashboard.putNumber("speedCommandC", speeds_final[2]);
			// SmartDashboard.putNumber("speedCommandD", speeds_final[3]);
			for (int i = 0; i < 4; i++) {
				modules[i].set(speeds_final[i]);// control traction if good and there
			}
			// SmartDashboard.putNumber("Module A Input", speeds_final[0]);
			// SmartDashboard.putNumber("Module B Input", speeds_final[1]);
			// SmartDashboard.putNumber("Module C Input", speeds_final[2]);
			// SmartDashboard.putNumber("Module D Input", speeds_final[3]);

		} else
			stop();// otherwise, stop traction

		if (spin < 0.07)
			moduleD.checkTractionEncoder();
	}

	private double[] speedsFromModuleD() {
		double rawSpeed = moduleD.tractionSpeed() * moduleD.getDecapitated();
		if (Math.abs(rawSpeed) > moduleD_maxSpeed)
			moduleD_maxSpeed = Math.abs(rawSpeed);
		rawSpeed /= moduleD_maxSpeed;

		final double angle = Math.toRadians(moduleD_previousAngle);

		final double drivetrainX = /* linear */rawSpeed * Math.sin(angle)
				+ /* rotational */previousSpin * PIVOT_TO_AFT_Y / PIVOT_TO_AFT * Math.signum(rawSpeed);
		final double drivetrainY = /* linear */rawSpeed * Math.cos(angle)
				+ /* rotational */previousSpin * PIVOT_TO_AFT_X / PIVOT_TO_AFT * Math.signum(rawSpeed);

		return new double[] { drivetrainX, drivetrainY };
	}

	public void formX() {
		moduleA.swivelTo(45.0);
		moduleB.swivelTo(-45.0);
		moduleC.swivelTo(-45.0);
		moduleD.swivelTo(45.0);
	}


	public void faceTo(double direction) {
		double gyroHeading = Robot.gyroHeading;
		while(direction-gyroHeading > 180){
			gyroHeading += 360;
		}
		while(direction - gyroHeading < -180){
			gyroHeading -= 360;
		}
		double faceSpeed = pid.calculate(gyroHeading, direction);
		//faceSpeed = Math.abs(faceSpeed) <=0.4 *? faceSpeed: Math.signum(faceSpeed )*0.4;

		setSpin(faceSpeed);
		//SmartDashboard.putNumber("FaceTo", faceSpeed);
	}
	
	
	public boolean isThere(final double threshold) {
		return true;
		// return moduleA.isThere(threshold) && moduleB.isThere(threshold) &&
		// moduleC.isThere(threshold) && moduleD.isThere(threshold);
	}

	private void stop() {
		for (SwerveModule module : modules)
			module.set(0.0);
	}

	@Override
	public void completeLoopUpdate() {
		holonomic_encoderIgnorant(direction, speed, spin);
		//SmartDashboard.putNumber("holoCommand", spin);
		for (SwerveModule module : modules)
			module.completeLoopUpdate();
		// for (SwerveModule module : modules) module.swivelTo(0.0);
		//getRPM();
	}

	// -------------------------------------------------COMPUTATION
	// CODE------------------------------------------
	private static double[] computeComponents(final double speedX, final double speedY, final double speedSpin) {
		return new double[] { speedX + speedSpin * PIVOT_TO_FRONT_Y / PIVOT_TO_FRONT, // moduleAX
				speedY + speedSpin * PIVOT_TO_FRONT_X / PIVOT_TO_FRONT, // moduleAY
				speedX + speedSpin * PIVOT_TO_FRONT_Y / PIVOT_TO_FRONT, // moduleBX
				speedY - speedSpin * PIVOT_TO_FRONT_X / PIVOT_TO_FRONT, // moduleBY
				speedX - speedSpin * PIVOT_TO_AFT_Y / PIVOT_TO_AFT, // moduleCX
				speedY + speedSpin * PIVOT_TO_AFT_X / PIVOT_TO_AFT, // moduleCY
				speedX - speedSpin * PIVOT_TO_AFT_Y / PIVOT_TO_AFT, // moduleDX
				speedY - speedSpin * PIVOT_TO_AFT_X / PIVOT_TO_AFT// moduleDY
		};
	}

	private static double[] computeAngles(final double[] moduleComponents) {
		double[] angles = new double[4];
		for (int i = 0; i < 4; i++)
			angles[i] = Math.toDegrees(Math.atan2(moduleComponents[i * 2], moduleComponents[i * 2 + 1]));
			//SmartDashboard.putNumber("AngleCommand", angles[1]);
		return angles;
	}

	private static double[] computeSpeeds(final double[] moduleComponents) {
		// don't use for loop because of max divide
		final double speedA = Math.hypot(moduleComponents[0], moduleComponents[1]),
				speedB = Math.hypot(moduleComponents[2], moduleComponents[3]),
				speedC = Math.hypot(moduleComponents[4], moduleComponents[5]),
				speedD = Math.hypot(moduleComponents[6], moduleComponents[7]);
		double max = Math.max(speedA, Math.max(speedB, Math.max(speedC, speedD)));
		if (max < 1.0) {
			max = 1.0;
		}
		return new double[] { speedA / max, speedB / max, speedC / max, speedD / max };
	}

	public void setFieldCentric() {
		currentSwerveMode = SwerveMode.FIELD_CENTRIC;
	}

	public void setRobotCentric() {
		currentSwerveMode = SwerveMode.ROBOT_CENTRIC;
	}

	public SwerveMode getSwerveMode() {
		return currentSwerveMode;
	}

	public synchronized SwerveModule[] getSwerveModules() {
		return modules;
	}

	/**
	 * Outputs relevant information to the SmartDashboard.
	 */
	// public void outputToSmartDashboard() {
	// SmartDashboard.putNumber("moduleA Traction Temp (C)",
	// moduleA.getTractionMotor().getMotorTemperature());
	// SmartDashboard.putNumber("moduleB Traction Temp (C)",
	// moduleB.getTractionMotor().getMotorTemperature());
	// SmartDashboard.putNumber("moduleC Traction Temp (C)",
	// moduleC.getTractionMotor().getMotorTemperature());
	// SmartDashboard.putNumber("moduleD Traction Temp (C)",
	// moduleD.getTractionMotor().getMotorTemperature());
	// }

	public void setAllModulesToZero() {
		moduleA.swivelTo(0.0);
		moduleB.swivelTo(0.0);
		moduleC.swivelTo(0.0);
		moduleD.swivelTo(0.0);
	}

	// ------------------------------------------------CONFORMING
	// CODE----------------------------------------
	@Override
	public void setSpeed(final double speed) {
		this.speed = speed <= 1.0 ? speed : 1.0;
	}

	@Override
	public void setSpin(final double speed) {
		this.spin = Math.abs(speed) <= 1.0 ? speed : Math.signum(speed);
	}

	@Override
	public void travelTowards(final double heading) {
		this.direction = heading;
	}

	@Override
	public void correctFor(final double errorDirection, final double errorMagnitude) {
		travelTowards(errorDirection);

		//double speed = PID.get("leash", errorMagnitude);// DO NOT use I gain with this because errorMagnitude is always
														// positive
		if (speed > 0.6)
			speed = 0.6;

		setSpeed(speed);
	}

	@Override
	public double face(final double orientation, double maximumOutput) {
		final double error = Compass.path(Robot.gyroHeading, orientation);
		//final double spin = PID.get("spin", error);
		setSpin(Math.max(-maximumOutput, Math.min(spin, maximumOutput)));
		return error;
	}
}