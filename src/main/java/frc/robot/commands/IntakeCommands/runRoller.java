package frc.robot.commands.IntakeCommands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Intake;

public class runRoller extends CommandBase {
    private final Intake intake;
    private double desiredRPM;
    private double kP = 2, kI, kD;

    public runRoller (Intake subsystem, double _desiredRPM) {
        intake = subsystem;
        desiredRPM = _desiredRPM;
    }

    @Override
    public void initialize() {
        SmartDashboard.putString("Intake Command Running: ", "Run Roller " + desiredRPM);
    }

    @Override
    public void execute() {
        //Find the rpm the roller currently is at
        double currentRPM = intake.rollerEncoder.getVelocity();

        //How far off the current rpm is from the desired
        double error = desiredRPM - currentRPM;

        //If the error is greater then 1000 rpm then keep power full
        double proportionalOutput = (error/1000) * kP;

        //set output bassed off proportional
        double input = proportionalOutput;
        intake.rollerMotor.set(input);

    }

    @Override
    public void end(boolean interrupted) {
        SmartDashboard.putString("Intake Command Running: ", "No Command Running");
        intake.rollerMotor.set(0);
    }
    @Override
    public boolean isFinished() {
        return false;
    }
}
