// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.DriveCommands;

import frc.robot.RobotContainer;
import frc.robot.RobotMap;
import frc.robot.subsystems.Drive;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;

/** An example command that uses an example subsystem. */
public class mecanumDrive extends CommandBase {
  @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
  private final Drive drive;

  public mecanumDrive(Drive subsystem) {
    drive = subsystem;
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(subsystem);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  @Override
  public void execute() {
    SmartDashboard.putNumber("YAW:", Drive.navx.getYaw());
    SmartDashboard.putNumber("ANGLE:", Drive.navx.getAngle());

    double deadband = 0.2;
    double xMoveSpeed, zMoveSpeed, rotateSpeed;
    double leftSlider, rightSlider;
    
    xMoveSpeed = RobotContainer.leftJoystick.getRawAxis(RobotMap.VERTICAL_AXIS);
    zMoveSpeed = RobotContainer.leftJoystick.getRawAxis(RobotMap.HORIZONTAL_AXIS);
    rotateSpeed = RobotContainer.leftJoystick.getRawAxis(RobotMap.ROTATIONAL_AXIS);


    xMoveSpeed = checkDeadband(RobotContainer.leftJoystick, RobotMap.HORIZONTAL_AXIS, deadband);
    zMoveSpeed = checkDeadband(RobotContainer.leftJoystick, RobotMap.VERTICAL_AXIS, deadband);
    rotateSpeed = checkDeadband(RobotContainer.rightJoystick, RobotMap.ROTATIONAL_AXIS, deadband);

    leftSlider = -RobotContainer.leftJoystick.getRawAxis(RobotMap.SLIDER_AXIS);
    rightSlider = -RobotContainer.rightJoystick.getRawAxis(RobotMap.SLIDER_AXIS);
    
    leftSlider = (leftSlider + 1) / 2;
    rightSlider = (rightSlider + 1) / 2;

    drive.mecanumDrive((xMoveSpeed * leftSlider), (-zMoveSpeed * leftSlider), (rotateSpeed * rightSlider));    
  }

  public double checkDeadband (Joystick _joystick, int axis, double deadband) {
    return (Math.abs(_joystick.getRawAxis(axis)) < deadband ? 0.0 : _joystick.getRawAxis(axis));
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
