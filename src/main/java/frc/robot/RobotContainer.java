// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.commands.mecanumDrive;
import frc.robot.subsystems.Drive;

public class RobotContainer {
  // The robot's subsystems and commands are defined here...
  
  public static Joystick leftJoystick = new Joystick(2);
  public static Joystick rightJoystick = new Joystick(1);
  public static Joystick manipJoystick = new Joystick(0);

  public static Drive drive = new Drive();

  /** The container for the robot. Contains subsystems, OI devices, and commands. */
  public RobotContainer() {
    // Configure the button bindings
    drive.setDefaultCommand(new mecanumDrive(drive));
    configureButtonBindings();
  }

  private void configureButtonBindings() {}

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    // An ExampleCommand will run in autonomous
    return null;
  }
}
