package frc.robot; 

public class TractionControl{

    private final SparkMaxNeo tractionMotor;

    //constructor
   public TractionControl(int deviceID){
       tractionMotor= new SparkMaxNeo(deviceID,false); 
   }

   public void set(double speed){
        tractionMotor.set(speed);
   }

   public void completeLoopUpdate(){
    
       tractionMotor.completeLoopUpdate();
   }

   public double getRPS(){
       
    return tractionMotor.getRPS();
   }

}

