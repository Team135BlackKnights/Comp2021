package frc.robot.commands.DriveCommands;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotContainer;
import frc.robot.RobotMap;
import frc.robot.subsystems.Drive;
import frc.robot.subsystems.pidControl;

// An example command that uses an example subsystem. 
public class motorSpecificControl extends CommandBase {
  @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
  private final Drive drive;
  RobotContainer container;
  double RPM, Px, Py, theta;
  public pidControl xControl = new pidControl(), zControl = new pidControl(), rControl = new pidControl();

  public motorSpecificControl(Drive subsystem) {
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

    double leftX = checkDeadband(RobotContainer.leftJoystick, RobotMap.HORIZONTAL_AXIS, .2);
    double leftY = -checkDeadband(RobotContainer.leftJoystick, RobotMap.VERTICAL_AXIS, .2); // get joystick postion
    double rightR = checkDeadband(RobotContainer.rightJoystick, RobotMap.ROTATIONAL_AXIS, .2);

    double r = Math.hypot(leftX, leftY);
    double robotAngle = Math.atan2(leftY, leftX) - Math.PI / 4;
    double rightX = rightR;
    final double v1 = r * Math.cos(robotAngle) + rightX;
    final double v2 = r * Math.sin(robotAngle) - rightX;
    final double v3 = r * Math.sin(robotAngle) + rightX;
    final double v4 = r * Math.cos(robotAngle) - rightX;

    drive.frontLeftMotor.set(v1);
    drive.frontRightMotor.set(-v2);
    drive.backLeftMotor.set(v3);
    drive.backRightMotor.set(-v4);
 
  }

  public double checkDeadband(Joystick _joystick, int axis, double deadband) {
    return (Math.abs(_joystick.getRawAxis(axis)) < deadband ? 0.0 : _joystick.getRawAxis(axis));// if the joystick is
    // less than the
    // deadband return 0
    // else reutn joystick
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