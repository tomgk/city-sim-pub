package org.exolin.citysim.model.building.vacant;

import java.math.BigDecimal;
import org.exolin.citysim.model.EmptyStructureParameters;
import org.exolin.citysim.model.Structure;
import org.exolin.citysim.model.zone.ZoneType;

/**
 *
 * @author Thomas
 */
public class Vacant extends Structure<Vacant, VacantType, VacantType.Variant, EmptyStructureParameters>
{   
    public Vacant(VacantType type, int x, int y, VacantType.Variant version)
    {
        this(type, x, y, version, EmptyStructureParameters.getInstance());
    }
    
    public Vacant(VacantType type, int x, int y, VacantType.Variant version, EmptyStructureParameters data)
    {
        super(type, x, y, version, data);
    }

    @Override
    public ZoneType getZoneType()
    {
        return getType().getZoneType();
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