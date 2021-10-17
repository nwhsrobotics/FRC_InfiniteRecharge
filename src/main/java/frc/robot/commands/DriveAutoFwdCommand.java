/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.DriveSubsystem;

public class DriveAutoFwdCommand extends CommandBase {
    private static final double POWER = 0.40;
    double m_time_s = 0.0;
    DriveSubsystem m_drive;
  /**
   * Creates a new DriveAutoFwdCommand.
   */
  public DriveAutoFwdCommand(DriveSubsystem drive, double dist_ft) {
    // Use addRequirements() here to declare subsystem dependencies.
    m_drive = drive;
    addRequirements(drive);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
      m_time_s = 0.5;
      System.out.println("Started DriveAutoFwdCommand.");
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
      m_time_s -= 0.02;
      if (m_time_s > 0.0) {
          // Apply power
          m_drive.setDrivePower(POWER, 0.0);
      }
      else {
          // Stop
          m_drive.setDrivePower(0.0, 0.0);
      }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return m_time_s <= 0.0;
  }
}
