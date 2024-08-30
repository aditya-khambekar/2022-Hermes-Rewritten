package frc.robot.tuning;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class TunableFloatSource implements FloatSource {
    private final String name;
    private final float defaultValue;

    public TunableFloatSource(String name, float defaultValue) {
        this.name = name;
        this.defaultValue = defaultValue;
        if (!SmartDashboard.containsKey(name)) {
            SmartDashboard.putNumber(name, this.defaultValue);
        }
    }

    @Override
    public String name() {
        return name;
    }

    @Override
    public float value() {
        return (float) SmartDashboard.getNumber(name, defaultValue);
    }
}
