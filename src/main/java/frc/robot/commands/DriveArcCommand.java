/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.DriveSubsystem;

public class DriveArcCommand extends DrivePathBase {
  private static final double ACCEL = 2.0;  // 1.0 meters per sec^2
  private static final double DELTA_T = 0.02;  // 50 updates per second
  private static final double METERS_PER_FOOT = 0.305;
  private DriveSubsystem m_drive;
  private double m_v;
  private double m_t;

  /**
   * Creates a new Drive1mCommand.
   */
  public DriveArcCommand(DriveSubsystem driveSubsystem, double distance_feet, double turnRadius_feet)
  {
    super(driveSubsystem);
    DrivePathBase.PathElt path_scratch[] = 
        new DrivePathBase.PathElt[10000];

    double dist_meters = distance_feet * METERS_PER_FOOT;
    double r_meters = turnRadius_feet * METERS_PER_FOOT;
    double time = 2.0 * Math.sqrt(dist_meters / ACCEL);


    double v = 0.0;
    double vn = 0.0;
    double omega = 0.0;
    double omega_n = 0.0;
    double t = 0.0;
    double a = ACCEL;
    double h = DELTA_T;
    double d = 0.0;
    double theta = 0.0;
    int tick = 0;
    while (d < dist_meters) {
      if (d < (dist_meters / 2.0)) {
        vn = v + a * h;
      }
      else {
        vn = v - a * h;
      }
      omega_n = vn / r_meters;
      d += h*(v+vn)/2.0;
      theta += h*(omega+omega_n)/2.0;
      v = vn;
      omega = omega_n;
      t += h;
      System.out.printf("created path element %d\n", tick);
      
      PathElt s = new DrivePathBase.PathElt(v, d, omega, theta);
      path_scratch[tick] = s;
      tick += 1;
    }
    PathElt s = new DrivePathBase.PathElt(0.0, d, 0.0, theta);
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
