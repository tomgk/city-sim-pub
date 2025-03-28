package org.exolin.citysim.bt;

import org.exolin.citysim.ActualBuildingType;
import static org.exolin.citysim.bt.Buildings.createBuildingType;
import static org.exolin.citysim.bt.Zones.zone_industrial;

/**
 *
 * @author Thomas
 */
public class IndustrialBuildings
{
    public static final ActualBuildingType industrial_small_1 = createBuildingType("industrial_small_1", 1, zone_industrial);
    public static final ActualBuildingType industrial_small_2 = createBuildingType("industrial_small_2", 1, zone_industrial);
    public static final ActualBuildingType industrial_small_3 = createBuildingType("industrial_small_3", 1, zone_industrial);
    public static final ActualBuildingType industrial_small_4 = createBuildingType("industrial_small_4", 1, zone_industrial);

    static void init()
    {
        BuildingTypes.init();
    }
}
