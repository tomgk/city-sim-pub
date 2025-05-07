package org.exolin.citysim.model.tree;

import java.util.Objects;
import java.util.Optional;
import org.exolin.citysim.model.StructureParameters;
import org.exolin.citysim.model.zone.ZoneState;
import org.exolin.citysim.model.zone.ZoneType;
import org.exolin.citysim.utils.PropertyWriter;

/**
 *
 * @author Thomas
 */
public class TreeParameters implements StructureParameters<TreeParameters>
{
    private Optional<ZoneState> zone;

    public TreeParameters(Optional<ZoneState> zone)
    {
        this.zone = Objects.requireNonNull(zone);
    }

    @Override
    public void writeAdditional(PropertyWriter writer)
    {
        zone.ifPresent(z -> z.write(writer));
    }

    public Optional<ZoneState> getZone()
    {
        return zone;
    }

    void setZone(Optional<ZoneState> zone)
    {
        this.zone = Objects.requireNonNull(zone);
    }
    
    @Override
    public TreeParameters copy()
    {
        return new TreeParameters(zone);
    }
}
