/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.ShooterSubsystem;
import frc.robot.subsystems.StorageSubsystem;

public class ToggleShootCommand extends CommandBase {
  private StorageSubsystem m_storageSubsystem; 
  private boolean currentState;
  private boolean newState;
  /**
   * Creates a new ToggleShootCommand.
   */
  public ToggleShootCommand(StorageSubsystem subsystem) {
    m_storageSubsystem = subsystem;
    addRequirements(subsystem);
    // Use addRequirements() here to declare subsystem dependencies.
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    currentState = m_storageSubsystem.getShootState();
    if (currentState == true){
      newState = false;
    } else if (currentState == false){
      newState = true;
    }
    SmartDashboard.putBoolean("Current State", currentState);
    System.out.println("CurrentState: " + currentState);
    SmartDashboard.putBoolean("New State: ", newState);
    System.out.println("New State:  " + newState);

    m_storageSubsystem.setShoot(newState);
    
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
