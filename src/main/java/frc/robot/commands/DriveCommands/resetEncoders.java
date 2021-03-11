package frc.robot.commands.DriveCommands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Drive;

public class resetEncoders extends CommandBase {
  Drive drive;

  public resetEncoders(Drive subsystem) {
    drive = subsystem;
  }

  @Override
  public void initialize() {
    // Runs method to reset the encoders from drivetrain
    drive.resetEncoders();
    Drive.navx.reset();
    addRequirements(drive);
  }

  @Override
  public void end(boolean interrupted) {
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return true;
  }
}
