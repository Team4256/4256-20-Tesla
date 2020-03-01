/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Limelight.CamMode;
/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {
  private static final String port = "port";
  private static final String leftTrench = "left trench";
  private static final String rightTrench = "right trench";
  private static final String crossLine = "cross white line";
  private String m_autoSelected;
  private final SendableChooser<String> m_chooser = new SendableChooser<>();
  public static double gyroHeading = 0.0;
  private JoystickControl subsystems = new JoystickControl();
  private static Gyro gyro = Gyro.getInstance();
  NetworkTable table = NetworkTableInstance.getDefault().getTable("limelight");
  Limelight camera = Limelight.getInstance();
  D_Swerve swerve = D_Swerve.getInstance();
  private static Auto auto = new Auto();
  public synchronized static void updateGyroHeading() {
    gyroHeading = gyro.getCurrentAngle();
   
}
  //private Compressor compress = new Compressor();
  /**
   * This function is run when the robot is first started up and should be used
   * for any initialization code.
   */
  @Override
  public void robotInit() {
    m_chooser.setDefaultOption("cross white line", crossLine);
    m_chooser.addOption("left trench", leftTrench);
    m_chooser.addOption("right trench", rightTrench);
    m_chooser.addOption("port", port);
    SmartDashboard.putData("Auto choices", m_chooser);
    gyro.setAngleAdjustment(Parameters.GYRO_OFFSET);
    gyro.reset();
    //subsystems.setSwerveToZero();
    
    
    
    
   // compress.start();
  }

  /**
   * This function is called every robot packet, no matter the mode. Use this for
   * items like diagnostics that you want ran during disabled, autonomous,
   * teleoperated and test.
   *
   * <p>
   * This runs after the mode specific periodic functions, but before LiveWindow
   * and SmartDashboard integrated updating.
   */
  @Override
  public void robotPeriodic() {
      camera.turnLEDOn();
      subsystems.displaySwerveAngles();
      updateGyroHeading();
      swerve.completeLoopUpdate();
      StopWatch.getInstance().updateTimer(); 
        // apollo.getEntry("Selected Starting Position").setString(autoModeChooser.getRawSelections()[0]);
        // apollo.getEntry("Desired Auto Mode").setString(autoModeChooser.getRawSelections()[1]);
        // apollo.getEntry("Has Ball Test").setBoolean(ballIntake.hasBall());
        // apollo.getEntry("Is Autonomous").setBoolean(autoModeExecutor != null);
  }

  /**
   * This autonomous (along with the chooser code above) shows how to select
   * between different autonomous modes using the dashboard. The sendable chooser
   * code works with the Java SmartDashboard. If you prefer the LabVIEW Dashboard,
   * remove all of the chooser code and uncomment the getString line to get the
   * auto name from the text box below the Gyro
   *
   * <p>
   * You can add additional auto modes by adding additional comparisons to the
   * switch structure below with additional strings. If using the SendableChooser
   * make sure to add them to the chooser code above as well.
   */
  @Override
  public void autonomousInit() {
    
    m_autoSelected = m_chooser.getSelected();
    m_autoSelected = SmartDashboard.getString("Auto Selector", crossLine );
    System.out.println("Auto selected: " + m_autoSelected);
    auto.autoInit();
  }

  /**
   * This function is called periodically during autonomous.
   */
  @Override
  public void autonomousPeriodic() {
    
  //   switch (m_autoSelected) {
  //   case port:
  //     auto.mode2();
  //     break;
  //   case leftTrench:
  //  // default:
  //     auto.mode3();
  //     break;
  //     case rightTrench:
  //     auto.mode1();
  //     break;
  //     case crossLine:
  //     auto.mode4();
  //     break;
  // }  
      auto.mode1();
  swerve.completeLoopUpdate();
  }

  /**
   * This function is called periodically during operator control.
   */
  @Override
  public void teleopPeriodic() {
    camera.turnLEDOn();
    table.getEntry("pipeline").setNumber(1);
    NetworkTableEntry tx = table.getEntry("tx");
    NetworkTableEntry ty = table.getEntry("ty");
    NetworkTableEntry ta = table.getEntry("ta");
    
    double x = tx.getDouble(0.0);
    double y = ty.getDouble(0.0);
    double area = ta.getDouble(0.0);
    subsystems.swervePeriodic();
    subsystems.intakePeriodic();
    subsystems.shooterPeriodic();
    subsystems.ClimbingPeriodic();
    
    
  }

  /**
   * This function is called periodically during test mode.
   */
  @Override
  public void testPeriodic() {
    
    camera.turnLEDOn();
    // camera.updateVisionTracking2();
    // camera.setCamMode(CamMode.VISION);
  }
}
