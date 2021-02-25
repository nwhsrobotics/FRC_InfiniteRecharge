/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.VisionSubsystem;

public class SwitchCameraCommand extends CommandBase {
  private VisionSubsystem m_visionSubsystem;

  /**
   * Creates a new SwitchCameraCommand.
   */

public SwitchCameraCommand(VisionSubsystem visionSubsystem) {
  // Use addRequirements() here to declare subsystem dependencies.
  m_visionSubsystem = visionSubsystem;
  addRequirements(m_visionSubsystem);
}


// Called when the command is initially scheduled.
  @Override
  public void initialize() {
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    m_visionSubsystem.switchCamera();
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return true;
  }
}
