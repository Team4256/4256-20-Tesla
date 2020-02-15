package frc.robot;
import edu.wpi.first.wpilibj.util.Color;
import com.revrobotics.ColorMatch;

public class Parameters{

    // Swerve Motors
    public static final int ROTATION_MOTOR_A_ID = 11; //Front Left
    public static final int ROTATION_MOTOR_B_ID = 12; //Front Right
    public static final int ROTATION_MOTOR_C_ID = 13; // AFT Left
    public static final int ROTATION_MOTOR_D_ID = 14; // AFT Right

    public static final int TRACTION_MOTOR_A_ID = 21; //Front Left
    public static final int TRACTION_MOTOR_B_ID = 22; //Front Right
    public static final int TRACTION_MOTOR_C_ID = 23; // AFT Left
    public static final int TRACTION_MOTOR_D_ID = 24; // AFT Right


   // Control panel
    public static final int WHEEL_ARM_MOTOR_ID = 15; // Main
    public static final int WHEEL_ARM_UP_SOLENOID_CHANNEL = 4; //EXTEND
    public static final int WHEEL_ARM_DOWN_SOLENOID_CHANNEL = 5; // RETRACT
    public static final double COLOR_MOTOR_SPEED = 0.5;
    public static final int TARGET_WEDGE_COUNT = 3*8+4; // 3 REVOLUTIONS, 8 WEDGES PER REVOLUTION, + 4 EXTRA WEDGES

    //Color Sensor colors
    public static final Color myBlue = ColorMatch.makeColor(0.128174, 0.431152, 0.440674);
    public static final Color myGreen = ColorMatch.makeColor(0.172363, 0.579834, 0.247803);
    public static final Color myRed = ColorMatch.makeColor(0.513428, 0.351807, 0.134766);
    public static final Color myYellow = ColorMatch.makeColor(0.320557, 0.556641, 0.122803);
    

    //Climber
    //soloniod motor stuff in competition up will be 2 down will be 3
    public static final int ClimberForwardChannel = 0; //solenoid port Extend 
    public static final int ClimberReverseChannel = 1; //solenoid port Retract
    //public static final int A = 69420; Nice ;)
    public static final int R_CLIMBER_MOTOR_ID = 32; //Climber Motor right
    public static final int L_CLIMBER_MOTOR_ID = 28; //Climber Motor RIGHT
    //need actual encoder values, from engineering team
    public static double MED_HEIGHT_COUNT = 2000;
    public static double MAX_HEIGHT_COUNT = 4200;//NICE
    public static final double CLIMBER_MOTOR_SPEED_DPAD = 0.5; //need the actual speed. This is for the Dpad when both poles retract at the same time
    public static final double CLIMBER_MOTOR_SPEED_INDIVIDUAL = 0.5;//This is for when the joysticks are controlling individual motors


    //intake and shooter motors IDs
    public static final int SuccMotor_ID = 33; //Intake Motor
    public static final int ShooterMotor_1_ID = 0;
    public static final int ShooterMoror_2_ID = 0;
    public static final int HopperMotor_ID = 0;
    public static final int FeederMotor_ID = 0;
    public static final int ShroudForwardChannel = 2;//TODO get the real ID
    public static final int ShroudReverseChannel = 3;//TODO get the real ID

   
    
    
}