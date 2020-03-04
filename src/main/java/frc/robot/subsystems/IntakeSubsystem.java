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

public class IntakeSubsystem extends SubsystemBase {
  /**
   * Creates a new Intake subsystem.
   */
  private final CANSparkMax m_intake;
  private double m_intakeSpeed = 0.0;
  //private CANEncoder m_intakeEncoder;
  
  
  
  
  //intake position control constants.
  private CANSparkMax m_intakeArmMotor1 = null;
  private CANSparkMax m_intakeArmMotor2 = null;
  private double m_intakePower=0.0;
  private CANPIDController m_intakePid1 = null;
  private CANEncoder m_intakeEncoder1;
  private CANPIDController m_intakePid2 = null;
  private CANEncoder m_intakeEncoder2;
  private double kIntakeP, kIntakeI, kIntakeD, kIntakeIz, kIntakeFF, kIntakeMaxOutput, kIntakeMinOutput;
  private static final double DOWNPOS = 100.0;
  private static final double UPPOS = 0.0;
  private static final double TICKS_PER_SECOND = 50.0;
  private static final double SPEED_ROT_PER_TICK = ((DOWNPOS - UPPOS)/ (2.0 * TICKS_PER_SECOND)); //2 seconds to move
  private double m_armPos = 0;

  public IntakeSubsystem() {
    m_intake = new CANSparkMax(Constants.IntakeArm.CANID_INTAKE, MotorType.kBrushless);
    
    //2nd intake motor
    m_intakeArmMotor1 = new CANSparkMax(Constants.IntakeArm.CANID_INTAKEARM1, MotorType.kBrushless);
    if(m_intakeArmMotor1 != null){
      m_intakePid1 = m_intakeArmMotor1.getPIDController();
      m_intakeEncoder1 = m_intakeArmMotor1.getEncoder();
      m_intakeEncoder1.setPosition(0);

      
      kIntakeP = 1.0;
      kIntakeI = 0;
      kIntakeD = 0;
      kIntakeIz = 0;
      kIntakeFF = 0;
      kIntakeMaxOutput = 0;
      kIntakeMinOutput = 0;

    
  
      m_intakePid1.setP(kIntakeP);
      m_intakePid1.setI(kIntakeI);
      m_intakePid1.setD(kIntakeD);
      m_intakePid1.setIZone(kIntakeIz);
      m_intakePid1.setFF(kIntakeFF);
      m_intakePid1.setOutputRange(kIntakeMinOutput, kIntakeMaxOutput);
      m_intakePid1.setReference(0.0, ControlType.kPosition);
      System.out.println("Intake Arm Sparks 1 Initialized.");
    }

    //2nd intake arm motor
    m_intakeArmMotor2 = new CANSparkMax(Constants.IntakeArm.CANID_INTAKE, MotorType.kBrushless);
    if(m_intakeArmMotor2 != null){
      m_intakePid2 = m_intakeArmMotor2.getPIDController();
      m_intakeEncoder2 = m_intakeArmMotor2.getEncoder();
      m_intakeEncoder2.setPosition(0);
  
      m_intakePid2.setP(kIntakeP);
      m_intakePid2.setI(kIntakeI);
      m_intakePid2.setD(kIntakeD);
      m_intakePid2.setIZone(kIntakeIz);
      m_intakePid2.setFF(kIntakeFF);
      m_intakePid2.setOutputRange(kIntakeMinOutput, kIntakeMaxOutput);
      m_intakePid2.setReference(0.0, ControlType.kPosition);
      System.out.println("Intake Arm Sparks 2 Initialized.");
    }

  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run

    //intake motor
    if(m_intake != null){
      m_intake.set(m_intakeSpeed);
    }
    
    
    //intake arm motor 
    if(m_intakePid1 != null){
      if (isArmDown()){
        m_intakeArmMotor1.set(0);
        System.out.println("Arm is down.");
      }
      else {
        m_intakePid1.setReference(m_armPos, ControlType.kPosition);
      }
    }

    if(m_intakePid2 != null){
      if (isArmDown()){
        m_intakeArmMotor2.set(0);
        System.out.println("Arm is down.");
      }
      else {
        m_intakePid2.setReference(-m_armPos, ControlType.kPosition);
      }
    //  m_intakePid.setReference(m_armPos, ControlType.kPosition);
    }
  }
    
  

  //intake motor
  public void intakeMotor(double speed){
    m_intakeSpeed = speed;
  }


//intake arm methods
public void moveArmDown() {
  m_armPos += SPEED_ROT_PER_TICK;
  if (m_armPos >= DOWNPOS){
    m_armPos = DOWNPOS;
  }

}

public void moveArmUp() {
  m_armPos -= SPEED_ROT_PER_TICK;
  if (m_armPos <= UPPOS){
    m_armPos = UPPOS;
  }
  
}

public boolean isArmDown() {

	return (m_armPos == DOWNPOS);
}

public boolean isArmUp() {

	return (m_armPos == UPPOS);
}

}