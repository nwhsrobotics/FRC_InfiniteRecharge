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
import frc.robot.subsystems.ShooterSubsystem;
import frc.robot.subsystems.StorageSubsystem;
import frc.robot.subsystems.VisionSubsystem;
import frc.robot.commands.ToggleArmedCommand;
import frc.robot.commands.FlywheelManualCommand;



// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/stable/docs/software/commandbased/convenience-features.html
public class InfiniteRechargeAutoCommandGroup extends SequentialCommandGroup {
  /**
   * Creates a new PathABlueCommandGroup.
   */
  public InfiniteRechargeAutoCommandGroup(IntakeSubsystem intakeSubsystem, 
  DriveSubsystem driveSubsystem, VisionSubsystem visionSubsystem, StorageSubsystem storageSubsystem, ShooterSubsystem shooterSubsytem) {
    // Add your commands in the addCommands() call, e.g.
    // addCommands(new FooCommand(), new BarCommand());
    addCommands(
      new FlywheelManualCommand(shooterSubsytem, false), //flywheel on auto
      new ToggleArmedCommand(storageSubsystem), //TODO: Change to set armed command 
      new DriveFwdCommand(driveSubsystem, 5.0),
      new TrackTargetAutoCommand(shooterSubsytem),
      new WaitForFlywheelCommand(shooterSubsytem),
      new ToggleShootCommand(storageSubsystem),
      new TrackTargetAutoCommand(shooterSubsytem),
      new WaitForFlywheelCommand(shooterSubsytem),
      new ToggleShootCommand(storageSubsystem),
      new TrackTargetAutoCommand(shooterSubsytem),
      new WaitForFlywheelCommand(shooterSubsytem),
      new ToggleShootCommand(storageSubsystem)
      //TODO: Continue developing autonomous routine
       
      

      );
  }
}
