package frc.robot.commands.autonomous;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.DriveCommands.angleDrive;
import frc.robot.commands.DriveCommands.encoderDrive;
import frc.robot.commands.DriveCommands.resetEncoders;
import frc.robot.subsystems.Drive;

public class barrelRacing extends SequentialCommandGroup{
    public barrelRacing (Drive drive) {
        super(
            sequence(
            new resetEncoders(drive),
            new encoderDrive(drive, 0, 135)
            /*
            new angleDrive(drive, 90), //at c6
            new encoderDrive(drive, 0, 50),
            new angleDrive(drive, 180),//at e6
            new encoderDrive(drive, 0, 60),
            new angleDrive(drive, 270), //at e4
            new encoderDrive(drive, 0, 60),
            new angleDrive(drive, 8),//at c4
            new encoderDrive(drive, 0, 130),
            new angleDrive(drive, -45),//at c8
            new encoderDrive(drive, 0, 43),
            new angleDrive(drive, -135),//at b9

            new encoderDrive(drive, 0, 46),
            new angleDrive(drive, 135),//at a8
            new encoderDrive(drive, 0, 48),
            new angleDrive(drive, 45),//at b7
            new encoderDrive(drive, 0, 132),
            new angleDrive(drive, -45),//at e10
            new encoderDrive(drive, 0, 43),
            new angleDrive(drive, -135),//at d11
            new encoderDrive(drive, 0, 50),
            new angleDrive(drive, 178),//at c10
            new encoderDrive(drive, 0, 270) 
            */
            )  
        );
    }
}
