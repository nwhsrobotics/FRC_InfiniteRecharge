/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandGroupBase;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.subsystems.VisionSubsystem;

public class PathACommandGroup extends SequentialCommandGroup {
  private PathABlueCommandGroup m_ABlue;
  private PathARedCommandGroup m_ARed;
  private VisionSubsystem m_visionSubsystem;
  /**
   * Add your docs here.
   */
  public PathACommandGroup() {
    // TODO: need to instantiate m_ABlue, m_ARed

    // Add Commands here:
    // e.g. addSequential(new Command1());
    // addSequential(new Command2());
    // these will run in order.
    addCommands(new PathADecideRedBlueCommand(m_ABlue, m_ARed, m_visionSubsystem));
    
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
