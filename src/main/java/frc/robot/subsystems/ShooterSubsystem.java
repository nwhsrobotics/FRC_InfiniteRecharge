/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.revrobotics.CANEncoder;
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
  //TODO: Add Turret
  private final CANSparkMax m_turret;
  private final CANPIDController m_turretPID;
  private CANEncoder m_turretEncoder;
  private double kP, kI, kD, kIz, kFF, kmaxOutput, kminOutput; //maxRPM

  //TODO: Add Hood

  public ShooterSubsystem() {
    //TODO: Add Flywheel
      //1621 rpm 5676: NEO max (28%)
    //TODO: Add Turret
    m_turret = new CANSparkMax(14, MotorType.kBrushless);

    m_turretPID = m_turret.getPIDController();
    m_turretEncoder = m_turret.getEncoder();
    m_turretEncoder.setPosition(0); //Zero at initial position

    kP = 0.05;
    kI = 0;
    kD = 0;
    kIz = 0;
    kFF = 0;
    kmaxOutput = 0;
    kminOutput = 0;
    //maxRPM = 0;

    m_turretPID.setP(kP);
    m_turretPID.setI(kI);
    m_turretPID.setD(kD);
    m_turretPID.setIZone(kIz);
    m_turretPID.setFF(kFF);
    m_turretPID.setOutputRange(kminOutput, kmaxOutput);
    System.out.println("Sparks Initialized.");
    


    //TODO: Add Hood
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    //TODO: Add Flywheel
    //TODO: Add Turret
    System.out.println(m_turretEncoder.getPosition());
    //TODO: Add Hood
  }
    //TODO: Add Flywheel
    //TODO: Add Turret
    //TODO: Add Hood

  public void MoveTurret(double setPoint){
    m_turretPID.setReference(setPoint, ControlType.kPosition);
    System.out.println("Running Function moveTurretCommand");
  }
}
