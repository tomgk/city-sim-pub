package org.exolin.citysim.model.fire;

import java.util.Optional;
import org.exolin.citysim.model.StructureParameters;
import org.exolin.citysim.model.StructureType;
import org.exolin.citysim.model.zone.ZoneType;
import org.exolin.citysim.utils.PropertyWriter;

/**
 *
 * @author Thomas
 */
public class FireParameters implements StructureParameters<FireParameters>
{
    int remainingLife;
    final Optional<ZoneType> zone;
    final boolean returnToZone;
    final Optional<StructureType> afterBurn;

    public FireParameters(int remainingLife, Optional<ZoneType> zone, boolean returnToZone, Optional<StructureType> afterBurn)
    {
        if(returnToZone)
        {
            if(zone.isEmpty())
                throw new IllegalArgumentException();
        }
        
        this.remainingLife = remainingLife;
        this.zone = zone;
        this.returnToZone = returnToZone;
        this.afterBurn = afterBurn;
    }

    @Override
    public void writeAdditional(PropertyWriter writer)
    {
        writer.add("remainingLife", remainingLife);
        writer.addOptional("zone", zone.map(ZoneType::getName));
        writer.addOptional("returnToZone", returnToZone);
        writer.addOptional("afterBurn", afterBurn.map(StructureType::getName));
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

    public Optional<StructureType> getAfterBurn()
    {
        return afterBurn;
    }
    
    @Override
    public FireParameters copy()
    {
        return new FireParameters(remainingLife, zone, returnToZone, afterBurn);
    }
}
