package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.util.Color8Bit;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;

public class Robot extends TimedRobot {
    private Command autonomousCommand;

    private RobotContainer robotContainer;

    @Override
    public void robotInit() {
        robotContainer = new RobotContainer();
    }

    @Override
    public void robotPeriodic() {
        CommandScheduler.getInstance().run();
    }

    @Override
    public void disabledInit() {
    }

    @Override
    public void disabledPeriodic() {
        robotContainer.ledStrip.usePattern((_1, _2) -> new Color8Bit((int) SmartDashboard.getNumber("LED_R", 0) & 255, (int) SmartDashboard.getNumber("LED_G", 0) & 255, (int) SmartDashboard.getNumber("LED_B", 0) & 255));

        SmartDashboard.putString("matchTime", "0");
    }

    @Override
    public void autonomousInit() {
        autonomousCommand = robotContainer.getAutonomousCommand();

        if (autonomousCommand != null) {
            autonomousCommand.schedule();
        }
    }

    @Override
    public void autonomousPeriodic() {
        SmartDashboard.putString("matchTime", "%d".formatted((int) Timer.getMatchTime()));
    }

    @Override
    public void teleopInit() {
        if (autonomousCommand != null) {
            autonomousCommand.cancel();
        }
    }

    @Override
    public void teleopPeriodic() {
    }


    @Override
    public void testInit() {
        CommandScheduler.getInstance().cancelAll();
    }


    /**
     * This method is called periodically during test mode.
     */
    @Override
    public void testPeriodic() {
    }


    /**
     * This method is called once when the robot is first started up.
     */
    @Override
    public void simulationInit() {
    }


    /**
     * This method is called periodically whilst in simulation.
     */
    @Override
    public void simulationPeriodic() {
    }
}
