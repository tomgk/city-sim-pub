package org.exolin.citysim.model.zone;

/**
 *
 * @author Thomas
 */
public enum Density
{
    DEFAULT(1),
    LOW_DENSITY(2);

    private final int factor;

    private Density(int factor)
    {
        this.factor = factor;
    }

    public int getFactor()
    {
        return factor;
    }

    public boolean isLowDensity()
    {
        return this == LOW_DENSITY;
    }
}
