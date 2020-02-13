/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.XboxController;
import frc.robot.commands.Drive3FeetCommand;
import frc.robot.commands.ExampleCommand;
import frc.robot.commands.MoveTurretCommand;
import frc.robot.commands.ParkCommand;
import frc.robot.commands.TeleopCommand;
import frc.robot.commands.IntakeCommand;
import frc.robot.subsystems.IntakeSubsystem;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.ExampleSubsystem;
import frc.robot.subsystems.ShooterSubsystem;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;

/**
 * This class is where the bulk of the robot should be declared.  Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls).  Instead, the structure of the robot
 * (including subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer {
  XboxController joy = new XboxController(0);
  private final JoystickButton a = new JoystickButton(joy, 1);
  private final JoystickButton b = new JoystickButton(joy, 2);
  // The robot's subsystems and commands are defined here...
  // TODO: Remove examples and things that depend on them.
  private final XboxController m_joy0 = new XboxController(0);
  private final XboxController m_joy1 = new XboxController(1);


  private final ExampleSubsystem m_exampleSubsystem = new ExampleSubsystem();

  private final ExampleCommand m_autoCommand = new ExampleCommand(m_exampleSubsystem);

  // --- Create Subsystems and Commands ----------------------
  // 

    //private final XboxController m_joy1 = new XboxController(1);
  private final JoystickButton intakeButtonOn = new JoystickButton(m_joy1, 1);
  private final JoystickButton intakeButtonOff = new JoystickButton(m_joy1, 2);

  // Create m_intakeSubsystem
  // Create commands for intake
  private final IntakeSubsystem m_intakeSubsystem = new IntakeSubsystem();
  private final IntakeCommand m_intakeOnCommand = new IntakeCommand(m_intakeSubsystem, Constants.Intake.INTAKE_POWER);
  private final IntakeCommand m_intakeOffCommand = new IntakeCommand(m_intakeSubsystem, 0.0);

  // TODO: Create m_shooterSubsystem
  // TODO: create commands for shooter
  private final ShooterSubsystem m_shooterSubsystem = new ShooterSubsystem();

  private final MoveTurretCommand m_turretRightCommand = new MoveTurretCommand(m_shooterSubsystem, 20);
  private final MoveTurretCommand m_stopTurretCommand = new MoveTurretCommand(m_shooterSubsystem, 0);
  private final MoveTurretCommand m_turretLeftCommand = new MoveTurretCommand(m_shooterSubsystem, -20);


  // TODO: Create m_storageSubsystem
  // TODO: Create commands for storage

  // TODO: Create m_hangSubsystem
  // TODO: Create commands for hang

  // TODO: Create m_controlPanelSubsystem (wheel thing)
  // TODO: Create commands for control panel

  // TODO: Create m_visionSubsystem
  // TODO: Create commands for vision

  // TODO: Create m_driveSubsystem

  private final DriveSubsystem m_driveSubsystem = new DriveSubsystem();

  private final TeleopCommand m_teleopCommand = new TeleopCommand(m_driveSubsystem,m_joy0);

  private final Drive3FeetCommand m_drive3FeetCommand = new Drive3FeetCommand(m_driveSubsystem);

  private final ParkCommand m_parkCommand = new ParkCommand(m_driveSubsystem);

  // TODO: Create commands for drive 


  /**
   * The container for the robot.  Contains subsystems, OI devices, and commands.
   */
  public RobotContainer() {
    m_driveSubsystem.setDefaultCommand(m_teleopCommand);

    // Configure the button bindings
    configureButtonBindings();
    
  }

  /**
   * Use this method to define your button->command mappings.  Buttons can be created by
   * instantiating a {@link GenericHID} or one of its subclasses ({@link
   * edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then passing it to a
   * {@link edu.wpi.first.wpilibj2.command.button.JoystickButton}.
   */
  private void configureButtonBindings() {
    // TODO: Buttons for intake
    intakeButtonOn.whenPressed(m_intakeOnCommand);
    intakeButtonOff.whenPressed(m_intakeOffCommand);
    // TODO: Buttons for storage
    // TODO: Buttons for shooter
    a.whenPressed(m_turretRightCommand);
    a.whenReleased(m_stopTurretCommand);
    b.whenPressed(m_turretLeftCommand);
    b.whenReleased(m_stopTurretCommand);
    // TODO: Buttons for hang
    // TODO: Buttons for drive
    // TODO: Buttons for vision
    // TODO: Buttons for control panel
  }


  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    // An ExampleCommand will run in autonomous
    return m_autoCommand;
  }
}
