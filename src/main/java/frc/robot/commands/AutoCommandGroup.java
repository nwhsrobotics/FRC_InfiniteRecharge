/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.IntakeSubsystem;
import frc.robot.subsystems.ShooterSubsystem;
import frc.robot.subsystems.StorageSubsystem;
import frc.robot.subsystems.StorageSubsystem.IndexerState;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/latest/docs/software/commandbased/convenience-features.html
public class AutoCommandGroup extends SequentialCommandGroup {
  /**
   * Creates a new AutoCommandGroup.
   */
  public AutoCommandGroup(StorageSubsystem storage, ShooterSubsystem shooter, IntakeSubsystem intake, DriveSubsystem drive) { // ADD REST OF SUBSYSTEMS

    // Add your commands in the super() call, e.g.
    // super(new FooCommand(), new BarCommand());
    super(new IntakePosCommand(intake, true),
    new IntakeCommand(intake, 0.2).withTimeout(2),
    new IntakeCommand(intake, 0.0).withTimeout(0),
    new ToggleArmedCommand(storage),      //TIMING IS OFF
    new WaitCommand(0.01),
    new ToggleSensorCommand(storage, 2),
    new WaitCommand(2),
    new ToggleShootCommand(storage).withTimeout(1),
    new ToggleShootCommand(storage).withTimeout(1),
    new ToggleSensorCommand(storage, 2),
    new WaitCommand(0.2),
    new ToggleSensorCommand(storage, 2),
    new WaitCommand(2),
    new ToggleShootCommand(storage).withTimeout(1),
    new ToggleShootCommand(storage).withTimeout(1),
    new ToggleSensorCommand(storage, 2),
    new WaitCommand(0.2),
    new ToggleSensorCommand(storage, 2),
    new WaitCommand(2),
    new ToggleShootCommand(storage).withTimeout(1),
    new ToggleShootCommand(storage).withTimeout(1),
    new ToggleSensorCommand(storage, 2),
    new WaitCommand(0.5),
    new ToggleArmedCommand(storage));
    new WaitCommand(0.3);
    storage.m_IndexerState = IndexerState.EMPTYBALLS;
  }
}
