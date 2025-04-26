package org.exolin.citysim.bt;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;
import org.exolin.citysim.model.Animation;
import org.exolin.citysim.model.StructureType;
import org.exolin.citysim.model.building.BuildingType;
import org.exolin.citysim.model.zone.ZoneType;

/**
 *
 * @author Thomas
 */
public class Vacants
{
    private static final Set<BuildingType> vacantBuildings = new LinkedHashSet<>();
    private final Map<ZoneType, BuildingType> versions = new HashMap<>();

    public Vacants(String name, int size)
    {
        for(ZoneType z : ZoneType.types(ZoneType.class))
        {
            BuildingType t = new BuildingType(z.getName()+":"+name, Animation.createUnanimated(name), size, z, 0, BigDecimal.ZERO);
            versions.put(z, t);
            vacantBuildings.add(t);
        }
    }
    
    static
    {
        BuildingTypes.init();
    }
    
    public BuildingType getVacantBuilding(ZoneType zone)
    {
        BuildingType t = versions.get(zone);
        if(t == null)
            throw new NoSuchElementException("no vacant building for "+zone.getName());
        return t;
    }
    
    public static boolean isVacant(StructureType type)
    {
        if(type instanceof BuildingType t)
            return isVacant(t);
        else
            return false;
    }
    
    public static boolean isVacant(BuildingType type)
    {
        return vacantBuildings.contains(type);
    }
    
    public static final Vacants tore_down = new Vacants("destruction/tore_down", 1);
    public static final Vacants abandoned_small_1 = new Vacants("destruction/abandoned_small_1", 1);
    public static final Vacants abandoned_small_2 = new Vacants("destruction/abandoned_small_2", 1);
    
    public static final Vacants abandoned_middle_1 = new Vacants("destruction/abandoned_middle_1", 2);
    public static final Vacants abandoned_middle_2 = new Vacants("destruction/abandoned_middle_2", 2);
    public static final Vacants abandoned_middle_3 = new Vacants("destruction/abandoned_middle_3", 2);
    public static final Vacants abandoned_middle_4 = new Vacants("destruction/abandoned_middle_4", 2);
    
    public static final Vacants abandoned_big_1 = new Vacants("destruction/abandoned_big_1", 3);
    public static final Vacants abandoned_big_2 = new Vacants("destruction/abandoned_big_2", 3);
}
