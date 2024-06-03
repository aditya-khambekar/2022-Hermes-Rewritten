package frc.robot.commands;

import edu.wpi.first.math.filter.SlewRateLimiter;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants;
import frc.robot.OI;
import frc.robot.subsystems.DriveSubsystem;

import static frc.robot.Constants.Axes;

public class DriveCommand extends Command {

    private final DriveSubsystem drive;
    private final OI oi;

    private final SlewRateLimiter rotationLimiter = new SlewRateLimiter(10);
	private final SlewRateLimiter speedLimiter = new SlewRateLimiter(3);

    public DriveCommand(DriveSubsystem drive, OI oi) {
        this.drive = drive;
        this.oi = oi;
        
        addRequirements(drive);
    }

    @Override
    public void initialize() {
        drive.arcadeDrive(0, 0);
    }

    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {
        drive.arcadeDrive(
        -rotationLimiter.calculate(oi.getAxis(0, Axes.LEFT_STICK_Y)) * Constants.DRIVE_SPEED,
        speedLimiter.calculate(oi.getAxis(0, Axes.RIGHT_STICK_X)) * Constants.ROTATION_SENSITIVITY
        );
    }

    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {
        drive.stop();
    }

    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
        return false;
    }
}
