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
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Constants.OIConstants;
import frc.robot.commands.AutoCommand;
import frc.robot.commands.BallOverrideCommand;
import frc.robot.commands.MoveTurretCommand;
import frc.robot.commands.ToggleSensorCommand;
import frc.robot.commands.ToggleShootCommand;
import frc.robot.commands.ToggleArmedCommand;
import frc.robot.subsystems.ShooterSubsystem;
import frc.robot.subsystems.StorageSubsystem;
import frc.robot.commands.ExtendHookCommand;
import frc.robot.commands.FlyWheelTestingCommand;
import frc.robot.commands.ParkCommand;
import frc.robot.commands.SwitchCameraCommand;
import frc.robot.commands.MoveWinchCommand;
import frc.robot.commands.TeleopCommand;
import frc.robot.commands.TrackTargetCommand;
import frc.robot.commands.IntakeCommand;
import frc.robot.commands.IntakePosCommand;
import frc.robot.subsystems.IntakeSubsystem;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.HangSubsystem;
import frc.robot.subsystems.VisionSubsystem;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;

/**
 * This class is where the bulk of the robot should be declared.  Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls).  Instead, the structure of the robot
 * (including subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer {
  // The robot's subsystems and commands are defined here...
  // TODO: Remove examples and things that depend on them.
 
  
  
  // The robot's subsystems and commands are defined here...
  // TODO: Remove examples and things that depend on them.
  private final XboxController m_joy0 = new XboxController(0);
  private final XboxController m_joy1 = new XboxController(1);
  
  private final JoystickButton joy0_a = new JoystickButton(m_joy0, 1);
  private final JoystickButton joy0_b = new JoystickButton(m_joy0, 2);
  private final JoystickButton joy0_x = new JoystickButton(m_joy0, 3);
  private final JoystickButton joy0_y = new JoystickButton(m_joy0, 4);
  private final JoystickButton joy0_startButton = new JoystickButton(m_joy0, 8);
  //private final JoystickButton joy0_

  private final JoystickButton joy1_a = new JoystickButton(m_joy1, 1);
  private final JoystickButton joy1_b = new JoystickButton(m_joy1, 2);
  private final JoystickButton joy1_x = new JoystickButton(m_joy1, 3);
  private final JoystickButton joy1_y = new JoystickButton(m_joy1, 4);


  //private final JoystickButton intakeButtonOn = new JoystickButton(m_joy1, 1); //a
  //private final JoystickButton intakeButtonOff = new JoystickButton(m_joy1, 2); //b
  //private final JoystickButton intakeButtonUp = new JoystickButton(m_joy1, 1); //a
  //private final JoystickButton intakeButtonDown = new JoystickButton(m_joy1, 2); //b




  

  // --- Create Subsystems and Commands ----------------------
  // 

  
  
  //VISION Subsystem
  private final VisionSubsystem m_visionSubsystem = new VisionSubsystem();


  // Create m_intakeSubsystem
  private final IntakeSubsystem m_intakeSubsystem = new IntakeSubsystem();

  // Create commands for intake
  private final IntakePosCommand m_intakePosUp = new IntakePosCommand(m_intakeSubsystem, false);
  private final IntakePosCommand m_intakePosDown = new IntakePosCommand(m_intakeSubsystem, true);


  // TODO: Create m_shooterSubsystem
  private final ShooterSubsystem m_shooterSubsystem = new ShooterSubsystem(m_visionSubsystem);

  // TODO: create commands for shooter
  private final MoveTurretCommand m_turretRightCommand = new MoveTurretCommand(m_shooterSubsystem, 20);
  private final MoveTurretCommand m_stopTurretCommand = new MoveTurretCommand(m_shooterSubsystem, 0);
  private final MoveTurretCommand m_turretLeftCommand = new MoveTurretCommand(m_shooterSubsystem, -20);
  private final FlyWheelTestingCommand m_flyWheelTestCmd = new FlyWheelTestingCommand(m_shooterSubsystem, m_joy0);





  // TODO: Create m_storageSubsystem
  private final StorageSubsystem m_storageSubsystem = new StorageSubsystem();

  // TODO: Create commands for storage
  private final BallOverrideCommand m_overrideBall = new BallOverrideCommand(m_storageSubsystem);
  private final ToggleSensorCommand m_sensor1Command = new ToggleSensorCommand(m_storageSubsystem, 0);
  private final ToggleSensorCommand m_sensor2Command = new ToggleSensorCommand(m_storageSubsystem, 1);
  private final ToggleSensorCommand m_Sensor3Command = new ToggleSensorCommand(m_storageSubsystem, 2);
  private final ToggleArmedCommand m_toggleArmedCommand = new ToggleArmedCommand(m_storageSubsystem);
  private final ToggleShootCommand m_toggleShootCommand = new ToggleShootCommand(m_storageSubsystem);
  


  // TODO: Create m_hangSubsystem
  private final HangSubsystem m_hangSubsystem = new HangSubsystem();

  // TODO: Create commands for hang
  private final MoveWinchCommand m_moveWinch = new MoveWinchCommand(m_hangSubsystem, 0.3);
  private final MoveWinchCommand m_stopWinch = new MoveWinchCommand(m_hangSubsystem, 0.0);
  private final ExtendHookCommand m_extendHookCommand =  new ExtendHookCommand(m_hangSubsystem, 10);
  private final ExtendHookCommand m_retractHookCommand =  new ExtendHookCommand(m_hangSubsystem, 0);



  // TODO: Create m_controlPanelSubsystem (wheel thing)
  // TODO: Create commands for control panel



  // TODO: Create m_visionSubsystem
  private TrackTargetCommand m_trackTargetCommand = new TrackTargetCommand(m_shooterSubsystem);//m_visionSubsystem.getTargetX());

  // TODO: Create commands for vision
  private final SwitchCameraCommand m_switchCameraCommand = new SwitchCameraCommand(m_visionSubsystem);



  // TODO: Create m_driveSubsystem
  private final DriveSubsystem m_driveSubsystem = new DriveSubsystem();

  // TODO: Create commands for drive 
  private final TeleopCommand m_teleopCommand = new TeleopCommand(m_driveSubsystem,m_joy0);

  



  //AUTOCOMMAND
  private final AutoCommand m_autoCommand = new AutoCommand(m_storageSubsystem, m_shooterSubsystem, 0, 0, 0);


  /**
   * The container for the robot.  Contains subsystems, OI devices, and commands.
   */
  public RobotContainer() {
    //m_shooterSubsystem.setDefaultCommand(m_flyWheelTestCmd); ////--Undoing the setDefaultCommand to test storage
    
    // Configure the button bindings
    configureButtonBindings();
    
  }

  public void update(){
    //System.out.println(XboxController.Button.values());
  }

  public void teleopInit(){

  }

  /**
   * Use this method to define your button->command mappings.  Buttons can be created by
   * instantiating a {@link GenericHID} or one of its subclasses ({@link
   * edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then passing it to a
   * {@link edu.wpi.first.wpilibj2.command.button.JoystickButton}.
   */
  private void configureButtonBindings() {
    // TODO: Buttons for intake
    //intakeButtonOn.whenPressed(m_intakeOnCommand);
    //intakeButtonOff.whenPressed(m_intakeOffCommand);
    //intakeButtonUp.whenPressed(m_intakePosUp);
    //intakeButtonDown.whenPressed(m_intakePosDown);

    // TODO: Buttons for storage
    // TODO: Buttons for shooter
    //a.whenPressed(m_turretRightCommand);
    //a.whenReleased(m_stopTurretCommand);
    //b.whenPressed(m_turretLeftCommand);
    //b.whenReleased(m_stopTurretCommand);
    // TODO: Buttons for storage
    //a.toggleWhenActive(m_StorageCommand); //sensor2 toggle
    //x.toggleWhenActive(m_NextStageCommand); //sensor1 toggle
    //y.whenPressed(m_deleteCommand); //sensor2 false
    joy0_a.whenPressed(m_sensor1Command); //sensor1 toggle
    joy0_b.whenPressed(m_sensor2Command); //sensor2 toggle 
    joy0_startButton.whenPressed(m_toggleArmedCommand); //armedstate toggle
    joy0_y.whenPressed(m_Sensor3Command); //sensor3 toggle
    joy0_x.whenPressed(m_toggleShootCommand);
    // TODO: Buttons for shooter
    joy1_a.whenPressed(m_turretRightCommand);
    joy1_a.whenReleased(m_stopTurretCommand);
    joy1_b.whenPressed(m_turretLeftCommand);
    joy1_b.whenReleased(m_stopTurretCommand);
    joy1_x.toggleWhenPressed(m_trackTargetCommand);
    // TODO: Buttons for hang

    //joy0_a.whenPressed(m_moveWinch);
    //joy0_a.whenReleased(m_stopWinch);
    //joy0_y.whenPressed(m_extendHookCommand);
    //joy0_y.whenReleased(m_retractHookCommand);

     // TODO: Buttons for drive
    // TODO: Buttons for vision
    joy1_y.whenPressed(m_switchCameraCommand);
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
