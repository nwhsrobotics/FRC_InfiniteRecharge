/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.StorageSubsystem;
import frc.robot.subsystems.VisionSubsystem;


public class AutoCaptureBallCommand extends CommandBase {

  private static final double CENTER_X = 160.0; //center of camera view in pixels

  /*
  TURN_FACTOR SPEED_FACTOR  NOTE
  0.75        1.0           Not enough turning, too fast
  1.25        0.5           Turning power too high
  1.00        0.5           Turning power too high
  0.75        0.5
  */
  private static final double TURN_FACTOR =  -1.5 / CENTER_X; //turning radians per second per pixel
  private static final double SPEED_FACTOR = 0.5 / 36.0; //meters per second per inch
  private static final double TERMINAL_DISTANCE = 60.0;
  private static final double BASE_DISTANCE = 20.0; //distance where power equals 0

  private static final double ACCEL = 4.0;
  private DriveSubsystem m_drive;
  private boolean m_terminalStage;
  private VisionSubsystem m_visionSubsystem;

  private double m_speed;

  private int m_noballs;
 
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
    m_speed = 0;
    m_noballs = 0;
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
      //m_drive.setVel(0.0,0.0);
    }
    else if(dist_inches <= 0.0){
      //TODO: seek ball
      m_noballs += 1;
      System.out.printf("No balls counter: %d", m_noballs);
      if(m_noballs > 10){
        System.out.println("Terminated due to seeing no balls");
        m_terminalStage = true;
      }
    }
    else{
      //convert angle to turning power
      m_noballs = 0;
      double turn = x_pixels * TURN_FACTOR;
      if(turn <= -1.0){
        turn = -1.0;
      }
      if(turn > 1.0){
        turn = 1.0;
      }

      //convert distance to drive power
      double speed = (dist_inches - BASE_DISTANCE) * SPEED_FACTOR;
      if(speed > m_speed + 0.02 * ACCEL){
        m_speed += 0.02 * ACCEL;
      }
      else{
        m_speed = speed;
      }
      System.out.printf("power %f turn %f\n", m_speed, turn);
      m_drive.setVel(m_speed, turn);
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
