package org.exolin.citysim.bt;

import static org.exolin.citysim.bt.Buildings.createBuildingType;
import org.exolin.citysim.model.ActualBuildingType;

/**
 *
 * @author Thomas
 */
public class ResidentialBuildings
{
    public static final ActualBuildingType small_house_1 = createBuildingType("small_house_1", 1, Zones.zone_residential);
    
    public static final ActualBuildingType small_houses_1 = createBuildingType("small_houses_1", 1, Zones.zone_residential);
    public static final ActualBuildingType small_houses_2 = createBuildingType("small_houses_2", 1, Zones.zone_residential);
    public static final ActualBuildingType small_houses_3 = createBuildingType("small_houses_3", 1, Zones.zone_residential);
    public static final ActualBuildingType small_houses_4 = createBuildingType("small_houses_4", 1, Zones.zone_residential);
    public static final ActualBuildingType small_houses_5 = createBuildingType("small_houses_5", 1, Zones.zone_residential);
    public static final ActualBuildingType small_houses_6 = createBuildingType("small_houses_6", 1, Zones.zone_residential);
    public static final ActualBuildingType small_houses_7 = createBuildingType("small_houses_7", 1, Zones.zone_residential);
    public static final ActualBuildingType small_houses_8 = createBuildingType("small_houses_8", 1, Zones.zone_residential);
    public static final ActualBuildingType small_houses_9 = createBuildingType("small_houses_9", 1, Zones.zone_residential);
    
    public static final ActualBuildingType house_1 = createBuildingType("house_1", 3, Zones.zone_residential);
    public static final ActualBuildingType house_2 = createBuildingType("house_2", 3, Zones.zone_residential);
    public static final ActualBuildingType house_3 = createBuildingType("house_3", 4, Zones.zone_residential);
    public static final ActualBuildingType house_4 = createBuildingType("house_4", 4, Zones.zone_residential);
    
    public static void init()
    {
        
    }
}
