/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
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
  NetworkTableEntry angleEntryT;

  NetworkTableEntry xEntryB;
  NetworkTableEntry yEntryB;
  NetworkTableEntry distanceEntryB;

  private double m_chooseCamera;


  public VisionSubsystem() {

    NetworkTableInstance inst = NetworkTableInstance.getDefault();

    NetworkTable table = inst.getTable("SmartDashboard");

    System.out.println(table);

    xEntryT = table.getEntry("Center X Green");
    yEntryT = table.getEntry("Center Y Green");
    distanceEntryT =  table.getEntry("Green Distance");
    angleEntryT = table.getEntry("Green Angle");

    xEntryB = table.getEntry("Center X Yellow");
    yEntryB = table.getEntry("Center Y Yellow");
    distanceEntryB = table.getEntry("Yellow Entry");

    m_chooseCamera = 1;
    SmartDashboard.putNumber("Camera chooser", m_chooseCamera);
    //cameraChooserEntry = table.getEntry("Camera chooser");
    System.out.println(m_chooseCamera);
  }


  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }

  public void switchCamera() {
    
    System.out.println(m_chooseCamera);
    if (m_chooseCamera == 1 ) {
      SmartDashboard.putNumber("Camera chooser", 2);
      m_chooseCamera = 2;
    } else if (m_chooseCamera == 2) {
      SmartDashboard.putNumber("Camera chooser", 3);
      m_chooseCamera = 3;
    } else if (m_chooseCamera == 3) {
      SmartDashboard.putNumber("Camera chooser", 1);
      m_chooseCamera = 1;
    } else {
      SmartDashboard.putNumber("Camera chooser", 1);
      m_chooseCamera = 1;
    }
     

    
  }

  public double getTargetX() {

    double x = xEntryT.getDouble(-1);
    return x;
  }

  public double getTargetY() {

    double y = yEntryT.getDouble(-1);
    return y;
  }

  public double getTargetdistance() {

    double d = distanceEntryT.getDouble(-1);
    return d;
  }
  public double getTargetAngle() {

    double a = angleEntryT.getDouble(-1);
    return a;
  }

  public double getBallX() {

    double x = xEntryB.getDouble(-1);
    return x;
  }

  public double getBallY() {

    double y = yEntryB.getDouble(-1);
    return y;
  }

  public double getBalldistance() {

    double d = distanceEntryB.getDouble(-1);
    return d;
  }


}
