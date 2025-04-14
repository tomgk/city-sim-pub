package org.exolin.citysim.bt;

import org.exolin.citysim.model.ActualBuildingType;
import org.exolin.citysim.model.Animation;
import static org.exolin.citysim.model.Animation.createAnimation;
import static org.exolin.citysim.model.Animation.createUnanimated;
import org.exolin.citysim.model.ZoneType;

/**
 *
 * @author Thomas
 */
public class Buildings
{
    public static final ActualBuildingType plant_solar = createBuildingType(createUnanimated("plant_solar"), 4, Zones.plants, 1300);
    public static final ActualBuildingType gas_plant = createBuildingType(createAnimation("gas_plant", 8), 4, Zones.plants, 2000);
    public static final ActualBuildingType oil_plant = createBuildingType(createAnimation("oil_plant", 8), 4, Zones.plants, 6600);
    
    public static final ActualBuildingType zoo = createBuildingType(createUnanimated("parks/zoo"), 4, Zones.parks, 100);
    
    static ActualBuildingType createBuildingType(Animation animation, int size, ZoneType zoneType, int cost)
    {
        return new ActualBuildingType(animation.getName(), animation, size, zoneType, cost);
    }

    static void init()
    {
        BuildingTypes.init();
    }
}
