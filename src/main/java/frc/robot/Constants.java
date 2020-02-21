/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.XboxController;

/**
 * The Constants class provides a convenient place for teams to hold robot-wide
 * numerical or boolean constants. This class should not be used for any other
 * purpose. All constants should be declared globally (i.e. public static). Do
 * not put anything functional in this class.
 *
 * <p>
 * It is advised to statically import this class (or one of its inner classes)
 * wherever the constants are needed, to reduce verbosity.
 */
public final class Constants {
    // TODO: General constants

    // TODO: Intake

    // TODO: Storage

    // TODO: Shooter
    public final class Shooter {
        public static final int CANID_FLYWHEEL1=10;
        public static final int CANID_FLYWHEEL2=11;
        public static final int CANID_TURRET=12;
        public static final int CANID_HOOD=13;
    }

    // TODO: Hang

    // TODO: Control Panel (wheel)
    
    // TODO: Vision

    // TODO: Drive





    public static final class OIConstants {
        XboxController joy = new XboxController(0);
        //public final int StartSequenceCommand = 1;  //NOT REAL VALUE
        //public final int NextStageButton = 0;  //NOT REAL VALUE
        //public final int PrevStageButton = 0;  //NOT REAL VALUE
    }
}
