package frc.robot.subsystems;

import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;

public class FrontLimelight extends SubsystemBase {

    private static FrontLimelight instance;

    NetworkTableInstance networkTableInstance = NetworkTableInstance.getDefault();
	NetworkTable TurretLimelightTable = networkTableInstance.getTable("limelight-turret");
	NetworkTableEntry 
    validTargetEntry = TurretLimelightTable.getEntry("tv"),
    ledModeEntry = TurretLimelightTable.getEntry("ledMode"),
	cameraModeEntry = TurretLimelightTable.getEntry("camMode"),
    limelightPipelineEntry = TurretLimelightTable.getEntry("pipeline");

    public static boolean isTargetVisable = false;

    @Override
    public void periodic() 	
	{
        if (validTargetEntry.getDouble(0.0) >=1){isTargetVisable = true;}
        else {isTargetVisable = false;}
        SmartDashboard.putBoolean("Target Exist", isTargetVisable); //retuns if the limelight sees a target
	}

	public static FrontLimelight getInstance() {if (instance == null) {instance = new FrontLimelight();}return instance;}
    
}
