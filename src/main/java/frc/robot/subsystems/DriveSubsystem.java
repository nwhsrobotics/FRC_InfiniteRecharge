/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class DriveSubsystem extends SubsystemBase {
  //To do: Declare motor controllers (CANSparkMax)
  //Declare motor groups 
  //Declare ArcadeDrive
  
  /**
   * Creates a new DriveSubsystem.
   */
  public DriveSubsystem() {
    //Add motors to groups
    //Create ArcadeDrive


  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }

  public void setDrivePower(double power, double turn){
    //To do: Control motors
  }
}

