package frc.robot;

public class Parameters{

    // Swerve Motors
    public static final int ROTATION_MOTOR_A_ID = 50; //Front Left
    public static final int ROTATION_MOTOR_B_ID = 12; //Front Right
    public static final int ROTATION_MOTOR_C_ID = 13; // AFT Left
    public static final int ROTATION_MOTOR_D_ID = 14; // AFT Right

    public static final int TRACTION_MOTOR_A_ID = 51; //Front Left
    public static final int TRACTION_MOTOR_B_ID = 22; //Front Right
    public static final int TRACTION_MOTOR_C_ID = 23; // AFT Left
    public static final int TRACTION_MOTOR_D_ID = 24; // AFT Right
   // Color Wheel
    public static final int COLOR_WHEEL_MOTOR_ID = 15; // Main
    public static final int COLOR_WHEEL_UP_SOLENOID_ID = 4; //EXTEND
    public static final int COLOR_WHEEL_DOWN_SOLENOID_ID = 5; // RETRACT

    //soloniod motor stuff in competition up will be 2 down will be 3
    public static final int ClimberForwardChannel = 0; //solenoid port Extend 
    public static final int ClimberReverseChannel = 1; //solenoid port Retract
    //public static final int A = 69420; Nice ;)
// add motors for arm going up and down
    public static final int R_CLIMBER_MOTOR_ID = 29; //Climber Motor right
    public static final int L_CLIMBER_MOTOR_ID = 28; //Climber Motor RIGHT

    public static final int SuccMotor_ID = 33; //Intake Motor
    public static final int ShooterMotor_1_ID = 0;
    public static final int ShooterMoror_2_ID = 0;
    public static final int HopperMotor_ID = 0;
    public static final int FeederMotor_ID = 0;
    public static final int ShroudForwardChannel = 2;//TODO get the real ID
    public static final int ShroudReverseChannel = 3;//TODO get the real ID

}