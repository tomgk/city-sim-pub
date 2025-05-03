package org.exolin.citysim.model.building.vacant;

import java.util.Objects;
import java.util.Optional;
import org.exolin.citysim.model.StructureParameters;
import org.exolin.citysim.model.zone.ZoneType;
import org.exolin.citysim.utils.PropertyWriter;

/**
 *
 * @author Thomas
 */
public class VacantParameters implements StructureParameters<VacantParameters>
{
    private final Optional<ZoneType> zoneType;

    public VacantParameters(Optional<ZoneType> zoneType)
    {
        this.zoneType = Objects.requireNonNull(zoneType);
    }

    public Optional<ZoneType> getZoneType()
    {
        return zoneType;
    }

    @Override
    public VacantParameters copy()
    {
        return new VacantParameters(zoneType);
    }

    @Override
    public void writeAdditional(PropertyWriter writer)
    {
        writer.addOptional("zone", zoneType);
    }
}
