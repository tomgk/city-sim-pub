package org.exolin.citysim.bt;

import org.exolin.citysim.ActualBuildingType;
import org.exolin.citysim.Animation;
import org.exolin.citysim.ZoneType;

/**
 *
 * @author Thomas
 */
public class Buildings
{
    public static final ActualBuildingType plant_solar = createBuildingType("plant_solar", 4, null);
    
    static ActualBuildingType createBuildingType(String name, int size, ZoneType zoneType)
    {
        return new ActualBuildingType(name, Animation.create(name), size, zoneType);
    }

    static void init()
    {
        BuildingTypes.init();
    }
}
