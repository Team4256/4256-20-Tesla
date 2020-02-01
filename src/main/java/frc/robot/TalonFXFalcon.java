package frc.robot;

import edu.wpi.first.wpilibj.AnalogEncoder;
import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * SparkMax Motor Controller Used With a Neo Brushless Motor.
 * <p>
 * <i>Do not attempt to use followers with this class as it is not intended to
 * be used in such a way and may cause errors.</i>
 * 
 * @author Ian Woodard
 */
public class TalonFXFalcon extends WPI_TalonFX implements Motor {

    private final int deviceID;
    public final NeutralMode idleMode;
    private final boolean isInverted;
    private final double encoder;
    private boolean updated = false;
    private double lastSetpoint = 0.0;
    private Logger logger;
    private PIDController anglePIDController = new PIDController(.00278, 0.0, 0.00);
    public final Compass compass = new Compass();
    private double lastLegalDirection = 1.0;
    private AnalogInput encoderport = new AnalogInput(0); // TODO get the encoder port
    private AnalogEncoder angleEncoder = new AnalogEncoder(encoderport);

    /**
     * Offers a simple way of initializing and using NEO Brushless motors with a
     * SparkMax motor controller.
     * 
     * @param deviceID    CAN ID of the SparkMax
     * @param neutralMode IdleMode (Coast or Brake)
     * @param isInverted  Indication of whether the SparkMax's motor is inverted
     */
    public TalonFXFalcon(final int deviceID, final NeutralMode neutralMode, final boolean isInverted) {
        super(deviceID);
        encoder = getSensorCollection().getIntegratedSensorPosition();
        idleMode = NeutralMode.Coast;
        this.deviceID = deviceID;
        this.isInverted = isInverted;
        logger = Logger.getLogger("SparkMax " + Integer.toString(deviceID));

    }

    /**
     * Offers a simple way of initializing and using NEO Brushless motors with a
     * SparkMax motor controller.
     * <p>
     * This constructor is for NEO Brushless motors set by default to coast
     * <code>IdleMode</code>.
     * 
     * @param deviceID   CAN ID of the SparkMax
     * @param isInverted Indication of whether the SparkMax's motor is inverted
     */
    public TalonFXFalcon(final int deviceID, final boolean isInverted) {
        this(deviceID, NeutralMode.Coast, isInverted);
    }

    /**
     * Performs necessary initialization
     */
    public void init() {

        setInverted(isInverted);
        set(0.0);
        anglePIDController.enableContinuousInput(-180.0, 180.0);
    }

    /**
     * @return Counts of the motor
     */



    /**
     * @return Rotations of the motor
     */
    public double getPosition() {
        return getSensorCollection().getIntegratedSensorPosition();
    }

    /**
     * @return Revolutions per minute of the motor
     */
    public double getRPM() {
        return getSensorCollection().getIntegratedSensorVelocity();
    }

    /**
     * @return Revolutions per second of the motor
     */
    public double getRPS() {
        return (getRPM() / 60.0);
    }

    // get angle
    public double getCurrentAngle() {
       // return Math.toDegrees(getSensorCollection().getIntegratedSensorPosition());
       return angleEncoder.get()*360;
    }

    // Set Speed
    @Override
    public void set(final double speed) {
        super.set(speed);
        lastSetpoint = speed;
        updated = true;
        logger.log(Level.FINE, Double.toString(speed));
    }

    // Set Angle
    public void setAngle(double targetAngle) {

        double encoderPosition = getCurrentAngle();
        while (encoderPosition > 180) {
            encoderPosition -= 360;
        }
        while (encoderPosition < -180) {
            encoderPosition += 360;
        }
        SmartDashboard.putNumber("encoder position", encoderPosition);

        if (Math.abs(targetAngle - encoderPosition) < 2) {
            super.set(0.);
            SmartDashboard.putNumber("Percent Output", 0.);
            return;
        }

        double percentSpeed = anglePIDController.calculate(encoderPosition, targetAngle);

        if (Math.abs(percentSpeed) > .5) {
            percentSpeed = Math.signum(percentSpeed) * .5;
        } else if (Math.abs(percentSpeed) < .01) {
            percentSpeed = Math.signum(percentSpeed) * .01;
        }

        super.set(percentSpeed);
        SmartDashboard.putNumber("Percent Output", percentSpeed);
    }

    public double pathTo(double target) {// ANGLE
        final double current = getCurrentAngle();
        double path = compass.legalPath(current, target);
        if (current == compass.legalize(current))
            lastLegalDirection = Math.signum(path);
        else if (Math.signum(path) != -lastLegalDirection)
            path -= Math.copySign(360, path);

        return path;
    }

    public void completeLoopUpdate() {
        if (!updated) {
            super.set(lastSetpoint);
        }
        updated = false;
    }

    public void setParentLogger(final Logger logger) {
        this.logger = logger;
    }

    public void getCurrentAngle(double angle) {

    }

    @Override
    public void setSpeed(double speed) {
        // TODO Auto-generated method stub

    }

    
}