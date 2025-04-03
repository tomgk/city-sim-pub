package org.exolin.citysim.model;

/**
 *
 * @author Thomas
 */
public class ActualBuilding extends Building
{
    public ActualBuilding(ActualBuildingType type, int x, int y, ActualBuildingType.Variant version)
    {
        super(type, x, y, version);
    }

    @Override
    public ActualBuildingType getType()
    {
        return (ActualBuildingType)super.getType();
    }

    @Override
    public ZoneType getZoneType()
    {
        return getType().getZoneType();
    }
}
