package frc.robot.subsystems.DriveSubsystems;

public interface IDriveSubsystem {
    void arcadeDrive(double speed, double rotation);
    void tankDrive(double left, double right);
    void stop();
}
