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
        return getType().getMaintenance();
    }

    @Override
    public BigDecimal getTaxRevenue()
    {
        //TODO
        
        //very trivial temporary way to get a number
        ZoneType zt = getType().getZoneType();
        if(zt != null && zt.isUserPlaceableZone())
        {
            int size = getType().getSize();
            int area = size * size;
            
            return BigDecimal.valueOf(area).divide(BigDecimal.TEN);
        }
        
        return BigDecimal.ZERO;
    }
}
