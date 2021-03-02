/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.VisionSubsystem;

public class ReverseCommand extends CommandBase {
  /**
   * Creates a new ReverseCommand.
   */

  private DriveSubsystem m_driveSubsystem;
  private VisionSubsystem m_visionSubsystem;

  public ReverseCommand(DriveSubsystem drive, VisionSubsystem vision) {
    // Use addRequirements() here to declare subsystem dependencies.
    m_driveSubsystem = drive;
    m_visionSubsystem = vision;
    addRequirements(m_driveSubsystem);
    addRequirements(m_visionSubsystem);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    boolean reverse = m_driveSubsystem.Reverse();
    if(reverse){
      m_visionSubsystem.setCamera(3);
    }
    else{
      m_visionSubsystem.setCamera(1);
    }
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
