package frc.robot;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class AutoNav extends Autopilot {
    private D_Swerve swerve;
    private StopWatch stopWatch = StopWatch.getInstance();
    int currentPhase = 0;
    double maxEncoder = 0;
    AutoPhases[] phases = { 
            new AutoPhases(.6, 0, 0, 110)};//, new AutoPhases(.6, 90, 0, 50), new AutoPhases(.6, 180, 0, 60), new AutoPhases(.6, 270, 0, 60),

            //new AutoPhases(.6, 0, 0, 160), new AutoPhases(.6, -90, 0, 60), new AutoPhases(.6, 180, 0, 62), new AutoPhases(.6, 90, 0, 62) };

    AutoNav() {
        super();
        swerve = D_Swerve.getInstance();
        swerve.resetEncoderPosition();
    }

    public void barrelPath() {
        SmartDashboard.putNumber("currentBarrelState", currentPhase);
        SmartDashboard.putNumber("checkEncoderB4Reset", swerve.getAverageIntegratedSensorPosition());
        maxEncoder = Math.max(maxEncoder, swerve.getAverageIntegratedSensorPosition());
        SmartDashboard.putNumber("maxEncoder", maxEncoder);

        if (stopWatch.getElapsedTime() < .25) {
            return;
        }
        if (currentPhase > phases.length) {
            speed = 0;
            return;
        }

        if (swerve.getAverageIntegratedSensorPosition() > phases[currentPhase].distance) {
            currentPhase++;
            speed = 0;
            stopWatch.resetTimer();
            if (currentPhase > phases.length) {
                speed = 0;
                return;

            }
            //swerve.resetEncoderPosition();
            SmartDashboard.putNumber("checkEncoderReset", swerve.getAverageIntegratedSensorPosition());
            return;
        }

        //SmartDashboard.putNumber("checkEncoderB4Reset", swerve.getAverageIntegratedSensorPosition());
        speed = Math.min(phases[currentPhase].speed, .1 + (phases[currentPhase].distance - swerve.getAverageIntegratedSensorPosition()) * .004);
        
        travelDirection = phases[currentPhase].direction;
        faceDirection = phases[currentPhase].face;

    }
}
