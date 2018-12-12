/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import frc.robot.RobotMap.DriveStyle;
import frc.robot.commands.AutoMovementGroupExample1;
import frc.robot.commands.AutoMovementGroupExample2;
import frc.robot.commands.AutoMovementGroupExample3;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.WidgetSubsystem;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.TimedRobot;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {
  private DriveSubsystem driveSubsystem;
	private WidgetSubsystem widgetSubsystem;
	@SuppressWarnings("unused")
	private static OI oi;
	private Joystick driverLeftStick, driverRightStick, operatorGamePad;
	private SpeedController leftController, rightController;	// left and right motor controllers
	private SpeedController widgetController;					// widget motor controllers
	private DifferentialDrive drive;

  Command autonomousCommand;
  SendableChooser<Command> chooser;

  /**
   * This function is run when the robot is first started up and should be
   * used for any initialization code.
   */
  @Override
  public void robotInit() {
    driverLeftStick = new Joystick(RobotMap.DRIVER_LEFT_JOYSTICK_ID);
    driverRightStick = new Joystick(RobotMap.DRIVER_RIGHT_JOYSTICK_ID);
    operatorGamePad = new Joystick(RobotMap.OPERATOR_GAMEPAD_ID);
      
    // This is how you create controllers that use PWM ports on the RoboRIO.
      // if you have Victors, Sparks, or Talons
    leftController = new SpeedControllerGroup(new Spark(RobotMap.DRIVE_LEFT1_PWM_ID), new Spark(RobotMap.DRIVE_LEFT2_PWM_ID));
    rightController = new SpeedControllerGroup(new Spark(RobotMap.DRIVE_RIGHT1_PWM_ID), new Spark(RobotMap.DRIVE_RIGHT2_PWM_ID));
    // OR
    //leftController = new VictorSP(RobotMap.DRIVE_LEFT_PWM_ID);
    //rightController = new VictorSP(RobotMap.DRIVE_RIGHT_PWM_ID);
    // OR
    //leftController = new Talon(RobotMap.DRIVE_LEFT_PWM_ID);
    //rightController = new Talon(RobotMap.DRIVE_RIGHT_PWM_ID);
    // If you have multiple motors on each side create the ones you want then combine using a SpeedControllerGroup
    
      // use the controllers to create a drive class that will take the move and turn values and pass the correct power to each motor controller.
    drive = new DifferentialDrive(leftController, rightController);
    
    // the drive subsystem is the wrapper code that ties the joysticks and the drive class together. We also specify the drive style desired.
    /*
     * We have several different joystick arrangements
     * These are listed below. The most common ones are two single joysticks, a single flight stick and a single game pad, using
     * the two thumbsticks.
     */
    // two joysticks
    //driveSubsystem = new DriveSubsystem(driverLeftStick::getY, driverRightStick::getY, drive, RobotMap.DRIVE_STYLE);
    // flight stick
    driveSubsystem = new DriveSubsystem(driverLeftStick::getY, driverLeftStick::getTwist, drive, DriveStyle.DRIVE_STYLE_ARCADE);
    // Gamepad with thumbsticks
    //driveSubsystem = new DriveSubsystem(() -> {return driverLeftStick.getRawAxis(1);}, () -> {return driverLeftStick.getRawAxis(5);}, drive, RobotMap.DRIVE_STYLE);
      /*
       *  create a widget subsystem. This is code that controls some widget. In the example code it is just a simple motor.
       *  The widgetController is the motor, and needs to be created for the subsystem to manipulate.
       *  
       *  Here we use the Victor SP, a PWM controller that will be available. Yes, we can mix and match our motor controllers,
       *  although it is better to use the same controller when working in groups (such as left and right on the drive train.
       */
    widgetController = new VictorSP(RobotMap.WIDGET_CONTROLLER_ID);
    widgetSubsystem = new WidgetSubsystem(widgetController);

    chooser = new SendableChooser<Command>();
    chooser.addDefault("Default Auto", new AutoMovementGroupExample1(driveSubsystem));
    chooser.addObject("Group 2 Description", new AutoMovementGroupExample2(driveSubsystem));
    chooser.addObject("Group 3 Description", new AutoMovementGroupExample3(driveSubsystem, widgetSubsystem));
    SmartDashboard.putData("Auto mode", chooser);

      /*
       * The Operator Interface is the code that we use to bind the joystick buttons to the various actions on the robot.
       * The operator interface registers the buttons and their associated commands. When the button is pressed/held/or released the associated command
       * is "fired" or made ready to run. The command will then be executed until it completes, or it is interupted.
       */
    oi = new OI(driverLeftStick, driverRightStick, operatorGamePad, widgetSubsystem);		// create operator interface.

  }

  /**
   * This function is called every robot packet, no matter the mode. Use
   * this for items like diagnostics that you want ran during disabled,
   * autonomous, teleoperated and test.
   *
   * <p>This runs after the mode specific periodic functions, but before
   * LiveWindow and SmartDashboard integrated updating.
   */
  @Override
  public void robotPeriodic() {
    Scheduler.getInstance().run();
  }

  /**
   * This function is called once each time the robot enters Disabled mode.
   * You can use it to reset any subsystem information you want to clear when
   * the robot is disabled.
   */
  @Override
  public void disabledInit() {
  }

  @Override
  public void disabledPeriodic() {
  }

  /**
   * This autonomous (along with the chooser code above) shows how to select
   * between different autonomous modes using the dashboard. The sendable
   * chooser code works with the Java SmartDashboard. If you prefer the
   * LabVIEW Dashboard, remove all of the chooser code and uncomment the
   * getString code to get the auto name from the text box below the Gyro
   *
   * <p>You can add additional auto modes by adding additional commands to the
   * chooser code above (like the commented example) or additional comparisons
   * to the switch structure below with additional strings & commands.
   */
  @Override
  public void autonomousInit() {
    autonomousCommand = chooser.getSelected();
    
    /* String autoSelected = SmartDashboard.getString("Auto Selector", "Default");
    switch(autoSelected) {
    case "My Auto":
      autonomousCommand = new MyAutoCommand();
      break;
    case "Default Auto":
    default:
      autonomousCommand = new ExampleCommand();
      break;
    } */
    
    // Fetch the autonomous randomisation data from the FMS message. This will allow us to determine which sides are ours.
    String gameData;
    gameData = DriverStation.getInstance().getGameSpecificMessage();
    // The String is three L or R characters, one for each of the alliance switch, scale, other alliance switch.
    // See http://wpilib.screenstepslive.com/s/currentCS/m/getting_started/l/826278-2018-game-data-details for details.
    System.out.println("GameData: " + gameData);
  
      // schedule the autonomous command (example)
    if (autonomousCommand != null) {
      autonomousCommand.start();
    }
  }

  /**
   * This function is called periodically during autonomous.
   */
  @Override
  public void autonomousPeriodic() {
    Scheduler.getInstance().run();
  }

  @Override
  public void teleopInit() {
    // This makes sure that the autonomous stops running when
    // teleop starts running. If you want the autonomous to
    // continue until interrupted by another command, remove
    // this line or comment it out.
    if (autonomousCommand != null) {
      autonomousCommand.cancel();
    }
    driveSubsystem.setEnabled(true);		// enable drive subsystem for teleop.
  }

  /**
   * This function is called periodically during operator control.
   */
  @Override
  public void teleopPeriodic() {
  }

  /**
   * This function is called periodically during test mode.
   */
  @Override
  public void testPeriodic() {
  }
}
