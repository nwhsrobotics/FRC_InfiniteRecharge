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
import com.revrobotics.CANSparkMaxLowLevel;
import com.revrobotics.ControlType;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.RobotContainer;

public class ShooterSubsystem extends SubsystemBase {
  /**
   * Creates a new ShooterSubsystem.
   */
  //TODO: Add Flywheel
  private CANSparkMax m_flywheel = null;
  private CANSparkMax m_flywheel2 = null;
  private double m_flyWheelSpeed;
  private CANPIDController m_flywheelPID = null;
  private CANPIDController m_flywheel2PID = null;
  private CANEncoder m_flyWheelEncoder;
  private CANEncoder m_flyWheelEncoder2;
  private double kflywheelP, kflywheelI, kflywheelD, kflywheelIz, kflywheelFF, kflywheelmaxOutput, kflywheelminOutput;
  public boolean flyWheelExist = true;
  //TODO: Add Turret
  private CANSparkMax m_turret = null;
  private CANPIDController m_turretPID = null;
  private CANEncoder m_turretEncoder;
  private double kP, kI, kD, kIz, kFF, kmaxOutput, kminOutput; //maxRPM
  public boolean turretExist = true;

  //TODO: Add Hood
  private CANSparkMax m_hoodMotor = null;
  private double m_hoodPower=0.0;
  private CANPIDController m_hoodPid = null;
  private CANEncoder m_hoodEncoder;
  private double kHoodP, kHoodI, kHoodD, kHoodIz, kHoodFF, kHoodMaxOutput, kHoodMinOutput;
  private VisionSubsystem m_visionSubsystem;
  private StorageSubsystem m_storageSubsystem;
  private double m_x;
  public boolean hoodExist = true;

  private CANSparkMaxLowLevel.MotorType flyWheelMT;
  private CANSparkMaxLowLevel.MotorType flyWheelMT2;
  private CANSparkMaxLowLevel.MotorType turretMT;
  private CANSparkMaxLowLevel.MotorType hoodMT;

  private XboxController m_joy;
  private int m_axis; 

  double m_inputPower = 0;
  double m_inputChanged;

  public ShooterSubsystem(VisionSubsystem visionSubsystem, StorageSubsystem storageSubsystem, XboxController joy, int axis) {
    //ADDING THE FAKE MOTOR
    
    
    m_visionSubsystem = visionSubsystem;
    m_storageSubsystem = storageSubsystem;
    //TODO: Add Flywheel
      //1621 rpm 5676: NEO max (28%)
      
    if (Constants.Shooter.CANID_FLYWHEEL1 == 0 || Constants.Shooter.CANID_FLYWHEEL2 == 0){
      m_flywheel = null;
      m_flywheel2 = null;
      flyWheelExist = false;
    }


    if (m_flywheel != null || m_flywheel2 != null){
      m_flywheel = new CANSparkMax(Constants.Shooter.CANID_FLYWHEEL1, MotorType.kBrushless);
      m_flywheel2 = new CANSparkMax(Constants.Shooter.CANID_FLYWHEEL2, MotorType.kBrushless);
      flyWheelExist = true;
      m_flyWheelEncoder = m_flywheel.getEncoder();
      m_flyWheelEncoder2 = m_flywheel2.getEncoder();
      m_flywheelPID = m_flywheel.getPIDController();
      m_flywheel2PID = m_flywheel2.getPIDController();
      
      kflywheelP = 0;
      kflywheelI = 0;
      kflywheelIz = 0;
      kflywheelFF = 0.000178; //Based on 2800 at 50%      TODO: (VOLTAGE/MAXR RPM) X SETPOINT
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

      //Controller code for manual flywheel
      m_joy = joy;
      m_axis = axis;
    
    }
    //TODO: Add Turret
    m_turret = new CANSparkMax(Constants.Shooter.CANID_TURRET, MotorType.kBrushless);
  
    //turretMT = m_turret.getMotorType();
    if (Constants.Shooter.CANID_TURRET == 0){
      m_turret = null;
      turretExist = false;
    }

    if (m_turret != null){
      turretExist = true;
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

    /*
    //TODO: Add Hood
    m_hoodMotor = new CANSparkMax(Constants.Shooter.CANID_HOOD, MotorType.kBrushless);
    //hoodMT = m_hoodMotor.getMotorType();
    
    if (Constants.Shooter.CANID_HOOD == 0){
      m_hoodMotor = null;
      hoodExist = false;
    }
    if (m_hoodMotor != null){
      hoodExist = true;
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
    */
  }


  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    //TODO: Add Flywheel
    //TODO: Add Turret
    if(m_flywheel == null){
      return; //TODO: RESTORE THIS!!!
    }
    



    //Get Controller input
    m_inputPower = m_joy.getRawAxis(m_axis);
    m_inputChanged = (m_inputPower - 1.00) / 2.00; //Since slider is from -1 to 1
    
    //System.out.printf("Slider power: %f\n", m_inputChanged);

    if (m_flywheel != null || m_flywheel2 != null){
      SmartDashboard.putNumber("Flywheel Motor 1 Velocity", m_flyWheelEncoder.getVelocity());
      SmartDashboard.putNumber("Flywheel Motor 2 Velocity", m_flyWheelEncoder2.getVelocity());
      if (m_storageSubsystem.getShootState()) {
        setShooterPower(m_inputChanged);
      } else if (m_storageSubsystem.getArmed()){
        setShooterPower(m_inputChanged);
      } else {
        setShooterPower(0);
      }
    }
    if (m_turret != null){
      SmartDashboard.putNumber("Rotation Position", m_turretEncoder.getPosition());
      m_x = m_visionSubsystem.getTargetX();
    }
    /*
    //TODO: Add Hood
    if (m_hoodMotor != null){
      m_hoodMotor.set(m_hoodPower);
    }
    */
  }


  public void setShooterPower(double speed){
    if (m_flywheel != null && m_flywheel2 != null){
      //m_flyWheelSpeed = speed;
      m_flywheelPID.setReference(speed*5600, ControlType.kVelocity);
      m_flywheel2PID.setReference(-speed*5600, ControlType.kVelocity); //5600 is 100% power
      //m_flywheel.set(speed);
      //m_flywheel2.set(speed);
    }
    
  }

  //TODO: Add Turret
  public void MoveTurret(double setPoint){
    if (m_turret != null){
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
    if (m_turret != null){
      m_turretPID.setReference(0, ControlType.kPosition);
    }
  }
  //TODO: Add Hood
  /*
  public void setHoodPower(double power){
    if (m_hoodMotor != null){
      m_hoodPower = power;
    }
  }
  */  

  public void trackTarget(){
    if (m_turret != null){
      double currentPos = m_turretEncoder.getPosition();
      double m_X = 0.1*(m_x - 320);
      System.out.println(m_X);
      if (m_X > 15){
        m_X = 15;
      }else if (m_X < -15){
        m_X = -15;
      } 


      if (m_x != -1){
        m_turretPID.setReference((currentPos + m_X), ControlType.kPosition);
      }
    }
  }
  public boolean getTurretExist(){
    return turretExist;
  }
  
  public boolean getFlyWheelExist(){
    return flyWheelExist;
  }

  /*
  public boolean getHoodExist(){
    return hoodExist;
  }
  */
}
