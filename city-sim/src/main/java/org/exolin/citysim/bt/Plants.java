package org.exolin.citysim.bt;

import static org.exolin.citysim.bt.Buildings.createBuildingType;
import org.exolin.citysim.model.ActualBuildingType;
import static org.exolin.citysim.model.Animation.createAnimation;
import static org.exolin.citysim.model.Animation.createUnanimated;

/**
 *
 * @author Thomas
 */
public class Plants
{
    public static final ActualBuildingType plant_solar = createBuildingType(createUnanimated("plant_solar"), 4, Zones.plants, 1300);
    public static final ActualBuildingType gas_plant = createBuildingType(createAnimation("gas_plant", 8), 4, Zones.plants, 2000);
    public static final ActualBuildingType oil_plant = createBuildingType(createAnimation("oil_plant", 8), 4, Zones.plants, 6600);
}
