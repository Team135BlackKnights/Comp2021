package frc.robot.commands.DriveCommands;

import frc.robot.RobotContainer;
import frc.robot.RobotMap;
import frc.robot.subsystems.Drive;
import frc.robot.subsystems.pidControl;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;

/** An example command that uses an example subsystem. */
public class mecanumDrive extends CommandBase {
  @SuppressWarnings({ "PMD.UnusedPrivateField", "PMD.SingularField" })
  private final Drive drive;
  private double leftSlider, rightSlider;
  public pidControl xControl = new pidControl(), zControl = new pidControl(), rControl = new pidControl();

  public mecanumDrive(Drive subsystem) {
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

    final double RPM = (drive.frontLeftMotor.getSelectedSensorVelocity()
        + drive.frontRightMotor.getSelectedSensorVelocity() + drive.backLeftMotor.getSelectedSensorVelocity()
        + drive.backRightMotor.getSelectedSensorVelocity()) / 88000; // adds the native volocity of all motor before
                                                                     // dividing be the RPM equation times 4

    xControl.desired = -checkDeadband(RobotContainer.leftJoystick, RobotMap.HORIZONTAL_AXIS, .2);
    zControl.desired = checkDeadband(RobotContainer.leftJoystick, RobotMap.VERTICAL_AXIS, .2); // get joystick postion
    rControl.desired = -checkDeadband(RobotContainer.rightJoystick, RobotMap.ROTATIONAL_AXIS, .2);

    leftSlider = RobotContainer.leftJoystick.getRawAxis(RobotMap.SLIDER_AXIS);
    rightSlider = RobotContainer.rightJoystick.getRawAxis(RobotMap.SLIDER_AXIS); // setting sliders to use as speed mod

    leftSlider = (leftSlider + 1) / 2;
    rightSlider = (rightSlider + 1) / 2; // sliders to limit speed

    xControl.desired *= -leftSlider;
    zControl.desired *= -leftSlider; // limit speed with sliders
    rControl.desired *= -rightSlider;

    if (checkDeadband(RobotContainer.rightJoystick, RobotMap.ROTATIONAL_AXIS, .2) != 0) { // fix turning interfearing
                                                                                          // with drive
      rControl.error = (rControl.desired) - RPM;
      zControl.error = 0;
      xControl.error = 0;
    } else {
      xControl.error = (xControl.desired) - RPM;
      zControl.error = (zControl.desired) - RPM;
      rControl.error = 0;
    }

    xControl.caculateOutputs();
    zControl.caculateOutputs();
    rControl.caculateOutputs();

    if (Math.abs(xControl.pidReturn()) < 0.2) { // drive.mecanumDrive(xControl.desired, zControl.desired, rControl.desired);
      drive.mecanumDrive(0, zControl.pidReturn() * 2, rControl.pidReturn() * 2);
    } else if (Math.abs(zControl.pidReturn()) < 0.2) {
      drive.mecanumDrive(xControl.pidReturn() * 2, 0, rControl.pidReturn() * 2);
    } else {
      drive.mecanumDrive(xControl.pidReturn() * 2, zControl.pidReturn() * 2, rControl.pidReturn() * 2);
    }

    SmartDashboard.putNumber("front right encoder", drive.frontRightMotor.getSelectedSensorPosition());
    SmartDashboard.putNumber("back Left encoder", drive.backLeftMotor.getSelectedSensorPosition());
    SmartDashboard.putNumber("left side encoders", drive.getSideDistance());

    SmartDashboard.putNumber("YAW:", Drive.navx.getYaw());

    SmartDashboard.putNumber("Volocity: RPM", RPM); // get rounded RPM

    SmartDashboard.putNumber("Xout", xControl.pidReturn());
    SmartDashboard.putNumber("Zout", zControl.pidReturn());

    SmartDashboard.putNumber("frontleftmotor native units",
        Math.ceil(drive.frontLeftMotor.getSelectedSensorVelocity()));
    SmartDashboard.putNumber("frontrightmotor native units",
        Math.ceil(drive.frontRightMotor.getSelectedSensorVelocity()));
    SmartDashboard.putNumber("backrightmotor native units",
        Math.ceil(drive.backRightMotor.getSelectedSensorVelocity()));
    SmartDashboard.putNumber("backleftmotor native units", Math.ceil(drive.backLeftMotor.getSelectedSensorVelocity())); // debug output for motor speeds
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