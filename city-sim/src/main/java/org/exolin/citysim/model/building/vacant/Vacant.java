package org.exolin.citysim.model.building.vacant;

import java.math.BigDecimal;
import org.exolin.citysim.model.Structure;
import org.exolin.citysim.model.zone.ZoneType;

/**
 *
 * @author Thomas
 */
public class Vacant extends Structure<Vacant, VacantType, VacantType.Variant, VacantParameters>
{   
    public Vacant(VacantType type, int x, int y, VacantType.Variant version, VacantParameters data)
    {
        super(type, x, y, version, data);
    }

    @Override
    public ZoneType getZoneType()
    {
        return getData().getZoneType();
    }

    @Override
    public BigDecimal getTaxRevenue()
    {
        return BigDecimal.ZERO;
    }

    @Override
    public BigDecimal getMaintenance()
    {
        return BigDecimal.ZERO;
    }
}