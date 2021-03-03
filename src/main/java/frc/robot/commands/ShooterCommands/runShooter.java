package frc.robot.commands.ShooterCommands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotContainer;
import frc.robot.subsystems.Shooter;

public class runShooter extends CommandBase{
    private final Shooter shooter;
    
    public runShooter(Shooter subsystem) {
        shooter = subsystem;
    }
    @Override
    public void initialize() {

    }

    @Override
    public void execute() {
        shooter.shooterLauncherLeaderMotor.set((Math.floor(RobotContainer.manipJoystick.getRawAxis(1) * 10)) / 10);
        shooter.shooterLauncherFollowerMotor.set((Math.floor(-RobotContainer.manipJoystick.getRawAxis(1) * 10)) / 10);
    }

    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {}

    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
        return false;
    }
}