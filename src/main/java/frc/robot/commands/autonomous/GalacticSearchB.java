package frc.robot.commands.autonomous;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.DriveCommands.encoderDrive;
import frc.robot.subsystems.Drive;

public class GalacticSearchB extends SequentialCommandGroup{
    public GalacticSearchB (Drive drive) {
        super(
            sequence(
            new encoderDrive(drive, 10, 0)
            )  
        );
    }
}