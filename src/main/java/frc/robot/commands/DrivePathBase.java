/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.DriveSubsystem;

public class DrivePathBase extends CommandBase {
  protected class PathElt {
    double m_vel_mps;        // current fwd vel [meters per sec]
    double m_rot_rps;        // current rot vel [radians per sec]
    double m_dist_m;         // distance from start of move [m]
    double m_heading_rad;    // heading change from start of move [rad]

    PathElt(double v, double d, double vr, double hdg) {
      m_vel_mps = v;
      m_rot_rps = vr;
      m_dist_m = d;
      m_heading_rad = hdg;
    }
  };

  private DriveSubsystem m_drive;
  private PathElt[] m_path;
  private int m_index;

  /**
   * Creates a new DriveSequenceCommandBase.
   */
  public DrivePathBase(DriveSubsystem drive) {
    // Use addRequirements() here to declare subsystem dependencies.
    m_drive = drive;
    addRequirements(m_drive);
  }

  protected void setPath(PathElt[] path) {
    m_path = path;
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    m_index = 0;
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    System.out.printf("m_index: =%d", m_index);
    System.out.printf("m_path[m_index] = %s", m_path[m_index]);
    if (m_index < m_path.length) {
      if(m_drive == null){
        System.out.println("m_drive is null");
      }
      if(m_path == null){
        System.out.println("m_path is null");
      }
      
      m_drive.setVel(m_path[m_index].m_vel_mps, 
                     m_path[m_index].m_rot_rps);
                    
      m_index += 1;
    }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return m_index >= m_path.length;
  }
}
