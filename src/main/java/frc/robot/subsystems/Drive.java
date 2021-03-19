package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;

import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.drive.MecanumDrive;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import com.kauailabs.navx.frc.*;
import edu.wpi.first.wpilibj.I2C;

public class Drive extends SubsystemBase implements frc.robot.RobotMap {
  private final I2C.Port i2cPort = I2C.Port.kOnboard;
  public WPI_TalonFX frontRightMotor, frontLeftMotor, backRightMotor, backLeftMotor, centralMotor;
  public SpeedControllerGroup frontLeftMotorCG, frontRightMotorCG, backLeftMotorCG, backRightMotorCG;
  public MecanumDrive mecanumDrive;
  public SpeedControllerGroup leftMotors, rightMotors;
  public DifferentialDrive differentialDrive;
  public static AHRS navx;

  public Drive() {
    frontRightMotor = new WPI_TalonFX(FRONT_RIGHT_MOTOR_ID);
    frontLeftMotor = new WPI_TalonFX(FRONT_LEFT_MOTOR_ID);
    backRightMotor = new WPI_TalonFX(BACK_RIGHT_MOTOR_ID);
    backLeftMotor = new WPI_TalonFX(BACK_LEFT_MOTOR_ID);

    voltageComp(frontLeftMotor);
    voltageComp(frontRightMotor);
    voltageComp(backLeftMotor);
    voltageComp(backRightMotor);

    frontLeftMotor.setSafetyEnabled(false);
    frontRightMotor.setSafetyEnabled(false);
    backLeftMotor.setSafetyEnabled(false);
    backRightMotor.setSafetyEnabled(false);//disable child safety

    frontLeftMotorCG = new SpeedControllerGroup(frontLeftMotor);
    frontRightMotorCG = new SpeedControllerGroup(frontRightMotor);
    backLeftMotorCG = new SpeedControllerGroup(backLeftMotor);
    backRightMotorCG = new SpeedControllerGroup(backRightMotor);

    setBrakeMode(NeutralMode.Brake);

    mecanumDrive = new MecanumDrive(frontLeftMotorCG, backLeftMotorCG, frontRightMotorCG, backRightMotorCG);

    navx = new AHRS(i2cPort);
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

  public void mecanumDrive(double xSpeed, double ySpeed, double zRotation) {
    mecanumDrive.driveCartesian(xSpeed, ySpeed, zRotation);
  }

  public double getSideDistance() {
    return (((frontLeftMotor.getSelectedSensorPosition() / 788) + 
     (backLeftMotor.getSelectedSensorPosition() / 788)) / 2); // avrage side pos
  }

  public double getFrontDistance() {
    return (((frontLeftMotor.getSelectedSensorPosition() / 788) +
     (frontRightMotor.getSelectedSensorPosition() / 788)) / 2); // avrage front side pos
  }

  public void resetEncoders() {
    frontLeftMotor.setSelectedSensorPosition(0);
    frontRightMotor.setSelectedSensorPosition(0);
    backLeftMotor.setSelectedSensorPosition(0);
    backRightMotor.setSelectedSensorPosition(0);
  }
}