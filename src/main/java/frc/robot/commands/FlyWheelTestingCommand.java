/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.ShooterSubsystem;

public class FlyWheelTestingCommand extends CommandBase {
  private final ShooterSubsystem m_ShooterSubsystem;
  private final XboxController m_joy;
  private boolean armed;
  /**
   * Creates a new FlyWheelTestingCommand.
 * 
   */
  public FlyWheelTestingCommand(ShooterSubsystem subsystem, XboxController joy) {
    m_ShooterSubsystem = subsystem;
    m_joy = joy;
    
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(subsystem);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    //m_ShooterSubsystem.switcharmedState();
    //armed = m_ShooterSubsystem.getarmedState();
    System.out.println("flywheel command initializd");
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {

    
    m_ShooterSubsystem.manualClosedLoopFlywheel(1000);
    //TODO - Make it turn off when released
    
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