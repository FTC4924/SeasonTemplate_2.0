package org.firstinspires.ftc.teamcode.Qualifier_1.Autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.Qualifier_1.Robot;

@Autonomous(name = "Park")
public class Park extends LinearOpMode {

    @Override
    public void runOpMode() {

        Robot robot = new Robot(this);
        ElapsedTime runtime = new ElapsedTime();

        waitForStart();

        robot.moveForward(73, 0.5);
        sleep(5000);
//        robot.moveForwardOdometry(80, 0.8);
    }
}