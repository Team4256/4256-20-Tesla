package frc.robot;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;

public class Intake {
    private final WPI_TalonFX succMotor;

    public Intake(int deviceID) {
        succMotor = new WPI_TalonFX(33);
    }

    public void succ() {
        succMotor.set(.5);
    }
    public void spew(){
        succMotor.set(-0.5);
    }
}
