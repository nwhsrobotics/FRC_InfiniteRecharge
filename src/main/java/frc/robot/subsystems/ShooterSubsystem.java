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

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.RobotContainer;

public class ShooterSubsystem extends SubsystemBase {
  /**
   * Creates a new ShooterSubsystem.
   */
  //TODO: Add Flywheel
  private final CANSparkMax m_flywheel;
  private final CANSparkMax m_flywheel2;
  private double m_flyWheelSpeed;
  private final CANPIDController m_flywheelPID;
  private final CANPIDController m_flywheel2PID;
  private CANEncoder m_flyWheelEncoder;
  private double kflywheelP, kflywheelI, kflywheelD, kflywheelIz, kflywheelFF, kflywheelmaxOutput, kflywheelminOutput;
  //TODO: Add Turret
  private final CANSparkMax m_turret;
  private CANPIDController m_turretPID;
  private CANEncoder m_turretEncoder;
  private double kP, kI, kD, kIz, kFF, kmaxOutput, kminOutput; //maxRPM
  private boolean turretExist;

  //TODO: Add Hood
  private final CANSparkMax m_hoodMotor;
  private double m_hoodPower=0.0;
  private CANPIDController m_hoodPid;
  private CANEncoder m_hoodEncoder;
  private double kHoodP, kHoodI, kHoodD, kHoodIz, kHoodFF, kHoodMaxOutput, kHoodMinOutput;
  private VisionSubsystem m_visionSubsystem;
  private double m_x;
  private boolean hoodExist;
  

  public ShooterSubsystem(VisionSubsystem visionSubsystem) {
    m_visionSubsystem = visionSubsystem;
    //TODO: Add Flywheel
      //1621 rpm 5676: NEO max (28%)
    m_flywheel = new CANSparkMax(Constants.Shooter.CANID_FLYWHEEL1, MotorType.kBrushless);
    m_flywheel.set(0.0);
    m_flywheel2 = new CANSparkMax(Constants.Shooter.CANID_FLYWHEEL2, MotorType.kBrushless);
    m_flywheel2.follow(m_flywheel, true);
    System.out.println(m_flywheel.getMotorTemperature());
    //m_flywheel2.setInverted(true);
    m_flyWheelEncoder = m_flywheel.getEncoder();
     m_flywheelPID = m_flywheel.getPIDController();
     m_flywheel2PID = m_flywheel.getPIDController();

     
     kflywheelP = 0;
     kflywheelI = 0;
     kflywheelIz = 0;
     kflywheelFF = 0.000178; //Based on 2800 at 50%
     kflywheelmaxOutput = 1;
     kflywheelminOutput = -1;

     m_flywheelPID.setP(kflywheelP);
     m_flywheelPID.setI(kflywheelI);
     m_flywheelPID.setD(kflywheelD);
     m_flywheelPID.setIZone(kflywheelIz);
     m_flywheelPID.setFF(kflywheelFF);
     m_flywheelPID.setOutputRange(kflywheelminOutput, kflywheelmaxOutput);
     m_flywheelPID.setReference(0.0, ControlType.kVelocity);
     System.out.println("Sparks Initialized. ");

     m_flywheel2PID.setP(kflywheelP);
     m_flywheel2PID.setI(kflywheelI);
     m_flywheel2PID.setD(kflywheelD);
     m_flywheel2PID.setIZone(kflywheelIz);
     m_flywheel2PID.setFF(kflywheelFF);
     m_flywheel2PID.setOutputRange(kflywheelminOutput, kflywheelmaxOutput);
     m_flywheel2PID.setReference(0.0, ControlType.kVelocity);
    

    //TODO: Add Turret
    m_turret = new CANSparkMax(Constants.Shooter.CANID_TURRET, MotorType.kBrushless);
    if (m_turret.getMotorTemperature() > 50 || m_turret.getMotorTemperature() < 20){
      turretExist = false;
    } else {
      turretExist = true;
    }

    if (turretExist){
      m_turretPID = m_turret.getPIDController();
      m_turretEncoder = m_turret.getEncoder();
      m_turretEncoder.setPosition(0); //Zero at initial position

      kP = 0.05;
      kI = 0;
      kD = 0;
      kIz = 0;
      kFF = 0;
      kmaxOutput = 1;
      kminOutput = -1;
      //maxRPM = 0;

      m_turretPID.setP(kP);
      m_turretPID.setI(kI);
      m_turretPID.setD(kD);
      m_turretPID.setIZone(kIz);
      m_turretPID.setFF(kFF);
      m_turretPID.setOutputRange(kminOutput, kmaxOutput);
      System.out.println("Sparks Initialized.");
      
      m_turret.setClosedLoopRampRate(Constants.Shooter.TURRET_RAMP_RATE);
    }

    //TODO: Add Hood
    m_hoodMotor = new CANSparkMax(Constants.Shooter.CANID_HOOD, MotorType.kBrushless);
    if (m_hoodMotor.getMotorTemperature() > 50 || m_hoodMotor.getMotorTemperature() < 20){
      hoodExist = false;
    } else {
      hoodExist = true;
    }

    if (hoodExist){
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
      System.out.println("The Turret Temperature is:  " + m_turret.getMotorTemperature());
      System.out.println("The Hood Temperature is:  " + m_hoodMotor.getMotorTemperature());
      
    }
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    //TODO: Add Flywheel
    //TODO: Add Turret
    if (turretExist){
      SmartDashboard.putNumber("Rotation Position", m_turretEncoder.getPosition());
      m_x = m_visionSubsystem.getTargetX();
    }
    //TODO: Add Hood
    if (hoodExist){
      m_hoodMotor.set(m_hoodPower);
    }
    SmartDashboard.putNumber("FlyWheel Power:  ", m_flyWheelSpeed);
    SmartDashboard.putNumber("FlyWheel Velocity:  ", m_flyWheelEncoder.getVelocity());
  }

  //TODO: Add Flywheel

  /*public void shootVelocity(double joystick) {
    m_flywheelPID.setReference(joystick, ControlType.kVoltage);
  }*/

  public void setShooterPower(double speed){
    //m_flyWheelSpeed = speed;
    m_flywheelPID.setReference(speed*5600, ControlType.kVelocity);
    m_flywheel2PID.setReference(-speed*5600, ControlType.kVelocity); //5600 is 100% power
    //m_flywheel.set(speed);
  }

  //TODO: Add Turret
  public void MoveTurret(double setPoint){
      if (turretExist){
      double currentPos = m_turretEncoder.getPosition();
      if(m_turretEncoder.getPosition() >= 128) {
        m_turretPID.setReference( 127 , ControlType.kPosition);
      } else if(m_turretEncoder.getPosition() <= -128){
        m_turretPID.setReference( -127 , ControlType.kPosition);
      } else {
        m_turretPID.setReference( (currentPos + setPoint) , ControlType.kPosition);
      }
     }
  }
  
  public void CenterTurret(){
    if (turretExist){
      m_turretPID.setReference(0, ControlType.kPosition);
    }
  }
  //TODO: Add Hood
  public void setHoodPower(double power){
    m_hoodPower = power;
  }

  public void trackTarget(){
    double currentPos = m_turretEncoder.getPosition();
    double m_X = 0.1*(m_x - 320);
    System.out.println(m_X);
    if (m_X > 15){
      m_X = 15;
    }else if (m_X < -15){
      m_X = -15;
    } 


    if (m_x != -1){
      if (turretExist){
        m_turretPID.setReference((currentPos + m_X), ControlType.kPosition);
      }
    }
    
  }
}
