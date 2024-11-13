package frc.robot.subsystems.ShooterSubsystems;

import com.revrobotics.CANSparkBase.IdleMode;
import com.revrobotics.CANSparkFlex;
import com.revrobotics.CANSparkLowLevel.MotorType;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.tuning.RobotConfiguration;

public class VortexShooter extends SubsystemBase implements IShooterSubsystem {
    private final CANSparkFlex ShooterMotor = new CANSparkFlex(Constants.ShooterMotor, MotorType.kBrushless);
    private final PIDController ShooterPID = new PIDController(0, 0, 0);

//   private final SysIdRoutine routine =
//       new SysIdRoutine(
//           // Empty config defaults to 1 volt/second ramp rate and 7 volt step voltage.
//           new SysIdRoutine.Config(),
//           new SysIdRoutine.Mechanism(
//               (Measure<Voltage> voltage) -> ShooterMotor.setVoltage(voltage.in(Volts)),
//               log -> {
//                 log.motor("shooter")
//                 .angularVelocity(null)
//                 .angularPosition()
//                 .an
//               },
//               this));

    public VortexShooter() {
        //still need to configure the spark flex's current limits
        ShooterMotor.restoreFactoryDefaults();
        ShooterMotor.setIdleMode(IdleMode.kCoast);
        //PID stuff
        ShooterPID.setP(RobotConfiguration.getNumber("shooter.kp").valueAsDouble());
        ShooterPID.setI(RobotConfiguration.getNumber("shooter.ki").valueAsDouble());
        ShooterPID.setD(RobotConfiguration.getNumber("shooter.kd").valueAsDouble());
        RobotConfiguration.getNumber("shooter.kp").addListener(ShooterPID::setP);
        RobotConfiguration.getNumber("shooter.ki").addListener(ShooterPID::setI);
        RobotConfiguration.getNumber("shooter.kd").addListener(ShooterPID::setD);
    }

    @Override
    public void setShooter(double targetSpeed) {
        double currentSpeed = Math.abs(ShooterMotor.getEncoder().getVelocity());
        SmartDashboard.putNumber("current shooter speed", currentSpeed);


//        ShooterPID.setP(RobotConfiguration.getNumber("shooter.kp").valueAsDouble());
//        ShooterPID.setI(RobotConfiguration.getNumber("shooter.ki").valueAsDouble());
//        ShooterPID.setD(RobotConfiguration.getNumber("shooter.kd").valueAsDouble());
        ShooterPID.setSetpoint(targetSpeed);
        double v = ShooterPID.calculate(currentSpeed);
        SmartDashboard.putNumber("Shooter PID output", v);

        if (ShooterMotor.getMotorTemperature() >= 80.0) {
            ShooterMotor.set(0);
        }
//        else if (currentSpeed >= targetSpeed) {
//            ShooterMotor.set(0);
//        }
        else {
            ShooterMotor.set(-v);
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

    // public Command sysidQuasistatic(Direction dir) {
    //     return routine.quasistatic(dir);
    // }

    // public Command sysidDynamic(Direction dir) {
    //     return routine.dynamic(dir);
    // }

}
