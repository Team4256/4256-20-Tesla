/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

/**
 * Add your docs here.
 */
public class StopWatch {
    private double elapsedTime;
    public static StopWatch instance = null;


    private StopWatch(){
        elapsedTime = 0.0;
    }


    public synchronized static StopWatch getInstance() {
      if (instance == null) {
        instance = new StopWatch();
      }
      return instance;
    }



    public void resetTimer(){
        elapsedTime = 0.0;
    }


    public void updateTimer(){
        elapsedTime += 0.02;
    }
    

    public double getElapsedTime(){
        return elapsedTime;

    }

}
