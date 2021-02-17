package frc.robot.subsystems;

import com.revrobotics.CANEncoder;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.RobotMap;

public class Shooter extends SubsystemBase implements RobotMap.SHOOTER {
    public CANSparkMax shooterPitchMotor;
    public CANEncoder shooterPitchEncoder;

    public CANSparkMax shooterLauncherMasterMotor;
    public CANSparkMax shooterLauncherSlaveMotor;
    public CANEncoder shooterLauncherMasterEncoder;

    public Shooter(){
       //shooter motor config
       shooterPitchMotor = new CANSparkMax (SHOOTER_PICH_SPARK, MotorType.kBrushless); //assigns the code to motor
       shooterPitchMotor.setIdleMode(IdleMode.kBrake);
       shooterPitchMotor.enableVoltageCompensation(12);
       shooterPitchMotor.getBusVoltage();
       shooterPitchEncoder = shooterPitchMotor.getEncoder();

       shooterLauncherSlaveMotor = new CANSparkMax (SHOOTER_LAUNCHER_SLAVE);
       shooterLauncherSlaveMotor.follow(shooterLauncherMasterMotor);


 

    }
}
