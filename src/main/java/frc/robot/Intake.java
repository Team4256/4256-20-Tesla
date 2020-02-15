package frc.robot;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;

public class Intake {
    private double intakeSpeed = 0.5;
    private double stopSpeed = 0.0;
    private final TalonSRX succMotor;

    public Intake() {
        succMotor = new TalonSRX(Parameters.SUCCMOTOR_ID);
    }

    public void succ() {
        succMotor.set(ControlMode.PercentOutput, intakeSpeed);
    }
    public void spew(){
        succMotor.set(ControlMode.PercentOutput, -intakeSpeed);
    }
    public void stop() {
        succMotor.set(ControlMode.PercentOutput, stopSpeed);
    }
}
