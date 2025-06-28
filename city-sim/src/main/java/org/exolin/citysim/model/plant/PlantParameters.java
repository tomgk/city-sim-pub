package org.exolin.citysim.model.plant;

import java.util.Objects;
import java.util.Optional;
import org.exolin.citysim.model.StructureParameters;
import org.exolin.citysim.model.zone.ZoneType;
import org.exolin.citysim.utils.PropertyWriter;

/**
 *
 * @author Thomas
 */
public class PlantParameters implements StructureParameters<PlantParameters>
{
    private Optional<ZoneType> zone;

    public PlantParameters(Optional<ZoneType> zone)
    {
        this.zone = Objects.requireNonNull(zone);
    }

    @Override
    public void writeAdditional(PropertyWriter writer)
    {
        writer.addOptionalExplicit("zone", zone.map(ZoneType::getName));
    }

    public Optional<ZoneType> getZone()
    {
        return zone;
    }

    void setZone(Optional<ZoneType> zone)
    {
        this.zone = Objects.requireNonNull(zone);
    }
    
    @Override
    public PlantParameters copy()
    {
        return new PlantParameters(zone);
    }
}
