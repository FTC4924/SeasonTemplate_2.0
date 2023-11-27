package org.firstinspires.ftc.teamcode.opmodes;

import android.util.Log;

import com.acmerobotics.roadrunner.util.NanoClock;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.subsystems.CrabRobot;
import org.firstinspires.ftc.teamcode.subsystems.SmartGamepad;

@TeleOp
public class AATele extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        CrabRobot robot = new CrabRobot(this);
        waitForStart();
        robot.addGamepads(gamepad1, gamepad2);
        SmartGamepad smartGamepad1 = robot.smartGamepad1;
        SmartGamepad smartGamepad2 = robot.smartGamepad2;

        NanoClock clock = NanoClock.system();
        double prevTime = clock.seconds();
        int intakeState = 0; // 0 = outtake; 1 = intake;

        while (!isStopRequested()) {
            telemetry.update();
            robot.update();
            //robot.mecanumDrive.setDrivePower(new Pose2d(-gamepad1.left_stick_y, gamepad1.left_stick_x, -gamepad1.right_stick_x));

                //UG OUTTAKE
            if(smartGamepad2.left_trigger>0) { robot.outtake.moveDumper(-0.5);}
            if(smartGamepad2.right_trigger>0) {robot.outtake.moveDumper( 0.5);}

            if (smartGamepad2.right_stick_x>0) {
                robot.outtake.moveArm(0.5);
            }
            if (smartGamepad2.right_stick_x<0) {
                robot.outtake.moveArm(-0.5);
            }

            if (smartGamepad2.x_pressed()) {
                robot.outtake.toIntakePos();
            }
            if (smartGamepad2.y_pressed()) {
                robot.outtake.toTravelPos();
            }
            if (smartGamepad2.right_bumper) {
                robot.outtake.dropPixelPos();
            }
            if (smartGamepad2.dpad_up) {
                robot.outtake.lift.adjustLift(1, false);
                telemetry.addLine("dpad up pressed");
                Log.v("PIDLift: gamepad", "dpad up");
            }
            else if (smartGamepad2.dpad_down) {
                robot.outtake.lift.adjustLift(-1, false);
                telemetry.addLine("dpad down pressed");
                Log.v("PIDLift: gamepad", "dpad down");
            } else if (robot.outtake.lift.isLevelReached()){
                robot.outtake.lift.stopMotor();
            }

            // INTAKE
            if(smartGamepad1.a_pressed()){
                if(intakeState == 0) {
                    robot.intake.toIntakePos();
                    intakeState=1;
                } else if (intakeState == 1){
                    robot.intake.toOuttakePos();
                    robot.outtake.toIntakePos();
                    intakeState=0;
                }
            }
            if(smartGamepad2.a_pressed()){
                robot.intake.setPower(0);
                robot.intake.toBasePos();
                robot.outtake.prepOuttake();
            }
            if(smartGamepad2.b_pressed()){
                robot.outtake.toDumpPos();
            }
            if(smartGamepad1.left_bumper){
                robot.intake.setPower(1);
            } else{
                robot.intake.setPower(0);
            }


            telemetry.addData("right servo position: ", robot.outtake.get_RightServoPos());
            telemetry.addData("left servo position: ", robot.outtake.get_LeftServoPos());
            telemetry.addData("dumper servo position: ", robot.outtake.getDumperPos());
            telemetry.addData("intake pos", intakeState);
            telemetry.addData("slide pos", robot.outtake.getLiftPos());
            telemetry.addData("slide power", robot.outtake.getLiftPower());
            //Log.v("arm", "right servo position: "+ robot.outtake.getRightServoPos());
            double currentTime = clock.seconds();
            telemetry.addData("Update time: ", currentTime - prevTime);
            prevTime = currentTime;
            }
        }
    }
