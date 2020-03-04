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
  private CANSparkMax m_intakeMotor = null;
  private double m_intakePower=0.0;
  private CANPIDController m_intakePid = null;
  private CANEncoder m_intakeEncoder;
  private double kIntakeP, kIntakeI, kIntakeD, kIntakeIz, kIntakeFF, kIntakeMaxOutput, kIntakeMinOutput;
  private static final double DOWNPOS = 100.0;
  private static final double UPPOS = 0.0;
  private static final double TICKS_PER_SECOND = 50.0;
  private static final double SPEED_ROT_PER_TICK = ((DOWNPOS - UPPOS)/ (2.0 * TICKS_PER_SECOND)); //2 seconds to move
  private double m_armPos = 0;

  public IntakeSubsystem() {
    m_intake = new CANSparkMax(Constants.IntakeArm.CANID_INTAKE, MotorType.kBrushless);
    
    //2nd intake motor
    m_intakeMotor = new CANSparkMax(Constants.IntakeArm.CANID_INTAKEARM, MotorType.kBrushless);
    if(m_intakeMotor != null){
      m_intakePid = m_intakeMotor.getPIDController();
      m_intakeEncoder = m_intakeMotor.getEncoder();
      m_intakeEncoder.setPosition(0);

      kIntakeP = 1.0;
      kIntakeI = 0;
      kIntakeD = 0;
      kIntakeIz = 0;
      kIntakeFF = 0;
      kIntakeMaxOutput = 0;
      kIntakeMinOutput = 0;
  
      m_intakePid.setP(kIntakeP);
      m_intakePid.setI(kIntakeI);
      m_intakePid.setD(kIntakeD);
      m_intakePid.setIZone(kIntakeIz);
      m_intakePid.setFF(kIntakeFF);
      m_intakePid.setOutputRange(kIntakeMinOutput, kIntakeMaxOutput);
      m_intakePid.setReference(0.0, ControlType.kPosition);
      System.out.println("Intake Arm Sparks Initialized.");
    }

  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    if(m_intake != null){
      m_intake.set(m_intakeSpeed);
    }
    
    

    if(m_intakePid != null){
      if (isArmDown()){
       m_intakeMotor.set(0);
        System.out.println("Arm is down.");
      }
      else {
        m_intakePid.setReference(m_armPos, ControlType.kPosition);
      }
    //  m_intakePid.setReference(m_armPos, ControlType.kPosition);

    }
    
  }

  //1st motor
  public void intakeMotor(double speed){
    m_intakeSpeed = speed;
  }

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