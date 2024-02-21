package frc.robot.enums;

public interface AutonomousModeOptions
    {
    public int getId();

    @Override
    public String toString();

    /**
     * Get the autonomous mode based on the ID
     * 
     * @return An {@link AutonomousModeOptions} enum
     */
    public AutonomousModeOptions getFromId(final int id);
    }