// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import frc.robot.Constants.OI;
import frc.robot.Constants.Operating;
import frc.robot.subsystems.ElevatorSubsystem;
import frc.robot.subsystems.ElevatorSubsystem.Setpoint;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.Joystick; // for the joystick itself
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.RunCommand; // for inline commands
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.button.JoystickButton; // for joystick buttons

/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and trigger mappings) should be declared here.
 */


public class RobotContainer {
  private ElevatorSubsystem elevatorSubsystem;

  // Replace with CommandPS4Controller or CommandJoystick if needed
  private final CommandXboxController m_driver1Controller =
      new CommandXboxController(OI.Constants.k_driver1ControllerPort);
  
  private final GenericHID m_driver2Controller =
      new GenericHID(OI.Constants.k_driver2ControllerPort);

  private final Joystick m_LA3 = new Joystick(OI.Constants.k_LA3Port);
  

  /** The container for the robot. Contains subsystems, OI devices, and commands. */
  public RobotContainer() {
    // Configure the trigger bindings
    initSubsystems();
    configureBindings();
  }

  public void initSubsystems() {
    if (Operating.Constants.k_usingElevator)
      elevatorSubsystem = new ElevatorSubsystem();
  }

  /**
   * Use this method to define your trigger->command mappings. Triggers can be created via the
   * {@link Trigger#Trigger(java.util.function.BooleanSupplier)} constructor with an arbitrary
   * predicate, or via the named factories in {@link
   * edu.wpi.first.wpilibj2.command.button.CommandGenericHID}'s subclasses for {@link
   * CommandXboxController Xbox}/{@link edu.wpi.first.wpilibj2.command.button.CommandPS4Controller
   * PS4} controllers or {@link edu.wpi.first.wpilibj2.command.button.CommandJoystick Flight
   * joysticks}.
   */

  private boolean inputLock = false;

  private void configureBindings() {

    //Move elevator to level 1
    new Trigger(() -> m_driver1Controller.getHID().getPOV() == 0 && !inputLock) // Xbox
        .onTrue(
            new InstantCommand(() -> inputLock = true)// Set the lock
            .andThen(elevatorSubsystem.setSetpointCommand(Setpoint.k_level1)) // Run the actual command
            .finallyDo(() -> inputLock = false) // Release the lock
        );
    
    new Trigger(() -> m_driver2Controller.getPOV() == 0 && !inputLock) // 8bitdo
        .onTrue(
            new InstantCommand(() -> inputLock = true)// Set the lock
            .andThen(elevatorSubsystem.setSetpointCommand(Setpoint.k_level1)) // Run the actual command
            .finallyDo(() -> inputLock = false) // Release the lock
        );

    new JoystickButton(m_LA3, 6) // logitech attack 3
        .onTrue(
            new InstantCommand(() -> inputLock = true)// Set the lock
            .andThen(elevatorSubsystem.setSetpointCommand(Setpoint.k_level1)) // Run the actual command
            .finallyDo(() -> inputLock = false) // Release the lock
        );




    //Move elevator to level 2
    new Trigger(() -> m_driver1Controller.getHID().getPOV() == 90 && !inputLock) //xbox
        .onTrue(
            new InstantCommand(() -> inputLock = true)
            .andThen(elevatorSubsystem.setSetpointCommand(Setpoint.k_level2))
            .finallyDo(() -> inputLock = false)
        );
    
    new Trigger(() -> m_driver2Controller.getPOV() == 90 && !inputLock) //8bitdo
        .onTrue(
            new InstantCommand(() -> inputLock = true)
            .andThen(elevatorSubsystem.setSetpointCommand(Setpoint.k_level2))
            .finallyDo(() -> inputLock = false)
        );

    new JoystickButton(m_LA3, 7) //logitech attack 3
        .onTrue(
            new InstantCommand(() -> inputLock = true)
            .andThen(elevatorSubsystem.setSetpointCommand(Setpoint.k_level2))
            .finallyDo(() -> inputLock = false)
        );





    //Move elevator to level 3
    new Trigger(() -> m_driver1Controller.getHID().getPOV() == 180 && !inputLock) //xbox
        .onTrue(
          new InstantCommand(() -> inputLock = true)
          .andThen(elevatorSubsystem.setSetpointCommand(Setpoint.k_level3))
          .finallyDo(() -> inputLock = false)
        );
    
    // Bind D-Pad Down to a command
    new Trigger(() -> m_driver2Controller.getPOV() == 180 && !inputLock) //8bitdo
        .onTrue(
          new InstantCommand(() -> inputLock = true)
          .andThen(elevatorSubsystem.setSetpointCommand(Setpoint.k_level3))
          .finallyDo(() -> inputLock = false)
        );

    new JoystickButton(m_LA3, 8) //logitech attack 3
        .onTrue(
          new InstantCommand(() -> inputLock = true)
          .andThen(elevatorSubsystem.setSetpointCommand(Setpoint.k_level3))
          .finallyDo(() -> inputLock = false)
        );






    //Move elevator to level 4
    m_driver1Controller.leftBumper() //xbox
        .onTrue(
            new InstantCommand(() -> inputLock = true)
            .andThen(elevatorSubsystem.setSetpointCommand(Setpoint.k_level4))
            .finallyDo(() -> inputLock = false)
        );

    new JoystickButton(m_driver2Controller, 5) //8bitdo
        .onTrue(
            new InstantCommand(() -> inputLock = true)
            .andThen(elevatorSubsystem.setSetpointCommand(Setpoint.k_level4))
            .finallyDo(() -> inputLock = false)
        );

    new JoystickButton(m_LA3, 9) //logitech attack 3
        .onTrue(
            new InstantCommand(() -> inputLock = true)
            .andThen(elevatorSubsystem.setSetpointCommand(Setpoint.k_level4))
            .finallyDo(() -> inputLock = false)
        );





    //Move elevator to feeder level
    m_driver1Controller.rightBumper() //xbox
        .onTrue(
            new InstantCommand(() -> inputLock = true)
            .andThen(elevatorSubsystem.setSetpointCommand(Setpoint.k_feeder))
            .finallyDo(() -> inputLock = false)
        );

    new JoystickButton(m_driver2Controller, 6) //8bitdo
        .onTrue(
            new InstantCommand(() -> inputLock = true)
            .andThen(elevatorSubsystem.setSetpointCommand(Setpoint.k_feeder))
            .finallyDo(() -> inputLock = false)
        );

    new JoystickButton(m_LA3, 10) //logitech attack 3
        .onTrue(new InstantCommand(() -> {
          if (!inputLock) {
            inputLock = true;
            elevatorSubsystem.setSetpointCommand(Setpoint.k_feeder).schedule();
          }
      }).finallyDo(() -> inputLock = false));
  }

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
}
