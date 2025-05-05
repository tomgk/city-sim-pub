package org.exolin.citysim.bt.buildings;

import org.exolin.citysim.bt.Zones;
import static org.exolin.citysim.bt.buildings.Buildings.createZoneBuildingType;
import static org.exolin.citysim.model.Animation.createUnanimated;
import org.exolin.citysim.model.building.BuildingType;

/**
 *
 * @author Thomas
 */
public class ResidentialBuildings
{
    public static final BuildingType small_house_1 = createZoneBuildingType(createUnanimated("residential/small_house_1"), 1, Zones.residential);
    
    public static final BuildingType small_houses_1 = createZoneBuildingType(createUnanimated("residential/small_houses_1"), 1, Zones.residential);
    public static final BuildingType small_houses_2 = createZoneBuildingType(createUnanimated("residential/small_houses_2"), 1, Zones.residential);
    public static final BuildingType small_houses_3 = createZoneBuildingType(createUnanimated("residential/small_houses_3"), 1, Zones.residential);
    public static final BuildingType small_houses_4 = createZoneBuildingType(createUnanimated("residential/small_houses_4"), 1, Zones.residential);
    public static final BuildingType small_houses_5 = createZoneBuildingType(createUnanimated("residential/small_houses_5"), 1, Zones.residential);
    public static final BuildingType small_houses_6 = createZoneBuildingType(createUnanimated("residential/small_houses_6"), 1, Zones.residential);
    public static final BuildingType small_houses_7 = createZoneBuildingType(createUnanimated("residential/small_houses_7"), 1, Zones.residential);
    public static final BuildingType small_houses_8 = createZoneBuildingType(createUnanimated("residential/small_houses_8"), 1, Zones.residential);
    public static final BuildingType small_houses_9 = createZoneBuildingType(createUnanimated("residential/small_houses_9"), 1, Zones.residential);
    public static final BuildingType small_houses_10 = createZoneBuildingType(createUnanimated("residential/small_houses_10"), 1, Zones.residential);
    public static final BuildingType small_houses_11 = createZoneBuildingType(createUnanimated("residential/small_houses_11"), 1, Zones.residential);
    
    public static final BuildingType house_1 = createZoneBuildingType(createUnanimated("residential/house_1"), 2, Zones.residential);
    public static final BuildingType house_2 = createZoneBuildingType(createUnanimated("residential/house_2"), 2, Zones.residential);
    public static final BuildingType house_3 = createZoneBuildingType(createUnanimated("residential/house_3"), 3, Zones.residential);
    public static final BuildingType house_4 = createZoneBuildingType(createUnanimated("residential/house_4"), 3, Zones.residential);
    
    public static void init()
    {
        
    }
}
