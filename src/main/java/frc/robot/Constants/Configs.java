package frc.robot.Constants;

import com.revrobotics.spark.config.SparkMaxConfig;
import com.revrobotics.spark.config.ClosedLoopConfig.FeedbackSensor;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;

public class Configs {
    public static final class Elevator {
        public static final SparkMaxConfig elevatorConfigRight = new SparkMaxConfig();
        public static final SparkMaxConfig elevatorConfigLeft = new SparkMaxConfig();
        static {
            // PIDF values for right elevator
            elevatorConfigRight
                .idleMode(IdleMode.kCoast)
                .smartCurrentLimit(50)
                .voltageCompensation(12);
            elevatorConfigRight.closedLoop
                .feedbackSensor(FeedbackSensor.kPrimaryEncoder)
                .p(0.1)
                .outputRange(-1.0, 1.0)
                .maxMotion
                .maxVelocity(4200)
                .maxAcceleration(6000)
                .allowedClosedLoopError(0.5);
            // PIDF values for left elevator
            elevatorConfigLeft
                .idleMode(IdleMode.kCoast)
                .smartCurrentLimit(50)
                .voltageCompensation(12);
            elevatorConfigLeft.closedLoop
                .feedbackSensor(FeedbackSensor.kPrimaryEncoder)
                .p(0.1)
                .outputRange(-1, 1)
                .maxMotion
                // Set MAXMotion parameters for position control - Edit
                .maxVelocity(4200) 
                .maxAcceleration(6000) 
                .allowedClosedLoopError(0.5);     
        }
    }
}
