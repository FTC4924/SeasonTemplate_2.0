package developing;

import com.qualcomm.robotcore.util.ElapsedTime;

public class MotionPlanner2 {

    public double restPow = 0;
    public double approachRate = 0;
    public double proportionalCoeff = 0;

    public double targetDis = 0;
    public double startDis = 0;

    public double curDis = 0;
    public double curPow = 0;

    public double lastDis = 0;
    public double lastTime = -0.1;

    public double curVel = 0;

    public double acc = 0;


    public boolean hasTargetBeenSet = false;

    public ElapsedTime timer = new ElapsedTime();


    public void setPAR(double p, double a, double r){
        proportionalCoeff = p;
        approachRate = a;
        restPow = r;
    }

    public void setAcc(double a){
        acc = a;
    }



    public void setTargetDis(double td, double sd){
        startDis = sd;
        targetDis = td-sd;
        timer.reset();
        lastTime = -0.1;
        hasTargetBeenSet = true;
    }




    public double VofS(){
        return approachRate*Math.pow(Math.abs(targetDis-curDis), 1/approachRate)*Math.signum(targetDis-curDis);
    }
    public double getRestPow(){
        return Math.signum(targetDis-curDis)*restPow;
    }

    public void update(double curPos){
        curDis = (curPos-startDis);
        updateValues();
        curPow = (proportionalCoeff*(VofS()-curVel))+getRestPow();
    }


    public void updateValues(){
        double deltaDis = curDis - lastDis;
        lastDis = curDis;

        double curtime = timer.seconds();
        double deltaTime = curtime - lastTime;
        lastTime = curtime;

        curVel = deltaDis/deltaTime;
    }




    public double getPower(){
        return curPow;
    }


    public boolean isDone(){
        return Math.abs(targetDis-curDis) < acc;
    }


    public void reset(){
        hasTargetBeenSet = false;
    }
}
