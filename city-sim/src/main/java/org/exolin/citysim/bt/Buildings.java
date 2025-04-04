package org.exolin.citysim.bt;

import org.exolin.citysim.model.ActualBuildingType;
import org.exolin.citysim.model.Animation;
import static org.exolin.citysim.model.Animation.createUnanimated;
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
        return createBuildingType(createUnanimated(name), size, zoneType);
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
