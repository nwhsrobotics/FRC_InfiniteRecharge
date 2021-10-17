/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.util;

import com.revrobotics.CANEncoder;
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
    static final double DEFAULT_WHEEL_BASE = 0.559;      // [meters] (22 inches)
    static final double DEFAULT_ACCEL_FWD = 1.0;       // [meters per second per second]
    static final double DEFAULT_ACCEL_TURN = 1.0;      // [radians per second per second]
    static final double DEFAULT_MAX_V_FWD = 2.0;       // [meters per second]
    static final double DEFAULT_MAX_V_TURN = 2.0;      // [radians per second]
    private static final double DRIVE_P = 0.001;//0.0002;
    private static final double DRIVE_I = 0;
    private static final double DRIVE_D = 0;
    private static final double DRIVE_Iz = 0;
    private static final double DRIVE_FF = 0.000182;//0.000179;     
    private static final double DRIVE_minOutput = -1.0;
    private static final double DRIVE_maxOutput = 1.0;
    private static final double DRIVE_IMaxAccum = 0;
    private static final double DRIVE_IAccum = 0;
    private static final double GEAR_RATIO = -7.0;
    private static final double WHEEL_R = 0.0762; //meters(6 inch wheel)
    private static final double SEC_PERMIN = 60;
    private static final double FACTOR = (SEC_PERMIN * GEAR_RATIO) / (2.0 * Math.PI * WHEEL_R);

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

    private CANEncoder m_l1_encoder;
    private CANEncoder m_r1_encoder;

    // Spark MAX PID controllers
    CANPIDController m_pid_l1, m_pid_l2, m_pid_r1, m_pid_r2;

    // Position and heading estimates
    double m_x, m_y;   // [meters]
    double m_heading;  // [radians]
    private double m_wheelBase = DEFAULT_WHEEL_BASE;
    private boolean m_enable = false;


    public VelDiffDrive(CANSparkMax l1, CANSparkMax l2, CANSparkMax r1, CANSparkMax r2) {
        // store spark max references
        m_l1 = l1;
        m_l2 = l2;
        m_r1 = r1;
        m_r2 = r2;
        // init enabled flag to false

        m_l1_encoder = m_l1.getEncoder();
        m_r1_encoder = m_r1.getEncoder();
        
        // init accels to default
        // init velocities to zero.
        // init max velocities to defaults.
        m_max_v_turn = DEFAULT_MAX_V_TURN;
        m_max_v_fwd = DEFAULT_MAX_V_FWD;

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
        setSafetyEnabled(m_enable);
        
    }

    public void disable() {
        // clear flag -- no more updates via arcadeDrive
        m_l1.getPIDController().setReference(0.0, ControlType.kDutyCycle);
        m_l2.getPIDController().setReference(0.0, ControlType.kDutyCycle);
        m_r1.getPIDController().setReference(0.0, ControlType.kDutyCycle);
        m_r2.getPIDController().setReference(0.0, ControlType.kDutyCycle);

        m_enable = false;
        setSafetyEnabled(m_enable);
    }

    // Convert joystick inputs to velocity commands
    public void arcadeDrive(double fwd, double turn) {
        // Don't do anything if not in enabled state.
        if(!m_enable){
            return;
        }
        // Limit inputs
        //fwd = limit(fwd);
        //turn = limit(turn);
        
        // Apply deadbands
        fwd = applyDeadband(fwd, m_deadband);
        turn = applyDeadband(turn, m_deadband);

        //convert to real units
        fwd = fwd * m_max_v_fwd;
        turn = turn * m_max_v_turn;

        // Apply accel limits to inputs to get commands

        // (Update command velocities)
        
        setVel(fwd, turn);

        
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
        System.out.println("MotorSafety stopped the VelDiffDrive.");
        m_l1.set(0.0);
        m_l2.set(0.0);
        m_r1.set(0.0);
        m_r2.set(0.0);
    }


	public void setVel(double fwd, double turn) {
        // Compute turn wheel speeds
        double right_turn = turn * m_wheelBase / 2.0;
        double left_turn = -turn * m_wheelBase / 2.0;

        // Combine forward and turn wheel speeds
        double right = fwd + right_turn;
        double left = fwd + left_turn;

        // Normalize wheel speeds
        // Negate input to left side
        
        /*
        System.out.printf("VellDiffDrive: %f (%f m/s), %f (%f m/s)\n", -left*FACTOR, -left, right*FACTOR, right);
        System.out.printf("Actual RPM: %f, %f\n", 
                            m_l1_encoder.getVelocity(),
                            m_r1_encoder.getVelocity());

        */
        
        // Update reference vel to motors.
        m_pid_l1.setReference(-left*FACTOR, ControlType.kVelocity);
        m_pid_l2.setReference(-left*FACTOR, ControlType.kVelocity); 
        m_pid_r1.setReference(right*FACTOR, ControlType.kVelocity);
        m_pid_r2.setReference(right*FACTOR, ControlType.kVelocity);

        // Tell MotorSafety we updated the motors.
        feed();

	}

}
