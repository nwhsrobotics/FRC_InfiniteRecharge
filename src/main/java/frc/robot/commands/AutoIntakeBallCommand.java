/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.IntakeSubsystem;
import frc.robot.subsystems.StorageSubsystem;

public class AutoIntakeBallCommand extends CommandBase {
  private static final double DRIVE_POWER = 0.2; //Drive power during autoIntake
  private static final double AUTOINTAKETIME1 = 0.50; //Time that drive power is on
  private static final double AUTOINTAKETIME2 = 5.0; //Time that intake runs
  private static final double INTAKE_POWER = 0.9; //Power that intake arm uses
  private DriveSubsystem m_drive;
  private IntakeSubsystem m_intake;
  private double m_elapsed;
  private StorageSubsystem m_storageSubsystem;
  private boolean m_finished;
  private boolean m_intook;

  /**
   * Creates a new AutoIntakeBallCommand.
   */
  public AutoIntakeBallCommand(DriveSubsystem driveSubsystem, IntakeSubsystem intakeSubsystem, StorageSubsystem storageSubsystem) {
    m_drive = driveSubsystem;
    m_intake = intakeSubsystem;
    m_storageSubsystem = storageSubsystem;
    m_elapsed = 0.0;
    addRequirements(m_drive);
    addRequirements(m_intake);
    // Use addRequirements() here to declare subsystem dependencies.
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    System.out.println("AutoIntake Started\n");
    m_elapsed = 0.0;
    m_finished = false;
    m_intook = false;
    m_drive.setDrivePower(DRIVE_POWER, 0.0);
    m_intake.setIntakeStatus(true);
    m_intake.intakeMotor(INTAKE_POWER);
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    m_elapsed += 0.020;
    System.out.printf("AutoIntakeBallCommand %f\n", m_elapsed);
    if(m_elapsed >= AUTOINTAKETIME1){
      m_drive.setDrivePower(0.0, 0.0);
    }
    if(m_storageSubsystem.isStorageRunning()){
      m_intake.intakeMotor(0.0);
      m_intake.setIntakeStatus(false);
      m_intook = true;
    }
    if((!m_storageSubsystem.isStorageRunning() && m_intook) || (m_elapsed >= AUTOINTAKETIME2)){
      m_finished = true; 
      System.out.println("AutoIntakeCommand Finished\n");
    }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    System.out.println("AutoIntake Ended\n");
    m_drive.setDrivePower(0.0, 0.0);
    m_intake.intakeMotor(0.0);
    m_intake.setIntakeStatus(false);
    
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return m_finished;
  }
}
