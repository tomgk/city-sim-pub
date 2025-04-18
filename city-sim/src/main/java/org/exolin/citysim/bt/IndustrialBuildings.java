package org.exolin.citysim.bt;

import static org.exolin.citysim.bt.Buildings.createBuildingType;
import static org.exolin.citysim.bt.Zones.industrial;
import static org.exolin.citysim.model.Animation.createUnanimated;
import org.exolin.citysim.model.ab.BuildingType;

/**
 *
 * @author Thomas
 */
public class IndustrialBuildings
{
    public static final BuildingType small_1 = createBuildingType(createUnanimated("industrial/small_1"), 1, industrial, 0);
    public static final BuildingType small_2 = createBuildingType(createUnanimated("industrial/small_2"), 1, industrial, 0);
    public static final BuildingType small_3 = createBuildingType(createUnanimated("industrial/small_3"), 1, industrial, 0);
    public static final BuildingType small_4 = createBuildingType(createUnanimated("industrial/small_4"), 1, industrial, 0);

    static void init()
    {
        BuildingTypes.init();
    }
}
