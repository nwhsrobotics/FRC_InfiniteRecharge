/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.XboxController.Button;
import frc.robot.Constants.OIConstants;
import frc.robot.commands.AutoCommand;
import frc.robot.commands.MoveTurretCommand;
import frc.robot.commands.ToggleSensorCommand;
import frc.robot.commands.ToggleArmedCommand;
import frc.robot.subsystems.ShooterSubsystem;
import frc.robot.subsystems.StorageSubsystem;
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
  private final JoystickButton x = new JoystickButton(joy, 3);
  private final JoystickButton y = new JoystickButton(joy, 4);
  private final JoystickButton startButton = new JoystickButton(joy, 7);
  
  // The robot's subsystems and commands are defined here...
  // TODO: Remove examples and things that depend on them.
 
  

  // --- Create Subsystems and Commands ----------------------
  // 
  // Create m_intakeSubsystem
  // Create commands for intake

  // TODO: Create m_shooterSubsystem
  // TODO: create commands for shooter
  private final ShooterSubsystem m_shooterSubsystem = new ShooterSubsystem();

  private final MoveTurretCommand m_moveTurretCommand = new MoveTurretCommand(m_shooterSubsystem);

  // TODO: Create m_storageSubsystem
  private final StorageSubsystem m_storageSubsystem = new StorageSubsystem();
  // TODO: Create commands for storage
  private final ToggleSensorCommand m_sensor1Command = new ToggleSensorCommand(m_storageSubsystem, 1);
  private final ToggleSensorCommand m_sensor2Command = new ToggleSensorCommand(m_storageSubsystem, 2);
  private final ToggleSensorCommand m_Sensor3Command = new ToggleSensorCommand(m_storageSubsystem, 3);
  private final ToggleArmedCommand m_toggleArmedCommand = new ToggleArmedCommand(m_storageSubsystem);
  
  private final AutoCommand m_autoCommand = new AutoCommand(m_storageSubsystem, m_shooterSubsystem, 0, 0, 0);

  // TODO: Create m_hangSubsystem
  // TODO: Create commands for hang

  // TODO: Create m_controlPanelSubsystem (wheel thing)
  // TODO: Create commands for control panel

  // TODO: Create m_visionSubsystem
  // TODO: Create commands for vision

  // TODO: Create m_driveSubsystem
  // TODO: Create commands for drive


  /**
   * The container for the robot.  Contains subsystems, OI devices, and commands.
   */
  public RobotContainer() {
    // Configure the button bindings
    configureButtonBindings();
  }

  public void update(){

  }

  /**
   * Use this method to define your button->command mappings.  Buttons can be created by
   * instantiating a {@link GenericHID} or one of its subclasses ({@link
   * edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then passing it to a
   * {@link edu.wpi.first.wpilibj2.command.button.JoystickButton}.
   */
  private void configureButtonBindings() {
    // TODO: Buttons for intake
    // TODO: Buttons for storage
    //a.toggleWhenActive(m_StorageCommand); //sensor2 toggle
    //x.toggleWhenActive(m_NextStageCommand); //sensor1 toggle
    //y.whenPressed(m_deleteCommand); //sensor2 false
    x.whenPressed(m_sensor1Command); //sensor1 toggle
    b.whenPressed(m_sensor2Command); //sensor2 toggle 
    startButton.whenPressed(m_toggleArmedCommand); //armedstate toggle
    y.whenPressed(m_Sensor3Command); //sensor3 toggle
    // TODO: Buttons for shooter
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
