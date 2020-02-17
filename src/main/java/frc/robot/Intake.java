package frc.robot;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;

public class Intake {
    private final TalonSRX succMotor;
    private DoubleSolenoid SuccerSolenoid;

    public Intake(int succMotorID, int IntakeUpChannel, int IntakeDownChannel) {
        succMotor = new TalonSRX(succMotorID);
        SuccerSolenoid = new DoubleSolenoid(IntakeUpChannel, IntakeDownChannel);
    }
    public void succ() {
        succMotor.set(ControlMode.PercentOutput, Parameters.intakeSpeed);
    }
    public void spew(){
        succMotor.set(ControlMode.PercentOutput, -Parameters.intakeSpeed);
    }
    public void stop() {
        succMotor.set(ControlMode.PercentOutput, Parameters.stopSpeed);
    }
    public void succerUp(){
        SuccerSolenoid.set(Value.kReverse);
    }
    public void succerDown(){
        SuccerSolenoid.set(Value.kForward);
    }
}