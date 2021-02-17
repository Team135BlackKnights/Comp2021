package frc.robot.subsystems;

import com.revrobotics.CANEncoder;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.RobotMap;

public class Storage extends SubsystemBase implements RobotMap.STORAGE {
    
    public CANSparkMax conveyorMotor;
    public CANEncoder conveyorEncoder;
    public DigitalInput intakeBallSwitch;

    public int currentBallCount = 0;
    public boolean lastSwtichPosition = false;

    public Storage(){

        conveyorMotor = new CANSparkMax(CONVEYOR_SPARK, MotorType.kBrushless); //set the motor to the motor

        conveyorMotor.setInverted(false);
        conveyorMotor.enableVoltageCompensation(12);
        conveyorMotor.getBusVoltage();
        conveyorMotor.setIdleMode(IdleMode.kBrake); //motor setup
    
        conveyorEncoder = conveyorMotor.getEncoder(); //set the encoder
    
        intakeBallSwitch = new DigitalInput(INTAKE_TRIP_SWITCH); //set the switch

        System.out.println("Storage Initialized: Talking to Intake");
    }

    @Override
    public void periodic(){

        if(intakeBallSwitch.get() != lastSwtichPosition && intakeBallSwitch.get()) //check to see if ball is at intake
        {
          currentBallCount++;
        }
        else if(!intakeBallSwitch.get()) //check to see if it is the same ball
        {
          lastSwtichPosition = intakeBallSwitch.get(); //update switch pos
        }
    }
    
}
