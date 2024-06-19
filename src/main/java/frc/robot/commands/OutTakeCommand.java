package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.HopperSubsystem;
import frc.robot.subsystems.IntakeSubsystem;

public class OutTakeCommand extends Command {
    private final IntakeSubsystem intake;
    private final HopperSubsystem hopper;

    public OutTakeCommand(IntakeSubsystem intake, HopperSubsystem hopper) {
        this.intake = intake;
        this.hopper = hopper;
        addRequirements(intake, hopper);
    }

    @Override
    public void initialize() {
        intake.setIntake(0);
        hopper.setHopper(0);
    }

    @Override
    public void execute() {
        intake.setIntake(-0.4);
        hopper.setHopper(-0.4);
    }

    @Override
    public void end(boolean Interrupted) {
        intake.stopIntake();
        hopper.stopHopper();
    }
}