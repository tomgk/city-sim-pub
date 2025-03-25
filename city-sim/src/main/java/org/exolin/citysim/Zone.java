package org.exolin.citysim;

/**
 *
 * @author Thomas
 */
public class Zone extends Building
{
    public Zone(ZoneType type, int x, int y, ZoneType.Variant variant)
    {
        super(type, x, y, variant);
    }

    @Override
    public ZoneType getType()
    {
        return (ZoneType)super.getType();
    }
}
