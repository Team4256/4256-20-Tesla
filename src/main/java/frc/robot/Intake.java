package frc.robot;

import com.ctre.phoenix.motorcontrol.ControlMode;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
public class Intake {
    private final VictorSPX intakeMotor;
    private DoubleSolenoid intakeSolenoid;

    public Intake() {
        intakeMotor = new VictorSPX(Parameters.INTAKE_MOTOR_ID);
        intakeSolenoid = new DoubleSolenoid(Parameters.INTAKE_FORWARD_CHANNEL, Parameters.INTAKE_REVERSE_CHANNEL);
    }
    public void succ() {
        intakeMotor.set(ControlMode.PercentOutput, Parameters.INTAKE_REVERSE_CHANNEL);
    }
    public void spew(){
        intakeMotor.set(ControlMode.PercentOutput, -Parameters.INTAKE_MOTOR_SPEED);
    }
    public void stop() {
        intakeMotor.set(ControlMode.PercentOutput, 0.0);
    }
    public void intakeUp(){
        intakeSolenoid.set(Value.kReverse);
    }
    public void intakeDown(){
        intakeSolenoid.set(Value.kForward);
    }
}