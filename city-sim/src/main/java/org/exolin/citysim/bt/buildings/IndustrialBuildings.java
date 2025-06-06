package org.exolin.citysim.bt.buildings;

import org.exolin.citysim.bt.StructureTypes;
import static org.exolin.citysim.bt.Zones.industrial;
import static org.exolin.citysim.bt.buildings.Buildings.createZoneBuildingType;
import static org.exolin.citysim.model.Animation.createUnanimated;
import static org.exolin.citysim.model.StructureSize._1;
import org.exolin.citysim.model.building.BuildingType;

/**
 *
 * @author Thomas
 */
public class IndustrialBuildings
{
    public static final BuildingType small_1 = createZoneBuildingType(createUnanimated("industrial/small_1"), _1, industrial);
    public static final BuildingType small_2 = createZoneBuildingType(createUnanimated("industrial/small_2"), _1, industrial);
    public static final BuildingType small_3 = createZoneBuildingType(createUnanimated("industrial/small_3"), _1, industrial);
    public static final BuildingType small_4 = createZoneBuildingType(createUnanimated("industrial/small_4"), _1, industrial);

    static void init()
    {
        StructureTypes.init();
    }
}
