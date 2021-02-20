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
  double RPM;
  public pidControl xControl = new pidControl(), zControl= new pidControl(), rControl = new pidControl();



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
    SmartDashboard.putNumber("YAW:", Drive.navx.getYaw());

    //final double RPM = 78640800 / (drive.frontLeftMotor.getSelectedSensorVelocity() + drive.frontRightMotor.getSelectedSensorVelocity() + drive.backLeftMotor.getSelectedSensorVelocity() + drive.backRightMotor.getSelectedSensorVelocity()); //adds the native volocity of all motor before dividing be the RPM equation times 4
    
      //adds the native volocity of all motor before dividing be the RPM equation times 4
    RPM = (drive.frontLeftMotor.getSelectedSensorVelocity() + drive.frontRightMotor.getSelectedSensorVelocity() + drive.backLeftMotor.getSelectedSensorVelocity() + drive.backRightMotor.getSelectedSensorVelocity()) / 88000; //adds the native volocity of all motor before dividing be the RPM equation times 4
    //RPM = drive.backLeftMotor.getSelectedSensorVelocity() / 22000;
      //RPM = (drive.frontLeftMotor.getSelectedSensorVelocity() + drive.frontRightMotor.getSelectedSensorVelocity()) / 44000;
      //RPM = (Math.abs(drive.frontLeftMotor.getSelectedSensorVelocity()) + Math.abs(drive.frontRightMotor.getSelectedSensorVelocity()) + Math.abs(drive.backLeftMotor.getSelectedSensorVelocity()) +  Math.abs(drive.backRightMotor.getSelectedSensorVelocity())) / 88000; //adds the native volocity of all motor before dividing be the RPM equation times 4

    
    
    SmartDashboard.putNumber("frontleftmotor native units", Math.ceil(drive.frontLeftMotor.getSelectedSensorVelocity()));
    SmartDashboard.putNumber("frontrightmotor native units", Math.ceil(drive.frontRightMotor.getSelectedSensorVelocity()));
    SmartDashboard.putNumber("backrightmotor native units", Math.ceil(drive.backRightMotor.getSelectedSensorVelocity()));
    SmartDashboard.putNumber("backleftmotor native units", Math.ceil(drive.backLeftMotor.getSelectedSensorVelocity()));


    
    double leftSlider, rightSlider;

    xControl.desired = checkDeadband(RobotContainer.leftJoystick, RobotMap.HORIZONTAL_AXIS, .2);
    zControl.desired = checkDeadband(RobotContainer.leftJoystick, RobotMap.VERTICAL_AXIS, .2);
    rControl.desired = checkDeadband(RobotContainer.rightJoystick, RobotMap.ROTATIONAL_AXIS, .2);

    SmartDashboard.putNumber("desired", rControl.desired);

    leftSlider = -RobotContainer.leftJoystick.getRawAxis(RobotMap.SLIDER_AXIS);
    rightSlider = -RobotContainer.rightJoystick.getRawAxis(RobotMap.SLIDER_AXIS); //setting parts
    
    leftSlider = (leftSlider + 1) / 2;
    rightSlider = (rightSlider + 1) / 2; //sliders to limit speed

    xControl.desired *= leftSlider;
    zControl.desired *= leftSlider;
    rControl.desired *= rightSlider;

    if (checkDeadband(RobotContainer.rightJoystick, RobotMap.ROTATIONAL_AXIS, .2) != 0) {
      rControl.error = (rControl.desired) - RPM;
      zControl.error = 0;
      xControl.error = 0;
    }
    else {
      xControl.error = (xControl.desired) - RPM;
      zControl.error = (zControl.desired) - RPM;  
      rControl.error = 0;
    }
   
    SmartDashboard.putNumber("R Error", rControl.error);

    xControl.getIntergralZone();
    zControl.getIntergralZone();
    rControl.getIntergralZone();
    SmartDashboard.putNumber("r integral top", rControl.integralTop);


    xControl.getPidOut();
    zControl.getPidOut();
    rControl.getPidOut();

    drive.mecanumDrive(xControl.Output() * 2, zControl.Output() * 2, rControl.Output() * 2);  
    //drive.mecanumDrive(xControl.desired, zControl.desired, rControl.desired);
    SmartDashboard.putNumber("Volocity: Native Units", drive.frontLeftMotor.getSelectedSensorVelocity()); //get native unit volocity
    if (drive.frontLeftMotor.getSelectedSensorVelocity() == 0){
      SmartDashboard.putNumber("Volocity: RPM", 0);  }
    else { SmartDashboard.putNumber("Volocity: RPM", RPM); //get rounded RPM
   }
   //SmartDashboard.putNumber("xControl Out", xControl.Output());
   //SmartDashboard.putNumber("zControl Out", zControl.Output());
   SmartDashboard.putNumber("rControl Out", rControl.Output());
   SmartDashboard.putNumber("Proportional R", rControl.proportionalOutput);

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
