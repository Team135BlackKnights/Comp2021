// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;

public class Drive extends SubsystemBase implements frc.robot.RobotMap {

  public WPI_TalonFX frontRightMotor, frontLeftMotor, backRightMotor, backLeftMotor, centralMotor;
  public SpeedControllerGroup leftMotors, rightMotors;
  public DifferentialDrive differentialDrive;

  public Drive() {
    frontRightMotor = new WPI_TalonFX(FRONT_RIGHT_MOTOR_ID);
    frontLeftMotor = new WPI_TalonFX(FRONT_LEFT_MOTOR_ID);
    backRightMotor = new WPI_TalonFX(BACK_RIGHT_MOTOR_ID);
    backLeftMotor = new WPI_TalonFX(BACK_LEFT_MOTOR_ID);
    centralMotor = new WPI_TalonFX(CENTRAL_MOTOR_ID);

    
    leftMotors = new SpeedControllerGroup(frontLeftMotor, backLeftMotor);
    rightMotors = new SpeedControllerGroup(frontRightMotor, backRightMotor);
     
    differentialDrive = new DifferentialDrive(leftMotors, rightMotors);
    
  }

  public void ArcadeDrive (double lateralPower, double rotatePower) {
    differentialDrive.arcadeDrive(lateralPower, rotatePower);
  }

  public double getPosition(WPI_TalonFX motor) {
    return motor.getSelectedSensorPosition();
  }

  public double getLeftDistance() {
    return (getPosition(frontLeftMotor) + getPosition(backLeftMotor)) / 2;
  }

  public double getRightDistance() {
    return (getPosition(frontRightMotor) + getPosition(backRightMotor)) / 2;
  }

  public double getCentralDistance() {
    return getPosition(centralMotor);
  }

  public void resetEncoders () {
    frontLeftMotor.setSelectedSensorPosition(0);
    frontRightMotor.setSelectedSensorPosition(0);
    backLeftMotor.setSelectedSensorPosition(0);
    backRightMotor.setSelectedSensorPosition(0);
    centralMotor.setSelectedSensorPosition(0);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }

  @Override
  public void simulationPeriodic() {
    // This method will be called once per scheduler run during simulation
  }
}
