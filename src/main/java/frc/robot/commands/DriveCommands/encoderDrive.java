package frc.robot.commands.DriveCommands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Drive;
import frc.robot.subsystems.pidControl;

public class encoderDrive extends CommandBase{

    public pidControl xControl = new pidControl(), zControl = new pidControl();
    public Drive drive;
    public boolean isFinished;

    public encoderDrive(Drive subsystem, double xDesired, double zDesired){
        xControl.desired = xDesired;
        zControl.desired = zDesired;
        drive = subsystem;
        addRequirements(drive);
    }

    @Override
    public void initialize() {
        SmartDashboard.putBoolean("encoder drive", true);

        //reset encoder values and find distance to target position
        drive.resetEncoders();
        xControl.error = xControl.desired - drive.getFrontDistance();
        zControl.error =  zControl.desired - drive.getSideDistance();

        SmartDashboard.putNumber("Z desired", zControl.desired);

        xControl.kP = 1;
        xControl.kI = .1;
        
        zControl.kP = 1;
        zControl.kI = .1;
    }

    @Override
    public void execute() {
        if (Math.abs(xControl.error) < 5  && Math.abs(zControl.error) < 5){
            isFinished = true;
        } //check to see if arived
        
        xControl.error = xControl.desired - drive.getFrontDistance();
        zControl.error = zControl.desired - drive.getSideDistance();
        //get distance to disired pos
        SmartDashboard.putNumber("Current Distance", drive.getSideDistance());

        SmartDashboard.putNumber("Z Error", zControl.error);

        xControl.proportionalOutput = xControl.error/60;
        zControl.proportionalOutput = zControl.error/60;
        //get the power to set to arive at disired pos

        SmartDashboard.putNumber("Z proportional", zControl.proportionalOutput);


        xControl.getIntergralZone(); 
        zControl.getIntergralZone();

        xControl.getPidOut();
        zControl.getPidOut(); //use the subsystem to calc the pid output

        SmartDashboard.putNumber("Z output", zControl.Output());


        drive.mecanumDrive(xControl.Output(), zControl.Output(), 0); 
        //drive

        SmartDashboard.putNumber("frontleftmotor native units", Math.ceil(drive.frontLeftMotor.getSelectedSensorVelocity()));
        SmartDashboard.putNumber("frontrightmotor native units", Math.ceil(drive.frontRightMotor.getSelectedSensorVelocity()));
        SmartDashboard.putNumber("backrightmotor native units", Math.ceil(drive.backRightMotor.getSelectedSensorVelocity()));
        SmartDashboard.putNumber("backleftmotor native units", Math.ceil(drive.backLeftMotor.getSelectedSensorVelocity()));
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
        drive.mecanumDrive(0,0,0);
        //drive.centralMotor.set(0); //zero power at end
    }

    @Override
    public boolean isFinished(){
        SmartDashboard.putBoolean("encoder drive", false);
        return isFinished;
    }
}