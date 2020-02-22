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

public class HangSubsystem extends SubsystemBase {
  /**
   * Creates a new HangSubsystem.
   */
  ///TODO: Add Winch
  private final CANSparkMax m_winch;
  private double m_speed=0;
  


  ///TODO: Add Hook
  private final CANSparkMax m_hook;
  private final CANPIDController m_hookPID;
  private CANEncoder m_hookEncoder;
  private double kHookP, kHookI, kHookD, kHookIz, kHookFF, kHookmaxOutput, kHookminOutput;

  public HangSubsystem() {
    ///TODO: Add Winch
    m_winch = new CANSparkMax(Constants.Hang.CANID_WINCH, MotorType.kBrushless);
    m_winch.set(0.0);
    
    ///TODO: Add Hook
    m_hook = new CANSparkMax(Constants.Hang.CANID_HOOK, MotorType.kBrushless);
    m_hookPID = m_hook.getPIDController();
    m_hookEncoder = m_hook.getEncoder();
    m_hookEncoder.setPosition(0); //Zero at initial position
    kHookP = 0.05;
    kHookI = 0;
    kHookD = 0;
    kHookIz = 0;
    kHookFF = 0;
    kHookmaxOutput = 1;
    kHookminOutput = -1;

    m_hookPID.setP(kHookP);
    m_hookPID.setI(kHookI);
    m_hookPID.setD(kHookD);
    m_hookPID.setIZone(kHookIz);
    m_hookPID.setFF(kHookFF);
    m_hookPID.setOutputRange(kHookminOutput, kHookmaxOutput);
    System.out.println("Sparks Initialized");

    System.out.println("The hook temp is:  " + m_hook.getMotorTemperature());
    System.out.println("The Winch Temp is:  " + m_winch.getMotorTemperature());

  }

  @Override
  public void periodic() {
    m_winch.set(m_speed);
    // This method will be called once per scheduler run
  }

  public void MoveWinch(double speed){
   m_speed= speed;
  }
  public void ExtendHook(double setPoint){
    m_hookPID.setReference(setPoint, ControlType.kPosition);
  }
}
