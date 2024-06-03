// Copyright (c) FIRST and other WPILib contributors.

// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.util.*;
import edu.wpi.first.wpilibj2.command.*;
import edu.wpi.first.wpilibj2.command.button.*;
import frc.robot.commands.*;
import frc.robot.led.*;
import frc.robot.subsystems.*;


/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and trigger mappings) should be declared here.
 */
public class RobotContainer
{
    // The robot's subsystems and commands are defined here...
    private final DriveSubsystem driveSubsystem = new DriveSubsystem();
    private final ClimberSubsystem climberSubsystem = new ClimberSubsystem();
    private final NavX navx = new NavX();
    private final OI oi = new OI();
    public final LEDStrip ledStrip = new PhysicalLEDStrip(0, 96);
    private final ShooterSubsystem shooterSubsystem = new ShooterSubsystem();
    private final IntakeSubsystem intakeSubsystem = new IntakeSubsystem();

    /** The container for the robot. Contains subsystems, OI devices, and commands. */
    public RobotContainer()
    {
        // Configure the trigger bindings
        configureBindings();
    }
    
    
    /**
     * Use this method to define your trigger->command mappings. Triggers can be created via the
     * {@link Trigger#Trigger(java.util.function.BooleanSupplier)} constructor with an arbitrary
     * predicate, or via the named factories in {@link
     * edu.wpi.first.wpilibj2.command.button.CommandGenericHID}'s subclasses for {@link
     * CommandXboxController Xbox}/{@link edu.wpi.first.wpilibj2.command.button.CommandPS4Controller
     * PS4} controllers or {@link edu.wpi.first.wpilibj2.command.button.CommandJoystick Flight
     * joysticks}.
     */
    private void configureBindings()
    {
        driveSubsystem.setDefaultCommand(new DriveCommand(driveSubsystem, oi));

        oi.getPovButton(0, 0).whileTrue(new ClimbCommand(climberSubsystem,  1, 1));
        oi.getPovButton(0, 180).whileTrue(new ClimbCommand(climberSubsystem,  -1, -1));
        oi.getPovButton(0, 90).whileTrue(new ClimbCommand(climberSubsystem,  -1, 1));
        oi.getPovButton(0, 270).whileTrue(new ClimbCommand(climberSubsystem,  1, -1));

        oi.getButton(0, Constants.Buttons.LEFT_BUMPER).whileTrue(new Command() {
            { addRequirements(climberSubsystem); }
            public void initialize() { climberSubsystem.extendPistons(); }
        });
        oi.getButton(0, Constants.Buttons.RIGHT_BUMPER).whileTrue(new Command() {
            { addRequirements(climberSubsystem); }
            public void initialize() { climberSubsystem.retractPistons(); }
        });

        oi.getButton(0, Constants.Buttons.A_BUTTON).whileTrue(new ShootCommand(shooterSubsystem, ledStrip));

        oi.getButton(0, Constants.Buttons.X_BUTTON).onTrue(new RunCommand(intakeSubsystem::pistonIn));
        oi.getButton(0, Constants.Buttons.B_BUTTON).onTrue(new RunCommand(intakeSubsystem::pistonOut));

        LEDPattern pattern = (led, time) -> {
            time *= 2;
            double x = led * 0.2 + time * 3;
            double h = 20 * Math.pow(Math.sin(x), 2) + 90;
            double v = Math.pow(Math.sin(time), 2) * 0.9 + 0.1;

            return new Color8Bit(Color.fromHSV((int)(h), 255, (int)(255 * v)));
        };

        ledStrip.setDefaultCommand(new Command() {
            {
                addRequirements(ledStrip);
            }

            @Override
            public void execute() {
                ledStrip.usePattern(pattern);
            }
        });
    }
    
    /**
     * Use this to pass the autonomous command to the main {@link Robot} class.
     *
     * @return the command to run in autonomous
     */
    public Command getAutonomousCommand()
    {
        // An example command will be run in autonomous
        return null;
    }
}
