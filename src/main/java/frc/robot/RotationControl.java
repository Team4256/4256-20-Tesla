package frc.robot; 

public class RotationControl{

    private final Motor rotationMotor;
    double TareAngle = 0;
    private double lastLegalDirection = 1.0;

    //constructor
   public RotationControl(int deviceID){
       rotationMotor= new SparkMaxNeo(deviceID,false); 
   }

   public void SetAngle(double angle){
        rotationMotor.setAngle(angle);
   }

   public void SetTareAngle(double inputAngle){
       TareAngle=inputAngle;
   }
   
   public double GetTareAngle(){
       
    return TareAngle;
   }
   
   public void completeLoopUpdate(){
       
    rotationMotor.completeLoopUpdate();
   }

   public double GetCurrentAngle(){

       return rotationMotor.getCurrentAngle();
   }

   public double pathTo(double target) {//ANGLE
    final double current = GetCurrentAngle();
    double path = Compass.legalPath(current, target);
    if (current == Compass.legalize(current)) lastLegalDirection = Math.signum(path);
    else if (Math.signum(path) != -lastLegalDirection) path -= Math.copySign(360, path);
    
    return path;
}

}

