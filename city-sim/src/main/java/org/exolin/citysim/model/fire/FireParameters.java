package org.exolin.citysim.model.fire;

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
    final boolean returnToZone;
/*
    public FireParameters()
    {
        this(10000);
    }
*/
    public FireParameters(int remainingLife, Optional<ZoneType> zone, boolean returnToZone)
    {
        this.remainingLife = remainingLife;
        this.zone = zone;
        this.returnToZone = returnToZone;
    }

    public int getRemainingLife()
    {
        return remainingLife;
    }

    public Optional<ZoneType> getZone()
    {
        return zone;
    }

    public boolean isReturnToZone()
    {
        return returnToZone;
    }
    
    @Override
    public FireParameters copy()
    {
        return new FireParameters(remainingLife, zone, returnToZone);
    }
}
