package org.exolin.citysim.bt.buildings;

import org.exolin.citysim.bt.StructureTypes;
import static org.exolin.citysim.bt.Zones.business;
import static org.exolin.citysim.bt.buildings.Buildings.createZoneBuildingType;
import static org.exolin.citysim.model.Animation.createAnimation;
import static org.exolin.citysim.model.Animation.createUnanimated;
import org.exolin.citysim.model.building.BuildingType;

/**
 *
 * @author Thomas
 */
public class BusinessBuildings
{
    public static final BuildingType office = createZoneBuildingType(createUnanimated("business/office"), 3, business);
    public static final BuildingType office2 = createZoneBuildingType(createUnanimated("business/office_2"), 3, business);
    public static final BuildingType office3 = createZoneBuildingType(createUnanimated("business/office_3"), 3, business);
    public static final BuildingType car_cinema = createZoneBuildingType(createAnimation("business/car-cinema", 9), 3, business);
    public static final BuildingType cinema = createZoneBuildingType(createUnanimated("business/cinema"), 3, business);
    public static final BuildingType parkbuilding = createZoneBuildingType(createUnanimated("business/parkbuilding"), 3, business);
    
    public static final BuildingType small_1 = createZoneBuildingType(createUnanimated("business/small_1"), 1, business);
    public static final BuildingType small_2 = createZoneBuildingType(createUnanimated("business/small_2"), 1, business);
    public static final BuildingType small_3 = createZoneBuildingType(createUnanimated("business/small_3"), 1, business);
    public static final BuildingType small_4 = createZoneBuildingType(createUnanimated("business/small_4"), 1, business);
    public static final BuildingType small_5 = createZoneBuildingType(createUnanimated("business/small_5"), 1, business);
    public static final BuildingType small_6 = createZoneBuildingType(createUnanimated("business/small_6"), 1, business);
    public static final BuildingType small_7 = createZoneBuildingType(createUnanimated("business/small_7"), 1, business);
    public static final BuildingType small_8 = createZoneBuildingType(createUnanimated("business/small_8"), 1, business);
    
    static void init()
    {
        StructureTypes.init();
    }
}
