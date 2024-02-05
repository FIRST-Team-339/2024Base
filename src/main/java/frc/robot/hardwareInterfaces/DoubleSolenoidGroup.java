package frc.robot.hardwareInterfaces;

import java.util.ArrayList;

import edu.wpi.first.util.sendable.Sendable;
import edu.wpi.first.util.sendable.SendableBuilder;
import edu.wpi.first.wpilibj.DoubleSolenoid;

public class DoubleSolenoidGroup implements Sendable, AutoCloseable
    {
    private ArrayList<DoubleSolenoid> doubleSolenoids = new ArrayList<DoubleSolenoid>();

    public DoubleSolenoidGroup(final DoubleSolenoid[] doubleSolenoids)
            throws IllegalArgumentException
        {
            if (doubleSolenoids.length < 1)
                {
                throw new IllegalArgumentException(
                        "doubleSolenoids must have at least one solenoid in it");
                }

            for (DoubleSolenoid doubleSolenoid : doubleSolenoids)
                {
                this.doubleSolenoids.add(doubleSolenoid);
                }
        }

    @Override
    public synchronized void close()
    {
        for (DoubleSolenoid doubleSolenoid : doubleSolenoids)
            {
            doubleSolenoid.close();
            }
    }

    /**
     * Set the value of the solenoids.
     *
     * @param value
     *            The value to set (Off, Forward, Reverse)
     */
    public void set(final DoubleSolenoid.Value value)
    {
        for (DoubleSolenoid doubleSolenoid : doubleSolenoids)
            {
            doubleSolenoid.set(value);
            }
    }

    /**
     * Read the current value of the solenoids.
     *
     * @return The current value of the solenoids.
     */
    public DoubleSolenoid.Value get()
    {
        return doubleSolenoids.get(0).get();
    }

    /**
     * Toggle the value of the solenoids.
     *
     * <p>
     * If the solenoids are set to forward, it'll be set to reverse. If the
     * solenoids are set to reverse, it'll be set to forward. If the solenoids
     * are set to off, nothing happens.
     */
    public void toggle()
    {
        for (DoubleSolenoid doubleSolenoid : doubleSolenoids)
            {
            doubleSolenoid.toggle();
            }
    }

    /**
     * Get the forward channel.
     *
     * @return the forward channel.
     */
    public int getFwdChannel()
    {
        return doubleSolenoids.get(0).getFwdChannel();
    }

    /**
     * Get the reverse channel.
     *
     * @return the reverse channel.
     */
    public int getRevChannel()
    {
        return doubleSolenoids.get(0).getFwdChannel();
    }

    /**
     * Check if the forward solenoids are Disabled. If the solenoid is shorted,
     * it is added to the DisabledList and disabled until power cycle, or until
     * faults are cleared.
     *
     * @return If solenoids are disabled due to short.
     */
    public boolean isFwdSolenoidDisabled()
    {
        return doubleSolenoids.get(0).isFwdSolenoidDisabled();
    }

    /**
     * Check if the reverse solenoids are Disabled. If the solenoid is shorted,
     * it is added to the DisabledList and disabled until power cycle, or until
     * faults are cleared.
     *
     * @return If solenoids are disabled due to short.
     */
    public boolean isRevSolenoidDisabled()
    {
        return doubleSolenoids.get(0).isRevSolenoidDisabled();
    }

    @Override
    public void initSendable(SendableBuilder builder)
    {
        for (DoubleSolenoid doubleSolenoid : doubleSolenoids)
            {
            doubleSolenoid.initSendable(builder);
            }
    }
    }
