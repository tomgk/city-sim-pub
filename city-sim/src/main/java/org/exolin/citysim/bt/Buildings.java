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
        return new ActualBuildingType(name, Animation.create(name), size, zoneType);
    }

    static void init()
    {
        BuildingTypes.init();
    }
}
