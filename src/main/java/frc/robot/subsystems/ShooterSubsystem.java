package frc.robot.subsystems;

import com.ctre.phoenix6.configs.CurrentLimitsConfigs;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.NeutralModeValue;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class ShooterSubsystem extends SubsystemBase {
    private final TalonFX ShooterMotor = new TalonFX(Constants.ShooterMotor);

    public ShooterSubsystem() {
        ShooterMotor.setNeutralMode(NeutralModeValue.Coast);
        ShooterMotor.getConfigurator().apply(new CurrentLimitsConfigs().withStatorCurrentLimit(4));
    }

    public void setShooter(double speed) {
        ShooterMotor.set(-speed);
    }

    public void stopShooter() {
        ShooterMotor.set(0);
    }

    public double getRate() {
        return ((-ShooterMotor.getVelocity().getValue()));
    }

}