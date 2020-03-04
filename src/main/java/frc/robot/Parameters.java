package frc.robot;
import edu.wpi.first.wpilibj.util.Color;
import com.revrobotics.ColorMatch;

public class Parameters{
    // Gyro
    public static final byte GYRO_UPDATE_HZ = 50;
    public static final double GYRO_OFFSET = 0;

    //Automomous
    public static final double AUTO_SWERVE_TRACTION_SPEED = 0.2; //TODO get the actual speed
    public static final double FALCON_PERCENT_TO_ENCODER_SPEED = 18000; //TODO get the actual speed
    public static final double CROSS_WHITE_LINE_DISTANCE_IN_INCHES = 120; //TODO get the actual DISTANCE
    public static final double DRIVE_TO_TRENCH_DISTANCE_IN_INCHES = 186; //TODO get the actual DISTANCE
    public static final double MOVE_BACK_DISTANCE_IN_INCHES = 20; //TODO get the actual DISTANCE mode 4 only after crossing the white line
    public static final double STARTING_DISTANCE_FROM_RIGHT_IN_INCHES = 8; //TODO get the actual DISTANCE
    //public static final double STARTING_DISTANCE_FROM_MIDDLE_IN_INCHES = 5; //TODO get the actual DISTANCE
    //public static final double STARTING_DISTANCE_FROM_LEFT_IN_INCHES = 0; //TODO get the actual DISTANCE

    


    // Swerve Motors
    public static final int ROTATION_MOTOR_A_ID = 11; //Front Left
    public static final int ROTATION_MOTOR_B_ID = 12; //Front Right
    public static final int ROTATION_MOTOR_C_ID = 13; // AFT Left
    public static final int ROTATION_MOTOR_D_ID = 14; // AFT Right

    public static final int TRACTION_MOTOR_A_ID = 21; //Front Left
    public static final int TRACTION_MOTOR_B_ID = 22; //Front Right
    public static final int TRACTION_MOTOR_C_ID = 23; // AFT Left
    public static final int TRACTION_MOTOR_D_ID = 24; // AFT Right
   // Color Wheel
    public static final int WHEEL_ARM_MOTOR_ID = 15; // Main
    public static final int WHEEL_ARM_UP_SOLENOID_CHANNEL = 4; //EXTEND
    public static final int WHEEL_ARM_DOWN_SOLENOID_CHANNEL = 5; // RETRACT
    public static final double COLOR_MOTOR_SPEED = 0.5;
    public static final int TARGET_WEDGE_COUNT = 3*8+4; // 3 REVOLUTIONS, 8 WEDGES PER REVOLUTION, + 4 EXTRA WEDGES

    //Color Sensor
    public static final Color myBlue = ColorMatch.makeColor(0.128174, 0.431152, 0.440674);
    public static final Color myGreen = ColorMatch.makeColor(0.172363, 0.579834, 0.247803);
    public static final Color myRed = ColorMatch.makeColor(0.513428, 0.351807, 0.134766);
    public static final Color myYellow = ColorMatch.makeColor(0.320557, 0.556641, 0.122803);

    //soloniod motor stuff in competition up will be 2 down will be 3
    public static final int CLIMBER_FORWARD_CHANNEL = 6; //solenoid port Extend 
    public static final int CLIMBER_REVERSE_CHANNEL = 7; //solenoid port Retract
    public static final double CLIMBING_SPEED_LOW = 0.3;
    // add motors for arm going up and down
    public static final int R_CLIMBER_MOTOR_ID = 32; //Climber Motor right
    public static final int L_CLIMBER_MOTOR_ID = 28; //Climber Motor RIGHT
    //need actual encoder values, from engineering team
    public static double MED_HEIGHT_COUNT = 2000;
    public static double MAX_HEIGHT_COUNT = 4200;//NICE
    public static final double CLIMBER_MOTOR_SPEED_DPAD = 0.5; //need the actual speed. This is for the Dpad when both poles retract at the same time
    public static final double CLIMBER_MOTOR_SPEED_INDIVIDUAL = 0.5;//This is for when the joysticks are controlling individual motors

    //INTAKE
    public static final int SUCCMOTOR_ID = 16; //Intake Motor
    public static final int INTAKE_FORWARD_CHANNEL = 0;//TODO need to get the real channel ID
    public static final double INTAKE_SUCC_MOTOR = 0.5;//TODO need to get the actual channel ID
    public static final double INTAKE_MOTOR_SPEED = 0.3;
    public static final int INTAKE_REVERSE_CHANNEL = 1; //TODO get the actual intake motor speed 

    //shooter
    public static final int SHOOTERMOTOR_L_ID = 26;
    public static final int SHOOTERMOTOR_R_ID = 27;
    public static final int STIRRERMOTOR_ID = 18;
    public static final int FEEDERMOTOR_ID = 17;
    public static final int SHROUD_DOWN_CHANNEL = 3;
    public static final int SHROUD_UP_CHANNEL = 2;
    public static double SHOOTER_MOTOR_SPEED = -1.0;
    public static double FEEDER_STIRRER_MOTOR_SPEED = -0.5; 
    public static double STIRRER_MOTOR_SPEED = 0.3;
    public static double STIRRER_MOTOR_DELAY = 0.75;
    //public static double MOTORSPEEDHIGH = 0.3;// we don't know yet what the engineers are going to do
    public static double DISTANCE_LOW_MIN = 4.5;
    public static double DISTANCE_LOW_MAX = 5.5;
    public static double DISTANCE_MED_MIN = 9.5;
    public static double DISTANCE_MED_MAX = 10.5;
    public static double DISTANCE_HIGH_MIN = 14.5;
    public static double DISTANCE_HIGH_MAX = 15.5;

    // Minimum and Maximum Voltage

    public static final double angleEncoderMinVoltage[] = {0.00366, 0.01099, 0.009766, 0.01343};
    public static final double angleEncoderMaxVoltage[] = {4.9133, 4.9255, 4.9219, 4.9243};
    public static final double angleEncoderTareVoltage[] = {4.155,1.748,1.331,4.215};//Swerve Number(A,B,C,D):
    // Aligner tolerances

    public static final double POSITION_TOLERANCE = 5.00;
    public static final double VELOCITY_TOLERANCE = 5.00;



}