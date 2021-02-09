package frc.robot.commands.DriveCommands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Drive;

public class angleDrive extends CommandBase{
    public double angleDesired, angleError, currentAngle , rotateSpeed;
    public Drive drive;
    public boolean isFinished = false;
    public double proportionalOutput, intergralTop, intergralBottom, integralOutput;

    public double kP = 1.1, kI = 0.1, kD;

    public angleDrive(Drive subsystem, double _angleDesired) {
        angleDesired = _angleDesired;
        drive = subsystem;
        addRequirements(drive);
    }

    @Override
    public void initialize() {
        SmartDashboard.putBoolean("Runnning Angle Drive:", true);
        drive.resetEncoders();
        currentAngle = drive.navx.getYaw();
        isFinished = false;
         //get the distance to the desired pos
        }

    @Override
    public void execute() {
        
        currentAngle = drive.navx.getYaw();

        angleError = angleDesired - currentAngle;

        proportionalOutput = (angleError/90) * kP;
        integralOutput = angleError * kI;

        intergralTop = angleDesired * 1.37;
        intergralBottom = angleDesired - (angleDesired * .37);
        
        if (proportionalOutput > intergralBottom && proportionalOutput < intergralTop){
            rotateSpeed = proportionalOutput + integralOutput;
        }
        else { rotateSpeed = proportionalOutput; }
       

        rotateSpeed = limit(rotateSpeed, .40, -.40);

        drive.mecanumDrive(0,0, rotateSpeed); 

        if (Math.abs(angleError) < .5){
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
        isFinished = false;
        SmartDashboard.putBoolean("isFinished", isFinished());
        drive.mecanumDrive(0, 0, 0); //zero power at end
    }

    @Override
    public boolean isFinished(){
        SmartDashboard.putBoolean("Runnning Angle Drive:", false);
        return isFinished;
    }
}