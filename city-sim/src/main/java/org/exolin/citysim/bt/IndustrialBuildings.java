package org.exolin.citysim.bt;

import static org.exolin.citysim.bt.Buildings.createBuildingType;
import static org.exolin.citysim.bt.Zones.zone_industrial;
import org.exolin.citysim.model.ActualBuildingType;

/**
 *
 * @author Thomas
 */
public class IndustrialBuildings
{
    public static final ActualBuildingType industrial_small_1 = createBuildingType("industrial_small_1", 1, 1, zone_industrial);
    public static final ActualBuildingType industrial_small_2 = createBuildingType("industrial_small_2", 1, 1, zone_industrial);
    public static final ActualBuildingType industrial_small_3 = createBuildingType("industrial_small_3", 1, 1, zone_industrial);
    public static final ActualBuildingType industrial_small_4 = createBuildingType("industrial_small_4", 1, 1, zone_industrial);

    static void init()
    {
        BuildingTypes.init();
    }
}
