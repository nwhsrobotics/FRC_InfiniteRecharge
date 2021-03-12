/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.DriveSubsystem;

public class PTAutoTurnCommand extends CommandBase {
  private DriveSubsystem m_drive;
  private double m_turn;
  private double m_time;
  private double m_elapsed;

/**
   * Creates a new PTAutoTurnCommand.
   */
  public PTAutoTurnCommand(DriveSubsystem drive, double turn, double time) {
    // Use addRequirements() here to declare subsystem dependencies.
    m_drive = drive;
    m_turn = turn;
    m_time = time;
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    m_elapsed = 0.0;
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    m_elapsed += 0.020;

    m_drive.setDrivePower(0.0, m_turn);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {

    m_drive.setDrivePower(0.0, 0.0);

  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return m_elapsed >= m_time;
  }
}
