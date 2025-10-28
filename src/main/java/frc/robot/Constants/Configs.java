package frc.robot.Constants;

import com.revrobotics.spark.config.SparkMaxConfig;
import com.revrobotics.spark.config.ClosedLoopConfig.FeedbackSensor;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;

public class Configs {
    public static final class Elevator {
        public static final SparkMaxConfig elevatorConfigRight = new SparkMaxConfig();
        public static final SparkMaxConfig elevatorConfigLeft = new SparkMaxConfig();
        static {
            // Common configurations for both elevators
            

            // PIDF values for right elevator
           

            // PIDF values for left elevator
            
        }
    }
}
