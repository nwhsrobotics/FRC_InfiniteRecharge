/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.util;

import com.revrobotics.CANPIDController;
import com.revrobotics.CANSparkMax;

import edu.wpi.first.wpilibj.drive.RobotDriveBase;

/**
 * A Differential Drive class for Four Spark Max controllers, using 
 * closed loop velocity control.
 */
public class VelDiffDrive extends RobotDriveBase {
    // Constants
    static final double DEFAULT_METERS_PER_REV = 1.0;  // TODO
    static final double DEFAULT_WHEEL_BASE = 1.0;      // TODO
    static final double DEFAULT_ACCEL_FWD = 1.0;       // [meters per second per second]
    static final double DEFAULT_ACCEL_TURN = 1.0;      // [radians per second per second]
    static final double DEFAULT_MAX_V_FWD = 2.0;       // [meters per second]
    static final double DEFAULT_MAX_V_TURN = 2.0;      // [radians per second]

    // Fields
    // current velocities
    double m_v_fwd; // [meters per second]
    double m_v_turn; // [radians per second]

    // max velocities
    double m_max_v_fwd;
    double m_max_v_turn;

    // max accel
    double m_a_fwd; // [meters per second per second]
    double m_a_turn;  // [radians per second per second]

    // Spark Max controllers
    CANSparkMax m_l1, m_l2, m_r1, m_r2;

    // Spark MAX PID controllers
    CANPIDController m_pid_l1, m_pid_l2, m_pid_r1, m_pid_r2;

    // Position and heading estimates
    double m_x, m_y;   // [meters]
    double m_heading;  // [radians]


    public VelDiffDrive(CANSparkMax l1, CANSparkMax l2, CANSparkMax r1, CANSparkMax r2) {
        // store spark max references
        // init enabled flag to false

        // init accels to default
        // init velocities to zero.
        // init max velocities to defaults.
    }

    //
    public void setMetersPerRev(double meters) {
        // TODO
    }

    public void setWheelBase(double meters) {

    }

    public void setFwdAccel(double fwd) {
        // TODO : set internal accel
    }

    public double getFwdAccel() {

    }

    public void setTurnAccel(double turn) {

    }

    public double getTurnAccel() {

    }

    public double getFwdVel() {

    }

    public double getTurnVel() {

    }

    // Tell VelDiffDrive to take control of motors
    public void enable() {
        // Make sure we have valid spark max references.  If not, don't enable.
        // Get PID Controllers
        // Init PID Controllers
        // Initialize velocity
        // Set flag to permit updates via arcadeDrive
    }

    public void disable() {
        // clear flag -- no more updates via arcadeDrive
    }

    // Convert joystick inputs to velocity commands
    public void arcadeDrive(double fwd, double turn) {
        // Don't do anything if not in enabled state.
        // Limit inputs
        // Apply deadbands
        // Apply accel limits to inputs to get commands
        // (Update command velocities)
        // Compute forward wheel speeds
        // Compute turn wheel speeds
        // Normalize wheel speeds
        // Negate input to left side
        // Update reference vel to motors.
    }

    @Override
    // getDescription for RobotDriveBase
    public String getDescription() {
        // return
    }

    @Override
    // stopMotor for RobotDriveBase
    public void stopMotor() {
        // Stop all the motors.
    }

}
