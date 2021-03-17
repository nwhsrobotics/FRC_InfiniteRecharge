/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.util;

import com.revrobotics.CANPIDController;
import com.revrobotics.CANSparkMax;
import com.revrobotics.ControlType;

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
    private static final double DRIVE_P = 0;
    private static final double DRIVE_I = 0;
    private static final double DRIVE_D = 0;
    private static final double DRIVE_Iz = 0;
    private static final double DRIVE_FF = 0;
    private static final double DRIVE_minOutput = 0;
    private static final double DRIVE_maxOutput = 0;
    private static final double DRIVE_IMaxAccum = 0;
    private static final double DRIVE_IAccum = 0;

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
    private double m_metersPerRev = DEFAULT_METERS_PER_REV;
    private double m_wheelBase = DEFAULT_WHEEL_BASE;
    private boolean m_enable = false;


    public VelDiffDrive(CANSparkMax l1, CANSparkMax l2, CANSparkMax r1, CANSparkMax r2) {
        // store spark max references
        m_l1 = l1;
        m_l2 = l2;
        m_r1 = r1;
        m_r2 = r2;
        // init enabled flag to false
        
        // init accels to default
        // init velocities to zero.
        // init max velocities to defaults.
    }

    //
    public void setMetersPerRev(double meters) {
        m_metersPerRev = meters;
    }

    public void setWheelBase(double meters) {
        m_wheelBase = meters;
    }

    public void setMaxVelFwd(double fwd){
        m_max_v_fwd = fwd;
    }

    public void setMaxVelTurn(double turn){
        m_max_v_turn = turn;
    }

    public void setFwdAccel(double fwd) {
        m_a_fwd = fwd;
    }

    public void setTurnAccel(double turn) {
        m_a_turn = turn;
    }

    public double getFwdVel() {
        return m_v_fwd;
    }

    public double getTurnVel() {
        return m_v_turn;
    }

    private void initPID(CANPIDController controller){
        controller.setP(DRIVE_P);
        controller.setI(DRIVE_I);
        controller.setD(DRIVE_D);
        controller.setIZone(DRIVE_Iz);
        controller.setIMaxAccum(DRIVE_IMaxAccum, 0);
        controller.setIAccum(DRIVE_IAccum);
        controller.setFF(DRIVE_FF);
        controller.setOutputRange(DRIVE_minOutput, DRIVE_maxOutput);
        controller.setReference(0.0, ControlType.kVelocity);
    }

    // Tell VelDiffDrive to take control of motors
    public void enable() {
        
        // Make sure we have valid spark max references. If not, don't enable.
        if((m_l1 != null) && (m_l2 != null) && (m_r1 != null) && (m_r2 != null)){
            // Get PID Controllers
            m_pid_l1 = m_l1.getPIDController();
            m_pid_l2 = m_l2.getPIDController();
            m_pid_r1 = m_r1.getPIDController();
            m_pid_r2 = m_r2.getPIDController();

            // Init PID Controllers
            initPID(m_pid_l1);
            initPID(m_pid_l2);
            initPID(m_pid_r1);
            initPID(m_pid_r2);
            // Initialize velocity
            m_v_fwd = 0.0;
            m_v_turn = 0.0;

            // Set flag to permit updates via arcadeDrive
            m_enable = true;
        }
        else{
            //motor controller don't exist
            m_enable = false;
        }
        
    }

    public void disable() {
        // clear flag -- no more updates via arcadeDrive
        m_enable = false;
    }

    // Convert joystick inputs to velocity commands
    public void arcadeDrive(double fwd, double turn) {
        // Don't do anything if not in enabled state.
        if(!m_enable){
            return;
        }
        // Limit inputs
        fwd = limit(fwd);
        turn = limit(turn);
        
        
        // Apply deadbands
        fwd = applyDeadband(fwd, m_deadband);
        turn= applyDeadband(turn, m_deadband);
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
        return new String("JagBots Developed PIDDrive");
    }

    @Override
    // stopMotor for RobotDriveBase
    public void stopMotor() {
        // Stop all the motors.
    }

}
