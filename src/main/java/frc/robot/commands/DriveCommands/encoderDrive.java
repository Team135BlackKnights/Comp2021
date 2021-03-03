package frc.robot.commands.DriveCommands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Drive;
import frc.robot.subsystems.pidControl;

public class encoderDrive extends CommandBase {

    public pidControl xControl = new pidControl(), zControl = new pidControl();
    public double pidOutput, lastOutput = 0;
    public Timer timer = new Timer();
    public Drive drive;
    public boolean isFinished;

    public encoderDrive(Drive subsystem, double xDesired, double zDesired) {
        xControl.desired = xDesired; //convert to in
        zControl.desired = zDesired;
        drive = subsystem;
        addRequirements(drive);
    }

    @Override
    public void initialize() {
        timer.reset();
        lastOutput = 0;
        timer.start();
        SmartDashboard.putBoolean("encoder drive", true);

        // reset encoder values and find distance to target position
        drive.resetEncoders();

        while (drive.getSideDistance() != 0){
        //    drive.resetEncoders();
            
        }

        xControl.error = xControl.desired - drive.getFrontDistance();
        zControl.error = zControl.desired - drive.getSideDistance();

        SmartDashboard.putNumber("Z desired", zControl.desired);

        xControl.kP = .2;
        xControl.kI = .1;

        zControl.kP = .01;
        zControl.kI = .0;
        isFinished = false;
    }

    @Override
    public void execute() {
        if (Math.abs(xControl.error) < 3 && Math.abs(zControl.error) < 3) {
            isFinished = true;
        } // check to see if arived
        SmartDashboard.putBoolean("isfinished", isFinished);
        xControl.error = xControl.desired - drive.getFrontDistance();
        zControl.error = zControl.desired - drive.getSideDistance();
        // get distance to disired pos
        SmartDashboard.putNumber("Current Distance", drive.getSideDistance());
        SmartDashboard.putNumber("front left encoder", drive.frontLeftMotor.getSelectedSensorPosition());
        SmartDashboard.putNumber("Z Error", zControl.error);
        SmartDashboard.putNumber("X Error", xControl.error);


        //xControl.proportionalOutput = xControl.error;
        //zControl.proportionalOutput = zControl.error;
        // get the power to set to arive at disired pos

        SmartDashboard.putNumber("Z proportional", zControl.proportionalOutput);

        xControl.caculateOutputs();
        zControl.caculateOutputs(); // use the subsystem to calc the pid output

        SmartDashboard.putNumber("Z output", zControl.Output());

        if (zControl.Output() < .07 && zControl.Output() > 0) {
            pidOutput = .07;
        }
        else if (zControl.Output() > -.07 && zControl.Output() < 0) {
            pidOutput = -.07;
        }
        else {pidOutput = zControl.Output();}

        pidOutput = limit(pidOutput, .4, -.4);

        if ((pidOutput - lastOutput) / timer.get() > .5){
            pidOutput = lastOutput + (.5 * timer.get());
        }

        drive.mecanumDrive(0, pidOutput, 0);
        if (pidOutput < 0 ) {
            isFinished = true;
        }
        //drive.mecanumDrive(0,.2,0);

        lastOutput = pidOutput;
        timer.reset();

        SmartDashboard.putNumber("test desired", zControl.desired);
        SmartDashboard.putNumber("test error", zControl.error);
        SmartDashboard.putNumber("test Z output", pidOutput);
        SmartDashboard.putNumber("test Z output no limit", zControl.Output());
        SmartDashboard.putNumber("test X output", limit(xControl.Output(), .6, -.6));
        SmartDashboard.putNumber("TImer", timer.get());


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
        lastOutput = 0;
        timer.stop();
        isFinished = false;
    }

    @Override
    public boolean isFinished() {
        SmartDashboard.putBoolean("encoder drive", false);
        return isFinished;
    }
}