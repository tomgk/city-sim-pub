package org.exolin.citysim.model.building.vacant;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import org.exolin.citysim.model.Animation;
import org.exolin.citysim.model.StructureType;
import org.exolin.citysim.model.zone.ZoneType;
import org.exolin.citysim.utils.Utils;

/**
 *
 * @author Thomas
 */
public class VacantsPack
{
    private final Map<ZoneType, VacantType> versions = new HashMap<>();
    private static final Map<ZoneType, Map<Integer, List<VacantType>>> vacants = new LinkedHashMap<>();

    public VacantsPack(String name, int size)
    {
        for(ZoneType z : ZoneType.types(ZoneType.class))
        {
            VacantType t = new VacantType(z.getName()+":"+name, Animation.createUnanimated(name), size, z);
            versions.put(z, t);
            addVacant(t);
        }
    }
    
    public static VacantType getRandom(ZoneType zone, int size)
    {
        Map<Integer, List<VacantType>> forZone = vacants.get(zone);
        if(forZone == null)
            throw new IllegalArgumentException("nothing for "+zone.getName());
        
        List<VacantType> forSize = forZone.get(size);
        if(forSize == null)
            throw new IllegalArgumentException("nothing for "+size);
        
        return Utils.random(forSize);
    }

    private void addVacant(VacantType t)
    {
        Map<Integer, List<VacantType>> forZone = vacants.computeIfAbsent(t.getZoneType(), k -> new LinkedHashMap<>());
        List<VacantType> forSize = forZone.computeIfAbsent(t.getSize(), s -> new ArrayList<>());
        forSize.add(t);
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
