package org.exolin.citysim.bt;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import org.exolin.citysim.model.Animation;
import org.exolin.citysim.model.building.BuildingType;
import org.exolin.citysim.model.zone.ZoneType;

/**
 *
 * @author Thomas
 */
public class Vacants
{
    private Map<ZoneType, BuildingType> versions = new HashMap<>();

    public Vacants(String name, int size)
    {
        for(ZoneType z : ZoneType.types(ZoneType.class))
            versions.put(z, new BuildingType(z.getName()+":"+name, Animation.createUnanimated(name), size, z, 0, BigDecimal.ZERO));
    }
    
    static
    {
        BuildingTypes.init();
    }
    
    public static final Vacants tore_down = new Vacants("destruction/tore_down", 1);
}
