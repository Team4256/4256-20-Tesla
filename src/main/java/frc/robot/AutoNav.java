package frc.robot;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class AutoNav extends Autopilot {
    private D_Swerve swerve;
    int currentPhase = 0;
    AutoPhases[] phases = { new AutoPhases(.1, 0, 0, 140), new AutoPhases(.1, 90, 0, 62),
            new AutoPhases(.1, 180, 0, 62), new AutoPhases(.1, 270, 0, 62) };

    AutoNav() {
        super();
        swerve = D_Swerve.getInstance();
        swerve.resetEncoderPosition();
    }

    public void barrelPath() {
        SmartDashboard.putNumber("currentBarrelState", currentPhase);
        if (currentPhase > 3) {
            return;
        }
        
        if ( swerve.getAverageIntegratedSensorPosition() > phases[currentPhase].distance) {
            currentPhase++;
            
            if (currentPhase > 3) {
                return;
            }
            swerve.resetEncoderPosition();

        }
        speed = phases[currentPhase].speed;
        travelDirection = phases[currentPhase].direction;
        faceDirection = phases[currentPhase].face;

        
    }

}
