package frc.robot.commands;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants;
import frc.robot.Robot;
import frc.robot.oi.OI;
import frc.robot.subsystems.HopperSubsystem;
import frc.robot.subsystems.IntakeSubsystem;
import frc.robot.tuning.TunableFloatSource;

public class IntakeCommand extends Command {
    private final IntakeSubsystem intake;
    private final HopperSubsystem hopper;
    private final OI oi = OI.getInstance();
    public final DigitalInput ballOccupied = new DigitalInput(Constants.feederSensor);

    private float speed = 0;

    public IntakeCommand(IntakeSubsystem intake, HopperSubsystem hopper) {
        this.intake = intake;
        this.hopper = hopper;
        addRequirements(intake, hopper);
    }

    @Override
    public void initialize() {
        intake.setIntake(0);
        hopper.setHopper(0);

        if(speed == 0) {
            speed = new TunableFloatSource("robot.INTAKE_SPEED", Robot.robotConfiguration.getFloat("robot.INTAKE_SPEED").value()).value();
        }
    }

    @Override
    public void execute() {
//        FloatSource intakeSpeed = Robot.robotConfiguration.getFloat("robot.INTAKE_SPEED");
//        FloatSource intakeSpeed = new TunableFloatSource("robot.INTAKE_SPEED", Robot.robotConfiguration.getFloat("robot.INTAKE_SPEED").value());
//        speed = intakeSpeed.value();
        intake.setIntake(speed);
        hopper.setHopper(speed);
    }

    @Override
    public void end(boolean Interrupted) {
        intake.stopIntake();
        hopper.stopHopper();
    }
}