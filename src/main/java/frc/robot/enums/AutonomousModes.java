package frc.robot.enums;

public enum AutonomousModes
    {
    PASS_START_LINE(1, "Pass Start Line"), SCORE_AMP(2,
            "Pass Start Line & Score Amp");

    private int id;
    private String friendlyName;

    private AutonomousModes(int id, String friendlyName)
        {
            this.id = id;
            this.friendlyName = friendlyName;
        }

    public int getId()
    {
        return this.id;
    }

    @Override
    public String toString()
    {
        return friendlyName;
    }

    /**
     * Get the autonomous mode based on the ID
     * 
     * @return An {@link AutonomousModes} enum
     */
    public AutonomousModes getFromId(final int id)
    {
        for (AutonomousModes autonomousMode : values())
            {
            if (autonomousMode.id == id)
                {
                return autonomousMode;
                }
            }
        return null;
    }
    }