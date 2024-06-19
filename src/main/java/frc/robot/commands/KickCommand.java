package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.FeederSubsystem;

public class KickCommand extends Command {
    private final FeederSubsystem feeder;

    public KickCommand(FeederSubsystem fm) {
        this.feeder = fm;
        addRequirements(feeder);
    }

    @Override
    public void initialize() {
        feeder.setFeeder(0);
    }

    @Override
    public void execute() {
        feeder.setFeeder(0.3);
    }

    @Override
    public void end(boolean Interrupted) {
        feeder.stop();
    }

}
