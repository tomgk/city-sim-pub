package org.exolin.citysim.bt;

import static org.exolin.citysim.bt.Buildings.createBuildingType;
import static org.exolin.citysim.bt.Zones.industrial;
import org.exolin.citysim.model.ab.ActualBuildingType;
import static org.exolin.citysim.model.Animation.createUnanimated;

/**
 *
 * @author Thomas
 */
public class IndustrialBuildings
{
    public static final ActualBuildingType small_1 = createBuildingType(createUnanimated("industrial/small_1"), 1, industrial, 0);
    public static final ActualBuildingType small_2 = createBuildingType(createUnanimated("industrial/small_2"), 1, industrial, 0);
    public static final ActualBuildingType small_3 = createBuildingType(createUnanimated("industrial/small_3"), 1, industrial, 0);
    public static final ActualBuildingType small_4 = createBuildingType(createUnanimated("industrial/small_4"), 1, industrial, 0);

    static void init()
    {
        BuildingTypes.init();
    }
}
