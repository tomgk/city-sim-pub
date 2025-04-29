package org.exolin.citysim.model.zone;

import java.math.BigDecimal;
import org.exolin.citysim.model.EmptyStructureParameters;
import org.exolin.citysim.model.Structure;

/**
 *
 * @author Thomas
 */
public class Zone extends Structure
{
    public Zone(ZoneType type, int x, int y, ZoneType.Variant variant)
    {
        this(type, x, y, variant, EmptyStructureParameters.getInstance());
    }
    
    public Zone(ZoneType type, int x, int y, ZoneType.Variant variant, EmptyStructureParameters data)
    {
        super(type, x, y, variant, data);
    }

    @Override
    public BigDecimal getMaintenance()
    {
        return BigDecimal.ZERO;
    }

    @Override
    public BigDecimal getTaxRevenue()
    {
        return BigDecimal.ZERO;
    }
}
