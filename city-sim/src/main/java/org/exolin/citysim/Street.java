package org.exolin.citysim;

import java.io.Writer;

/**
 *
 * @author Thomas
 */
public class Street extends Building
{
    public Street(StreetType type, int x, int y)
    {
        super(type, x, y);
    }

    @Override
    public BuildingType getType()
    {
        return (StreetType)super.getType();
    }

    @Override
    protected void serializeImpl(Writer out)
    {
    }
}
