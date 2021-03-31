/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.CommandGroupBase;
import frc.robot.subsystems.VisionSubsystem;

public class DecideRedBlueCommand extends CommandBase {
  private static final double CENTER_X = 160.0;
  private CommandGroupBase m_blue;
  private CommandGroupBase m_red;
  private VisionSubsystem m_visionSubsystem;

  /**
   * Creates a new DecideRedBlueCommand.
   */
  public DecideRedBlueCommand(CommandGroupBase blue, CommandGroupBase red, VisionSubsystem visionSubsystem) {
    m_blue = blue;
    m_red = red;
    m_visionSubsystem = visionSubsystem;
    // Use addRequirements() here to declare subsystem dependencies.
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    double x_pixels = m_visionSubsystem.getBallX() - CENTER_X;
    if(x_pixels <= 0){
      System.out.println("Decided Blue\n");
      m_blue.schedule();
    }
    else{
      System.out.println("Decided Red\n");
      m_red.schedule();
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
