package org.exolin.citysim;

import java.io.Writer;

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
