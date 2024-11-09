package frc.robot;

import edu.wpi.first.math.interpolation.InterpolatingDoubleTreeMap;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.RunCommand;
import frc.robot.commands.*;
import frc.robot.led.LEDStrip;
import frc.robot.led.PhysicalLEDStrip;
import frc.robot.oi.OI;
import frc.robot.subsystems.*;
import frc.robot.subsystems.DriveSubsystems.DriveSubsystem;
import frc.robot.subsystems.ShooterSubsystems.ShooterSubsystem;
import frc.robot.subsystems.ShooterSubsystems.VortexShooter;
import frc.robot.tuning.RobotConfiguration;
import frc.robot.tuning.TableSource;

public class RobotContainer {
    private final DriveSubsystem driveSubsystem = new DriveSubsystem();
    private final ClimberSubsystem climberSubsystem = new ClimberSubsystem();
    private final NavX navx = new NavX();
    private final OI oi = OI.getInstance();
    public final LEDStrip ledStrip = new PhysicalLEDStrip(0, 96);
    private final VortexShooter shooterSubsystem = new VortexShooter();
    //private final ShooterSubsystem shooterSubsystem = new ShooterSubsystem();
    private final IntakeSubsystem intakeSubsystem = new IntakeSubsystem();
    private final FeederSubsystem feederSubsystem = new FeederSubsystem();
    private final HopperSubsystem hopperSubsystem = new HopperSubsystem();

    public RobotContainer() {
        loadConfigFiles();
        configureBindings();
    }

    private static void loadConfigFiles() {
        RobotConfiguration.loadFile("config/robot.rcfg");
        RobotConfiguration.loadFile("config/controls.rcfg");
    }

    private void configureBindings() {
//        driveSubsystem.setDefaultCommand(new DriveCommand(driveSubsystem));
//
//        if(false)
        {
            InterpolatingDoubleTreeMap interpolatorLeft = new InterpolatingDoubleTreeMap();
            InterpolatingDoubleTreeMap interpolatorRight = new InterpolatingDoubleTreeMap();
            {
                TableSource table = RobotConfiguration.getTable("robot.ShooterSetPointTable");
                for (int row = 0; row < table.numRows(); row++) {
                    double point = table.getCellAsDouble(row, 0);
                    double left = table.getCellAsDouble(row, 1);
                    double right = table.getCellAsDouble(row, 2);

                    interpolatorLeft.put(point, left);
                    interpolatorRight.put(point, right);
                }
                table.addListener((row, col, value) -> {
                    switch (col) {
                        case 1 -> interpolatorLeft.put(table.getCellAsDouble(row, 0), value);
                        case 2 -> interpolatorRight.put(table.getCellAsDouble(row, 0), value);
                    }
                });
            }

            driveSubsystem.setDefaultCommand(new Command() {
                {
                    addRequirements(driveSubsystem);
                }

                @Override
                public void execute() {
                    double axis = oi.driverController().axis(OI.Axes.LEFT_STICK_Y);

                    double left = interpolatorLeft.get(axis);
                    double right = interpolatorRight.get(axis);

                    driveSubsystem.tankDrive(left, right);
                }
            });
        }

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
