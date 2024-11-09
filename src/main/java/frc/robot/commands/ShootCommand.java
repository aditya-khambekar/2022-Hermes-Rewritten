package frc.robot.commands;

import edu.wpi.first.math.controller.BangBangController;
import edu.wpi.first.math.controller.SimpleMotorFeedforward;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.ShooterSubsystems.ShooterSubsystem;

public class ShootCommand extends Command {
    private final ShooterSubsystem shooter;
    private final SimpleMotorFeedforward feedforward = new SimpleMotorFeedforward(0.64665, 0.10772, 0.037027);
    private final BangBangController shooterBang = new BangBangController(5);
    private static final double desiredSpeed = 100;

    public ShootCommand(ShooterSubsystem shooter) {
        this.shooter = shooter;

        addRequirements(shooter);
    }

    public double calculate() {
        return shooterBang.calculate(shooter.getRate(), desiredSpeed) * 2 + 0.0006 * feedforward.calculate(desiredSpeed);
    }

    @Override
    public void initialize() {
        shooter.setShooter(0);
    }

    @Override
    public void execute() {
        shooter.setShooter(calculate());
    }

    @Override
    public void end(boolean interrupted) {
        shooter.stopShooter();
    }
}

//         shooterSubsystem.run();
//         ledStrip.usePattern((led, time) -> {
//             double s = 12;
//             double t = Math.asin(Math.sin(led * 0.3 + time * s)) * 2 / Math.PI + 1;
//             Color c = Color.fromHSV((int)(
//                     t * 10 + 170
//             ), 255, 255);
//             return new Color8Bit(c);
//         });