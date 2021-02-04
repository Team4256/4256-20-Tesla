// public class GalacticSearchAuto {
   
//     private D_Swerve swerve;
//     private Gyro gyro;
//     private Intake intake;
//     private boolean stageOneFinished;
//     private boolean stageTwoFinished;
//     private boolean stageThreeFinished;
//     private boolean stageFourFinished;
//     private double elapsedTime;
//     private StopWatch stopWatch = StopWatch.getInstance();
//     private Intake intakae = Intake.getInstance();

//     public GalacticSearchAuto(){
//         swerve = D_Swerve.getInstance();
//         gyro = Gyro.getInstance();
//         intake = Intake.getInstance();
    
//         }

//         public void autoInit(){
//             gyro.reset();
//             swerve.resetEncoderPosition();
//             stageOneFinished = false;
//             stageTwoFinished = false;
//             stageThreeFinished = false;
//             stageFourFinished = false;
//             elapsedTime = 0.0;
//           }

//           public static enum PhaseStates {
//             NOT_STARTED, STARTED, ENDED
//           }

//         private PhaseStates phase1 = PhaseStates.NOT_STARTED;
//         private PhaseStates phase2 = PhaseStates.NOT_STARTED;
//         private PhaseStates phase3 = PhaseStates.NOT_STARTED;
//         private PhaseStates phase4 = PhaseStates.NOT_STARTED;
//         private PhaseStates phase5 = PhaseStates.NOT_STARTED;
      
//     // Start at B1
//     public void RedRun() {
//         double distanceTravelled = Math.abs(swerve.getAverageIntegratedSensorPosition());
//         SmartDashboard.putNumber(distanceTravelled, 0);
//         if (phase1 == PhaseStates.NOT_STARTED) {
//             phase1 = PhaseStates.STARTED;
//             intake.intakeDown();
//         }
//         if (phase1 == PhaseStates.STARTED) {
//             if (intakeSolenoid.get(intakeSolenoid.Value) == Value.kForward){
//                 intake.succ();
//                 phase1 = PhaseStates.ENDED;
//             }
//         }
//         if (phase2 == PhaseStates.NOT_STARTED) {
//             swerve.faceTo(26.57);
//             gyro.reset();
//         }
//         if (phase3 == PhaseStates.NOT_STARTED) {
//             phase3 = PhaseStates.STARTED;
//         }
//         if (phase3 == PhaseStates.STARTED) {

//         }


//     }
        
// }