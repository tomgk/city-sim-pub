package org.exolin.citysim;

import java.io.Writer;

/**
 *
 * @author Thomas
 */
public class ActualBuilding extends Building
{
    public ActualBuilding(ActualBuildingType type, int x, int y)
    {
        super(type, x, y);
    }

    @Override
    public ActualBuildingType getType()
    {
        return (ActualBuildingType)super.getType();
    }

    @Override
    protected void serializeImpl(Writer out)
    {
    }
}
