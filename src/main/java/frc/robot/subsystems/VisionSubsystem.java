/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;

public class VisionSubsystem extends SubsystemBase {
  /**
   * Creates a new VisionSubsystem.
   */

  NetworkTableEntry xEntryT;
  NetworkTableEntry yEntryT;
  NetworkTableEntry distanceEntryT;

  NetworkTableEntry xEntryB;
  NetworkTableEntry yEntryB;
  NetworkTableEntry distanceEntryB;


  public VisionSubsystem() {

    NetworkTableInstance inst = NetworkTableInstance.getDefault();

    NetworkTable table = inst.getTable("SmartDashboard");

    System.out.println(table);

    xEntryT = table.getEntry("Center X Green");
    yEntryT = table.getEntry("Center Y Green");
    distanceEntryT =  table.getEntry("Green Distance");

    xEntryB = table.getEntry("Center X Yellow");
    yEntryB = table.getEntry("Center Y Yellow");
    distanceEntryB = table.getEntry("Yellow Entry");


  }


  @Override
  public void periodic() {
    // This method will be called once per scheduler run

  }

  public double getTargetX() {

    double x = xEntryT.getDouble(0);
    return x;
  }

  public double getTargetY() {

    double y = yEntryT.getDouble(0);
    return y;
  }

  public double getTargetdistance() {

    double d = distanceEntryT.getDouble(0);
    return d;
  }

  public double getBallX() {

    double x = xEntryB.getDouble(0);
    return x;
  }

  public double getBallY() {

    double y = yEntryB.getDouble(0);
    return y;
  }

  public double getBalldistance() {

    double d = distanceEntryB.getDouble(0);
    return d;
  }


}
