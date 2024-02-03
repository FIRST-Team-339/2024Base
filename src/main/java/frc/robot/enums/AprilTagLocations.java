package frc.robot.enums;

public enum AprilTagLocations
    {
    /* April Tag Location Declarations */
    BLUE_SOURCE_RIGHT(1), BLUE_SOURCE_LEFT(2),

    RED_SPEAKER_RIGHT(3), RED_SPEAKER_CENTER(4),

    RED_AMP(5),

    BLUE_AMP(6),

    BLUE_SPEAKER_RIGHT(7), BLUE_SPEAKER_CENTER(8),

    RED_SOURCE_RIGHT(9), RED_SOURCE_LEFT(10),

    RED_STAGE_LEFT(11), RED_STAGE_RIGHT(12), RED_STAGE_CENTER(13),

    BLUE_STAGE_CENTER(14), BLUE_STAGE_LEFT(15), BLUE_STAGE_RIGHT(16);

    private int id;

    private AprilTagLocations(int id)
        {
            this.id = id;
        }

    /**
     * Get the april tag location from it's id
     * 
     * @param id
     *            The id of the april tag (1-16)
     * @return The {@link DriveGears} enum if one was found, otherwise it is
     *         null
     */
    public static AprilTagLocations getFromId(final int id)
    {
        for (AprilTagLocations aprilTagLocations : values())
            {
            if (aprilTagLocations.id == id)
                return aprilTagLocations;
            }
        return null;
    }

    /**
     * Get the id of an april tag location
     * 
     * @return The id of the april tag location
     */
    public int getId()
    {
        return id;
    }
    }
