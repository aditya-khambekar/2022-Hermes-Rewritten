package frc.robot.commands;

import edu.wpi.first.math.filter.SlewRateLimiter;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants;
import frc.robot.oi.OI;
import frc.robot.subsystems.DriveSubsystems.DriveSubsystem;

public class DriveCommand extends Command {

    private final DriveSubsystem drive;
    private final OI oi = OI.getInstance();

    private final SlewRateLimiter rotationLimiter = new SlewRateLimiter(7);
    private final SlewRateLimiter speedLimiter = new SlewRateLimiter(7);

    public DriveCommand(DriveSubsystem drive) {
        this.drive = drive;

        addRequirements(drive);
    }

    @Override
    public void initialize() {
        drive.stop();
    }

    @Override
    public void execute() {
        double speed = Math.pow(oi.driverController().axis(OI.Axes.LEFT_STICK_Y), 3.0);
        double rotate = Math.pow(-oi.driverController().axis(OI.Axes.RIGHT_STICK_X), 3.0);
        drive.arcadeDrive(
            speedLimiter.calculate(speed) * Constants.DRIVE_SPEED,
            rotationLimiter.calculate(rotate) * Constants.ROTATION_SENSITIVITY
        );
    }

    @Override
    public void end(boolean interrupted) {
        drive.stop();
    }
}
