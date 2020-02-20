package frc.robot;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;

public class Intake {
    private final TalonSRX succMotor;
    private DoubleSolenoid SuccerSolenoid;

    public Intake() {
        succMotor = new TalonSRX(Parameters.SUCCMOTOR_ID);
        SuccerSolenoid = new DoubleSolenoid(Parameters.INTAKE_FORWARD_CHANNEL, Parameters.INTAKE_REVERSE_CHANNEL);
    }
    public void succ() {
        succMotor.set(ControlMode.PercentOutput, Parameters.INTAKE_REVERSE_CHANNEL);
    }
    public void spew(){
        succMotor.set(ControlMode.PercentOutput, -Parameters.INTAKE_MOTOR_SPEED);
    }
    public void stop() {
        succMotor.set(ControlMode.PercentOutput, 0.0);
    }
    public void succerUp(){
        SuccerSolenoid.set(Value.kReverse);
    }
    public void succerDown(){
        SuccerSolenoid.set(Value.kForward);
    }
}