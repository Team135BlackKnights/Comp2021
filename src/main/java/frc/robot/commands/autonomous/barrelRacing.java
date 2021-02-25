package frc.robot.commands.autonomous;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.DriveCommands.angleDrive;
import frc.robot.commands.DriveCommands.encoderDrive;
import frc.robot.subsystems.Drive;

public class barrelRacing extends SequentialCommandGroup{
    public barrelRacing (Drive drive) {
        super(
            parallel(
            new encoderDrive(drive, 120, 0),
            new encoderDrive(drive, 0, 20),
            new encoderDrive(drive, -20, 0),
            new encoderDrive(drive, 0, -20)
            )  
        );
    }
}
