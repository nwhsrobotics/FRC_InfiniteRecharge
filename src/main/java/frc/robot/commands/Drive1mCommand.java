/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.DriveSubsystem;

public class Drive1mCommand extends CommandBase {
  private static final double ACCEL = 1.0;  // 1.0 meters per sec^2
  private static final double DELTA_T = 0.02;  // 50 updates per second
  private DriveSubsystem m_drive;
  private double m_v;
  private double m_t;

  /**
   * Creates a new Drive1mCommand.
   */
  public Drive1mCommand(DriveSubsystem driveSubsystem) {
    // Use addRequirements() here to declare subsystem dependencies.
    m_drive = driveSubsystem;
    addRequirements(m_drive);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    m_v = 0.0;  // initial velocity
    m_t = 0.0;  // initial time
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    m_t += DELTA_T;
    // Update velocity
    if (m_t < 1.0) {
      // accelerate
      m_v += ACCEL * DELTA_T;
    }
    else {
      // decelerate
      m_v -= ACCEL * DELTA_T;
    }
    // m_drive.setVel(m_v, 0.0);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    // m_drive.setVel(0.0, 0.0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return (m_t >= 2.0);
  }
}
