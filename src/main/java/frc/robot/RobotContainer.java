// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.commands.DriveCommands.angleDrive;
import frc.robot.commands.DriveCommands.encoderDrive;
import frc.robot.commands.DriveCommands.motorSpecificControl;
import frc.robot.commands.IntakeCommands.fireSolenoid;
import frc.robot.commands.autonomous.barrelRacing;
import frc.robot.subsystems.Drive;
import frc.robot.subsystems.Intake;

public class RobotContainer implements RobotMap {

  // The robot's subsystems and commands are defined here...

  public static Joystick leftJoystick = new Joystick(1);
  public static Joystick rightJoystick = new Joystick(2);
  public static Joystick manipJoystick = new Joystick(0);

  public static JoystickButton rightTrigger = new JoystickButton(rightJoystick, KOI.TRIGGER_BUTTON),
      rightThumb = new JoystickButton(rightJoystick, KOI.THUMB_BUTTON),
      rightButton3 = new JoystickButton(rightJoystick, KOI.HANDLE_BOTTOM_LEFT_BUTTON),
      rightButton5 = new JoystickButton(rightJoystick, KOI.HANDLE_TOP_LEFT_BUTTON),
      rightButton6 = new JoystickButton(rightJoystick, KOI.HANDLE_TOP_RIGHT_BUTTON),
      rightButton10 = new JoystickButton(rightJoystick, KOI.BASE_MIDDLE_RIGHT_BUTTON),
      rightButton11 = new JoystickButton(rightJoystick, KOI.BASE_BOTTOM_LEFT_BUTTON),
      rightButton12 = new JoystickButton(rightJoystick, KOI.BASE_BOTTOM_RIGHT_BUTTON),
      leftTrigger = new JoystickButton(leftJoystick, KOI.TRIGGER_BUTTON),
      leftThumb = new JoystickButton(leftJoystick, KOI.THUMB_BUTTON),

      leftButton3 = new JoystickButton(leftJoystick, KOI.HANDLE_BOTTOM_LEFT_BUTTON),
      leftButton4 = new JoystickButton(leftJoystick, KOI.HANDLE_BOTTOM_RIGHT_BUTTON),
      leftButton7 = new JoystickButton(leftJoystick, KOI.BASE_TOP_LEFT_BUTTON),
      leftButton8 = new JoystickButton(leftJoystick, KOI.BASE_TOP_RIGHT_BUTTON),
      leftButton9 = new JoystickButton(leftJoystick, KOI.BASE_MIDDLE_LEFT_BUTTON),
      leftButton10 = new JoystickButton(leftJoystick, KOI.BASE_MIDDLE_RIGHT_BUTTON),
      leftButton11 = new JoystickButton(leftJoystick, KOI.BASE_BOTTOM_LEFT_BUTTON),
      leftButton12 = new JoystickButton(leftJoystick, KOI.BASE_BOTTOM_RIGHT_BUTTON),

      manipTrigger = new JoystickButton(manipJoystick, KOI.TRIGGER_BUTTON),
      manipThumb = new JoystickButton(manipJoystick, KOI.THUMB_BUTTON),
      manipButton3 = new JoystickButton(manipJoystick, KOI.HANDLE_BOTTOM_LEFT_BUTTON),
      manipButton4 = new JoystickButton(manipJoystick, KOI.HANDLE_BOTTOM_RIGHT_BUTTON),
      manipButton5 = new JoystickButton(manipJoystick, KOI.HANDLE_TOP_LEFT_BUTTON),
      manipButton7 = new JoystickButton(manipJoystick, KOI.BASE_TOP_LEFT_BUTTON),
      manipButton8 = new JoystickButton(manipJoystick, KOI.BASE_TOP_RIGHT_BUTTON),
      manipButton9 = new JoystickButton(manipJoystick, KOI.BASE_MIDDLE_LEFT_BUTTON),
      manipButton10 = new JoystickButton(manipJoystick, KOI.BASE_MIDDLE_RIGHT_BUTTON),
      manipButton11 = new JoystickButton(manipJoystick, KOI.BASE_BOTTOM_LEFT_BUTTON),
      manipButton12 = new JoystickButton(manipJoystick, KOI.BASE_BOTTOM_RIGHT_BUTTON);

  public static Intake intake = new Intake();
  public static Drive drive = new Drive();

  /**
   * The container for the robot. Contains subsystems, OI devices, and commands.
   */
  public RobotContainer() {

    // Configure the button bindings
    drive.setDefaultCommand(new mecanumDrive(drive));
    
    configureButtonBindings();
  }

  private void configureButtonBindings() {

    rightButton3.whenPressed(new angleDrive(drive, 80));
    manipButton4.whenPressed(new fireSolenoid(intake));
    rightButton5.whenPressed(new encoderDrive(drive, 0, 20));
    leftButton4.whenPressed(new barrelRacing(drive));

  }

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
