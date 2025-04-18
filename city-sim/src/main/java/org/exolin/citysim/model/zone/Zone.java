package org.exolin.citysim.model.zone;

import java.math.BigDecimal;
import org.exolin.citysim.model.Structure;

/**
 *
 * @author Thomas
 */
public class Zone extends Structure
{
    public Zone(ZoneType type, int x, int y, ZoneType.Variant variant)
    {
        super(type, x, y, variant);
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
