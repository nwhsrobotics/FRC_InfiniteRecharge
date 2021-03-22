/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.util.VelDiffDrive;

public class DriveSubsystem extends SubsystemBase {

  private static final double POWER_FACTOR = 0.6;
  private static final double TURN_FACTOR = 0.55;
  // To do: Declare motor controllers (CANSparkMax)
  private CANSparkMax m_left1 = null;
  private CANSparkMax m_left2 = null;
  private CANSparkMax m_right1 = null;
  private CANSparkMax m_right2 = null;
  private DifferentialDrive m_drive;

  public boolean driveExist;
  //todo: add PID control objects
  //todo: add trajectory objects
  //private CANPIDController m_pidController;
  //private double kP, kI, kD, kIz, kFF, kMaxOutput, kMinOutput;
  private boolean reverseDrive = false;
  private double m_turn = 0.0;
  private double m_power = 0.0;
  private VelDiffDrive m_vDrive;



  
  /**
   * Creates a new DriveSubsystem.
   */
  public DriveSubsystem() {
    //Add motors to groups
    //Create ArcadeDrive
    m_left1 = new CANSparkMax(Constants.Drive.CANID_LEFT1, MotorType.kBrushless);
    m_left2 = new CANSparkMax(Constants.Drive.CANID_LEFT2, MotorType.kBrushless);
    m_right1 = new CANSparkMax(Constants.Drive.CANID_RIGHT1, MotorType.kBrushless);
    m_right2 = new CANSparkMax(Constants.Drive.CANID_RIGHT2, MotorType.kBrushless);
    
    // todo: get PID controllers
    // todo: add PID parameters
    if (Constants.Drive.CANID_LEFT1 == 0 || Constants.Drive.CANID_LEFT2 == 0 || Constants.Drive.CANID_RIGHT1 == 0 || Constants.Drive.CANID_RIGHT2 == 0){
      m_left1 = null;
      driveExist = false;
    }
    if (m_left1 != null || m_left2 != null || m_right1 != null || m_right2 != null){
      //Set Idle mode for breaking
      m_left1.setIdleMode(IdleMode.kCoast);
      m_left2.setIdleMode(IdleMode.kCoast);
      m_right1.setIdleMode(IdleMode.kCoast);
      m_right2.setIdleMode(IdleMode.kCoast);
      //Declare motor groups 
      SpeedControllerGroup left = new SpeedControllerGroup(m_left1, m_left2);
      SpeedControllerGroup right = new SpeedControllerGroup(m_right1, m_right2);
      //Declare ArcadeDrive 
      m_drive = new DifferentialDrive(left, right);
      m_drive.setDeadband(0.15);
      m_vDrive = new VelDiffDrive(m_left1, m_left2, m_right1, m_right2);
      m_vDrive.setDeadband(0.15);
    }
  }

  @Override
  public void periodic() {

    //m_drive.arcadeDrive(m_power * POWER_FACTOR, m_turn * TURN_FACTOR, true);
    m_vDrive.arcadeDrive(m_power, m_turn);
    
  }
  /*
  @Override
  public void autonomousInit(){
    // todo:
  }
  
  @Override
  public void autonomousPeriodic(){
    m_drive.arcadeDrive(m_power, m_turn);
  }
  */
  /*
  @Override
  public void teleopInit(){

  }
  
  
  @Override
  public void teleopPeriodic(){
    m_drive.arcadeDrive(m_power, m_turn);
  }
 */

  public void setDrivePower(double power, double turn){
    //To do: Control motors
    if (m_left1 != null || m_left2 != null || m_right1 != null || m_right2 != null){
      //If reversedrive is true, put drive train in reverse
      if(reverseDrive){
        m_power = -power;
        m_turn = turn;
      }
      else{
        m_power = power;
        m_turn = turn;
      }
    }
  }

  public boolean Reverse(){
    //When function is called, switches boolean from true to false and vice versa.
    reverseDrive = !reverseDrive;
    return reverseDrive;
  }
}

