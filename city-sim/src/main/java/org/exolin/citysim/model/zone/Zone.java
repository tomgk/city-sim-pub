package org.exolin.citysim.model.zone;

import java.math.BigDecimal;
import java.util.Optional;
import org.exolin.citysim.model.EmptyStructureParameters;
import org.exolin.citysim.model.SingleVariant;
import org.exolin.citysim.model.Structure;

/**
 *
 * @author Thomas
 */
public class Zone extends Structure<Zone, ZoneType, SingleVariant, EmptyStructureParameters>
{
    public Zone(ZoneType type, int x, int y)
    {
        this(type, x, y, SingleVariant.DEFAULT, EmptyStructureParameters.getInstance());
    }
    
    public Zone(ZoneType type, int x, int y, SingleVariant variant, EmptyStructureParameters data)
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
