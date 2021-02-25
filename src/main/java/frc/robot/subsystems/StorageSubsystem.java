/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.XboxController.Axis;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.Constants.OIConstants;

import java.util.EnumMap;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FollowerType;
import com.revrobotics.CANEncoder;
import com.revrobotics.CANPIDController;
import com.revrobotics.CANSparkMax;
import com.revrobotics.ControlType;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

public class StorageSubsystem extends SubsystemBase {

    public enum IndexerState {
        EMPTYBALLS,
        INTAKE_S1,
        INTAKE_S2,
        INTAKE_S3,
        INTAKE_S4,
        FULL,
        ARMED_S1,
        ARMED_S2,
        ARMED_S3,
        ARMED_S4,
        ARMED_FULL;
        //RUNALL_OVERRIDE;
  
        
    }

    public enum BeltState {
      IDLE,
      INTAKE_1,
      INTAKE_2,
      REVERSE,
      ARMING,
      SHOOTING_S1,
      SHOOTING_S2,
      SHOOTING_ALL;
    }



    public IndexerState m_IndexerState = IndexerState.EMPTYBALLS;
    public BeltState m_beltState = BeltState.IDLE;

    // private static final EnumMap<BeltState, BeltState> nextStageMap = new EnumMap<>(BeltState.class);
    // private static final EnumMap<BeltState, BeltState> prevStageMap = new EnumMap<>(BeltState.class);

    public boolean armedSwitch;
    //private final boolean m_closedloop = true; // True = Position Control, Flase = PT

    public boolean sensor[] = new boolean[3];         //TODO: CHECK HOW MANY SENSORS
    private static final DigitalInput m_Sensor1 = new DigitalInput(0);
    private static final DigitalInput m_Sensor2 = new DigitalInput(1);
    private static final DigitalInput m_Sensor3 = new DigitalInput(2);
    







    //private double m_timeLeft_sec;

    //private int m_maxAmps = 40;
    //private double m_rampRate = 0;

    /**
     * Creates a new ExampleSubsystem.
     */
    //private static final int deviceID_2 = 0; Assign value 
    private CANSparkMax m_motor;
    private CANSparkMax m_motor2;
    private CANPIDController m_pidController;
    private CANPIDController m_pidController2;
    public CANEncoder m_encoder;    //TODO: MAKE A FUNCTION
    public CANEncoder m_encoder2;
    private double kP, kI, kD, kIz, kFF, kMaxOutput, kMinOutput, maxRPM, maxVel, minVel, maxAcc, allowedErr;
    public Boolean isFinished = false;
    private double m_Indexer_PTSpeed;
    private double positionSetter;
    private int ballPrediction;
    private boolean m_doIntake;
    private boolean m_doDisarm;
    private boolean m_doArm;
    private boolean m_doShoot;
    private boolean m_shootButtonPressed;
    private double m_belt_1Position_in;
    private double m_belt_2Position_in;
    private static final double GEAR_RATIO = 21;
    private static final double GEAR_CIRC = 1.75*Math.PI;
    private static final double REVS_PER_INCH = GEAR_RATIO/GEAR_CIRC;
    private static final double SECONDS_PER_TICK = 0.02;
    private static final double BELT_INTAKESPEED =10.0*SECONDS_PER_TICK;
    private static final double BELT_ARMINGSPEED = 10.0*SECONDS_PER_TICK;
    private static final double BELT_SHOOTINGSPEED = 20.0*SECONDS_PER_TICK;
    private boolean manual_switch = false;
    private final int m_axis;
    private final XboxController m_joy;
    private static final double M2_FACTOR = 3.0; //speeding up second belts by this value to prevent jamming 
    


    public StorageSubsystem(XboxController joy, int axis) {
      
      m_joy = joy;
      m_axis = axis;


      for(int n = 0; n < 3; n += 1) {
        sensor[n] = false;
      }

        //motor check for existence
        m_motor = new CANSparkMax(Constants.Storage.CANID_motor1, MotorType.kBrushless);


        //motor2 check for existence


        m_motor2 = new CANSparkMax(Constants.Storage.CANID_motor2, MotorType.kBrushless);
        //System.out.println(m_motor.getFirmwareVersion());
        //System.out.println("THE MOTOR IS:     " + motorHere);
        //m_motor2 = new CANSparkMax(deviceID_2, MotorType.kBrushless);
        System.out.println("Beginning to Initalize ");




        /**
         * The RestoreFactoryDefaults method can be used to reset the configuration parameters
         * in the SPARK MAX to their factory default state. If no argument is passed, these
         * parameters will not persist between power cycles
         */
          m_motor.restoreFactoryDefaults();


          // initialze PID controller and encoder objects
          m_pidController = m_motor.getPIDController();
          m_pidController2 = m_motor2.getPIDController();
          //m_pidController.setFeedbackDevice(m_encoder);
          m_encoder = m_motor.getEncoder();
          m_encoder.setPosition(0);
          m_encoder2 = m_motor2.getEncoder();
          m_encoder2.setPosition(0);

          m_belt_1Position_in = 0;
          m_belt_2Position_in = 0;


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

          m_pidController2.setP(kP);
          m_pidController2.setI(kI);
          m_pidController2.setD(kD);
          m_pidController2.setIZone(kIz);
          m_pidController2.setFF(kFF);
          m_pidController2.setOutputRange(kMinOutput, kMaxOutput);



          System.out.println("Initalize for Sparks Finished");
          sensor[0] = false;
          sensor[1] = false;

    }

    public void initialize() {

    }

    @Override
    public void periodic() {
        //System.out.println("Storage motor 1 temperature" + m_motor.getMotorTemperature());
      SmartDashboard.putBoolean("Sensor 1:  ", sensor[0]);
      SmartDashboard.putBoolean("Sensor 2:  ", sensor[1]);
      SmartDashboard.putBoolean("Sensor 3:  ", sensor[2]);
      SmartDashboard.putBoolean("armedSwitch:  ", armedSwitch);
      SmartDashboard.putNumber("The Ball Prediction is: ", ballPrediction);
      SmartDashboard.putBoolean("manualIndexer", manual_switch);
        getInputs();
        if (m_beltState == BeltState.IDLE) {
          runIndexerSm();
        }
        runBeltSm();
        updateActuators();
        //setActuators();
    }

    

  private void getInputs() {
    m_doIntake = false; //TODO:
    m_doDisarm = false; //TODO:
    m_doArm = false; //TODO:
    m_doShoot = false;
    //m_shootButtonPressed = false;
  }

  private void runBeltSm() {
    switch (m_beltState) {

      case IDLE:
        if (m_doIntake) {
          m_beltState = BeltState.INTAKE_1;
          m_doIntake = false;
        } else if (m_doDisarm) {
          m_beltState = BeltState.REVERSE;
          m_doDisarm = false;
        } else if (m_doArm) {
          m_beltState = BeltState.ARMING;
          m_doArm = false;
        } else if (m_doShoot) {
          m_beltState = BeltState.SHOOTING_S1;
          m_doShoot = false;
        }
        SmartDashboard.putString("Belt State is:   ", "Stage IDLE");
      break;

      case INTAKE_1:
        
        if (sensor[2] == true){ 
          //yikes ball is by the shooter!
          m_beltState = BeltState.IDLE; 
        } else if (sensor[1] == true) {
          m_beltState = BeltState.INTAKE_2;
        } 
        SmartDashboard.putString("Belt State is:   ", "Stage INTAKE_1");
      break;

      case INTAKE_2:
        if (sensor[2] == true){
          //yikes ball is by the shooter!
          m_beltState = BeltState.IDLE;
        } else if (sensor[1] == false) {
          m_beltState = BeltState.IDLE;
        } 
        SmartDashboard.putString("Belt State is:   ", "Stage INTAKE_2");
      break;

      case REVERSE:
        if (sensor[1] == true) {
          m_beltState = BeltState.IDLE;
        }
        SmartDashboard.putString("Belt State is:   ", "Stage REVERSE");
      break;

      case ARMING:
        if (sensor[2] == true) {
          m_beltState = BeltState.IDLE;
        }
        SmartDashboard.putString("Belt State is:   ", "Stage ARMING");
      break;

      case SHOOTING_S1:
        if (sensor[2] == true) {
          m_beltState = BeltState.SHOOTING_S2;
        } 
        SmartDashboard.putString("Belt State is:   ", "Stage SHOOTING_1");
      break;

      case SHOOTING_S2:
        if (sensor[2] == false) {
          m_beltState = BeltState.IDLE;
        }
        SmartDashboard.putString("Belt State is:   ", "Stage SHOOTING_2");
      break;

      case SHOOTING_ALL:
      SmartDashboard.putString("Belt State is:   ", "Stage SHOOTING_ALL");
      break;
    }
  }

  private void updateActuators() {
    
    switch (m_beltState) {
      
      case IDLE:
      break;

      case INTAKE_1:
      m_belt_1Position_in += BELT_INTAKESPEED;
      m_belt_2Position_in += M2_FACTOR*BELT_INTAKESPEED;
      break;

      case INTAKE_2:
      m_belt_1Position_in += BELT_INTAKESPEED;
      m_belt_2Position_in += M2_FACTOR*BELT_INTAKESPEED;
      break;

      case REVERSE:
      m_belt_1Position_in += -BELT_INTAKESPEED;
      m_belt_2Position_in += -M2_FACTOR*BELT_INTAKESPEED;
      break;

      case ARMING:
      m_belt_1Position_in += BELT_ARMINGSPEED;
      m_belt_2Position_in += M2_FACTOR*BELT_ARMINGSPEED;
      break;

      case SHOOTING_S1:
      m_belt_1Position_in += BELT_SHOOTINGSPEED;
      m_belt_2Position_in += M2_FACTOR*BELT_SHOOTINGSPEED;
      break;

      case SHOOTING_S2:
      m_belt_1Position_in += BELT_SHOOTINGSPEED;
      m_belt_2Position_in += M2_FACTOR*BELT_SHOOTINGSPEED;
      break;


      
    }
    if (manual_switch) {
      //set power manually
      double speed = -m_joy.getRawAxis(m_axis);
      if (speed > 0.05 || speed < -0.05) { 
      //m_motor.set(speed/M2_FACTOR);
      //m_motor2.set(speed);
      m_pidController.setReference(-speed/M2_FACTOR, ControlType.kDutyCycle);
      m_pidController2.setReference(speed, ControlType.kDutyCycle);
      } else {
        m_pidController.setReference(0, ControlType.kDutyCycle);
        m_pidController2.setReference(0, ControlType.kDutyCycle);
      }
    } else {
      m_pidController.setReference(-m_belt_1Position_in*REVS_PER_INCH, ControlType.kPosition);
      m_pidController2.setReference(m_belt_2Position_in*REVS_PER_INCH, ControlType.kPosition);  
    }
  
  }


  private void runIndexerSm() {
    switch (m_IndexerState) {

      case EMPTYBALLS:
      if (sensor[0] == true) {
          m_IndexerState = IndexerState.INTAKE_S1;
          m_doIntake = true;
          ballPrediction = 0;
        }
        SmartDashboard.putString("Indexer State is:   ", "Stage EMPTY");
      break;

      case INTAKE_S1:
        if (armedSwitch) {
          m_doArm = true;
          m_IndexerState = IndexerState.ARMED_S1;
        } 
        else if (sensor[0] == true) {
          m_IndexerState = IndexerState.INTAKE_S2;
          m_doIntake = true;
          ballPrediction = 1;
        }
        SmartDashboard.putString("Indexer State is:   ", "Stage INTAKE_S1");
      break;

      case INTAKE_S2:
      if (armedSwitch) {
        m_doArm = true;
        m_IndexerState = IndexerState.ARMED_S2;
      }
      else if (sensor[0] == true) {
        m_IndexerState = IndexerState.INTAKE_S3;
        m_doIntake = true;
        ballPrediction = 2;
      }
      SmartDashboard.putString("Indexer State is:   ", "Stage INTAKE_S2");
      break;

      case INTAKE_S3:
        if (armedSwitch) {
          m_doArm = true;
          m_IndexerState = IndexerState.ARMED_S3;
        }
        else if (sensor[0] == true) {
          m_IndexerState = IndexerState.INTAKE_S4;
          m_doIntake = true;
          ballPrediction = 3;
        }
        SmartDashboard.putString("Indexer State is:   ", "Stage INTAKE_S3");
        break;

      case INTAKE_S4:
        if (armedSwitch) {
          m_doArm = true;
          m_IndexerState = IndexerState.ARMED_S4;
        }
        else if (sensor[0] == true) {
          m_IndexerState = IndexerState.FULL;
          m_doIntake = true;
          ballPrediction = 4;
        }
        SmartDashboard.putString("Indexer State is:   ", "Stage INTAKE_S4");
      break;

      case FULL:
        if (armedSwitch) {
          m_doArm = true;
          m_IndexerState = IndexerState.ARMED_FULL;
        }
        SmartDashboard.putString("Indexer State is:   ", "Stage FULL");
        ballPrediction = 5;
        break;

      case ARMED_S1:
        if (armedSwitch == false) {
          m_IndexerState = IndexerState.INTAKE_S1;
          m_doDisarm = true;
        }
        else if (m_shootButtonPressed) {
          m_doShoot = true;
          m_IndexerState = IndexerState.EMPTYBALLS;
          m_shootButtonPressed = false;
          ballPrediction = 0;
        }
        SmartDashboard.putString("Indexer State is:   ", "Stage ARMED_S1");
      break;

      case ARMED_S2:
        if (armedSwitch == false) {
          m_IndexerState = IndexerState.INTAKE_S2;
          m_doDisarm = true;
        }
        else if (m_shootButtonPressed) {
          m_doShoot = true;
          m_IndexerState = IndexerState.ARMED_S1;
          m_shootButtonPressed = false;
          ballPrediction = 1;
        }
        SmartDashboard.putString("Indexer State is:   ", "Stage ARMED_S2");
      break;

      case ARMED_S3:
        if (armedSwitch == false) {
          m_IndexerState = IndexerState.INTAKE_S3;
          m_doDisarm = true;
        }
        else if (m_shootButtonPressed) {
          m_doShoot = true;
          m_IndexerState = IndexerState.ARMED_S2;
          m_shootButtonPressed = false;
          ballPrediction = 2;
        }
        SmartDashboard.putString("Indexer State is:   ", "Stage ARMED_S3");
      break;

      case ARMED_S4:
        if (armedSwitch == false) {
          m_IndexerState = IndexerState.INTAKE_S4;
          m_doDisarm = true;
        }
        else if (m_shootButtonPressed) {
          m_doShoot = true;
          m_IndexerState = IndexerState.ARMED_S3;
          m_shootButtonPressed = false;
          ballPrediction = 3;
        }
        SmartDashboard.putString("Indexer State is:   ", "Stage ARMED_S4");
      break;

      case ARMED_FULL:
        if (armedSwitch == false) {
          m_IndexerState = IndexerState.FULL;
          m_doDisarm = true;
          m_shootButtonPressed = false;
        }
        else if (m_shootButtonPressed) {
          m_doShoot = true;
          m_IndexerState = IndexerState.ARMED_S4;
          m_shootButtonPressed = false;
          ballPrediction = 4;
        }
        SmartDashboard.putString("Indexer State is:   ", "Stage ARMED_FULL");
      break;
    }
  }


    public void setManual(boolean state) {
      manual_switch = state;
      m_IndexerState = IndexerState.EMPTYBALLS;
      m_belt_1Position_in = 0;
      m_belt_2Position_in = 0;
      m_encoder.setPosition(0);
      m_encoder2.setPosition(0);
      m_pidController.setReference(0, ControlType.kPosition);
      m_pidController2.setReference(0, ControlType.kPosition);
      //m_motor.set(speed);
      //m_motor2.set(-3*speed);
    }

   /* public void autoStorage() {
      setActuators();                   USE IN CASE AUTO REQUIRES ANOTHER FUNCTION
    }
    */

    public void setSensor(int sensorID, boolean sensorState) {
      sensor[sensorID] = sensorState;
      System.out.printf("set sensor %d to %b", sensorID, sensor[sensorID]);
    }

    public boolean getSensor(int sensorID) {
      return sensor[sensorID];
    }

    public void setArmed(boolean armedState) {
      armedSwitch = armedState;
      System.out.println("Armed Switch is set to:  " + armedSwitch);
    }

    public boolean getArmed() {
      return armedSwitch;
    }

    public boolean getManual(){
      return manual_switch;
    }

    /*public void setManual(boolean manualState){
      manual_switch = manualState;
    } */

    public boolean getShootState(){
      return m_shootButtonPressed;
    }

    public void setShoot(boolean shoot){
      m_shootButtonPressed = shoot;
      System.out.println("m_shootButtonPressed:  " + m_shootButtonPressed); 
    }


    /*public void constantMotion(double increment) {
        positionSetter = increment + m_encoder.getPosition();
        setPosition(positionSetter);
        //System.out.println("running constantMotion");
    } 
    */



    /* public double getrunTimeleft(double m_Distance){

       m_beltRunTime = (m_Distance)/(m_velocity);
       
       return m_beltRunTime;
     } */


     /*
    public void RunBelts() {

        m_IndexerState = IndexerState.INTAKE_S1;
        setActuators();
    }
    */

    /*public void nextStage() {
  
      m_BeltState = nextStageMap.get(m_BeltState);
      setActuators();
  
    }
  
    public void PrevStage() {
  
      m_BeltState = prevStageMap.get(m_BeltState);
      setActuators();
      
    } */

























































    /*
    private void setActuators() {
        switch (m_IndexerState) {
            //This case will run when there is nothing inside the storage
            case EMPTYBALLS:
                if (sensor[0] == false && sensor[1] == false) {
                    //do nothing
                } else if (sensor[0] == true) {
                    m_IndexerState = IndexerState.INTAKE_S1;
                }
                SmartDashboard.putString("Belt State is:   ", "EmptyBalls");
                break;
                */
                

                //INTAKE STATES


            /*
            case INTAKE_S1:
                if (sensor[0] == true) {
                  if (sensor[1] == false){
                    constantMotion(3);
                  } 
                } else if (sensor[0] == false) {
                  if(sensor[1] == true){
                    //m_IndexerState = IndexerState.INTAKE_S1B;
                  }
                }
                SmartDashboard.putString("Belt State is:   ", "Stage 1A");
                ballPrediction = 1;
                break;
                */

            /*case INTAKE_S1B:
                if (armedSwitch == false) {
                    if (sensor[0] == false) {
                        //do nothing
                    } else if (sensor[0] == true) {
                        m_IndexerState = IndexerState.INTAKE_S2;
                        ballPrediction = 2;
                    }
                } else if (armedSwitch == true) {
                    m_IndexerState = IndexerState.ARMED_S1;
                }
                SmartDashboard.putString("Belt State is:   ", "Stage 1B");
                break;
                */
            /*
            case INTAKE_S2:
              if (sensor[0] == true) {
                if (sensor[1] == false){
                  constantMotion(3);
                }
              } else if (sensor[0] == false) {
                if(sensor[1] == true){
                  //m_IndexerState = IndexerState.INTAKE_S2B;
                }
              }
                SmartDashboard.putString("Belt State is:   ", "Stage 2A");
                break;
                */

            /*case INTAKE_S2B:
                if (armedSwitch == false) {
                    if (sensor[0] == false) {
                        //do nothing
                    } else if (sensor[0] == true) {
                        m_IndexerState = IndexerState.INTAKE_S3;
                        ballPrediction = 3;
                    }
                } else if (armedSwitch == true) {
                    m_IndexerState = IndexerState.ARMED_S2;
                }
                SmartDashboard.putString("Belt State is:   ", "Stage 2B");
                break;
                */
            /*
            case INTAKE_S3:
                if (sensor[0] == true) {
                  if (sensor[1] == false){
                    constantMotion(3);
                  } 
                } else if (sensor[0] == false) {
                  if(sensor[1] == true){
                    //m_IndexerState = IndexerState.INTAKE_S3B;
                  }
                }
                SmartDashboard.putString("Belt State is:   ", "Stage 3A");
                break;
                */

           /* case INTAKE_S3B:
                if (armedSwitch == false) {
                    if (sensor[0] == false) {
                        //do nothing
                    } else if (sensor[0] == true) {
                        m_IndexerState = IndexerState.INTAKE_S4;
                        ballPrediction = 4;
                    }
                } else if (armedSwitch == true) {
                    m_IndexerState = IndexerState.ARMED_S3;
                }
                SmartDashboard.putString("Belt State is:   ", "Stage 3B");
                break;
                */
            /*
            case INTAKE_S4:
            if (sensor[0] == true) {
              if (sensor[1] == false){
                constantMotion(3);
              } 
            } else if (sensor[0] == false && sensor[1] == true) {
                    //m_IndexerState = IndexerState.INTAKE_S4B;
                    ballPrediction = 4;
                }
                SmartDashboard.putString("Belt State is:   ", "Stage 4A");
                break;
                */

            /*case INTAKE_S4B:
                if (armedSwitch == false) {
                    if (sensor[0] == false) {
                        //do nothing
                    } else if (sensor[0] == true) {
                        m_IndexerState = IndexerState.FULL;
                        ballPrediction = 5;
                    }
                } else if (armedSwitch == true) {
                    m_IndexerState = IndexerState.ARMED_S4;
                }
                SmartDashboard.putString("Belt State is:   ", "Stage 4B");
                break;
                */
                /*case INTAKE_S5A:
                if (sensor[0] == true) {
                  if (sensor[1] == false){
                    constantMotion(5);
                  } else {
                    constantMotion(2);
                  }
                } else if (sensor[0] == false && sensor[1] == true) {
                        m_BeltState = BeltState.INTAKE_S5B;
                        ballPrediction = 5;
                    }
                    SmartDashboard.putString("Belt State is:   ", "Stage 5A");
                    break;
    
                case INTAKE_S5B:
                    if (armedSwitch == false) {
                        if (sensor[0] == false) {
                            //do nothing
                        } else if (sensor[0] == false && sensor[1] == true) {
                            m_BeltState = BeltState.FULL;
                            ballPrediction = 5;
                        }
                    } else if (armedSwitch == true) {
                        m_BeltState = BeltState.ARMED_S5;
                    }
                    SmartDashboard.putString("Belt State is:   ", "Stage 5B");
                    break;
                    */
            /*
            case FULL:
            if (armedSwitch == false) {
                //do nothing
            } else if (armedSwitch == true) {
              m_IndexerState = IndexerState.ARMED_FULL;
            }
                ballPrediction = 5;
                SmartDashboard.putString("Belt State is:   ", "FULL");
                break;
                */




                //ARMED STATES



                /*
                case ARMED_S1:
                if (armedSwitch){
                  if (sensor[2] == true) {
                    //MOVE BELTS TO POSITION TO FIRE
                    } else if (sensor[2] == false) {
                      constantMotion(5);
                    }
                    ballPrediction = 1;
                    SmartDashboard.putString("Belt State is:   ", "ARMED 1");
                } else {
                  //m_IndexerState = IndexerState.INTAKE_S1B;
                }
               
                break;
             
                case ARMED_S2:
                if (armedSwitch){
                  if (sensor[2] == true) {
                    //MOVE BELTS TO POSITION TO FIRE
                    } else if (sensor[2] == false) {
                      constantMotion(5);
                    }
                    ballPrediction = 2;
                  SmartDashboard.putString("Belt State is:   ", "ARMED 2");
                } else {
                  //m_IndexerState = IndexerState.INTAKE_S2B;
                }
                
                break;

                case ARMED_S3:
                if (armedSwitch){
                  if (sensor[2] == true) {
                    //MOVE BELTS TO POSITION TO FIRE
                    } else if (sensor[2] == false) {
                      constantMotion(5);
                    }
                  ballPrediction = 3;
                  SmartDashboard.putString("Belt State is:   ", "ARMED 3");
                } else {
                  //m_IndexerState = IndexerState.INTAKE_S3B;
                }
                
                break;
          
                case ARMED_S4:
                if (armedSwitch){
                  if (sensor[2] == true) {
                    //MOVE BELTS TO POSITION TO FIRE
                    } else if (sensor[2] == false) {
                      constantMotion(5);
                    }
                  ballPrediction = 4;
                  SmartDashboard.putString("Belt State is:   ", "ARMED 4");
                  } else {
                    //m_IndexerState = IndexerState.INTAKE_S4B;
                  }
                break;

                case ARMED_FULL:
                if (sensor[2] == true) {
                  //MOVE BELTS TO POSITION TO FIRE
                } else if (sensor[2] == false) {
                  constantMotion(5);
                }
                ballPrediction = 5;
                SmartDashboard.putString("Belt State is:   ", "ARMED FULL");
                break;
                */


                /*case RUNALL_OVERRIDE:
                constantMotion(5);
                break;
                */


                //FIRED_ALL STATE

                //case FIRED_ALL:
               

        
    }

