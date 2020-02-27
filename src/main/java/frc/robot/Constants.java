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
    public final class Intake {
        public static final double INTAKE_POWER = 0.5;
    }
    
    public final class IntakeArm{
        public static final int CANID_INTAKE = 30;
    }
    
    

    // TODO: Storage

    // TODO: Shooter
    public final class Shooter {
        public static final int CANID_FLYWHEEL1=14;
        public static final int CANID_FLYWHEEL2=2;
        public static final int CANID_TURRET=1; 
        public static final int CANID_HOOD=13;
        public static final double TURRET_RAMP_RATE = 0.25;
    }

    // TODO: Hang
    public final class Hang{ 
        public static final int CANID_WINCH=21;
        public static final int CANID_HOOK=20;
    }
    
    // TODO: Control Panel (wheel)
    
    // TODO: Vision

    // TODO: Drive

    public final class Drive {
        public static final int CANID_LEFT1 = 1;  // For example
        public static final int CANID_LEFT2 = 2;
        public static final int CANID_RIGHT1 = 3;
        public static final int CANID_RIGHT2 = 4;
    }

    // OICONSTANTS
    public static final class OIConstants {
    }


    
}
