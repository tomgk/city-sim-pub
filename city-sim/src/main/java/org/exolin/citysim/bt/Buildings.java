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
    public static final ActualBuildingType plant_solar = createBuildingType("plant_solar", 4, Zones.zone_plants, 1300);
    public static final ActualBuildingType gas_plant = createBuildingType(Animation.createAnimation("gas_plant", 8), 4, Zones.zone_plants, 6600);
    public static final ActualBuildingType oil_plant = createBuildingType(Animation.createAnimation("oil_plant", 8), 4, Zones.zone_plants, 6600);
    
    static ActualBuildingType createBuildingType(String name, int size, ZoneType zoneType, int cost)
    {
        return createBuildingType(createUnanimated(name), size, zoneType, cost);
    }
    
    static ActualBuildingType createBuildingType(Animation animation, int size, ZoneType zoneType, int cost)
    {
        return new ActualBuildingType(animation.getName(), animation, size, zoneType, cost);
    }

    static void init()
    {
        BuildingTypes.init();
    }
}
