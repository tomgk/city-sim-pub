package org.exolin.citysim.model.zone;

import java.math.BigDecimal;
import java.util.Optional;
import org.exolin.citysim.model.EmptyStructureParameters;
import org.exolin.citysim.model.Structure;

/**
 *
 * @author Thomas
 */
public class Zone extends Structure<Zone, ZoneType, ZoneType.Variant, EmptyStructureParameters>
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

    @Override
    public Optional<ZoneType> getZoneType(boolean includeEmptyZone)
    {
        return includeEmptyZone ? Optional.of(getType()) : Optional.empty();
    }
}
