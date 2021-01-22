// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import frc.robot.Robot;
import frc.robot.RobotContainer;
import frc.robot.RobotMap;
import frc.robot.subsystems.Drive;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.CommandBase;

/** An example command that uses an example subsystem. */
public class omniDrive extends CommandBase {
  @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
  private final Drive _Drive;

  public omniDrive(Drive subsystem) {
    _Drive = subsystem;
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(subsystem);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    double deadband = 0.4;
    double lateralMoveSpeed, horizontalMoveSpeed, rotateSpeed;
    double leftSlider, rightSlider;
    
    lateralMoveSpeed = RobotContainer.leftJoystick.getRawAxis(RobotMap.VERTICAL_AXIS);
    horizontalMoveSpeed = RobotContainer.leftJoystick.getRawAxis(RobotMap.HORIZONTAL_AXIS);
    rotateSpeed = RobotContainer.leftJoystick.getRawAxis(RobotMap.ROTATIONAL_AXIS);


    horizontalMoveSpeed = checkDeadband(RobotContainer.leftJoystick, RobotMap.HORIZONTAL_AXIS, deadband);
    lateralMoveSpeed = checkDeadband(RobotContainer.leftJoystick, RobotMap.VERTICAL_AXIS, deadband);
    rotateSpeed = checkDeadband(RobotContainer.rightJoystick, RobotMap.ROTATIONAL_AXIS, deadband);

    leftSlider = -RobotContainer.leftJoystick.getRawAxis(RobotMap.SLIDER_AXIS);
    rightSlider = -RobotContainer.rightJoystick.getRawAxis(RobotMap.SLIDER_AXIS);
    
    leftSlider = (leftSlider + 1) / 2;
    rightSlider = (rightSlider + 1) / 2;

    if (RobotContainer.leftJoystick.getRawButtonReleased(0)) {
      _Drive.centralMotor.set(horizontalMoveSpeed * leftSlider);  
      _Drive.frontRightMotor.set(lateralMoveSpeed * leftSlider);
      _Drive.frontLeftMotor.set(lateralMoveSpeed * leftSlider);
      _Drive.backRightMotor.set(lateralMoveSpeed * leftSlider);
      _Drive.backLeftMotor.set(lateralMoveSpeed * leftSlider);
    }
    else {
      _Drive.ArcadeDrive(lateralMoveSpeed * leftSlider, rotateSpeed * rightSlider);
    }
    
    

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
