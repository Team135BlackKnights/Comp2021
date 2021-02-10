package frc.robot.commands.storage;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Storage;

public class runConveyor extends CommandBase {

    private final Storage storage;
    private Joystick joystick; 
    
    public runConveyor(Storage subsystem, Joystick _joystick) {
        storage = subsystem; //set the subsystem
        joystick = _joystick;
      }

      @Override
      public void execute() {
    
        boolean isBallTrip = storage.intakeBallSwitch.get();
        double currentConveyPos = storage.conveyorEncoder.getPosition() / 4096;
        double conveyorPower = 0;    

        boolean isButton7 = joystick.getRawButton(7);
        boolean isButton8 = joystick.getRawButton(8);

        if(isBallTrip)
        {
           storage.conveyorEncoder.setPosition(0);
        }
        if(currentConveyPos <= 7 && !(isButton7 || isButton8)) //if there is no input move naturly
        {
          conveyorPower = .65;
          SmartDashboard.putString("CONVEYOR OVERRIDE:", "CONVEYOR NOT OVERWROTE");
        }
        else if (isButton7) //override to move forward
        {
          conveyorPower = .65;
          SmartDashboard.putString("CONVEYOR OVERRIDE:", "CONVEYOR GOING UP");
        } 
        else if (isButton8) //override to move backward
        {
          conveyorPower = -.65;
          SmartDashboard.putString("CONVEYOR OVERRIDE:", "CONVEYOR GOING DOWN");
        }
        else //cut power
        {
          conveyorPower = 0;
          SmartDashboard.putString("CONVEYOR OVERRIDE:", "CONVEYOR NOT OVERWROTE");
        }  
            
        storage.conveyorMotor.set(conveyorPower); //set the power
      }

      @Override
      public boolean isFinished() {
        return false;
      }    
}