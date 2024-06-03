package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.ClimberSubsystem;

public class ClimbCommand extends Command {
    private final ClimberSubsystem climberSubsystem;
    private final double leftSpeed, rightSpeed;

    public ClimbCommand(ClimberSubsystem climberSubsystem, double leftSpeed, double rightSpeed) {
        this.climberSubsystem = climberSubsystem;
        this.leftSpeed = leftSpeed;
        this.rightSpeed = rightSpeed;
        addRequirements(climberSubsystem);
    }

    @Override
    public void initialize() {
        climberSubsystem.stopClimber();
    }

    @Override
    public void execute() {
        climberSubsystem.setLeftClimber(leftSpeed);
        climberSubsystem.setRightClimber(rightSpeed);
    }

    @Override
    public void end(boolean interrupted) {
        climberSubsystem.stopClimber();
    }
}
