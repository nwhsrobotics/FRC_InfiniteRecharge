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
import frc.robot.Constants;

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
  private final CANSparkMax m_hoodMotor;
  private double m_hoodPower=0.0;
  private final CANPIDController m_hoodPid;
  private CANEncoder m_hoodEncoder;
  private double kHoodP, kHoodI, kHoodD, kHoodIz, kHoodFF, kHoodMaxOutput, kHoodMinOutput;
  

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
    m_hoodMotor = new CANSparkMax(Constants.Shooter.CANID_HOOD, MotorType.kBrushless);
    m_hoodPid = m_hoodMotor.getPIDController();
    m_hoodEncoder = m_hoodMotor.getEncoder();
    m_hoodEncoder.setPosition(0);

    kHoodP = 0.05;
    kHoodI = 0;
    kHoodD = 0;
    kHoodIz = 0;
    kHoodFF = 0;
    kHoodMaxOutput = 0;
    kHoodMinOutput = 0;

    m_hoodPid.setP(kHoodP);
    m_hoodPid.setI(kHoodI);
    m_hoodPid.setD(kHoodD);
    m_hoodPid.setIZone(kHoodIz);
    m_hoodPid.setFF(kHoodFF);
    m_hoodPid.setOutputRange(kHoodMinOutput, kHoodMaxOutput);
    m_hoodPid.setReference(0.0, ControlType.kPosition);
    System.out.println("Hood Sparks Initialized.");
    
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    //TODO: Add Flywheel
    //TODO: Add Turret
    //TODO: Add Hood
    m_hoodMotor.set(m_hoodPower);
  }
    //TODO: Add Flywheel
    //TODO: Add Turret
    //TODO: Add Hood
    public void setHoodPower(double power){
      m_hoodPower = power;
    }
}
