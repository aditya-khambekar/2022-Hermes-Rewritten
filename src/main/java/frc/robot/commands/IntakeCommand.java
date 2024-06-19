package frc.robot.commands;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants;
import frc.robot.oi.OI;
import frc.robot.subsystems.HopperSubsystem;
import frc.robot.subsystems.IntakeSubsystem;

public class IntakeCommand extends Command {
    private final IntakeSubsystem intake;
    private final HopperSubsystem hopper;
    private final OI oi = OI.getInstance();
    public final DigitalInput ballOccupied = new DigitalInput(Constants.feederSensor);

    public IntakeCommand(IntakeSubsystem intake, HopperSubsystem hopper) {
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
        intake.setIntake(0.7);
        hopper.setHopper(0.7);
    }

    @Override
    public void end(boolean Interrupted) {
        intake.stopIntake();
        hopper.stopHopper();
    }
}