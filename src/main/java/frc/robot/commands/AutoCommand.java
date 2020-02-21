/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.ShooterSubsystem;
import frc.robot.subsystems.StorageSubsystem;

public class AutoCommand extends CommandBase {
  private final StorageSubsystem m_storageSubsystem;
  private final ShooterSubsystem m_shooterSubsystem;
  //ADD REST OF SUBSYSTEMS

  private double m_speed;   
  private double m_turn;
  private double m_time;

  /**
   * Creates a new AutoCommand.
   */
  public AutoCommand(StorageSubsystem storage, ShooterSubsystem shooter, double X, double Y, double time) {          // ADD REST OF SUBSYSTEMS ONCE PULLED
    m_storageSubsystem = storage;
    m_shooterSubsystem = shooter;
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(storage);
    addRequirements(shooter);
    //ADD REST OF REQUIREMENTS

    m_speed = X;  //CHECK X AND Y FOR FORWARD/TURNING
    m_turn = Y;
    m_time = time;
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    withTimeout(m_time);
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {

    //m_driveSubsystem.arcadeDrive(m_speed, m_turn);      PLACEHOLDER FUNCTIONS.  USE REAL FUNCTION NAME
    //m_driveSubsystem.visionfunction();
    //m_intakeSubsystem.intakefunction();
    //m_shooterSubsystem.shootingfunction();
    m_storageSubsystem.periodic();
  }


  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false; //FIND HOW TO SET THE TIMEOUT OF THE COMMAND
  }
}
