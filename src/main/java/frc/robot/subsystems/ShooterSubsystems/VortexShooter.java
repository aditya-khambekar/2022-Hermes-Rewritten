package frc.robot.subsystems.ShooterSubsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

import com.revrobotics.CANSparkFlex;
import com.revrobotics.CANSparkBase.IdleMode;
import com.revrobotics.CANSparkLowLevel.MotorType;

public class VortexShooter extends SubsystemBase implements IShooterSubsystem {
    private final CANSparkFlex ShooterMotor = new CANSparkFlex(Constants.ShooterMotor,MotorType.kBrushless);

    public VortexShooter(){
        ShooterMotor.setIdleMode(IdleMode.kCoast);
        //still need to configure the spark flex's current limits
    }

    @Override
    public void setShooter(double speed) {
        //check for temperature issues
        if (ShooterMotor.getMotorTemperature()>=80.0){
            ShooterMotor.set(0);
        }else{
            ShooterMotor.set(speed);
        }
    }

    @Override
    public void stopShooter() {
        ShooterMotor.set(0);
    }

    @Override
    public double getRate() {
        return -1.0;
    }

}
