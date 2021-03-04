package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.RobotMap;

public class Shooter extends SubsystemBase implements RobotMap.SHOOTER {
    public WPI_TalonFX shooterPitchMotor;

    public WPI_TalonFX shooterLauncherLeaderMotor;
    public WPI_TalonFX shooterLauncherFollowerMotor;

    public Shooter(){
       //shooter motor config
       shooterPitchMotor = new WPI_TalonFX(SHOOTER_PICH_SPARK); //assigns the code to motor
       voltageComp(shooterPitchMotor);

       shooterLauncherFollowerMotor = new WPI_TalonFX(SHOOTER_LAUNCHER_FOLLOWER);
       shooterLauncherFollowerMotor.follow(shooterLauncherLeaderMotor);
       voltageComp(shooterLauncherFollowerMotor);

       shooterLauncherLeaderMotor = new WPI_TalonFX(SHOOTER_LAUNCHER_LEADER);
       voltageComp(shooterLauncherLeaderMotor);
       
    }
    public void voltageComp(TalonFX talon) {
        talon.enableVoltageCompensation(true);
        talon.configVoltageCompSaturation(12);
      }

    
}
