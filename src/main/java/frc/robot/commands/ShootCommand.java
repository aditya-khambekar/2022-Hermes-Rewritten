package frc.robot.commands;

import edu.wpi.first.wpilibj.util.Color;
import edu.wpi.first.wpilibj.util.Color8Bit;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.led.LEDStrip;
import frc.robot.subsystems.ShooterSubsystem;

public class ShootCommand extends Command {
    private final ShooterSubsystem shooterSubsystem;
    private final LEDStrip ledStrip;

    public ShootCommand(ShooterSubsystem shooterSubsystem, LEDStrip ledStrip) {
        this.shooterSubsystem = shooterSubsystem;
        this.ledStrip = ledStrip;
        addRequirements(shooterSubsystem);
    }

    @Override
    public void execute() {
        shooterSubsystem.run();
        ledStrip.usePattern((led, time) -> {
            double s = 12;
            double t = Math.asin(Math.sin(led * 0.3 + time * s)) * 2 / Math.PI + 1;
            Color c = Color.fromHSV((int)(
                    t * 10 + 170
            ), 255, 255);
            return new Color8Bit(c);
        });
    }

    @Override
    public void end(boolean interrupted) {
        shooterSubsystem.stop();
    }
}
