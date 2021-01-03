package frc.robot;

import com.ctre.phoenix.motorcontrol.ControlMode;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

public class Intake {
    private final VictorSPX intakeMotor;
    private DoubleSolenoid intakeSolenoid;
    private static Intake instance = null;

    // Constructor
    private Intake() {
        intakeMotor = new VictorSPX(Parameters.SUCCMOTOR_ID);
        intakeSolenoid = new DoubleSolenoid(Parameters.INTAKE_FORWARD_CHANNEL, Parameters.INTAKE_REVERSE_CHANNEL);
    }

    // Get instance method for initializing
    public synchronized static Intake getInstance() {
        if (instance == null) {
            instance = new Intake();
        }
        return instance;
    }

    // Set intake motors to take a ball in
    public void succ() {
        intakeMotor.set(ControlMode.PercentOutput, -Parameters.INTAKE_SUCC_MOTOR);
    }

    // Set intake motors to eject a ball from the intake
    public void spew() {
        intakeMotor.set(ControlMode.PercentOutput, Parameters.INTAKE_OUTAKE_SPEED);
    }

    // Stop intake motors
    public void stop() {
        intakeMotor.set(ControlMode.PercentOutput, 0.0);
    }

    // Move intake to up position
    public void intakeUp() {
        intakeSolenoid.set(Value.kForward);
    }

    // Move intake to down position
    public void intakeDown() {
        intakeSolenoid.set(Value.kReverse);
    }
}