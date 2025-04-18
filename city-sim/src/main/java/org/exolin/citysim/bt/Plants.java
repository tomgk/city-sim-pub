package org.exolin.citysim.bt;

import static org.exolin.citysim.bt.Buildings.createBuildingType;
import static org.exolin.citysim.model.Animation.createAnimation;
import static org.exolin.citysim.model.Animation.createUnanimated;
import org.exolin.citysim.model.ab.BuildingType;

/**
 *
 * @author Thomas
 */
public class Plants
{
    public static final BuildingType plant_solar = createBuildingType(createUnanimated("plants/plant_solar"), 4, Zones.plants, 1300);
    public static final BuildingType gas_plant = createBuildingType(createAnimation("plants/gas_plant", 8), 4, Zones.plants, 2000);
    public static final BuildingType oil_plant = createBuildingType(createAnimation("plants/oil_plant", 8), 4, Zones.plants, 6600);
}
