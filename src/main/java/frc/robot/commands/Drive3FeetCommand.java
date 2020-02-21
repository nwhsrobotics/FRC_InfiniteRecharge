/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.DriveSubsystem;

public class Drive3FeetCommand extends CommandBase {
  private final double TIME_PER_EXEC = 0.02; //seconds per execute loop
  private final double TIME_LIMIT = 1.0; //seconds to move
  private final double POWER = 0.1; //drive power to use

  private DriveSubsystem m_driveSubsystem;
  private double m_time;

  /**
   * Creates a new Drive3FeetCommand.
   */
  public Drive3FeetCommand(DriveSubsystem drive) {
    // Use addRequirements() here to declare subsystem dependencies.
    m_driveSubsystem = drive;
    m_time = 0.0;
    addRequirements(m_driveSubsystem);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    m_time = 0.0;
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    m_time += TIME_PER_EXEC;
    m_driveSubsystem.setDrivePower(POWER,0.0);
    System.out.println("Drive 3 feet");
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    m_driveSubsystem.setDrivePower(0,0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return (m_time > TIME_LIMIT);
  }
}
