package org.exolin.citysim.model.building.vacant;

import org.exolin.citysim.model.StructureParameters;
import org.exolin.citysim.model.zone.ZoneType;

/**
 *
 * @author Thomas
 */
public class VacantParameters implements StructureParameters<VacantParameters>
{
    private final ZoneType zoneType;

    public VacantParameters(ZoneType zoneType)
    {
        this.zoneType = zoneType;
    }

    public ZoneType getZoneType()
    {
        return zoneType;
    }

    @Override
    public VacantParameters copy()
    {
        return new VacantParameters(zoneType);
    }
}
