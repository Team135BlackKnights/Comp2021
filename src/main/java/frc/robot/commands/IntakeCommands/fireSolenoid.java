package frc.robot.commands.IntakeCommands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Intake;

public class fireSolenoid extends CommandBase {

  private final Intake intake;
  private static boolean solenoidPosition = false;

  public fireSolenoid(Intake subsystem) {
    intake = subsystem;
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    // Set intake to the position it isn't in
    solenoidPosition = !solenoidPosition;
    intake.intakeSolenoid.set(solenoidPosition);

  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return true;
  }
}
