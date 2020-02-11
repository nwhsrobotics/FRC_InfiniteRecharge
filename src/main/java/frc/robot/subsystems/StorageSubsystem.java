/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import java.util.EnumMap;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FollowerType;
import com.revrobotics.CANEncoder;
import com.revrobotics.CANPIDController;
import com.revrobotics.CANSparkMax;
import com.revrobotics.ControlType;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

public class StorageSubsystem extends SubsystemBase {

  enum BeltState {
    EMPTYBALLS,
    INTAKE_S1, INTAKE_S2A, INTAKE_S2B, INTAKE_S3, INTAKE_S4, IDLE;
  }
  private BeltState m_BeltState = BeltState.EMPTYBALLS;

  private static final EnumMap<BeltState, BeltState> nextStageMap = new EnumMap<>(BeltState.class);
  private static final EnumMap<BeltState, BeltState> prevStageMap = new EnumMap<>(BeltState.class);

  private final boolean m_closedloop = true; // True = Position Control, Flase = PT
  public boolean sensor1 = false;
  public boolean sensor2 = false;
  public boolean sensor3 = false;
  

 



 private double m_timeLeft_sec;

 private int m_maxAmps = 40;
  private double m_rampRate = 0;

  /**
   * Creates a new ExampleSubsystem.
   */
  private static final int deviceID_1 = 14;
  //private static final int deviceID_2 = 0; Assign value 
  private CANSparkMax m_motor;
  private CANSparkMax m_motor2;
  private CANPIDController m_pidController;
  private CANEncoder m_encoder;
  private double kP, kI, kD, kIz, kFF, kMaxOutput, kMinOutput, maxRPM, maxVel, minVel, maxAcc, allowedErr;
  private static final double GEAR_RATIO = 50;
  private static final double GEAR_CIRCUM = (30*5)/25.4; //30 Tooth by 5mm
  public static final double ROTATIONS_PER_INCH = -GEAR_RATIO/GEAR_CIRCUM; // 2 inch pulley TODO: check this   
  public Boolean isFinished = false;                                      


  public StorageSubsystem() {

    nextStageMap.put(BeltState.EMPTYBALLS, BeltState.EMPTYBALLS);

    nextStageMap.put(BeltState.INTAKE_S1, BeltState.INTAKE_S2A);
    nextStageMap.put(BeltState.INTAKE_S2A, BeltState.INTAKE_S2B);
    nextStageMap.put(BeltState.INTAKE_S2B, BeltState.INTAKE_S3);
    nextStageMap.put(BeltState.INTAKE_S3, BeltState.INTAKE_S4);
    nextStageMap.put(BeltState.INTAKE_S4, BeltState.IDLE);


    
    m_motor = new CANSparkMax(deviceID_1, MotorType.kBrushless);
    //m_motor2 = new CANSparkMax(deviceID_2, MotorType.kBrushless);
    System.out.println("Beginning to Initalize ");
   
    //m_encoder.setPosition(0);
    
    //m_motor2 = new CANSparkMax(deviceID_2, MotorType.kBrushless);
    //m_motor2.follow(m_motor);

    /**
     * The RestoreFactoryDefaults method can be used to reset the configuration parameters
     * in the SPARK MAX to their factory default state. If no argument is passed, these
     * parameters will not persist between power cycles
     */
    m_motor.restoreFactoryDefaults();
    

    // initialze PID controller and encoder objects
    m_pidController = m_motor.getPIDController();
    //m_pidController.setFeedbackDevice(m_encoder);
    m_encoder = m_motor.getEncoder();
    m_encoder.setPosition(0);
  

    // PID coefficients
    kP = 0.1;
    kI = 0;
    kD = 0; 
    kIz = 0; 
    kFF = 0; 
    kMaxOutput = 1; 
    kMinOutput = -1;
    maxRPM = 250;

    // Smart Motion Coefficients
    // maxVel = 100; // rpm
    // maxAcc = 50; 

    // set PID coefficients
    m_pidController.setP(kP);
    m_pidController.setI(kI);
    m_pidController.setD(kD);
    m_pidController.setIZone(kIz);
    m_pidController.setFF(kFF);
    m_pidController.setOutputRange(kMinOutput, kMaxOutput);


  
    System.out.println("Initalize for Sparks Finished");


  }

  public void initialize(){
  }

  @Override
  public void periodic(){
    System.out.println(m_encoder.getPosition());
    SmartDashboard.putNumber("storageEncoderPos", m_encoder.getPosition());
    setActuators();
  }

  public void beginMotion(double position){
    if (isFinished == false) {
      m_pidController.setReference(position, ControlType.kPosition);
    }
    double difference_position = m_encoder.getPosition() - position;
    difference_position = Math.abs(difference_position);
    if (difference_position < 2) {
      isFinished = true;
    } else {
      isFinished = false;
    }
    System.out.println("DIFFERENCE POSITION = " + difference_position);
    System.out.println("isFinished = " + isFinished);
  }




   /* public double getrunTimeleft(double m_Distance){

      m_beltRunTime = (m_Distance)/(m_velocity);
      
      return m_beltRunTime;
    } */
  
  
    public void RunBelts() {
  
      m_BeltState = BeltState.INTAKE_S1;
      setActuators();
    }
  
    public void nextStage() {
  
      m_BeltState = nextStageMap.get(m_BeltState);
      setActuators();
  
    }
  
    public void PrevStage() {
  
      m_BeltState = prevStageMap.get(m_BeltState);
      setActuators();
      
    }
  
  
  
  
  
  
    private void setActuators() {
      switch(m_BeltState) {
        //This case will run when there is nothing inside the storage
        case EMPTYBALLS:
          if (sensor1 == false && sensor2 == false){
            //do nothing
            System.out.println("Belt State is:    " + m_BeltState);
            System.out.println("Sensor 1 is:   " + sensor1);
          } else if (sensor1 == true){
            m_BeltState = BeltState.INTAKE_S1;
          }
          System.out.println("Stage EMPTY Activated");
          break;
  
        case INTAKE_S1:
          if (m_closedloop) {
            belt1MoveForward(15);
          
          }
          else {
           
          }
          System.out.println("Stage 1 Activated");
          break;
  
        case INTAKE_S2A:
          if (m_closedloop) {
            belt1MoveForward(30);

          }
          else {
         
           }
           System.out.println("Stage 2A Activated");
           break;
  
        case INTAKE_S2B:
           if (m_closedloop) {
            belt1MoveForward(45);
    
           }
           else {
 
           }
           System.out.println("Stage 2B Activated");
           break;
  
        case INTAKE_S3:
           if (m_closedloop) {
            belt1MoveForward(60);
      
           }
           else {

           }
           System.out.println("Stage 3 Activated");
           break;
  
        case INTAKE_S4:
           if (m_closedloop) {
            belt1MoveForward(100);
        
           }
           else {

           }
           System.out.println("Stage 4 Activated");
           break;
  
           case IDLE:
           break;
  
  
      }
  
    }

    /*public double getTimeLeft() {

      return m_timeLeft_sec;
  
    }  */
  
  
    public void belt1MoveForward(double position) {
      beginMotion(position);
    }
  
  
    public void Belt2_Forward_POS(double position) {
  
      
  
    }
  
    public void Belt1_Forward_PT(double speed) {
      if (m_motor != null) {
        m_motor.set(speed);
      }
    }
  
     /* public void //Belt2_Forward_PT(double speed) {
        if (m_motor2 != null) {
          m_motor2.set(speed);
        }
      }
      */
  
  
  
  }
  

