package org.exolin.citysim.bt;

import static org.exolin.citysim.bt.Buildings.createBuildingType;
import static org.exolin.citysim.bt.Zones.industrial;
import org.exolin.citysim.model.ActualBuildingType;
import static org.exolin.citysim.model.Animation.createUnanimated;

/**
 *
 * @author Thomas
 */
public class IndustrialBuildings
{
    public static final ActualBuildingType industrial_small_1 = createBuildingType(createUnanimated("industrial/industrial_small_1"), 1, industrial, 0);
    public static final ActualBuildingType industrial_small_2 = createBuildingType(createUnanimated("industrial/industrial_small_2"), 1, industrial, 0);
    public static final ActualBuildingType industrial_small_3 = createBuildingType(createUnanimated("industrial/industrial_small_3"), 1, industrial, 0);
    public static final ActualBuildingType industrial_small_4 = createBuildingType(createUnanimated("industrial/industrial_small_4"), 1, industrial, 0);

    static void init()
    {
        BuildingTypes.init();
    }
}
