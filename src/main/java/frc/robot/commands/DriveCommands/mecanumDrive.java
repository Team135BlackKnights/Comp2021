// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

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
  @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
  private final Drive drive;
  RobotContainer container;

  public pidControl xControl = new pidControl(), zControl= new pidControl(), rControl = new pidControl();

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

    final double RPM = 78640800 / (drive.frontLeftMotor.getSelectedSensorVelocity() + drive.frontRightMotor.getSelectedSensorVelocity() + drive.backLeftMotor.getSelectedSensorVelocity() + drive.backRightMotor.getSelectedSensorVelocity()); //adds the native volocity of all motor before dividing be the RPM equation times 4
    double leftSlider, rightSlider;

    xControl.desired = checkDeadband(RobotContainer.leftJoystick, RobotMap.HORIZONTAL_AXIS, .2);
    zControl.desired = checkDeadband(RobotContainer.leftJoystick, RobotMap.VERTICAL_AXIS, .2);
    rControl.desired = checkDeadband(RobotContainer.rightJoystick, RobotMap.ROTATIONAL_AXIS, .2);

    leftSlider = -RobotContainer.leftJoystick.getRawAxis(RobotMap.SLIDER_AXIS);
    rightSlider = -RobotContainer.rightJoystick.getRawAxis(RobotMap.SLIDER_AXIS); //setting parts
    
    leftSlider = (leftSlider + 1) / 2;
    rightSlider = (rightSlider + 1) / 2; //sliders to limit speed

    xControl.desired *= leftSlider;
    zControl.desired *= leftSlider;
    rControl.desired *= rightSlider;

    xControl.error = (xControl.desired * 1500) - RPM;
    zControl.error = (zControl.desired * 1500) - RPM;
    rControl.error = (rControl.desired * 1500) - RPM;

    xControl.getIntergralZone();
    zControl.getIntergralZone();
    rControl.getIntergralZone();

    xControl.getPidOut();
    zControl.getPidOut();
    rControl.getPidOut();

    drive.mecanumDrive(xControl.Output(), zControl.Output(), rControl.Output());  
  
    SmartDashboard.putNumber("Volocity: Native Units", drive.frontLeftMotor.getSelectedSensorVelocity()); //get native unit volocity
    if (drive.frontLeftMotor.getSelectedSensorVelocity() == 0){
      SmartDashboard.putNumber("Volocity: RPM", 0);  }
    else { SmartDashboard.putNumber("Volocity: RPM", Math.ceil(RPM)); //get rounded RPM
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
