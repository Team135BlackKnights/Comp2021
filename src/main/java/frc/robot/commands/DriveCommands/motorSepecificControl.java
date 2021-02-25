/*
package frc.robot.commands.DriveCommands;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotContainer;
import frc.robot.RobotMap;
import frc.robot.subsystems.Drive;
import frc.robot.subsystems.pidControl;

// An example command that uses an example subsystem. 
public class motorSepecificControl extends CommandBase {
  @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
  private final Drive drive;
  RobotContainer container;
  double RPM, Px, Py, theta;
  public pidControl xControl = new pidControl(), zControl= new pidControl(), rControl = new pidControl();

  public motorSepecificControl(Drive subsystem) {
    drive = subsystem;
    
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(subsystem);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {

    xControl.kP = .2;
    xControl.kI = .01;

    zControl.kP = .2;
    zControl.kI = .01;

    rControl.kP = .2;
    rControl.kI = .01;

  }

  @Override
  public void execute() {
    SmartDashboard.putNumber("YAW:", Drive.navx.getYaw());

    if (rotateSpeed > .18 || rotateSpeed < -.18 || moveSpeed > .18 || moveSpeed < -.18 ) {

      theta = (Math.atan2(rotateSpeed, moveSpeed)) - (Math.PI / 2);
  
      Px = Math.sqrt(Math.pow(moveSpeed, 2) + Math.pow(rotateSpeed, 2)) * (Math.sin(theta + Math.PI / 4));
      Py = Math.sqrt(Math.pow(moveSpeed, 2) + Math.pow(rotateSpeed, 2)) * (Math.sin(theta - Math.PI / 4));
  
      drive.frontLeftMotor.set(Py);
      FrontRight.set(-Px);
      BackLeft.set(Px);
      BackRight.set(-Py);
    }
    else 
      {
        FrontLeft.set(0);
        FrontRight.set(0);
        BackLeft.set(0);
        BackRight.set(0);
      }
    
  }

  public double checkDeadband (Joystick _joystick, int axis, double deadband) {
    return (Math.abs(_joystick.getRawAxis(axis)) < deadband ? 0.0 : _joystick.getRawAxis(axis));// if the joystick is less than the deadband return 0 else reutn joystick
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
*/