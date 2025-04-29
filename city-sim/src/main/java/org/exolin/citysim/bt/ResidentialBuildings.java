package org.exolin.citysim.bt;

import static org.exolin.citysim.bt.buildings.Buildings.createBuildingType;
import static org.exolin.citysim.model.Animation.createUnanimated;
import org.exolin.citysim.model.building.BuildingType;

/**
 *
 * @author Thomas
 */
public class ResidentialBuildings
{
    public static final BuildingType small_house_1 = createBuildingType(createUnanimated("residential/small_house_1"), 1, Zones.residential, 0);
    
    public static final BuildingType small_houses_1 = createBuildingType(createUnanimated("residential/small_houses_1"), 1, Zones.residential, 0);
    public static final BuildingType small_houses_2 = createBuildingType(createUnanimated("residential/small_houses_2"), 1, Zones.residential, 0);
    public static final BuildingType small_houses_3 = createBuildingType(createUnanimated("residential/small_houses_3"), 1, Zones.residential, 0);
    public static final BuildingType small_houses_4 = createBuildingType(createUnanimated("residential/small_houses_4"), 1, Zones.residential, 0);
    public static final BuildingType small_houses_5 = createBuildingType(createUnanimated("residential/small_houses_5"), 1, Zones.residential, 0);
    public static final BuildingType small_houses_6 = createBuildingType(createUnanimated("residential/small_houses_6"), 1, Zones.residential, 0);
    public static final BuildingType small_houses_7 = createBuildingType(createUnanimated("residential/small_houses_7"), 1, Zones.residential, 0);
    public static final BuildingType small_houses_8 = createBuildingType(createUnanimated("residential/small_houses_8"), 1, Zones.residential, 0);
    public static final BuildingType small_houses_9 = createBuildingType(createUnanimated("residential/small_houses_9"), 1, Zones.residential, 0);
    public static final BuildingType small_houses_10 = createBuildingType(createUnanimated("residential/small_houses_10"), 1, Zones.residential, 0);
    public static final BuildingType small_houses_11 = createBuildingType(createUnanimated("residential/small_houses_11"), 1, Zones.residential, 0);
    
    public static final BuildingType house_1 = createBuildingType(createUnanimated("residential/house_1"), 2, Zones.residential, 0);
    public static final BuildingType house_2 = createBuildingType(createUnanimated("residential/house_2"), 2, Zones.residential, 0);
    public static final BuildingType house_3 = createBuildingType(createUnanimated("residential/house_3"), 3, Zones.residential, 0);
    public static final BuildingType house_4 = createBuildingType(createUnanimated("residential/house_4"), 3, Zones.residential, 0);
    
    public static void init()
    {
        
    }
}
