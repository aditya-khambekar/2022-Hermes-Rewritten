package frc.robot.subsystems;

import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class IntakeSubsystem extends SubsystemBase {
    private final Solenoid solenoid;

    public IntakeSubsystem() {
        solenoid = new Solenoid(PneumaticsModuleType.CTREPCM, 1);
    }

    public void pistonOut() {
        solenoid.set(true);
    }

    public void pistonIn() {
        solenoid.set(false);
    }
}
