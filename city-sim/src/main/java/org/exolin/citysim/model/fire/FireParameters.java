package org.exolin.citysim.model.fire;

import java.util.Objects;
import java.util.Optional;
import org.exolin.citysim.model.StructureParameters;
import org.exolin.citysim.model.zone.ZoneType;

/**
 *
 * @author Thomas
 */
public class FireParameters implements StructureParameters<FireParameters>
{
    int remainingLife;
    final Optional<ZoneType> zone;
/*
    public FireParameters()
    {
        this(10000);
    }
*/
    public FireParameters(int remainingLife, Optional<ZoneType> zone)
    {
        this.remainingLife = remainingLife;
        this.zone = Objects.requireNonNull(zone);
    }
    
    public int getRemainingLife()
    {
        return remainingLife;
    }

    public Optional<ZoneType> getZone()
    {
        return zone;
    }
    
    @Override
    public FireParameters copy()
    {
        return new FireParameters(remainingLife, zone);
    }
}
