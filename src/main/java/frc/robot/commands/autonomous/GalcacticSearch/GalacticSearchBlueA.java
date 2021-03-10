package frc.robot.commands.autonomous.GalcacticSearch;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.DriveCommands.encoderDrive;
import frc.robot.subsystems.Drive;

public class GalacticSearchBlueA extends SequentialCommandGroup{
    public GalacticSearchBlueA (Drive drive) {
        super (
            sequence(
                new encoderDrive(drive, 0, 0)
            )
        );
    }
}