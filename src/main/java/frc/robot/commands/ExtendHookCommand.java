/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.HangSubsystem;

public class ExtendHookCommand extends CommandBase {
  private final double m_height;
  private HangSubsystem m_hangSubsystem;

  /**
   * Creates a new ExtendHookCommand.
   */

  public ExtendHookCommand(HangSubsystem subsystem, double height) {
    m_hangSubsystem = subsystem;
    addRequirements(m_hangSubsystem);
    m_height = height;
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    m_hangSubsystem.ExtendHook(m_height);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
