package org.exolin.citysim.bt.buildings;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.exolin.citysim.bt.Zones;
import static org.exolin.citysim.bt.buildings.Buildings.createBuildingType;
import org.exolin.citysim.model.Animation;
import static org.exolin.citysim.model.Animation.createAnimation;
import static org.exolin.citysim.model.Animation.createUnanimated;
import org.exolin.citysim.model.building.BuildingType;
import org.exolin.citysim.model.zone.ZoneType;

/**
 *
 * @author Thomas
 */
public class Plants
{
    public static final BuildingType plant_solar = createPlant(createUnanimated("plants/plant_solar"), 4, Zones.plants, 1300, 50);
    public static final BuildingType gas_plant = createPlant(createAnimation("plants/gas_plant", 8), 4, Zones.plants, 2000, 50);
    public static final BuildingType oil_plant = createPlant(createAnimation("plants/oil_plant", 8), 4, Zones.plants, 6600, 220);
    
    public static final String MEGA_WATT = "megaWatt";
    
    private static BuildingType createPlant(Animation animation, int size, ZoneType zoneType, int cost, int megaWatt)
    {
        BuildingType bt = createBuildingType(animation, size, zoneType, cost);
        bt.setCustom(MEGA_WATT, megaWatt);
        return bt;
    }
    
    
    public static final BuildingType pump = createBuildingType(createAnimation("water_pump/pump", 8), 1, Zones.plants, 0);
    public static final BuildingType protest = createBuildingType(createAnimation("protest/protest", 2), 1, Zones.plants, 0);
    
    public static final List<BuildingType> ALL = Collections.unmodifiableList(Arrays.asList(plant_solar, gas_plant, oil_plant));
}
