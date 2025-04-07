package org.exolin.citysim.model;

import java.math.BigDecimal;

/**
 *
 * @author Thomas
 */
public class Zone extends Building
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
}
