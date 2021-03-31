/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.DriveSubsystem;

public class DriveTurnCommand extends DrivePathBase {
  private static final double ACCEL = 0.5;  // 1.0 radians per sec^2
  private static final double DELTA_T = 0.02;  // 50 updates per second
  private static final double METERS_PER_FOOT = 0.305;
  private DriveSubsystem m_drive;
  private double m_v;
  private double m_t;

  /**
   * Creates a new Drive1mCommand.
   */
  public DriveTurnCommand(DriveSubsystem driveSubsystem, double degrees)
  {
    super(driveSubsystem);
    DrivePathBase.PathElt path_scratch[] = 
        new DrivePathBase.PathElt[10000];

    double radians = degrees * Math.PI/180.0;

    double omega = 0.0;
    double omega_n = 0.0;
    double t = 0.0;
    double a = ACCEL;
    double h = DELTA_T;
    double theta = 0.0;
    int tick = 0;
    while (theta < radians) {
      if (theta < (radians / 2.0)) {
        omega_n = omega + a * h;
      }
      else {
        omega_n = omega - a * h;
      }
      theta += h*(omega+omega_n)/2.0;
      omega = omega_n;
      t += h;
      //System.out.printf("created path element %d\n", tick);
      
      PathElt s = new DrivePathBase.PathElt(0.0, 0.0, omega, theta);
      path_scratch[tick] = s;
      tick += 1;
    }
    PathElt s = new DrivePathBase.PathElt(0.0, 0.0, 0.0, theta);
    path_scratch[tick] = s;

    DrivePathBase.PathElt path[] = 
        new DrivePathBase.PathElt[tick + 1];

    for(int n=0; n<=tick; n++){
      path[n] = path_scratch[n];
    }




    setPath(path);
    System.out.printf("I set the path!");

  }

}
