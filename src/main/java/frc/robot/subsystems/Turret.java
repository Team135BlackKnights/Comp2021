package frc.robot.subsystems;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;

public class Turret {
    NetworkTableInstance networkTableInstance = NetworkTableInstance.getDefault();
    NetworkTable TurretLimelightTable = networkTableInstance.getTable("limelight-turret");
    NetworkTableEntry validTargetEntry = TurretLimelightTable.getEntry("tv"),
        horizontalOffsetEntry = TurretLimelightTable.getEntry("tx"),
        verticalOffsetEntry = TurretLimelightTable.getEntry("ty"), 
        targetAreaEntry = TurretLimelightTable.getEntry("ta"),
        targetSkewEntry = TurretLimelightTable.getEntry("tl"), 
        ledModeEntry = TurretLimelightTable.getEntry("ledMode"),
        cameraModeEntry = TurretLimelightTable.getEntry("camMode"),
        limelightPipelineEntry = TurretLimelightTable.getEntry("pipeline");
    public double[] limelightData = new double[5];

    public static final int VALID_TARGET = 0, HORIZONTAL_OFFSET = 1, 
        VERTICAL_OFFSET = 2, TARGET_AREA = 3, TARGET_SKEW = 4,
        LED_ON = 0, LED_OFF = 1, LED_BLINKING = 2,
        VISION_PROCESSOR = 0, DRIVER_CAMERA = 1,      
        REFLECTIVE_TAPE = 0, DRIVER_VISION = 1;


    public Turret() {

        limelightPipelineEntry.setNumber(1);
        ledModeEntry.setNumber(1);   
     }


    public double[] getLimelightData() {
        limelightData[VALID_TARGET] = validTargetEntry.getDouble(0);
        limelightData[HORIZONTAL_OFFSET] = horizontalOffsetEntry.getDouble(0);
        limelightData[VERTICAL_OFFSET] = verticalOffsetEntry.getDouble(0);
        limelightData[TARGET_AREA] = targetAreaEntry.getDouble(0);
        limelightData[TARGET_SKEW] = targetSkewEntry.getDouble(0);
    
        return limelightData;
    }
    
}
