// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import frc.robot.RobotMap;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

public class Drive extends SubsystemBase implements frc.robot.RobotMap {
  /** Creates a new ExampleSubsystem. */
  public WPI_TalonFX frontRightMotor, frontLeftMotor, backRightMotor, backLeftMotor, centralMotor;
  public Drive() {
    frontRightMotor = new WPI_TalonFX(FRONT_LEFT_MOTOR_ID);
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
