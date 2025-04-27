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
import org.exolin.citysim.model.building.vacant.VacantType;
import org.exolin.citysim.model.zone.ZoneType;

/**
 *
 * @author Thomas
 */
public class Vacants
{
    private final Map<ZoneType, VacantType> versions = new HashMap<>();

    public Vacants(String name, int size)
    {
        for(ZoneType z : ZoneType.types(ZoneType.class))
        {
            VacantType t = new VacantType(z.getName()+":"+name, Animation.createUnanimated(name), size, z);
            versions.put(z, t);
        }
    }
    
    static
    {
        BuildingTypes.init();
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
