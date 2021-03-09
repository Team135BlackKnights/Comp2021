package frc.robot.commands.autonomous;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.DriveCommands.angleDrive;
import frc.robot.commands.DriveCommands.encoderDrive;
import frc.robot.commands.DriveCommands.resetEncoders;
import frc.robot.subsystems.Drive;

public class slalam extends SequentialCommandGroup{
    public slalam (Drive drive) {
        super(
            sequence(
            new resetEncoders(drive),
            new encoderDrive(drive, 0, 30),
            new angleDrive(drive, -90), //at e3
            new encoderDrive(drive, 0, 60),
            new angleDrive(drive, 0), //at 
            new encoderDrive(drive, 0, 180)

            )  
        );
    }
}
