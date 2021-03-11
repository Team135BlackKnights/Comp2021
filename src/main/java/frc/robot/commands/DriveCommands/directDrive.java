/*
THIS IS FOR DEBUGGING ONLY
THIS SHOULD NEVER BE USED OUTSIDE OF THAT UNLESS THE OTHER DRIVE
WILL CAUSE THE ROBOT TO IRREPARABLY BREAK
EVEN THEN THERE ARE BETTER OPTIONS 
*/

package frc.robot.commands.DriveCommands;

import frc.robot.RobotContainer;
import frc.robot.subsystems.Drive;

import edu.wpi.first.wpilibj2.command.CommandBase;

public class directDrive extends CommandBase {
  @SuppressWarnings({ "PMD.UnusedPrivateField", "PMD.SingularField" })
  private final Drive drive;

  public directDrive(Drive subsystem) {
    drive = subsystem;
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(subsystem);
  }

  @Override
  public void execute() {
        drive.frontLeftMotor.set(-RobotContainer.leftJoystick.getRawAxis(1));
        drive.frontRightMotor.set(RobotContainer.leftJoystick.getRawAxis(1));
        drive.backLeftMotor.set(-RobotContainer.leftJoystick.getRawAxis(1));
        drive.backRightMotor.set(RobotContainer.leftJoystick.getRawAxis(1));
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