package frc.robot.commands.DriveCommands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Drive;
import frc.robot.subsystems.pidControl;

public class encoderDrive extends CommandBase {

    public pidControl xControl = new pidControl(), zControl = new pidControl(); //create the pid controles
    public Timer timer = new Timer();
    public Drive drive;
    public boolean isFinished;

    public encoderDrive(Drive subsystem, double xDesired, double zDesired) {
        xControl.desired = xDesired; //pull desired into pid command
        zControl.desired = zDesired;
        drive = subsystem; //set drive as drive
        addRequirements(drive);
    }

    @Override
    public void initialize() {
        isFinished = false;
        timer.reset();
        zControl.lastoutput = 0;
        timer.start(); //cleaning from prevous runs

        SmartDashboard.putBoolean("encoder drive", true); //note to smartdash that drive is running

        //reset encoder values and find distance to target position
        drive.resetEncoders();
        while (drive.getSideDistance() != 0) {} //wait for the encoders to be reset

        xControl.error = xControl.desired - drive.getFrontDistance();
        zControl.error = zControl.desired - drive.getSideDistance(); //caculate error

        SmartDashboard.putNumber("Z desired", zControl.desired);

        xControl.kP = .02;
        xControl.kI = 0; //ki is 0 untill a proper I loop can be setup!!

        zControl.kP = .01;
        zControl.kI = .0; //ki is 0 untill a proper I loop can be setup!!
    }

    @Override
    public void execute() {
        if (Math.abs(xControl.error) < 3 && Math.abs(zControl.error) < 3) {
            isFinished = true;
        } // check to see if finished
        SmartDashboard.putBoolean("isfinished", isFinished); //notify smartdash if finished
        xControl.error = xControl.desired - drive.getFrontDistance();
        zControl.error = zControl.desired - drive.getSideDistance();
        // get distance to disired pos
        SmartDashboard.putNumber("Current Distance", drive.getSideDistance());
        SmartDashboard.putNumber("front left encoder", drive.frontLeftMotor.getSelectedSensorPosition());
        SmartDashboard.putNumber("Z Error", zControl.error);
        SmartDashboard.putNumber("X Error", xControl.error);

        xControl.caculateOutputs();
        zControl.caculateOutputs(); // use the subsystem to calc the pid output

        if (zControl.pidReturn() < .07 && zControl.pidReturn() > 0) {
            zControl.proportionalOutput = .07;
        } else if (zControl.pidReturn() > -.07 && zControl.pidReturn() < 0) {
            zControl.proportionalOutput = -.07;
        } else {
            zControl.proportionalOutput = zControl.pidReturn();
        } // setup minimum power so if there is power but is under the wire's threshold
          // still give min output

        zControl.proportionalOutput = limit(zControl.proportionalOutput, .4, -.4); // limit the output to a max of (40)% speed

        if ((zControl.proportionalOutput - zControl.lastoutput) / timer.get() > .5) {
            zControl.proportionalOutput = zControl.lastoutput + (.5 * timer.get()); // add rampup for pos
        } else if ((zControl.proportionalOutput - zControl.lastoutput) / timer.get() < -.5) {
            zControl.proportionalOutput = zControl.lastoutput - (.5 * timer.get());// add rampup for neg
        }

        drive.mecanumDrive(0, zControl.proportionalOutput, 0); //output to drive

        zControl.lastoutput = zControl.proportionalOutput; //update zControl.lastoutput
        timer.reset();

        SmartDashboard.putNumber("test desired", zControl.desired);
        SmartDashboard.putNumber("test error", zControl.error);
        SmartDashboard.putNumber("test Z output", zControl.proportionalOutput);
        SmartDashboard.putNumber("test Z output no limit", zControl.pidReturn());
        SmartDashboard.putNumber("test X output", limit(xControl.pidReturn(), .6, -.6));
        SmartDashboard.putNumber("Timer", timer.get());

        SmartDashboard.putNumber("frontleftmotor native units",
                Math.ceil(drive.frontLeftMotor.getSelectedSensorVelocity()));
        SmartDashboard.putNumber("frontrightmotor native units",
                Math.ceil(drive.frontRightMotor.getSelectedSensorVelocity()));
        SmartDashboard.putNumber("backrightmotor native units",
                Math.ceil(drive.backRightMotor.getSelectedSensorVelocity()));
        SmartDashboard.putNumber("backleftmotor native units",
                Math.ceil(drive.backLeftMotor.getSelectedSensorVelocity()));
    }

    public static double limit(double x, double upperLimit, double lowerLimit) {
        if (x >= upperLimit) {
            x = upperLimit;
        } else if (x <= lowerLimit) {
            x = lowerLimit;
        }
        return x;
    }

    @Override
    public void end(boolean interrupted) {
        drive.mecanumDrive(0, 0, 0);
        zControl.lastoutput = 0;
        timer.stop();
        isFinished = false;
    }

    @Override
    public boolean isFinished() {
        SmartDashboard.putBoolean("encoder drive", false);
        return isFinished;
    }
}