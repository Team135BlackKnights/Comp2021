package frc.robot.subsystems;

import com.revrobotics.CANEncoder;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.RobotMap;

public class Intake extends SubsystemBase implements RobotMap.INTAKE {
    
    public CANSparkMax rollerMotor;
    public CANEncoder rollerEncoder;

    public Compressor compressor;

    public Intake() {
        //roller motor configuration
        rollerMotor = new CANSparkMax(ROLLER_SPARK, MotorType.kBrushless);
        rollerMotor.setIdleMode(IdleMode.kBrake);
        rollerMotor.enableVoltageCompensation(12);
        rollerMotor.setSmartCurrentLimit(30);
        rollerEncoder = rollerMotor.getEncoder();
        
        //Setup/start compressor
        compressor = new Compressor();
        compressor.setClosedLoopControl(false);
        compressor.start();
    }
}