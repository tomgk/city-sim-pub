package org.exolin.citysim.model.building.vacant;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import org.exolin.citysim.model.Animation;
import org.exolin.citysim.model.StructureType;
import org.exolin.citysim.model.zone.ZoneType;

/**
 *
 * @author Thomas
 */
public class VacantsPack
{
    private final Map<ZoneType, VacantType> versions = new HashMap<>();

    public VacantsPack(String name, int size)
    {
        for(ZoneType z : ZoneType.types(ZoneType.class))
        {
            VacantType t = new VacantType(z.getName()+":"+name, Animation.createUnanimated(name), size, z);
            versions.put(z, t);
        }
    }
    
    public VacantType getVacantBuilding(ZoneType zone)
    {
        VacantType t = versions.get(zone);
        if(t == null)
            throw new NoSuchElementException("no vacant building for "+zone.getName());
        return t;
    }
    
    public static boolean isVacant(StructureType type)
    {
        return type instanceof VacantType;
    }
}
