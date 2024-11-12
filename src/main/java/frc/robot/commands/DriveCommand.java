package frc.robot.commands;

import edu.wpi.first.math.filter.SlewRateLimiter;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants;
import frc.robot.oi.OI;
import frc.robot.subsystems.DriveSubsystems.DriveSubsystem;
import frc.robot.subsystems.DriveSubsystems.IDriveSubsystem;

public class DriveCommand extends Command {

    private final IDriveSubsystem drive;
    private final OI oi = OI.getInstance();

    private final SlewRateLimiter rotationLimiter = new SlewRateLimiter(3);
    private final SlewRateLimiter speedLimiter = new SlewRateLimiter(7);

    public DriveCommand(IDriveSubsystem drive) {
        this.drive = drive;
        switch(drive.getClass().getName()){
            case "frc.robot.subsystems.DriveSubsystems.DriveSubsystem"->addRequirements((DriveSubsystem)drive);
        }
        //addRequirements(drive);
    }

    @Override
    public void initialize() {
        drive.stop();
    }

    @Override
    public void execute() {
        drive.arcadeDrive(speedLimiter.calculate(oi.driverController().axis(OI.Axes.LEFT_STICK_Y)) * Constants.DRIVE_SPEED, rotationLimiter.calculate(oi.driverController().axis(OI.Axes.RIGHT_STICK_X)) * Constants.ROTATION_SENSITIVITY);
    }

    @Override
    public void end(boolean interrupted) {
        drive.stop();
    }
}
