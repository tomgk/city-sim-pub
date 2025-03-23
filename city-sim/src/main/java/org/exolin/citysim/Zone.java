package org.exolin.citysim;

/**
 *
 * @author Thomas
 */
public class Zone extends Building
{
    public Zone(ZoneType type, int x, int y)
    {
        super(type, x, y);
    }

    @Override
    public ZoneType getType()
    {
        return (ZoneType)super.getType();
    }
}
