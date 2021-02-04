package frc.robot;

import com.ctre.phoenix.motorcontrol.ControlMode;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
public class Intake {
    private final VictorSPX intakeMotor;
    private DoubleSolenoid intakeSolenoid;
    private static Intake instance = null;

    private Intake() {
        intakeMotor = new VictorSPX(Parameters.SUCCMOTOR_ID);
        intakeSolenoid = new DoubleSolenoid(Parameters.INTAKE_FORWARD_CHANNEL, Parameters.INTAKE_REVERSE_CHANNEL);
    }

    public synchronized static Intake getInstance() {
		if (instance == null) {
			instance = new Intake(); 
		}
			return instance;
		
    }
    
    public void succ() {
        intakeMotor.set(ControlMode.PercentOutput, -Parameters.INTAKE_SUCC_MOTOR );
    }
    public void spew(){
        intakeMotor.set(ControlMode.PercentOutput, Parameters.INTAKE_OUTAKE_SPEED);
    }
    public void stop() {
        intakeMotor.set(ControlMode.PercentOutput, 0.0);
    }
    
    public void intakeUp(){
        intakeSolenoid.set(Value.kForward);
    }
    public void intakeDown(){
        intakeSolenoid.set(Value.kReverse);
    }
    // public boolean isIntakeDown(){
    //     if (intakeSolenoid.get(intakeSolenoid.Value) == Value.kForward) {
    //         return true;
    //     } else {
    //         return false;
    //     }
    // }


}