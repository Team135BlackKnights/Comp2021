package frc.robot.commands.ShooterCommands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.pidControl;

public class aimTurret extends CommandBase {
    public Shooter shooter;
    public boolean isFinished = false;
    public pidControl pidControl;

    public aimTurret(Shooter subsystem) {    
        shooter = subsystem;
        addRequirements(shooter);
    }

    @Override
    public void initialize() {
        SmartDashboard.putBoolean("Runnning AimBot:", true);
        pidControl.kP = .05;
        isFinished = false;
        // get the distance to the desired pos
    }

    @Override
    public void execute() {
        // get the angle from a scale of -180 to 180
        pidControl.error = Shooter.verticalOffsetEntry.getDouble(0);
        SmartDashboard.putNumber("Current vertical offset:", pidControl.error);

        // How far off the desired angle is from the current angle
        SmartDashboard.putNumber("current Error", pidControl.error);

        pidControl.caculateOutputs();

        //prevent power from falling under the nonsent thrshold. 
        if (limit(pidControl.pidReturn(), .40, -.40) < .07 && limit(pidControl.pidReturn(), .40, -.40) > 0) {
            pidControl.proportionalOutput = .07;
        }
        else if (limit(pidControl.pidReturn(), .40, -.40) > -.07 && limit(pidControl.pidReturn(), .40, -.40) < 0) {
            pidControl.proportionalOutput= -.07;
        }

        // Set power
        shooter.shooterPitchMotor.set(pidControl.proportionalOutput);
        
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
        shooter.shooterPitchMotor.set(0);

    }

    @Override
    public boolean isFinished() {
        SmartDashboard.putBoolean("Runnning Angle Drive:", false);
        return isFinished;
    }
}