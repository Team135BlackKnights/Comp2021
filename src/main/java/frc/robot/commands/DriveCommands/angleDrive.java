package frc.robot.commands.DriveCommands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Drive;

public class angleDrive extends CommandBase {
    public double angleDesired, angleError, currentAngle, rotateSpeed;
    public Drive drive;
    public boolean isFinished = false;
    public double proportionalOutput, intergralTop, intergralBottom, integralOutput;

    public double kP = .45, kI = 0, kD;

    public angleDrive(Drive subsystem, double _angleDesired) {
        angleDesired = _angleDesired;
        drive = subsystem;
        addRequirements(drive);
    }

    @Override
    public void initialize() {
        SmartDashboard.putBoolean("Runnning Angle Drive:", true);
        drive.resetEncoders();
        isFinished = false;
        // get the distance to the desired pos

    }

    @Override
    public void execute() {
        // get the angle from a scale of -180 to 180
        currentAngle = Drive.navx.getYaw() - angleDesired;
        SmartDashboard.putNumber("Current Yaw:", currentAngle);

        if (currentAngle < -180)
            currentAngle += 360;
        else if (currentAngle > 180) 
            currentAngle -= 360;

        // How far off the desired angle is from the current angle
        angleError = currentAngle;
        SmartDashboard.putNumber("current Error", angleError);

        // When over 90 degrees off of the desired make the power full
        proportionalOutput = (angleError / 90) * kP;
        
        

        // make maxe speed 40% power
        proportionalOutput = limit(proportionalOutput, .40, -.40);

        if (proportionalOutput < .07 && proportionalOutput > 0) {
            proportionalOutput = .07;
        }
        else if (proportionalOutput > -.07 && proportionalOutput < 0) {
            proportionalOutput= -.07;
        }

        // Set power
        drive.mecanumDrive(0, 0, -proportionalOutput);

        // until the current is within .5 degrees
        if (Math.abs(angleError) <  .25) {
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
    public void end(boolean interrupted) {
        isFinished = false;
        SmartDashboard.putBoolean("isFinished", isFinished());
        drive.mecanumDrive(0, 0, 0); // zero power at end
    }

    @Override
    public boolean isFinished() {
        SmartDashboard.putBoolean("Runnning Angle Drive:", false);
        return isFinished;
    }
}