/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.StorageSubsystem;

public class ToggleSensorCommand extends CommandBase {
  private final StorageSubsystem m_storageSubsystem;
  private final int m_sensorID;
  /**
   * Creates a new Sensor1Command.
   */
  public ToggleSensorCommand(StorageSubsystem subsystem, int sensorID) {
    m_storageSubsystem = subsystem;
    m_sensorID = sensorID;
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(subsystem);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    m_storageSubsystem.setSensor(m_sensorID, !m_storageSubsystem.getSensor(m_sensorID));
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
