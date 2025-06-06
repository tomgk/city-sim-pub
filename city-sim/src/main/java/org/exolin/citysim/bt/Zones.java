package org.exolin.citysim.bt;

import java.util.List;
import static org.exolin.citysim.model.StructureSize._1;
import org.exolin.citysim.model.zone.Density;
import org.exolin.citysim.model.zone.ZoneType;
import org.exolin.citysim.model.zone.ZoneTypeType;

/**
 *
 * @author Thomas
 */
public class Zones
{
    public static final ZoneType residential = createUserplaceableZone("residential", 5);
    public static final ZoneType low_residential = createLowDensity("residential_low", residential);
    public static final ZoneTypeType residential_category = residential.getCategory();
    public static final ZoneType business = createUserplaceableZone("business", 5);
    public static final ZoneType low_business = createLowDensity("business_low", business);
    public static final ZoneTypeType business_category = business.getCategory();
    public static final ZoneType industrial = createUserplaceableZone("industrial", 5);
    public static final ZoneType low_industrial = createLowDensity("industrial_low", industrial);
    public static final ZoneTypeType industrial_category = industrial.getCategory();
    
    public static ZoneType plants = createSpecialZone("plants");
    public static ZoneType parks = createSpecialZone("parks");
    public static ZoneType destroyed = createSpecialZone("destroyed");
    
    public static final int BUILDING_DISTANCE = 3;
    
    public static final List<ZoneType> BASIC_ZONES = List.of(
            residential, low_residential,
            business, low_business,
            industrial, low_industrial
    );
    
    private static ZoneType createUserplaceableZone(String name, int cost)
    {
        ZoneTypeType ztt = new ZoneTypeType(name, true, cost);
        return new ZoneType(name, "zone/"+name, _1, ztt, Density.DEFAULT);
    }
    
    private static ZoneType createLowDensity(String name, ZoneType type)
    {
        return new ZoneType(name, "zone/"+name, _1, type.getCategory(), Density.DEFAULT);
    }
    
    private static ZoneType createSpecialZone(String name)
    {
        ZoneTypeType ztt = new ZoneTypeType(name, false, 0);
        return new ZoneType(name, "zone/special", _1, ztt, Density.DEFAULT);
    }

    static void init()
    {
        StructureTypes.init();
    }
}
