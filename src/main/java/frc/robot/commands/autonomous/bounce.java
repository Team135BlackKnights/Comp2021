package frc.robot.commands.autonomous;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.DriveCommands.angleDrive;
import frc.robot.commands.DriveCommands.encoderDrive;
import frc.robot.subsystems.Drive;

public class bounce extends SequentialCommandGroup{
    public bounce (Drive drive) {
        super(
            sequence(
            new encoderDrive(drive, 10, 0),
            new angleDrive(drive, 90),
            new encoderDrive(drive, 0, 10)
            )  
        );
    }
}
