package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.RobotMap;

public class Shooter extends SubsystemBase implements RobotMap.SHOOTER {
    public WPI_TalonFX shooterPitchMotor;

    public WPI_TalonFX shooterLauncherLeaderMotor;
    public WPI_TalonFX shooterLauncherFollowerMotor;


    static NetworkTableInstance networkTableInstance = NetworkTableInstance.getDefault();
    static NetworkTable TurretLimelightTable = networkTableInstance.getTable("limelight-turret");
    NetworkTableEntry validTargetEntry = TurretLimelightTable.getEntry("tv"),
        horizontalOffsetEntry = TurretLimelightTable.getEntry("tx");

    public static NetworkTableEntry verticalOffsetEntry = TurretLimelightTable.getEntry("ty");

	NetworkTableEntry targetAreaEntry = TurretLimelightTable.getEntry("ta");

	NetworkTableEntry targetSkewEntry = TurretLimelightTable.getEntry("tl");

	NetworkTableEntry ledModeEntry = TurretLimelightTable.getEntry("ledMode");

	NetworkTableEntry cameraModeEntry = TurretLimelightTable.getEntry("camMode");

	NetworkTableEntry limelightPipelineEntry = TurretLimelightTable.getEntry("pipeline");
      public double[] limelightData = new double[5];

     

    public Shooter(){
       //shooter motor config
       shooterPitchMotor = new WPI_TalonFX(SHOOTER_PICH_SPARK); //assigns the code to motor
       voltageComp(shooterPitchMotor);

       shooterLauncherLeaderMotor = new WPI_TalonFX(SHOOTER_LAUNCHER_LEADER);
       voltageComp(shooterLauncherLeaderMotor);

       shooterLauncherFollowerMotor = new WPI_TalonFX(SHOOTER_LAUNCHER_FOLLOWER);
       shooterLauncherFollowerMotor.follow(shooterLauncherLeaderMotor);
       shooterLauncherFollowerMotor.setInverted(true);
       voltageComp(shooterLauncherFollowerMotor); 
      
       limelightPipelineEntry.setNumber(1);
       ledModeEntry.setNumber(1); 
       
      }
    public void voltageComp(TalonFX talon) {
        talon.enableVoltageCompensation(true);
        talon.configVoltageCompSaturation(12);
    }

    
}
