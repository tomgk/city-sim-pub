package org.exolin.citysim.bt.buildings;

import org.exolin.citysim.bt.Zones;
import static org.exolin.citysim.bt.buildings.Buildings.createBuildingType;
import static org.exolin.citysim.model.Animation.createAnimation;
import static org.exolin.citysim.model.Animation.createUnanimated;
import org.exolin.citysim.model.building.BuildingType;

/**
 *
 * @author Thomas
 */
public class Plants
{
    public static final BuildingType plant_solar = createBuildingType(createUnanimated("plants/plant_solar"), 4, Zones.plants, 1300);
    public static final BuildingType gas_plant = createBuildingType(createAnimation("plants/gas_plant", 8), 4, Zones.plants, 2000);
    public static final BuildingType oil_plant = createBuildingType(createAnimation("plants/oil_plant", 8), 4, Zones.plants, 6600);
    
    
    public static final BuildingType pump = createBuildingType(createAnimation("water_pump/pump", 8), 1, Zones.plants, 0);
    public static final BuildingType protest = createBuildingType(createAnimation("protest/protest", 2), 1, Zones.plants, 0);
}
