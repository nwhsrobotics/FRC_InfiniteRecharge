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
        //public static final double INTAKE_POWER = 0.5;
    }
    
    public final class IntakeArm{
        public static final int CANID_INTAKE = 7;
        public static final int CANID_INTAKEARM1 = 4;
        public static final int CANID_INTAKEARM2 = 14; 
    }
    

    // TODO: Storage
    public final class Storage{
        public static final int CANID_motor1 = 15; //15
        public static final int CANID_motor2 = 16; //16
    } 

    // TODO: Shooter
    public final class Shooter {
        public static final int CANID_FLYWHEEL1=1;  //1
        public static final int CANID_FLYWHEEL2=2; //2
        public static final int CANID_TURRET= 5; 
        public static final int CANID_HOOD=0;
        public static final double TURRET_RAMP_RATE = 0.25;
    }

    // TODO: Hang
    public final class Hang{ 
        public static final int CANID_WINCH = 3; //0
        public static final int CANID_HOOK = 12; //0
    }
    
    // TODO: Control Panel (wheel)
    
    // TODO: Vision

    // TODO: Drive

    public final class Drive {
        public static final int CANID_LEFT1 = 8; //11 // For example
        public static final int CANID_LEFT2 = 9;
        public static final int CANID_RIGHT1 = 11;
        public static final int CANID_RIGHT2 = 10;
    }

    // OICONSTANTS
    public static final class OIConstants {
    }


    
}
