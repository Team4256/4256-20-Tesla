package frc.robot;

import edu.wpi.first.wpilibj.DoubleSolenoid;

public class ColorWheelPrep {
    private DoubleSolenoid CSolenoid;
    public ColorWheelPrep (int ForwardChannel, int ReverseChannel) {
        CSolenoid = new DoubleSolenoid(ForwardChannel, ReverseChannel); 
    }
        public void wheelUpCW(){
            CSolenoid.set(DoubleSolenoid.Value.kReverse);
        }
        public void wheelDownCW() {
            CSolenoid.set(DoubleSolenoid.Value.kForward);
        }
    
    
}

