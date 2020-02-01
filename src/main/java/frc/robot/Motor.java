package frc.robot;

public interface Motor{

public double getPosition();

public void setSpeed(final double speed); 

public double getCurrentAngle();

public void setAngle(double targetAngle);

public double getRPM();

public double getRPS();

public void init();

public void completeLoopUpdate();








}