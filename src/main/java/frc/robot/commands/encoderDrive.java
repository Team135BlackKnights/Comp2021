package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Drive;

public class encoderDrive extends CommandBase{
    public double leftDesired, rightDesired, centralDesired, leftError, rightError, centralError;
    public Drive drive;
    public boolean isFinished;

    public encoderDrive(Drive subsystem, double _leftDesired, double _rightDesired, double _centralDesired) {
        leftDesired = _leftDesired;
        rightDesired = _rightDesired;
        centralDesired = _centralDesired;
        addRequirements(drive);
    }
    @Override
    public void initialize() {
        drive.resetEncoders();
        leftError = leftDesired - drive.getLeftDistance();
        rightError = rightDesired - drive.getRightDistance();
        }

    @Override
    public void execute() {
        if (Math.abs(leftError) < 5 && Math.abs(rightError) < 5){
            isFinished = true;
        }
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
    public void end(boolean interrupted){
        drive.ArcadeDrive(0,0);
    }

    @Override
    public boolean isFinished(){
        return isFinished;
    }
}
