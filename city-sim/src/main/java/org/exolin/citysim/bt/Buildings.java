package org.exolin.citysim.bt;

import org.exolin.citysim.model.ActualBuildingType;
import org.exolin.citysim.model.Animation;
import org.exolin.citysim.model.ZoneType;

/**
 *
 * @author Thomas
 */
public class Buildings
{
    public static final ActualBuildingType plant_solar = createBuildingType("plant_solar", 4, Zones.special);
    
    static ActualBuildingType createBuildingType(String name, int size, ZoneType zoneType)
    {
        return createBuildingType(Animation.createUnanimated(name), size, zoneType);
    }
    
    static ActualBuildingType createBuildingType(Animation animation, int size, ZoneType zoneType)
    {
        return new ActualBuildingType(animation.getName(), animation, size, zoneType);
    }

    static void init()
    {
        BuildingTypes.init();
    }
}
