/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class RobotMap {
  // For example to map the left and right motors, you could define the
  // following variables to use with your drivetrain subsystem.
  // public static int leftMotor = 1;
  // public static int rightMotor = 2;

  // If you are using multiple modules, make sure to define both the port
  // number and the module. For example you with a rangefinder:
  // public static int rangefinderPort = 1;
  // public static int rangefinderModule = 1;
	
	// Device configuration
	public static final int DRIVER_LEFT_JOYSTICK_ID = 0;
	public static final int DRIVER_RIGHT_JOYSTICK_ID = 1;
	public static final int OPERATOR_GAMEPAD_ID = 2;
	
	/* Drive Subsystem
	 * The robot has a single motor on each side.
	 * these motors are controlled by motor controllers. There are various different types.
	 * We provide example for PWM driven controllers
	 */
	
	/*
	 * PWM example. The ID is the PWM port on the RoboRIO we plug the talon into.
	 * The PWM controllers do not provide any feedback. All feedback has to be through external sensors.
	 */
	public static final int DRIVE_LEFT1_PWM_ID = 0;				// PWM channel for LEFT side
	public static final int DRIVE_LEFT2_PWM_ID = 1;				// PWM channel for LEFT side
	public static final int DRIVE_RIGHT1_PWM_ID = 2;			// PWM channel for RIGHT side.
	public static final int DRIVE_RIGHT2_PWM_ID = 3;			// PWM channel for RIGHT side.
	
	/*
	 * Drive style choices.
	 */
	public enum DriveStyle {
		DRIVE_STYLE_ARCADE,			// use arcade drive - left joystick = forward/back, right joystick = left/right.
		DRIVE_STYLE_TANK,			// use tank drive - left joystick = left wheels, right joystick = right wheels.
		DRIVE_STYLE_CURVE 			// use curve drive
	}
	public static final DriveStyle DRIVE_STYLE = DriveStyle.DRIVE_STYLE_TANK; // XXXXX CHOICE XXXXX pick your drive style here.
	
	// Widget subsystem
	// The demo robot controls a widget through a motor and speed controller (talon or whatever!)
	// We can make the motor move 'up' or 'down'. the speed we make it move is controlled here:
	public static final int WIDGET_CONTROLLER_ID = 9;
	public static final double WIDGET_SPEED_UP = 0.1;		// UP speed of widget (from 0 to 1)
	public static final double WIDGET_SPEED_DOWN = -0.1;	// DOWN speed of the widget (from 0 to -1)
}
