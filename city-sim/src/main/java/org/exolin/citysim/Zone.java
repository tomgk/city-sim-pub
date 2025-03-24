package org.exolin.citysim;

import java.io.Writer;

/**
 *
 * @author Thomas
 */
public class Zone extends Building
{
    public Zone(ZoneType type, int x, int y, int variant)
    {
        super(type, x, y, variant);
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
/*
    @Override
    void update(World world)
    {
        x
    }*/
}
