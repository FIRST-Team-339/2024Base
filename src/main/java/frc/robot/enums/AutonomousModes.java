package frc.robot.enums;

public enum AutonomousModes
    {
    EXAMPLE_AUTO(0, "Example Autonomous"); // Position 1

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
    }