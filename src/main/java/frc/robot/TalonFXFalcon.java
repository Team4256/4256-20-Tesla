package frc.robot;

import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import com.revrobotics.CANEncoder;

import java.util.logging.Level;
import java.util.logging.Logger;
import edu.wpi.first.wpilibj.AnalogEncoder;
import edu.wpi.first.wpilibj.AnalogInput;

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
    private CANEncoder angleEcoder2;
    private boolean updated = false;
    private double lastSetpoint = 0.0;
    private Logger logger;
    private PIDController anglePIDController = new PIDController(.005, 0.0, 0.0);
    public final Compass compass = new Compass();
    private double lastLegalDirection = 1.0;
    private AnalogInput encoderPort;
    private AnalogEncoder angleEncoder;
    private double encoderMinVoltage;
    private double encoderMaxVoltage;
    private double encoderTareVoltage;
    double maxAngle = 0;
    double minAngle = 360;
    

    
    /**
     * Offers a simple way of initializing and using NEO Brushless motors with a
     * SparkMax motor controller.
     * 
     * @param deviceID    CAN ID of the SparkMax
     * @param neutralMode IdleMode (Coast or Brake)
     * @param isInverted  Indication of whether the SparkMax's motor is inverted
     */
    public TalonFXFalcon(final int deviceID, final boolean isInverted) {
        super(deviceID);
        idleMode = NeutralMode.Brake;
        this.deviceID = deviceID;
        this.isInverted = isInverted;
        logger = Logger.getLogger("SparkMax " + Integer.toString(deviceID));
    }

    public TalonFXFalcon(final int deviceID, final NeutralMode neutralMode, final boolean isInverted, int analogEncoderID) {
        super(deviceID);
        idleMode = NeutralMode.Brake;
        this.deviceID = deviceID;
        this.isInverted = isInverted;
        encoderMaxVoltage = Parameters.angleEncoderMaxVoltage[analogEncoderID];
        encoderMinVoltage = Parameters.angleEncoderMinVoltage[analogEncoderID];
        encoderTareVoltage = Parameters.angleEncoderTareVoltage[analogEncoderID];
        encoderPort = new AnalogInput(analogEncoderID);
        angleEncoder = new AnalogEncoder(encoderPort);
        angleEncoder.setDistancePerRotation(360);
        
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
    

    /**
     * Performs necessary initialization
     */
    public void init() {

        setInverted(isInverted);
        set(0.0);
        //anglePIDController.enableContinuousInput(0, 360.0);
    }

    /**
     * @return Counts of the motor
     */



    /**
     * @return Rotations of the motor
     */
    public double getPosition() {
        return angleEncoder.get();
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
        return 360 - (encoderPort.getVoltage() - encoderTareVoltage)/(encoderMaxVoltage - encoderMinVoltage) * 360;
    }
    public double getEncoderVoltage() {
        return encoderPort.getVoltage();
    }

    // Set Speed
    @Override
    public void setSpeed(final double speed) {
        super.set(speed);
        lastSetpoint = speed;
        updated = true;
        logger.log(Level.FINE, Double.toString(speed));
        
    }

    // Set Angle
    public void setAngle(double targetAngle) {

        int channelID = encoderPort.getChannel();
        double encoderPosition = getCurrentAngle();
        
        while (targetAngle <= -180) {
            targetAngle += 360;
        } 
        while (targetAngle > 180) {
            targetAngle -= 360;
        }

        double error = targetAngle - encoderPosition;
        
        while (targetAngle - encoderPosition > 180) {
            encoderPosition += 360;
        }
        
        while (targetAngle - encoderPosition < -180) {
            encoderPosition -= 360;
        }
        
        double percentSpeed = anglePIDController.calculate(encoderPosition, targetAngle);
        if (Math.abs(percentSpeed) > .5) {
            percentSpeed = Math.signum(percentSpeed) * .5;
        }
        super.set(percentSpeed);
        //SmartDashboard.putNumber("Percent Output Us", percentSpeed);
        updated = true;
        lastSetpoint = percentSpeed; 
        SmartDashboard.putNumber("errrereer", error);
        
    }

    public double getPIDError() {
        return anglePIDController.getPositionError();
    }

    public void resetEncoder() {
        getSensorCollection().setIntegratedSensorPosition(0.0, 10);
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
            super.set(0);
        }
        updated = false;
    }

    public void setParentLogger(final Logger logger) {
        this.logger = logger;
    }

    public void getCurrentAngle(double angle) {

    }

    

    
}
