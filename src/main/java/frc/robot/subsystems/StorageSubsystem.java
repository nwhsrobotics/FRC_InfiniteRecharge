/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.DigitalInput;
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

    public enum BeltState {
        EMPTYBALLS,
        INTAKE_S1A,
        INTAKE_S1B,
        INTAKE_S2A,
        INTAKE_S2B,
        INTAKE_S3A,
        INTAKE_S3B,
        INTAKE_S4A,
        INTAKE_S4B,
        ARMED_S1,
        ARMED_S2,
        ARMED_S3,
        ARMED_S4,
        FULL,
        ARMED_FULL,
        RUNALL_OVERRIDE;
        //FIRED_ALL; CHECK IF WE NEED THIS
        
    }

    public BeltState m_BeltState = BeltState.EMPTYBALLS;

    // private static final EnumMap<BeltState, BeltState> nextStageMap = new EnumMap<>(BeltState.class);
    // private static final EnumMap<BeltState, BeltState> prevStageMap = new EnumMap<>(BeltState.class);

    public boolean armedSwitch;
    //private final boolean m_closedloop = true; // True = Position Control, Flase = PT

    public boolean sensor[] = new boolean[3];         //TODO: CHECK HOW MANY SENSORS
    private static final DigitalInput m_Sensor1 = new DigitalInput(0);
    private static final DigitalInput m_Sensor2 = new DigitalInput(1);
    //private static final DigitalInput m_Sensor3 = new DigitalInput(2);
    







    //private double m_timeLeft_sec;

    //private int m_maxAmps = 40;
    //private double m_rampRate = 0;

    /**
     * Creates a new ExampleSubsystem.
     */
    private static final int deviceID_1 = 14;
    //private static final int deviceID_2 = 0; Assign value 
    private CANSparkMax m_motor;
    //private CANSparkMax m_motor2;
    private CANPIDController m_pidController;
    private CANEncoder m_encoder;
    private double kP, kI, kD, kIz, kFF, kMaxOutput, kMinOutput, maxRPM, maxVel, minVel, maxAcc, allowedErr;
    private static final double GEAR_RATIO = 50;
    private static final double GEAR_CIRCUM = (30 * 5) / 25.4; //30 Tooth by 5mm
    public static final double ROTATIONS_PER_INCH = -GEAR_RATIO / GEAR_CIRCUM; // 2 inch pulley TODO: check this   
    public Boolean isFinished = false;
    private double positionSetter;
    private double ballPrediction;
    private int ballCounter;
    


    public StorageSubsystem() {
    



      for(int n = 0; n < 3; n += 1) {
        sensor[n] = false;
      }

      

     

     

        
        m_motor = new CANSparkMax(deviceID_1, MotorType.kBrushless);
        //System.out.println(m_motor.getFirmwareVersion());
        //System.out.println("THE MOTOR IS:     " + motorHere);
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

    public void initialize() {

    }

    @Override
    public void periodic() {
        SmartDashboard.putNumber("storageEncoderPos", m_encoder.getPosition());
        SmartDashboard.putBoolean("Sensor 1:  ", sensor[0]);
        SmartDashboard.putBoolean("Sensor 2:  ", sensor[1]);
        SmartDashboard.putBoolean("Sensor 3:  ", sensor[2]);
        SmartDashboard.putBoolean("armedSwitch:  ", armedSwitch);
        SmartDashboard.putNumber("Ball Prediction: ", ballPrediction);
        SmartDashboard.putBoolean("Ball Sensor:  ", (m_Sensor1.get()));
        SmartDashboard.putBoolean("Ball 2 Sensor:  ", (m_Sensor2.get()));
        sensor[0] = m_Sensor1.get();
        sensor[1] = m_Sensor2.get();
        //TODO: add 3rd sensor
        setActuators();
    }


    public void setPosition(double position) {
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
    }

    public boolean getArmed() {
      return armedSwitch;
    }



    public void constantMotion(double increment) {
        positionSetter = increment + m_encoder.getPosition();
        setPosition(positionSetter);
        //System.out.println("running constantMotion");
    }



    /* public double getrunTimeleft(double m_Distance){

       m_beltRunTime = (m_Distance)/(m_velocity);
       
       return m_beltRunTime;
     } */


    public void RunBelts() {

        m_BeltState = BeltState.INTAKE_S1A;
        setActuators();
    }

    /*public void nextStage() {
  
      m_BeltState = nextStageMap.get(m_BeltState);
      setActuators();
  
    }
  
    public void PrevStage() {
  
      m_BeltState = prevStageMap.get(m_BeltState);
      setActuators();
      
    } */






    private void setActuators() {
        switch (m_BeltState) {
            //This case will run when there is nothing inside the storage
            case EMPTYBALLS:
                if (sensor[0] == false && sensor[1] == false) {
                    //do nothing
                } else if (sensor[0] == true) {
                    m_BeltState = BeltState.INTAKE_S1A;
                }
                SmartDashboard.putString("Belt State is:   ", "EmptyBalls");
                break;

                

                //INTAKE STATES



            case INTAKE_S1A:
                if (sensor[0] == true) {
                  if (sensor[1] == false){
                    constantMotion(3);
                  } 
                } else if (sensor[0] == false) {
                  if(sensor[1] == true){
                    m_BeltState = BeltState.INTAKE_S1B;
                  }
                }
                SmartDashboard.putString("Belt State is:   ", "Stage 1A");
                ballPrediction = 1;
                break;

            case INTAKE_S1B:
                if (armedSwitch == false) {
                    if (sensor[0] == false) {
                        //do nothing
                    } else if (sensor[0] == true) {
                        m_BeltState = BeltState.INTAKE_S2A;
                        ballPrediction = 2;
                    }
                } else if (armedSwitch == true) {
                    m_BeltState = BeltState.ARMED_S1;
                }
                SmartDashboard.putString("Belt State is:   ", "Stage 1B");
                break;

            case INTAKE_S2A:
              if (sensor[0] == true) {
                if (sensor[1] == false){
                  constantMotion(3);
                }
              } else if (sensor[0] == false) {
                if(sensor[1] == true){
                  m_BeltState = BeltState.INTAKE_S2B;
                }
              }
                SmartDashboard.putString("Belt State is:   ", "Stage 2A");
                break;

            case INTAKE_S2B:
                if (armedSwitch == false) {
                    if (sensor[0] == false) {
                        //do nothing
                    } else if (sensor[0] == true) {
                        m_BeltState = BeltState.INTAKE_S3A;
                        ballPrediction = 3;
                    }
                } else if (armedSwitch == true) {
                    m_BeltState = BeltState.ARMED_S2;
                }
                SmartDashboard.putString("Belt State is:   ", "Stage 2B");
                break;


            case INTAKE_S3A:
                if (sensor[0] == true) {
                  if (sensor[1] == false){
                    constantMotion(3);
                  } 
                } else if (sensor[0] == false) {
                  if(sensor[1] == true){
                    m_BeltState = BeltState.INTAKE_S3B;
                  }
                }
                SmartDashboard.putString("Belt State is:   ", "Stage 3A");
                break;

            case INTAKE_S3B:
                if (armedSwitch == false) {
                    if (sensor[0] == false) {
                        //do nothing
                    } else if (sensor[0] == true) {
                        m_BeltState = BeltState.INTAKE_S4A;
                        ballPrediction = 4;
                    }
                } else if (armedSwitch == true) {
                    m_BeltState = BeltState.ARMED_S3;
                }
                SmartDashboard.putString("Belt State is:   ", "Stage 3B");
                break;

            case INTAKE_S4A:
            if (sensor[0] == true) {
              if (sensor[1] == false){
                constantMotion(3);
              } 
            } else if (sensor[0] == false && sensor[1] == true) {
                    m_BeltState = BeltState.INTAKE_S4B;
                    ballPrediction = 4;
                }
                SmartDashboard.putString("Belt State is:   ", "Stage 4A");
                break;

            case INTAKE_S4B:
                if (armedSwitch == false) {
                    if (sensor[0] == false) {
                        //do nothing
                    } else if (sensor[0] == true) {
                        m_BeltState = BeltState.FULL;
                        ballPrediction = 5;
                    }
                } else if (armedSwitch == true) {
                    m_BeltState = BeltState.ARMED_S4;
                }
                SmartDashboard.putString("Belt State is:   ", "Stage 4B");
                break;

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

            case FULL:
            if (armedSwitch == false) {
                //do nothing
            } else if (armedSwitch == true) {
              m_BeltState = BeltState.ARMED_FULL;
            }
                ballPrediction = 5;
                SmartDashboard.putString("Belt State is:   ", "FULL");
                break;




                //ARMED STATES




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
                  m_BeltState = BeltState.INTAKE_S1B;
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
                  m_BeltState = BeltState.INTAKE_S2B;
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
                  m_BeltState = BeltState.INTAKE_S3B;
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
                    m_BeltState = BeltState.INTAKE_S4B;
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


                case RUNALL_OVERRIDE:
                constantMotion(5);
                break;


                //FIRED_ALL STATE

                //case FIRED_ALL:
               

        }
    }

}