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
import frc.robot.subsystems.VisionSubsystem;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/stable/docs/software/commandbased/convenience-features.html
public class AutoCaptureGroup extends SequentialCommandGroup {
  /**
   * Creates a new AutoCaptureGroup.
   */
  public AutoCaptureGroup(IntakeSubsystem intakeSubsystem, 
                          DriveSubsystem driveSubsystem, VisionSubsystem visionSubsystem) {
    // Add your commands in the addCommands() call, e.g.
    // addCommands(new FooCommand(), new BarCommand());
    addCommands(new IntakePosCommand(intakeSubsystem, true),
                new AutoCaptureBallCommand(driveSubsystem, visionSubsystem),
                new AutoIntakeBallCommand(driveSubsystem, intakeSubsystem),
                new AutoCaptureBallCommand(driveSubsystem, visionSubsystem),
                new AutoIntakeBallCommand(driveSubsystem, intakeSubsystem)
                );
  }
}
