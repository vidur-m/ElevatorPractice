package frc.robot.subsystems;

import com.revrobotics.RelativeEncoder;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.config.SparkBaseConfig;
import com.revrobotics.spark.SparkBase.ControlType;
import com.revrobotics.spark.SparkBase.PersistMode;
import com.revrobotics.spark.SparkBase.ResetMode;
import com.revrobotics.spark.SparkClosedLoopController;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.IDs;
import frc.robot.Constants.Configs;
import frc.robot.Constants.Elevator.ElevatorSetpoints;
import frc.robot.Constants.IDs.ElevatorConstants;

public class ElevatorSubsystem extends SubsystemBase {
    public enum Setpoint{
      k_feeder,
      k_level1,
      k_level2,
      k_level3,
      k_level4
    }

    private final SparkMax m_elevatorRight;
    private final SparkMax m_elevatorLeft;
    // m_elevatorLeft.follow(m_elevatorRight); // Moved to constructor
    private final SparkClosedLoopController m_elevatorRightController;
    private final RelativeEncoder m_elevatorRightEncoder;
    private final RelativeEncoder m_elevatorLeftEncoder;
    private final DigitalInput m_topLimitSwitch;
    private final DigitalInput m_bottomLimitSwitch;
    
    private boolean reset_By_TopLimitSwitch;
    private boolean reset_By_BottomLimitSwitch;
    private double targetPosition;

  public ElevatorSubsystem() {
    m_elevatorRight = new SparkMax(ElevatorConstants.k_rightElevatorMotorID, MotorType.kBrushless);
    m_elevatorLeft = new SparkMax(ElevatorConstants.k_leftElevatorMotorID, MotorType.kBrushless);
    m_elevatorRight.configure(Configs.Elevator.elevatorConfigRight, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
    m_elevatorLeft.configure(Configs.Elevator.elevatorConfigLeft, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
    m_elevatorRightController = m_elevatorRight.getClosedLoopController();

    m_elevatorRightEncoder = m_elevatorRight.getEncoder();
    m_elevatorLeftEncoder = m_elevatorLeft.getEncoder();
    resetEncoders();

    m_topLimitSwitch = new DigitalInput(ElevatorConstants.k_elevatorLimitSwitchTopID);
    m_bottomLimitSwitch = new DigitalInput(ElevatorConstants.k_elevatoLimitSwitchBottomID);

    reset_By_TopLimitSwitch = false;
    reset_By_BottomLimitSwitch = false;
    targetPosition = ElevatorSetpoints.k_feeder;
  }

  private void moveToSetPoint()
  {
    m_elevatorRightController.setReference(targetPosition, ControlType.kPosition);
  }

  public Command setSetpointCommand(Setpoint setpoint) {
    return this.runOnce(
    () -> {
      switch (setpoint) {
        case k_feeder:
          targetPosition = ElevatorSetpoints.k_feeder;
          break;
        case k_level1:
          targetPosition = ElevatorSetpoints.k_level1;
          break;
        case k_level2:
          targetPosition = ElevatorSetpoints.k_level2;
          break;
        case k_level3:
          targetPosition = ElevatorSetpoints.k_level3;
          break;
        case k_level4:
          targetPosition = ElevatorSetpoints.k_level4;
          break;
      }
    });
}

  private void zeroElevatorOnBottomLimitSwitch()
  {
    if (!m_bottomLimitSwitch.get() && !reset_By_BottomLimitSwitch)
    {
      resetEncoders();
      reset_By_BottomLimitSwitch = true;
    }
    else if (m_bottomLimitSwitch.get())
    {
      reset_By_BottomLimitSwitch = false;
    }
  }

  private void setElevatorOnTopSwitch()
  {
    if (!m_topLimitSwitch.get() && !reset_By_TopLimitSwitch)
    {
      m_elevatorRightEncoder.setPosition(18);
      m_elevatorLeftEncoder.setPosition(18);
      reset_By_TopLimitSwitch = true;

    }
    else if (m_topLimitSwitch.get())
    {
      reset_By_TopLimitSwitch = false;
    }
  }

  private void resetEncoders()
  {
    m_elevatorRightEncoder.setPosition(0);
    m_elevatorLeftEncoder.setPosition(0);
  }

  private void stopMotors()
  {
    m_elevatorRight.stopMotor();
    m_elevatorLeft.stopMotor();
  }

  @Override
  public void periodic() {
    moveToSetPoint();
    zeroElevatorOnBottomLimitSwitch();
    setElevatorOnTopSwitch();
    SmartDashboard.putNumber("Elevator Target: ", targetPosition);
    SmartDashboard.putNumber("Actual Elevator Right Position: ", m_elevatorRightEncoder.getPosition());
    SmartDashboard.putNumber("Actual Elevator Left Position: ", m_elevatorLeftEncoder.getPosition());
    SmartDashboard.putBoolean("Top Limit Switch: ", !m_topLimitSwitch.get()); // when pressed, returns true
    SmartDashboard.putBoolean("Bottom Limit Switch: ", !m_bottomLimitSwitch.get()); // when pressed, returns true
  }
}
