/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019-2020 FIRST. All Rights Reserved.                        */
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

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/stable/docs/software/commandbased/convenience-features.html
public class PathABlueCommandGroup extends SequentialCommandGroup {
  /**
   * Creates a new PathABlueCommandGroup.
   */
  public PathABlueCommandGroup(IntakeSubsystem intakeSubsystem, 
  DriveSubsystem driveSubsystem, VisionSubsystem visionSubsystem, StorageSubsystem storageSubsystem) {
    // Add your commands in the addCommands() call, e.g.
    // addCommands(new FooCommand(), new BarCommand());
    addCommands(
      new DriveFwdCommand(driveSubsystem, 10.0),
      new AutoCaptureBallCommand(driveSubsystem, visionSubsystem),
      new AutoIntakeBallCommand(driveSubsystem, intakeSubsystem, storageSubsystem), 
      new DriveTurnCommand(driveSubsystem, 90.0), 
      new AutoCaptureBallCommand(driveSubsystem, visionSubsystem),
      new AutoIntakeBallCommand(driveSubsystem, intakeSubsystem, storageSubsystem), 
      new DriveTurnCommand(driveSubsystem, -90.0), 
      new AutoCaptureBallCommand(driveSubsystem, visionSubsystem),
      new AutoIntakeBallCommand(driveSubsystem, intakeSubsystem, storageSubsystem), 
      new DriveFwdCommand(driveSubsystem, 8.0),
      new DriveStopCommand(driveSubsystem)
      );
  }
}
