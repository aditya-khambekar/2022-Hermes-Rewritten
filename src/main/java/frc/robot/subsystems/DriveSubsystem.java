package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import com.ctre.phoenix6.controls.Follower;
import com.ctre.phoenix6.signals.NeutralModeValue;
import edu.wpi.first.math.filter.SlewRateLimiter;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class DriveSubsystem extends SubsystemBase {
    @SuppressWarnings("removal")
    public final WPI_TalonFX FrontLeft = new WPI_TalonFX(Constants.DriveMotorFrontLeft);
    @SuppressWarnings("removal")
    public final WPI_TalonFX BackLeft = new WPI_TalonFX(Constants.DriveMotorBackLeft);
    @SuppressWarnings("removal")
    public final WPI_TalonFX FrontRight = new WPI_TalonFX(Constants.DriveMotorFrontRight);
    @SuppressWarnings("removal")
    public final WPI_TalonFX BackRight = new WPI_TalonFX(Constants.DriveMotorBackRight);
    private final DifferentialDrive drive;

    public DriveSubsystem() {
        FrontLeft.setNeutralMode(NeutralMode.Brake);
        BackLeft.setNeutralMode(NeutralMode.Brake);
        FrontRight.setNeutralMode(NeutralMode.Brake);
        BackRight.setNeutralMode(NeutralMode.Brake);

        BackLeft.follow(FrontLeft);
        BackRight.follow(FrontRight);

        drive = new DifferentialDrive(FrontLeft, FrontRight);
        drive.setSafetyEnabled(false);
    }

    public void arcadeDrive(double speed, double rotation) {
        drive.arcadeDrive(speed, rotation);
    }
    public void stop() {
        drive.stopMotor();
    }
}
