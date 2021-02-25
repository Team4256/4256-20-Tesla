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
        
        if (stopWatch.getElapsedTime() > 10) {
            swerve.setSpeed(0);
        } else {
            swerve.setSpeed(.1);
            swerve.setSpin(aligner.setSwervePIDOn(0));
            swerve.travelTowards(0);
        }
        // if (phase1 == PhaseStates.NOT_STARTED) {
        //     phase1 = PhaseStates.STARTED;
        //     intake.intakeDown();
        // }
        // if (phase1 == PhaseStates.STARTED) {
        //     if (intake.isIntakeDown()){
        //         intake.succ();
        //         phase1 = PhaseStates.ENDED;
        //     }
        // }
        // if (phase2 == PhaseStates.NOT_STARTED) {
        //     phase2 = PhaseStates.STARTED;
        //     swerve.faceTo(26.57);
        //     gyro.reset();
        // }
        // if (phase2 == PhaseStates.STARTED) {
        //     stopWatch.resetTimer();
        //     swerve.setSpeed(.1);
        //     if(stopWatch.getElapsedTime() > 2) {
        //         swerve.setSpeed(0);
        //         phase3 = PhaseStates.ENDED;
        //     }
        // }
        // if (phase3 == PhaseStates.NOT_STARTED) {
        //     phase3 = PhaseStates.STARTED;
        // }
        // if (phase3 == PhaseStates.STARTED) {
        //    phase3 = PhaseStates.ENDED;
        // }


    }
        
}