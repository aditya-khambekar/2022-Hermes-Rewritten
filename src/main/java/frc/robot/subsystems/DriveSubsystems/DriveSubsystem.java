package frc.robot.subsystems.DriveSubsystems;

import com.ctre.phoenix6.configs.CurrentLimitsConfigs;
import com.ctre.phoenix6.controls.Follower;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.NeutralModeValue;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class DriveSubsystem extends SubsystemBase implements IDriveSubsystem{

    public final TalonFX FrontLeft = new TalonFX(Constants.DriveMotorFrontLeft);
    public final TalonFX BackLeft = new TalonFX(Constants.DriveMotorBackLeft);
    public final TalonFX FrontRight = new TalonFX(Constants.DriveMotorFrontRight);
    public final TalonFX BackRight = new TalonFX(Constants.DriveMotorBackRight);

    private final DifferentialDrive drive;

    public DriveSubsystem() {
        //FrontLeft.setNeutralMode(NeutralModeValue.Brake);
        //BackLeft.setNeutralMode(NeutralModeValue.Brake);
        //FrontRight.setNeutralMode(NeutralModeValue.Brake);
        //BackRight.setNeutralMode(NeutralModeValue.Brake);
        FrontLeft.setNeutralMode(NeutralModeValue.Coast);
        BackLeft.setNeutralMode(NeutralModeValue.Coast);   
        FrontRight.setNeutralMode(NeutralModeValue.Coast);
        BackRight.setNeutralMode(NeutralModeValue.Coast);

        //set current limits
        var limitConfigs = new CurrentLimitsConfigs();
        limitConfigs.StatorCurrentLimit = 100; //stator current limit
        limitConfigs.SupplyCurrentLimit = 40; //supply current limit
        limitConfigs.StatorCurrentLimitEnable = true;
        limitConfigs.SupplyCurrentLimitEnable = true;

        var flConfig = FrontLeft.getConfigurator();
        var frConfig = FrontRight.getConfigurator();
        var brConfig = BackRight.getConfigurator();
        var blConfig = BackLeft.getConfigurator();

        flConfig.apply(limitConfigs);
        frConfig.apply(limitConfigs);
        brConfig.apply(limitConfigs);
        blConfig.apply(limitConfigs);


        //ask @Nengyi-Jonathan-Jiang about this I dont think it needs to be inverted???
        FrontRight.setInverted(true);

        BackLeft.setControl(new Follower(Constants.DriveMotorFrontLeft, false));
        BackRight.setControl(new Follower(Constants.DriveMotorFrontRight, false));

        drive = new DifferentialDrive(FrontLeft, FrontRight);
//        drive.setSafetyEnabled(false);
    }
    @Override
    public void arcadeDrive(double speed, double rotation) {
        drive.arcadeDrive(speed, rotation);
    }
    @Override
    public void tankDrive(double left, double right) {
        drive.tankDrive(left, right);
    }
    @Override
    public void stop() {
        drive.stopMotor();
    }

}
