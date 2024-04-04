package frc.robot.enums;

public enum DriveGears
    {
    /* Gear Declarations */
    GEAR1(0, 0.325), GEAR2(1, 0.5) , GEAR3(2, 0.65);

    private double ratio;
    private int id;

    private DriveGears(int id, double ratio)
        {
            this.id = id;
            this.ratio = ratio;
        }

    /**
     * Get the gear from it's id
     * 
     * @param id
     *            The id of the gear (first gear has the id of 0 and it counts
     *            up from there)
     * @return The {@link DriveGears} enum if one was found, otherwise it is
     *         null
     */
    public static DriveGears getFromId(int id)
    {
        DriveGears[] allGears = values();

        for (DriveGears gear : values())
            {
            if (id == -1)
                {
                return allGears[allGears.length - 1];
                }
            else
                if (gear.id == id)
                    {
                    return gear;
                    }
            }
        return null;
    }

    /**
     * Get the id of a gear
     * 
     * @return The id of the gear
     */
    public int getId()
    {
        return id;
    }

    /**
     * Get the ratio of a gear
     * 
     * @return The ratio of the gear
     */
    public double getRatio()
    {
        return ratio;
    }
    }
