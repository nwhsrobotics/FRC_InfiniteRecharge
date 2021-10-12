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
  private static final double FLYWHEEL_RAMPRATE = 1;
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

  private double m_inputPower = 0;  //Did not have private before. Not sure if that was a problem
  private double m_inputChanged; //Did not have private before. Not sure if that was a problem
  private boolean m_manualFlywheel = true; //Starts in manual
  private int m_misses = 0;
  private double m_interpolatedRPM = 0;
  //Arrays for interpolation
  //TODO: FIND VALUES FOR ARRAY
  private static final double[] DIST_IN_ARRAY = {0, 109, 137, 163, 189, 236}; //THIS IS IN INCHES
  private static final double[] RPM_ARRAY = {4400, 4400, 4450, 4550, 4700, 5600}; //MAX RPM IS 5600
  private static final double CENTER_X = 162;
  private boolean m_onTarget = false;
  public ShooterSubsystem(VisionSubsystem visionSubsystem, StorageSubsystem storageSubsystem, XboxController joy, int axis) {
    //ADDING THE FAKE MOTOR
    
    
    m_visionSubsystem = visionSubsystem;
    m_storageSubsystem = storageSubsystem;
    //TODO: Add Flywheel
      //1621 rpm 5676: NEO max (28%)
      
      m_flywheel = new CANSparkMax(Constants.Shooter.CANID_FLYWHEEL1, MotorType.kBrushless);
      m_flywheel2 = new CANSparkMax(Constants.Shooter.CANID_FLYWHEEL2, MotorType.kBrushless);

    if (Constants.Shooter.CANID_FLYWHEEL1 == 0 || Constants.Shooter.CANID_FLYWHEEL2 == 0){
      m_flywheel = null;
      m_flywheel2 = null;
      flyWheelExist = false;
    }


    if (m_flywheel != null || m_flywheel2 != null){
      flyWheelExist = true;
      m_flyWheelEncoder = m_flywheel.getEncoder();
      m_flyWheelEncoder2 = m_flywheel2.getEncoder();
      m_flywheelPID = m_flywheel.getPIDController();
      m_flywheel2PID = m_flywheel2.getPIDController();
      
      kflywheelP = 0.0002;
      kflywheelI = 0;
      kflywheelIz = 0;
      kflywheelFF = 0.000178; //Based on 2800 at 50%      TODO: (VOLTAGE/MAXR RPM) X SETPOINT
      kflywheelmaxOutput = 1;
      kflywheelminOutput = -1;

      m_flywheelPID.setP(kflywheelP);
      m_flywheelPID.setI(kflywheelI);
      m_flywheelPID.setD(kflywheelD);
      m_flywheelPID.setIZone(kflywheelIz);
      //TODO: put setIMaxAccum, setIAccum
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

      //Ramp rate
      m_flywheel.setClosedLoopRampRate(FLYWHEEL_RAMPRATE);
      m_flywheel2.setClosedLoopRampRate(FLYWHEEL_RAMPRATE);
    
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
      m_turretEncoder.setPosition(130); //initial position

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
    //System.out.printf("Slider power: %f\n", m_inputChanged);

    if (m_flywheel != null || m_flywheel2 != null){

      //Get Controller input 
      m_inputPower = m_joy.getRawAxis(m_axis);
      m_inputChanged = -(m_inputPower - 1.00) / 2.00; //Since slider is from -1 to 1

      SmartDashboard.putNumber("Flywheel Motor 1 Velocity", m_flyWheelEncoder.getVelocity());
      SmartDashboard.putNumber("Flywheel Motor 2 Velocity", m_flyWheelEncoder2.getVelocity());
      if (m_storageSubsystem.getShootState()) {

        //If manual is true, shooter is driven by joystick
        if(m_manualFlywheel){
          setShooterPower(m_inputChanged);
        }
        else{
          //Automatic distance to RPM
          setShooterPower(interpolateRPM()); //Pass interpolation through for RPM
        }
        
      } else if (m_storageSubsystem.getArmed()){
        //If manual is true, shooter is driven by joystick
        if(m_manualFlywheel){
          setShooterPower(m_inputChanged);
        }
        else{
          //Automatic distance to RPM
          setShooterPower(interpolateRPM()); //Pass interpolation through for RPM
        }
        
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
      
      double rpm;

      //When in manual mode, account for control by using joystick
      if(m_manualFlywheel){
        rpm = 3300 + speed*2300; //top speed: 5600
      }
      else{
        //This is when interpolation is passed through, meaning that speed = rpm
        rpm = speed;
      }
      
      
      if(speed < 0.01){
        rpm = 0;
      }
      
      m_flywheelPID.setReference(-rpm, ControlType.kVelocity);
      m_flywheel2PID.setReference(rpm, ControlType.kVelocity); //5600 is 100% power
      //m_flywheel.set(speed);
      //m_flywheel2.set(speed);
    }
    
  }

  public void setManualFlywheel(boolean state){
    //If true, makes flywheel controlled by joystick
    m_manualFlywheel = state;
  }

  public double interpolateRPM(){
    double m_distance = m_visionSubsystem.getTargetdistance();
    int i;
    //When no target is visible, set RPM to zero.
    if(m_distance <= 0){
      if(m_misses >= 10){
        m_interpolatedRPM = 0;
      }
      else{
        m_misses += 1;
      }
    }
    //When target is seen...
    else{
      //If seen, reset miss counter
      m_misses = 0;
      //First find which range points distance is inbetween
      for(i = 0; i<DIST_IN_ARRAY.length; i++){
        if(DIST_IN_ARRAY[i] > m_distance){
          break;
        }
      }
      //Past furthest range in array
      if(i >= DIST_IN_ARRAY.length){
        m_interpolatedRPM = 0;
      }
      //Inbetweem DIST_ARRAY[i] and DIST_ARRAY[i-1], so interpolate with those points for linear interpolation
      else{
        //y = y1 + ((y2 - y1)/(x2 - x1)) * (x - x1)
        m_interpolatedRPM = RPM_ARRAY[i-1] + ((RPM_ARRAY[i]-RPM_ARRAY[i-1])/(DIST_IN_ARRAY[i]-DIST_IN_ARRAY[i-1])) * (m_distance - DIST_IN_ARRAY[i-1]);
      }
    }
    System.out.printf("Interpolated RPM: %f\n", m_interpolatedRPM);
    //Return Calculated RPM
    return m_interpolatedRPM;
  }

  public boolean isFlywheelReady(){
    double targetSpeed = interpolateRPM();
    double actualSpeed = m_flyWheelEncoder.getVelocity();

    if (Math.abs(targetSpeed - actualSpeed) < 0.01*targetSpeed){
      return true;
    } else{
      return false;
    }
  }

  //TODO: Add Turret
  public void MoveTurret(double setPoint){
    if (m_turret != null){
      double currentPos = m_turretEncoder.getPosition();
      //System.out.println(currentPos);
      SmartDashboard.putNumber("Turrent Current Pos", currentPos);
      if(m_turretEncoder.getPosition() >= 200) {
        m_turretPID.setReference( 200 , ControlType.kPosition);
      } else if(m_turretEncoder.getPosition() <= -200){
        m_turretPID.setReference( -200 , ControlType.kPosition);
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
      double m_X = 0.1*(m_x - CENTER_X);
      System.out.println(m_X);
      if (m_X > 15){
        m_X = 15;
      }else if (m_X < -15){
        m_X = -15;
      } 




      if (m_x != -1){
        m_turretPID.setReference((currentPos + m_X), ControlType.kPosition);
        
          m_onTarget =  (m_X == 0);
        
      } else{
        m_onTarget = false;
      }
    }
  }

  public boolean getOnTarget(){
    return m_onTarget;
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
