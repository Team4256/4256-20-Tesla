package frc.robot;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.revrobotics.CANSparkMax.IdleMode;

public class MotorFactory {

    public static enum MotorType {
        TALON, NEO
    }

    public static MotorType tractionMotorType = MotorType.TALON;
    public static MotorType rotationMotorType = MotorType.TALON;

    public static Motor createTractionMotor(int deviceID) {
        if (tractionMotorType == MotorType.TALON) {
            return new TalonFXFalcon(deviceID, NeutralMode.Coast, false);

        } else if (tractionMotorType == MotorType.NEO) {
                return new SparkMaxNeo(deviceID, IdleMode.kCoast, false);

        }
        return null;
        
 }
    public static Motor createRotationMotor(int deviceID) {
        if (rotationMotorType == MotorType.TALON) {
            return new TalonFXFalcon(deviceID, NeutralMode.Coast, false);

        } else if (tractionMotorType == MotorType.NEO) {
            return new SparkMaxNeo(deviceID, IdleMode.kCoast, false);
        }
        return null;
    }

    




}