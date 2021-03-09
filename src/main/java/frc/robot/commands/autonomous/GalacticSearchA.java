package frc.robot.commands.autonomous;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.DriveCommands.encoderDrive;
import frc.robot.subsystems.Drive;

public class GalacticSearchA extends SequentialCommandGroup{
    public GalacticSearchA (Drive drive) {
        if (true){pathB(drive);}
    }

    void pathB(Drive drive){
            sequence(
            new encoderDrive(drive, 50, 0),
            new encoderDrive(drive, 0, 50)
            );      
    }
}