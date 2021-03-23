package frc.robot.commands.ShooterCommands;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotContainer;
import frc.robot.subsystems.Shooter;

public class runShooter extends CommandBase {
    private final Shooter shooter;

    public runShooter(Shooter subsystem) {
        shooter = subsystem;
        addRequirements(shooter);
    }

    @Override
    public void initialize() {

    }

    @Override
    public void execute() {
        shooter.shooterLauncherLeaderMotor.set(checkDeadband(RobotContainer.manipJoystick, 1, .15));
    }

    public double checkDeadband(Joystick _joystick, int axis, double deadband) {
        return (Math.abs(_joystick.getRawAxis(axis)) < deadband ? 0.0 : _joystick.getRawAxis(axis));
    }

    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {
    }

    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
        return false;
    }
}