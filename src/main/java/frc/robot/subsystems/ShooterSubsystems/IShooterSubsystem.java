package frc.robot.subsystems.ShooterSubsystems;

import frc.robot.Constants;


public interface IShooterSubsystem {
    void setShooter(double speed);
    void stopShooter();
    double getRate();
}
