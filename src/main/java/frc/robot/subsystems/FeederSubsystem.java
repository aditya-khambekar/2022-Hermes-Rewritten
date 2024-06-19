package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class FeederSubsystem extends SubsystemBase {
    private final WPI_VictorSPX feederMotor = new WPI_VictorSPX(Constants.FeedMotor);

    public FeederSubsystem() {
        feederMotor.configFactoryDefault();
        feederMotor.setNeutralMode(NeutralMode.Brake);
        feederMotor.configVoltageCompSaturation(12);
        feederMotor.enableVoltageCompensation(true);
    }

    public void setFeeder(double speed) {
        feederMotor.set(-1);
    }

    public void stop() {
        feederMotor.set(0);
    }
}