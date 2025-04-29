package org.exolin.citysim.bt;

import java.util.List;
import org.exolin.citysim.model.zone.ZoneType;

/**
 *
 * @author Thomas
 */
public class Zones
{
    public static final ZoneType residential = createUserplaceableZone("residential", true, 5);
    public static final ZoneType business = createUserplaceableZone("business", true, 5);
    public static final ZoneType industrial = createUserplaceableZone("industrial", true, 5);
    
    public static ZoneType plants = createSpecialZone("plants");
    public static ZoneType parks = createSpecialZone("parks");
    public static ZoneType destroyed = createSpecialZone("destroyed");
    
    public static final int BUILDING_DISTANCE = 3;
    
    public static final List<ZoneType> BASIC_ZONES = List.of(residential, business, industrial);
    
    
    private static ZoneType createUserplaceableZone(String name, boolean withLowDensity, int cost)
    {
        return new ZoneType(name, "zone/"+name, 1, true, withLowDensity, cost);
    }
    
    private static ZoneType createSpecialZone(String name)
    {
        return new ZoneType(name, "zone/special", 1, false, false, 0);
    }

    static void init()
    {
        StructureTypes.init();
    }
}
