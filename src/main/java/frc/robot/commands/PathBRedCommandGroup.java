/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.IntakeSubsystem;
import frc.robot.subsystems.StorageSubsystem;
import frc.robot.subsystems.VisionSubsystem;

public class PathBRedCommandGroup extends SequentialCommandGroup {
  /**
   * Add your docs here.
   */
  public PathBRedCommandGroup(IntakeSubsystem intakeSubsystem, 
  DriveSubsystem driveSubsystem, VisionSubsystem visionSubsystem, StorageSubsystem storageSubsystem) {
    // Add Commands here:
    // e.g. addSequential(new Command1());
    // addSequential(new Command2());
    // these will run in order.
    addCommands(
      new AutoCaptureBallCommand(driveSubsystem, visionSubsystem),
      new AutoIntakeBallCommand(driveSubsystem, intakeSubsystem, storageSubsystem), 
      new DriveTurnCommand(driveSubsystem, -90.0),
      new AutoCaptureBallCommand(driveSubsystem, visionSubsystem),
      new AutoIntakeBallCommand(driveSubsystem, intakeSubsystem, storageSubsystem), 
      new DriveTurnCommand(driveSubsystem, 90.0), 
      new AutoCaptureBallCommand(driveSubsystem, visionSubsystem),
      new AutoIntakeBallCommand(driveSubsystem, intakeSubsystem, storageSubsystem), 
      new DriveTurnCommand(driveSubsystem, -75.0),
      new DriveFwdCommand(driveSubsystem, 12.0),
      new DriveStopCommand(driveSubsystem)
      );
    // To run multiple commands at the same time,
    // use addParallel()
    // e.g. addParallel(new Command1());
    // addSequential(new Command2());
    // Command1 and Command2 will run in parallel.

    // A command group will require all of the subsystems that each member
    // would require.
    // e.g. if Command1 requires chassis, and Command2 requires arm,
    // a CommandGroup containing them would require both the chassis and the
    // arm.
  }
}
