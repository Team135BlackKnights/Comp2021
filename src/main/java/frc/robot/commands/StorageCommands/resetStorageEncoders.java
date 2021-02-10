package frc.robot.commands.StorageCommands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Storage;

public class resetStorageEncoders extends CommandBase {

    public resetStorageEncoders(Storage subsystem) {
        Storage storage = subsystem;
        storage.conveyorEncoder.setPosition(0); //reset the conveyor encoder
    }

    @Override
    public boolean isFinished() {
      return false;
    }
}
*/