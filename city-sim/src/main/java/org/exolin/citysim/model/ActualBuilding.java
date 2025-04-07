package org.exolin.citysim.model;

import java.math.BigDecimal;

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

    @Override
    public BigDecimal getMaintenance()
    {
        //TODO
        return BigDecimal.ZERO;
    }
}
