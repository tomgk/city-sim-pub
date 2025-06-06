package org.exolin.citysim.bt.buildings;

import org.exolin.citysim.bt.Zones;
import static org.exolin.citysim.bt.buildings.Buildings.createZoneBuildingType;
import static org.exolin.citysim.model.Animation.createUnanimated;
import static org.exolin.citysim.model.StructureSize._1;
import static org.exolin.citysim.model.StructureSize._2;
import static org.exolin.citysim.model.StructureSize._3;
import org.exolin.citysim.model.building.BuildingType;

/**
 *
 * @author Thomas
 */
public class ResidentialBuildings
{
    public static final BuildingType small_house_1 = createZoneBuildingType(createUnanimated("residential/small_house_1"), _1, Zones.residential);
    
    public static final BuildingType small_houses_1 = createZoneBuildingType(createUnanimated("residential/small_houses_1"), _1, Zones.residential);
    public static final BuildingType small_houses_2 = createZoneBuildingType(createUnanimated("residential/small_houses_2"), _1, Zones.residential);
    public static final BuildingType small_houses_3 = createZoneBuildingType(createUnanimated("residential/small_houses_3"), _1, Zones.residential);
    public static final BuildingType small_houses_4 = createZoneBuildingType(createUnanimated("residential/small_houses_4"), _1, Zones.residential);
    public static final BuildingType small_houses_5 = createZoneBuildingType(createUnanimated("residential/small_houses_5"), _1, Zones.residential);
    public static final BuildingType small_houses_6 = createZoneBuildingType(createUnanimated("residential/small_houses_6"), _1, Zones.residential);
    public static final BuildingType small_houses_7 = createZoneBuildingType(createUnanimated("residential/small_houses_7"), _1, Zones.residential);
    public static final BuildingType small_houses_8 = createZoneBuildingType(createUnanimated("residential/small_houses_8"), _1, Zones.residential);
    public static final BuildingType small_houses_9 = createZoneBuildingType(createUnanimated("residential/small_houses_9"), _1, Zones.residential);
    public static final BuildingType small_houses_10 = createZoneBuildingType(createUnanimated("residential/small_houses_10"), _1, Zones.residential);
    public static final BuildingType small_houses_11 = createZoneBuildingType(createUnanimated("residential/small_houses_11"), _1, Zones.residential);
    
    public static final BuildingType house_1 = createZoneBuildingType(createUnanimated("residential/house_1"), _2, Zones.residential);
    public static final BuildingType house_2 = createZoneBuildingType(createUnanimated("residential/house_2"), _2, Zones.residential);
    public static final BuildingType house_3 = createZoneBuildingType(createUnanimated("residential/house_3"), _3, Zones.residential);
    public static final BuildingType house_4 = createZoneBuildingType(createUnanimated("residential/house_4"), _3, Zones.residential);
    
    public static void init()
    {
        
    }
}
