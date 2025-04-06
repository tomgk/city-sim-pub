package org.exolin.citysim.model;

/**
 *
 * @author Thomas
 */
public class ActualBuilding extends Building<ActualBuilding, ActualBuildingType, ActualBuildingType.Variant>
{
    public ActualBuilding(ActualBuildingType type, int x, int y, ActualBuildingType.Variant version)
    {
        super(type, x, y, version);
    }

    @Override
    public ZoneType getZoneType()
    {
        return getType().getZoneType();
    }
}
