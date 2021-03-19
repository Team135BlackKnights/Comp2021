package frc.robot.commands.DriveCommands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Drive;
import frc.robot.subsystems.pidControl;

public class angleDrive extends CommandBase {
    public Drive drive;
    public boolean isFinished = false;
    public pidControl pidControl;

    public angleDrive(Drive subsystem, double _angleDesired) {
        pidControl.desired = _angleDesired;
        drive = subsystem;
        addRequirements(drive);
    }

    @Override
    public void initialize() {
        SmartDashboard.putBoolean("Runnning Angle Drive:", true);
        drive.resetEncoders();
        pidControl.kP = .45;
        isFinished = false;
        // get the distance to the desired pos
    }

    @Override
    public void execute() {
        // get the angle from a scale of -180 to 180
        pidControl.error = Drive.navx.getYaw() - pidControl.desired;
        SmartDashboard.putNumber("Current Yaw:", pidControl.error);

        if (pidControl.error < -180)
            pidControl.error += 360;
        else if (pidControl.error > 180) 
            pidControl.error -= 360;

        // How far off the desired angle is from the current angle
        SmartDashboard.putNumber("current Error", pidControl.error);

        // When over 90 degrees off of the desired make the power full
        pidControl.error = pidControl.error / 90;    

        pidControl.caculateOutputs();

        //prevent power from falling under the nonsent thrshold. 
        if (limit(pidControl.pidReturn(), .40, -.40) < .07 && limit(pidControl.pidReturn(), .40, -.40) > 0) {
            pidControl.proportionalOutput = .07;
        }
        else if (limit(pidControl.pidReturn(), .40, -.40) > -.07 && limit(pidControl.pidReturn(), .40, -.40) < 0) {
            pidControl.proportionalOutput= -.07;
        }

        // Set power
        drive.mecanumDrive(0, 0, -pidControl.proportionalOutput);

        // until the current is within .5 degrees
        if (Math.abs(pidControl.error) <  .25) {
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