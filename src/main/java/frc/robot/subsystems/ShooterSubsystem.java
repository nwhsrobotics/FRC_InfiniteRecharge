/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.revrobotics.CANPIDController;
import com.revrobotics.CANSparkMax;
import com.revrobotics.ControlType;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class ShooterSubsystem extends SubsystemBase {
  /**
   * Creates a new ShooterSubsystem.
   */
  //TODO: Add Flywheel
  private final CANSparkMax m_flywheel;
  private final CANPIDController m_flywheelPID;
  private double kflywheelP, kflywheelI, kflywheelD, kflywheelIz, kflywheelFF, kflywheelmaxOutput, kflywheelminOutput;
  //TODO: Add Turret
  //TODO: Add Hood
  

  

  public ShooterSubsystem() {
    //TODO: Add Flywheel
    m_flywheel = new CANSparkMax(13, MotorType.kBrushless);
    m_flywheel.set(0.0);
     m_flywheelPID = m_flywheel.getPIDController();
     kflywheelP = 0.05;
     kflywheelI = 0;
     kflywheelIz = 0;
     kflywheelFF = 0;
     kflywheelmaxOutput = 0;
     kflywheelminOutput = 0;

     m_flywheelPID.setP(kflywheelP);
     m_flywheelPID.setI(kflywheelI);
     m_flywheelPID.setD(kflywheelD);
     m_flywheelPID.setIZone(kflywheelIz);
     m_flywheelPID.setFF(kflywheelFF);
     m_flywheelPID.setOutputRange(kflywheelminOutput, kflywheelmaxOutput);
     m_flywheelPID.setReference(0.0, ControlType.kVelocity);
     System.out.println("Sparks Initialized. ");


    //TODO: Add Turret
    //TODO: Add Hood
    
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    //TODO: Add Flywheel
    //TODO: Add Turret
    //TODO: Add Hood
  }
    //TODO: Add Flywheel
    //TODO: Add Turret
    //TODO: Add Hood
}
