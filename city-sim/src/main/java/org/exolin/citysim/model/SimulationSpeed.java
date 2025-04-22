package org.exolin.citysim.model;

/**
 *
 * @author Thomas
 */
public enum SimulationSpeed
{
    PAUSED(0),
    SPEED1(1),
    SPEED2(3),
    SPEED3(9),
    SPEED4(27),
    SPEED5(81);
    
    private final int tickCount;

    private SimulationSpeed()
    {
        this.tickCount = 0;
    }

    private SimulationSpeed(int tickCount)
    {
        this.tickCount = tickCount;
    }

    public int getTickCount()
    {
        return tickCount;
    }
}
