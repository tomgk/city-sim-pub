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
    public static final ActualBuildingType house_1 = createBuildingType("house_1", 3, Zones.zone_residential);
    public static final ActualBuildingType house_2 = createBuildingType("house_2", 3, Zones.zone_residential);
    public static final ActualBuildingType house_3 = createBuildingType("house_3", 4, Zones.zone_residential);
    public static final ActualBuildingType house_4 = createBuildingType("house_4", 4, Zones.zone_residential);
    
    public static void init()
    {
        
    }
}
