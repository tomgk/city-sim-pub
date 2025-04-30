package org.exolin.citysim.model.building.vacant;

import org.exolin.citysim.model.StructureParameters;
import org.exolin.citysim.model.zone.ZoneType;

/**
 *
 * @author Thomas
 */
public class VacantParameters implements StructureParameters<VacantParameters>
{
    private final ZoneType zone;

    public VacantParameters(ZoneType zone)
    {
        this.zone = zone;
    }

    public ZoneType getZone()
    {
        return zone;
    }
    
    @Override
    public VacantParameters copy()
    {
        return new VacantParameters(zone);
    }
}
