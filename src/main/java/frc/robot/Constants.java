// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.util.Color;
import edu.wpi.first.wpilibj.util.Color8Bit;
import frc.robot.led.LEDPattern;

/**
 * The Constants class provides a convenient place for teams to hold robot-wide numerical or boolean
 * constants. This class should not be used for any other purpose. All constants should be declared
 * globally (i.e. public static). Do not put anything functional in this class.
 *
 * <p>It is advised to statically import this class (or one of its inner classes) wherever the
 * constants are needed, to reduce verbosity.
 */
public final class Constants {
    public static final int DriveMotorFrontLeft = 1;
    public static final int DriveMotorBackLeft = 2;
    public static final int DriveMotorFrontRight = 3;
    public static final int DriveMotorBackRight = 4;
    public static final int ShooterMotor = 6;
    public static final int FeedMotor = 7;
    public static final int HopperMotor = 8;
    public static final int IntakeMotor = 9;
    public static final int LClimberMotor = 11;
    public static final int RClimberMotor = 12;

    public static final int ForwardPiston = 1;

    public static final int feederSensor = 1;

    public static final double DEADZONE_VALUE = 0.01;

    public static final double DRIVE_SPEED = 0.80;
    public static final double ROTATION_SENSITIVITY = 0.5;

    public static final LEDPattern LED_IdlePattern = (led, time) -> {
        time *= 2;
        double x = led * 0.2 + time * 3;
        double h = 20 * Math.pow(Math.sin(x), 2) + 90;
        double v = Math.pow(Math.sin(time), 2) * 0.9 + 0.1;

        return new Color8Bit(Color.fromHSV((int) (h), 255, (int) (255 * v)));
    };
}
