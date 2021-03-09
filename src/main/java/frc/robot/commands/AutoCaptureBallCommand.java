/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.VisionSubsystem;

public class AutoCaptureBallCommand extends CommandBase {

  private static final double CENTER_X = 160.0; //center of camera view in pixels
  private static final double TURN_FACTOR =  0.75 / CENTER_X; //turning power per pixel
  private static final double SPEED_FACTOR = 1.0 / 100.0;
  private static final double TERMINAL_DISTANCE = 55.0;
  private static final double BASE_DISTANCE = 20.0; //distance where power equals 0
  private DriveSubsystem m_drive;
  private boolean m_terminalStage;
  private VisionSubsystem m_visionSubsystem;
  /**
   * Creates a new AutoCaptureBallCommand.
   */

  public AutoCaptureBallCommand(DriveSubsystem driveSubsystem, VisionSubsystem visionSubsystem) {
    // Use addRequirements() here to declare subsystem dependencies.
    m_drive = driveSubsystem;
    m_visionSubsystem = visionSubsystem;
    addRequirements(m_drive);
  }


  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    m_terminalStage = false;
    System.out.println("This captureBallCommand started\n");
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    //find ball angle
    double x_pixels = m_visionSubsystem.getBallX() - CENTER_X;

    //find ball distance
    double dist_inches = m_visionSubsystem.getBalldistance();

    if(!m_terminalStage && (dist_inches > 0.0) && (dist_inches <= TERMINAL_DISTANCE)){

        m_terminalStage = true;
    }

    if(m_terminalStage){
      //TODO:
      System.out.println("terminalStage\n");
      m_drive.setDrivePower(0.0,0.0);
    }
    else if(dist_inches <= 0.0){
      //TODO: seek ball
      System.out.println("no ball\n");
      m_drive.setDrivePower(0.0,0.0); //no ball to chase

    }
    else{
      //convert angle to turning power
      double turn = x_pixels * TURN_FACTOR;
      if(turn <= -1.0){
        turn = -1.0;
      }
      if(turn > 1.0){
        turn = 1.0;
      }

      //convert distance to drive power
      double power = (dist_inches - BASE_DISTANCE) * SPEED_FACTOR;
      if(power < -1.0){
        power = -1.0;
      }
      if(power > 1.0){
        power = 1.0;
      }
      System.out.printf("power %f turn %f\n", power, turn);
      m_drive.setDrivePower(power, turn);
    }

    
    
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    //TODO: finish this
    if(m_terminalStage){
      System.out.println("AutoCaptureBallCommand finished\n");
    }
    return m_terminalStage;
  }
}
