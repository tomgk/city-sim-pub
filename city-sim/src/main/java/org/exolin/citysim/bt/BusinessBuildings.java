package org.exolin.citysim.bt;

import static org.exolin.citysim.bt.Buildings.createBuildingType;
import static org.exolin.citysim.bt.Zones.business;
import static org.exolin.citysim.model.Animation.createAnimation;
import static org.exolin.citysim.model.Animation.createUnanimated;
import org.exolin.citysim.model.ab.ActualBuildingType;

/**
 *
 * @author Thomas
 */
public class BusinessBuildings
{
    public static final ActualBuildingType office = createBuildingType(createUnanimated("business/office"), 4, business, 0);
    public static final ActualBuildingType office2 = createBuildingType(createUnanimated("business/office_2"), 3, business, 0);
    public static final ActualBuildingType office3 = createBuildingType(createUnanimated("business/office_3"), 3, business, 0);
    public static final ActualBuildingType car_cinema = createBuildingType(createAnimation("business/car-cinema", 9), 4, business, 0);
    public static final ActualBuildingType cinema = createBuildingType(createUnanimated("business/cinema"), 3, business, 0);
    public static final ActualBuildingType parkbuilding = createBuildingType(createUnanimated("business/parkbuilding"), 3, business, 0);
    
    public static final ActualBuildingType small_1 = createBuildingType(createUnanimated("business/small_1"), 1, business, 0);
    public static final ActualBuildingType small_2 = createBuildingType(createUnanimated("business/small_2"), 1, business, 0);
    public static final ActualBuildingType small_3 = createBuildingType(createUnanimated("business/small_3"), 1, business, 0);
    public static final ActualBuildingType small_4 = createBuildingType(createUnanimated("business/small_4"), 1, business, 0);
    public static final ActualBuildingType small_5 = createBuildingType(createUnanimated("business/small_5"), 1, business, 0);
    public static final ActualBuildingType small_6 = createBuildingType(createUnanimated("business/small_6"), 1, business, 0);
    public static final ActualBuildingType small_7 = createBuildingType(createUnanimated("business/small_7"), 1, business, 0);
    public static final ActualBuildingType small_8 = createBuildingType(createUnanimated("business/small_8"), 1, business, 0);
    
    static void init()
    {
        BuildingTypes.init();
    }
}
