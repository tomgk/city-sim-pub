package org.exolin.citysim.bt;

import static org.exolin.citysim.bt.Buildings.createBuildingType;
import static org.exolin.citysim.bt.Zones.zone_business;
import org.exolin.citysim.model.ActualBuildingType;
import org.exolin.citysim.model.Animation;

/**
 *
 * @author Thomas
 */
public class BusinessBuildings
{
    public static final ActualBuildingType office = createBuildingType("business/office", 4, zone_business, 0);
    public static final ActualBuildingType office2 = createBuildingType("business/office_2", 3, zone_business, 0);
    public static final ActualBuildingType office3 = createBuildingType("business/office_3", 3, zone_business, 0);
    public static final ActualBuildingType car_cinema = createBuildingType(Animation.createAnimation("business/car-cinema", 9), 4, zone_business, 0);
    public static final ActualBuildingType cinema = createBuildingType("business/cinema", 3, zone_business, 0);
    public static final ActualBuildingType parkbuilding = createBuildingType("business/parkbuilding", 3, zone_business, 0);
    
    public static final ActualBuildingType business_small_1 = createBuildingType("business/business_small_1", 1, zone_business, 0);
    public static final ActualBuildingType business_small_2 = createBuildingType("business/business_small_2", 1, zone_business, 0);
    public static final ActualBuildingType business_small_3 = createBuildingType("business/business_small_3", 1, zone_business, 0);
    public static final ActualBuildingType business_small_4 = createBuildingType("business/business_small_4", 1, zone_business, 0);
    public static final ActualBuildingType business_small_5 = createBuildingType("business/business_small_5", 1, zone_business, 0);
    public static final ActualBuildingType business_small_6 = createBuildingType("business/business_small_6", 1, zone_business, 0);
    public static final ActualBuildingType business_small_7 = createBuildingType("business/business_small_7", 1, zone_business, 0);
    public static final ActualBuildingType business_small_8 = createBuildingType("business/business_small_8", 1, zone_business, 0);
    
    static void init()
    {
        BuildingTypes.init();
    }
}
