package frc.robot.subsystems.ShooterSubsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class DummyShooter extends SubsystemBase implements IShooterSubsystem{

    @Override
    public void setShooter(double speed) {
        //set shooter to s speed
    }

    @Override
    public void stopShooter() {
       //do nothing
    }

    @Override
    public double getRate() {
        return -1.0;
    }
    
}
