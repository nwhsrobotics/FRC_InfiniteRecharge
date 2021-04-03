/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.CommandGroupBase;
import frc.robot.subsystems.VisionSubsystem;

public class PathDecideCommand extends CommandBase {
  private static final double CENTER_X = 160.0;
  private static final double DECISION_DIST_INCHES = 140; //todo:
  private static final double X_RED = 123; //todo:
  private static final double X1_BLUE = 105;//todo:
  private static final double X2_BLUE = 155;
  private static final int NUM_OBSERVATIONS = 50;
  private CommandGroupBase m_A_blue;
  private CommandGroupBase m_A_red;
  private CommandGroupBase m_B_blue;
  private CommandGroupBase m_B_red;
  private VisionSubsystem m_visionSubsystem;
  private int m_observations;

  /**
   * Creates a new DecideRedBlueCommand.
   */
  public PathDecideCommand(CommandGroupBase A_blue, CommandGroupBase A_red,CommandGroupBase B_blue, CommandGroupBase B_red, VisionSubsystem visionSubsystem) {
    m_A_blue = A_blue;
    m_A_red = A_red;
    m_B_blue = B_blue;
    m_B_red = B_red;

    m_visionSubsystem = visionSubsystem;
    // Use addRequirements() here to declare subsystem dependencies.
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    m_observations = 0;
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    if(m_visionSubsystem == null){
      System.out.println("Yea its def null");
    }
    double dist_inches = m_visionSubsystem.getBalldistance();
    double x_pixels = m_visionSubsystem.getBallX();
    if(dist_inches < 0){
      return;
    }
    else{
      m_observations += 1;
    }
    if (m_observations < NUM_OBSERVATIONS){
      return;
    }

    System.out.printf("Dist = %f, X = %f", dist_inches, x_pixels);

    if(dist_inches < DECISION_DIST_INCHES){
      if(x_pixels <= X_RED){
        System.out.println("Decide Path B Red\n");
        m_B_red.schedule();
      }
      else{
        System.out.println("Decided Path A Red\n");
        m_A_red.schedule();
      }
    }else{
      if((x_pixels >= X1_BLUE) && (x_pixels <= X2_BLUE)){
        System.out.printf("Decided Path B Blue\n");
        m_B_blue.schedule();
      }
      else{
        System.out.println("Decided A Blue\n");
        m_A_blue.schedule();
      }
    }

    
    
    
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return m_observations >= NUM_OBSERVATIONS;
  }
}
