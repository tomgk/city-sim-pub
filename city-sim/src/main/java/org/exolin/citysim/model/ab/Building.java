package org.exolin.citysim.model.ab;

import java.math.BigDecimal;
import org.exolin.citysim.model.Structure;
import org.exolin.citysim.model.zone.ZoneType;

/**
 *
 * @author Thomas
 */
public class Building extends Structure<Building, BuildingType, BuildingType.Variant>
{
    public Building(BuildingType type, int x, int y, BuildingType.Variant version)
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
