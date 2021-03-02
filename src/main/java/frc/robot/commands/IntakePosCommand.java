/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/


package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.IntakeSubsystem;

/**
 * An example command that uses an example subsystem.
 */
public class IntakePosCommand extends CommandBase {
  @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
  private final IntakeSubsystem m_intakeSubsystem;
  private final boolean m_down;
  private boolean armDown;
 
  /**
   * Creates a new ExampleCommand.
   *
   * @param subsystem The subsystem used by this command.
   */
  public IntakePosCommand(IntakeSubsystem intakeSubsystem, boolean down) {
    m_intakeSubsystem = intakeSubsystem;
    addRequirements(intakeSubsystem);
    m_down = down;
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    if (m_down){
        m_intakeSubsystem.moveArmDown();
    }
    else{
        m_intakeSubsystem.moveArmUp();
    }
      
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    if (m_down){
      if(m_intakeSubsystem.isArmDown()){
        m_intakeSubsystem.resetPos();
        return true;
      } else{
        return false;
      }

    }
    else{
      if(m_intakeSubsystem.isArmUp()){
        m_intakeSubsystem.resetPos();
        return true;
      } else{
        return false;
      }
        
    }
  }
}