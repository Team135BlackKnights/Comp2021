package frc.robot.commands.autonomous.GalcacticSearch;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.DriveCommands.encoderDrive;
import frc.robot.subsystems.Drive;

public class GalacticSearchBlueB extends SequentialCommandGroup{
    public GalacticSearchBlueB (Drive drive) {
        super(
            sequence(
            new encoderDrive(drive, 10, 0)
            )  
        );
    }
}