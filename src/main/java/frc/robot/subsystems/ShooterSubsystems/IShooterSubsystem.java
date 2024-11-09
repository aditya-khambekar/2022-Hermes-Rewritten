package frc.robot.subsystems.ShooterSubsystems;

import com.ctre.phoenix6.configs.CurrentLimitsConfigs;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.NeutralModeValue;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public interface IShooterSubsystem{

    void setShooter(double speed);
    void stopShooter();
    double getRate();
}
