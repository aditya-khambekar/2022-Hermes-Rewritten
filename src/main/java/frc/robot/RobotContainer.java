package frc.robot;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.RunCommand;
import frc.robot.commands.*;
import frc.robot.led.LEDStrip;
import frc.robot.led.PhysicalLEDStrip;
import frc.robot.oi.OI;
import frc.robot.subsystems.*;

public class RobotContainer {
    private final DriveSubsystem driveSubsystem = new DriveSubsystem();
    private final ClimberSubsystem climberSubsystem = new ClimberSubsystem();
    private final NavX navx = new NavX();
    private final OI oi = OI.getInstance();
    public final LEDStrip ledStrip = new PhysicalLEDStrip(0, 96);
    private final ShooterSubsystem shooterSubsystem = new ShooterSubsystem();
    private final IntakeSubsystem intakeSubsystem = new IntakeSubsystem();
    private final FeederSubsystem feederSubsystem = new FeederSubsystem();
    private final HopperSubsystem hopperSubsystem = new HopperSubsystem();

    public RobotContainer() {
        configureBindings();
    }

    private void configureBindings() {
        driveSubsystem.setDefaultCommand(new DriveCommand(driveSubsystem));

        oi.driverController().button(OI.Buttons.D_PAD_UP).whileTrue(new ClimbCommand(climberSubsystem, 1, 1));
        oi.driverController().button(OI.Buttons.D_PAD_DOWN).whileTrue(new ClimbCommand(climberSubsystem, -1, -1));
        oi.driverController().button(OI.Buttons.D_PAD_LEFT).whileTrue(new ClimbCommand(climberSubsystem, -1, 1));
        oi.driverController().button(OI.Buttons.D_PAD_RIGHT).whileTrue(new ClimbCommand(climberSubsystem, 1, -1));

        oi.driverController().button(OI.Buttons.X_BUTTON).whileTrue(new IntakeCommand(intakeSubsystem, hopperSubsystem));
        oi.driverController().button(OI.Buttons.B_BUTTON).whileTrue(new OutTakeCommand(intakeSubsystem, hopperSubsystem));
        oi.driverController().button(OI.Buttons.RIGHT_TRIGGER).whileTrue(new KickCommand(feederSubsystem));
        oi.driverController().button(OI.Buttons.Y_BUTTON).whileTrue(new RunCommand(intakeSubsystem::extendPistons));
        oi.driverController().button(OI.Buttons.A_BUTTON).whileTrue(new RunCommand(intakeSubsystem::retractPistons));
        oi.driverController().button(OI.Buttons.LEFT_BUMPER).whileTrue(new RunCommand(climberSubsystem::extendPistons));
        oi.driverController().button(OI.Buttons.RIGHT_BUMPER).whileTrue(new RunCommand(climberSubsystem::retractPistons));
        oi.driverController().button(OI.Buttons.LEFT_TRIGGER).whileTrue(new ShootCommand(shooterSubsystem));

        ledStrip.setDefaultCommand(new Command() {
            {
                addRequirements(ledStrip);
            }

            @Override
            public void execute() {
                ledStrip.usePattern(Constants.LED_IdlePattern);
            }
        });
    }

    public Command getAutonomousCommand() {
        return null;
    }
}
