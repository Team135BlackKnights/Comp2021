package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Drive;

public class encoderDrive extends CommandBase{
    public double leftDesired, rightDesired, centralDesired, leftError, rightError, centralError;
    public Drive drive;
    public boolean isFinished;

    public encoderDrive(Drive subsystem, double _leftDesired, double _rightDesired){//, double _centralDesired) {
        leftDesired = _leftDesired;
        rightDesired = _rightDesired;
       // centralDesired = _centralDesired;
        addRequirements(drive);
    }

    @Override
    public void initialize() {
        drive.resetEncoders();
        leftError = leftDesired - drive.getLeftDistance();
        rightError = rightDesired - drive.getRightDistance();
        centralError = centralDesired - drive.getCentralDistance(); //get the distance to the desired pos
        }

    @Override
    public void execute() {
        if (Math.abs(leftError) < 5 && Math.abs(rightError) < 5 && Math.abs(centralError) < 5){
            isFinished = true;
        } //check to see if arived
        
        double currentLeftPos = drive.getLeftDistance();
        double currentRightPos = drive.getRightDistance();
        double currentCentralPos = drive.getCentralDistance();

        leftError = currentLeftPos - leftDesired;
        rightError = currentRightPos - rightDesired;
        centralError = currentCentralPos - centralDesired; //get distance to disired pos

        double leftPower = leftError/60;
        double rightPower = rightError/60;
       // double centralPower = centralError/60; //get the power to set to arive at disired pos

        double leftMinAlt = leftError > 0 ? 1: -1;
        double rightMinAlt = rightError > 0 ? 1: -1;
        //double centralMinAlt = centralPower > 0 ? 1: -1; //min power 

        leftPower = limit(leftPower, .45, -.45);
        rightPower = limit(rightPower, .45, -.45);
       // centralPower = limit(centralPower, .45, -.45); //limit power first calc

        leftPower = (leftPower * 1.4) + (leftMinAlt * .04);
        rightPower = (rightPower * 1.4) + (rightMinAlt * .04);

       // centralPower = limit((centralPower * 1.4) + (centralMinAlt * .26), .7, -7); //limit power second round .26 is max

        drive.ArcadeDrive(leftPower, rightPower); 
       // drive.centralMotor.set(centralPower); //drive
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
        drive.centralMotor.set(0); //zero power at end
    }

    @Override
    public boolean isFinished(){
        return isFinished;
    }
}