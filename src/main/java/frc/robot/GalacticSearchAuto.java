package frc.robot;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class GalacticSearchAuto {
   
    private D_Swerve swerve;
    private Gyro gyro;
    private boolean stageOneFinished;
    private boolean stageTwoFinished;
    private boolean stageThreeFinished;
    private boolean stageFourFinished;
    private double elapsedTime;
    private StopWatch stopWatch = StopWatch.getInstance();
    private Intake intake = Intake.getInstance();
    private Aligner aligner = Aligner.getInstance();

    public GalacticSearchAuto(){
        swerve = D_Swerve.getInstance();
        gyro = Gyro.getInstance();
        intake = Intake.getInstance();
    
        }

        public void autoInit(){
            gyro.reset();
            swerve.resetEncoderPosition();
            stopWatch.resetTimer();
            stageOneFinished = false;
            stageTwoFinished = false;
            stageThreeFinished = false;
            stageFourFinished = false;
            elapsedTime = 0.0;
          }

          public static enum PhaseStates {
            NOT_STARTED, STARTED, ENDED
          }

        private PhaseStates phase1 = PhaseStates.NOT_STARTED;
        private PhaseStates phase2 = PhaseStates.NOT_STARTED;
        private PhaseStates phase3 = PhaseStates.NOT_STARTED;
        private PhaseStates phase4 = PhaseStates.NOT_STARTED;
        private PhaseStates phase5 = PhaseStates.NOT_STARTED;
      
    // Start at B1
    public void RedRun() {
        double distanceTravelled = Math.abs(swerve.getAverageIntegratedSensorPosition());
        SmartDashboard.putNumber("AutoDistanceTravelled", distanceTravelled);
        
        SmartDashboard.putNumber("autoTimeElapsed", stopWatch.getElapsedTime());
        
        // if (distanceTravelled > 90) {
        //     swerve.setSpeed(0);
        // } else {
        //     swerve.setSpeed(.1);
        //     swerve.setSpin(aligner.setSwervePIDOn(0));
        //     swerve.travelTowards(0);
        // }
        if (phase1 == PhaseStates.NOT_STARTED) {
            phase1 = PhaseStates.STARTED;
            intake.intakeDown();
        }
        if (phase1 == PhaseStates.STARTED) {
            SmartDashboard.putNumber("CurrentPhase", 1);
            swerve.faceTo(180 + 26.57);
            if (stopWatch.getElapsedTime() > 3) {
                intake.succ();
                phase1 = PhaseStates.ENDED;
                swerve.resetEncoderPosition();
            } 
            else {
                return;
            }
        } 
        if (phase2 == PhaseStates.NOT_STARTED) {
            
            //swerve.faceTo(153.43);
            phase2 = PhaseStates.STARTED;
                        //gyro.reset();
        }
        if (phase2 == PhaseStates.STARTED) {
            SmartDashboard.putNumber("CurrentPhase", 2);
            stopWatch.resetTimer();
            swerve.setSpeed(.1);
            swerve.travelTowards(26.57);
            if(distanceTravelled > 90) {
                swerve.setSpeed(0);
                phase2 = PhaseStates.ENDED;
            } else {
                return;
            }
        }
        if (phase3 == PhaseStates.NOT_STARTED) {
            phase3 = PhaseStates.STARTED;
        }
        if (phase3 == PhaseStates.STARTED) {
            SmartDashboard.putNumber("CurrentPhase", 3);
           phase3 = PhaseStates.ENDED;
        } else {
            return;
        }


    }
        
}