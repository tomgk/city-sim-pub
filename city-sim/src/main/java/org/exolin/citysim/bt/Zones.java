package org.exolin.citysim.bt;

import java.util.List;
import org.exolin.citysim.model.ZoneType;

/**
 *
 * @author Thomas
 */
public class Zones
{
    public static final ZoneType zone_residential = createUserplaceableZone("zone_residential", true);
    public static final ZoneType zone_business = createUserplaceableZone("zone_business", true);
    public static final ZoneType zone_industrial = createUserplaceableZone("zone_industrial", true);
    
    public static ZoneType special = createSpecialZone("zone_special");
    
    public static final int BUILDING_DISTANCE = 3;
    
    public static final List<ZoneType> BASIC_ZONES = List.of(zone_residential, zone_business, zone_industrial);
    
    
    private static ZoneType createUserplaceableZone(String name, boolean withLowDensity)
    {
        return new ZoneType(name, 1, true, withLowDensity);
    }
    
    private static ZoneType createSpecialZone(String name)
    {
        return new ZoneType(name, 1, false, false);
    }

    static void init()
    {
        BuildingTypes.init();
    }
}
