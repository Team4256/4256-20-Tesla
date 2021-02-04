package frc.robot;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.ctre.phoenix.motorcontrol.TalonFXControlMode;
import com.revrobotics.CANSparkMax.IdleMode;
import com.ctre.phoenix.motorcontrol.TalonFXFeedbackDevice;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class MotorFactory {

    public static enum MotorType {
        TALON, NEO
    }

    public static MotorType tractionMotorType = MotorType.TALON;
    public static MotorType rotationMotorType = MotorType.TALON;

    public static Motor createTractionMotor(int deviceID) {
        if (tractionMotorType == MotorType.TALON) {
            
            TalonFXFalcon falconMotor = new TalonFXFalcon(deviceID, false);
            //config sensor for PID(Integrated Sensor)
            falconMotor.configSelectedFeedbackSensor(TalonFXFeedbackDevice.IntegratedSensor, Parameters.TRACTION_PID_ID, Parameters.TRACTION_MOTOR_TIMEOUTMS);
            //                    ID                          P                            TimeoutMs
            falconMotor.config_kP(Parameters.TRACTION_PID_ID, Parameters.TRACTION_MOTORkP, Parameters.TRACTION_MOTOR_TIMEOUTMS);
            //                    ID                          P                            TimeoutMs
            falconMotor.config_kF(Parameters.TRACTION_PID_ID, Parameters.TRACTION_MOTORkF, Parameters.TRACTION_MOTOR_TIMEOUTMS);
            //                    ID                          D                            TimeoutMs
            falconMotor.config_kD(Parameters.TRACTION_PID_ID, Parameters.TRACTION_MOTORkD, Parameters.TRACTION_MOTOR_TIMEOUTMS);
            return falconMotor;

        } else if (tractionMotorType == MotorType.NEO) { 
                return new SparkMaxNeo(deviceID, IdleMode.kBrake, false);

        }
        return null;
        
 }
    public static Motor createRotationMotor(int deviceID, int analogEncoderID) {
        if (rotationMotorType == MotorType.TALON) {
            boolean isDevIDLegal = deviceID > 14;
            
           // SmartDashboard.putBoolean("isDevIDLegal", isDevIDLegal);
            return new TalonFXFalcon(deviceID, NeutralMode.Brake, true, analogEncoderID);

        } else if (tractionMotorType == MotorType.NEO) {
            return new SparkMaxNeo(deviceID, IdleMode.kCoast, false);
        }
        return null;
    }

    




}