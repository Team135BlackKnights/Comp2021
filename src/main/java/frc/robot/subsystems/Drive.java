// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import java.util.ArrayList;

import com.ctre.phoenix.music.*;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;

import edu.wpi.first.wpilibj.SerialPort;// (for navX)
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.drive.MecanumDrive;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import com.kauailabs.navx.frc.*;

public class Drive extends SubsystemBase implements frc.robot.RobotMap {

  public WPI_TalonFX frontRightMotor, frontLeftMotor, backRightMotor, backLeftMotor, centralMotor;
  public SpeedControllerGroup frontLeftMotorCG, frontRightMotorCG, backLeftMotorCG, backRightMotorCG;
  public MecanumDrive mecanumDrive;
  public SpeedControllerGroup leftMotors, rightMotors;
  public DifferentialDrive differentialDrive;
  public AHRS navx;
  public SerialPort.Port navXPort = SerialPort.Port.kUSB;


  public Drive() {
    frontRightMotor = new WPI_TalonFX(FRONT_RIGHT_MOTOR_ID);
    frontLeftMotor = new WPI_TalonFX(FRONT_LEFT_MOTOR_ID);
    backRightMotor = new WPI_TalonFX(BACK_RIGHT_MOTOR_ID);
    backLeftMotor = new WPI_TalonFX(BACK_LEFT_MOTOR_ID);
    centralMotor = new WPI_TalonFX(CENTRAL_MOTOR_ID); //set motor to robotmap ids

    Orchestra _orchestra;

    /* A list of TalonFX's that are to be used as instruments */
    ArrayList < TalonFX > _instruments = new ArrayList < TalonFX > ();

    /* Initialize the TalonFX's to be used */
    _instruments.add(frontLeftMotor);
    _instruments.add(frontRightMotor);
    _instruments.add(backLeftMotor);
    _instruments.add(backRightMotor);

    /* Create the orchestra with the TalonFX instruments */
    _orchestra = new Orchestra(_instruments);

    _orchestra.loadMusic("RAT.chrp");
    System.out.println("Auto-playing song.");
    _orchestra.play();

    voltageComp(frontLeftMotor);
    voltageComp(frontRightMotor);
    voltageComp(backLeftMotor);
    voltageComp(backRightMotor);

    frontLeftMotorCG = new SpeedControllerGroup(frontLeftMotor);
    frontRightMotorCG = new SpeedControllerGroup(frontRightMotor);
    backLeftMotorCG = new SpeedControllerGroup(backLeftMotor);
    backRightMotorCG = new SpeedControllerGroup(backRightMotor);

    setBrakeMode(NeutralMode.Brake);

    mecanumDrive = new MecanumDrive(frontLeftMotorCG, backLeftMotorCG, frontRightMotorCG, backRightMotorCG);
    /*
    leftMotors = new SpeedControllerGroup(frontLeftMotor, backLeftMotor);
    rightMotors = new SpeedControllerGroup(frontRightMotor, backRightMotor); //create speed controller grouos
     
    differentialDrive = new DifferentialDrive(leftMotors, rightMotors); //create the differential drive
    */
    navx = new AHRS(navXPort);
    navx.reset();
  }

  public void voltageComp(TalonFX talon) {
    talon.enableVoltageCompensation(true);
    talon.configVoltageCompSaturation(12);
  }


  public void setBrakeMode(NeutralMode neutralMode) {
    frontRightMotor.setNeutralMode(neutralMode);
    frontLeftMotor.setNeutralMode(neutralMode);
    backRightMotor.setNeutralMode(neutralMode);
    backLeftMotor.setNeutralMode(neutralMode);
  }

  public void ArcadeDrive (double lateralPower, double rotatePower) {
    differentialDrive.arcadeDrive(lateralPower, rotatePower); //drive function
  }
  public void mecanumDrive (double xSpeed, double ySpeed, double zRotation) {
    mecanumDrive.driveCartesian(xSpeed, ySpeed, zRotation);
  }

  public double getPosition(WPI_TalonFX motor) {
    return motor.getSelectedSensorPosition(); //get the motors pos
  }

  public double getLeftDistance() {
    return (getPosition(frontLeftMotor) + getPosition(backLeftMotor)) / 2; //avrage left side pos
  }

  public double getRightDistance() {
    return (getPosition(frontRightMotor) + getPosition(backRightMotor)) / 2; //avrage right side pos
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
