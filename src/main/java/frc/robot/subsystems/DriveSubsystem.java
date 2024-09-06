package frc.robot.subsystems;

import com.ctre.phoenix6.controls.Follower;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.NeutralModeValue;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class DriveSubsystem extends SubsystemBase {

    public final TalonFX FrontLeft = new TalonFX(Constants.DriveMotorFrontLeft);
    public final TalonFX BackLeft = new TalonFX(Constants.DriveMotorBackLeft);
    public final TalonFX FrontRight = new TalonFX(Constants.DriveMotorFrontRight);
    public final TalonFX BackRight = new TalonFX(Constants.DriveMotorBackRight);

    private final DifferentialDrive drive;

    public DriveSubsystem() {
        FrontLeft.setNeutralMode(NeutralModeValue.Brake);
        BackLeft.setNeutralMode(NeutralModeValue.Brake);
        FrontRight.setNeutralMode(NeutralModeValue.Brake);
        BackRight.setNeutralMode(NeutralModeValue.Brake);

        FrontRight.setInverted(true);

        BackLeft.setControl(new Follower(Constants.DriveMotorFrontLeft, false));
        BackRight.setControl(new Follower(Constants.DriveMotorFrontRight, false));

        drive = new DifferentialDrive(FrontLeft, FrontRight);
//        drive.setSafetyEnabled(false);
    }

    public void arcadeDrive(double speed, double rotation) {
        drive.arcadeDrive(speed, rotation);
    }

    public void tankDrive(double left, double right) {
        drive.tankDrive(left, right);
    }

    public void stop() {
        drive.stopMotor();
    }

}
