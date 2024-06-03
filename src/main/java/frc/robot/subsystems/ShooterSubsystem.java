package frc.robot.subsystems;

import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.NeutralModeValue;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import network.Network;

public class ShooterSubsystem extends SubsystemBase {
    private final TalonFX shooterMotor;

    public ShooterSubsystem() {
        shooterMotor = new TalonFX(Constants.ShooterMotor);
        shooterMotor.setNeutralMode(NeutralModeValue.Coast);
        shooterMotor.setInverted(true);
    }

    public void run() {
        shooterMotor.setVoltage(16);
    }

    public void stop() {
        shooterMotor.setVoltage(0);
    }

    @Override
    public void periodic() {
        Network.getTable("SmartDashboard").setDouble("Shooter speed", shooterMotor.getVelocity().getValue());
        Network.getTable("SmartDashboard").setDouble("Shooter pos", shooterMotor.getPosition().getValue());
    }
}
