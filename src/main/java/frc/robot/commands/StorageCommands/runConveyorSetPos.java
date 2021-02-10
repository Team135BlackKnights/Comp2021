package frc.robot.commands.StorageCommands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Storage;

public class runConveyorSetPos extends CommandBase{

private final Storage storage;
  private final double _targetPos;
  private boolean isFinished = false;
  public runConveyorSetPos(Storage subsystem, double targetPos) {
  storage = subsystem;
  this._targetPos = targetPos;
  }

  @Override
  public void execute() {
    double currentConveyPos = storage.conveyorEncoder.getPosition() / 4096;
    if((_targetPos)!=currentConveyPos)
    {
      storage.conveyorMotor.set(.65);
      isFinished = false;
    }
    else 
    {
        storage.conveyorMotor.set(0);
      isFinished = true;
    }
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return isFinished;
  }
}
