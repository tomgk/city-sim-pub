package org.exolin.citysim;

import java.io.Writer;

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

    @Override
    protected void serializeImpl(Writer out)
    {
    }
}
