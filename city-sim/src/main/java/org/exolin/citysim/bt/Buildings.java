package org.exolin.citysim.bt;

import org.exolin.citysim.ActualBuildingType;
import org.exolin.citysim.ZoneType;
import static org.exolin.citysim.ui.Utils.loadImage;

/**
 *
 * @author Thomas
 */
public class Buildings
{
    public static final ActualBuildingType plant_solar = createBuildingType("plant_solar", 4, null);
    
    static ActualBuildingType createBuildingType(String name, int size, ZoneType zoneType)
    {
        return new ActualBuildingType(name, loadImage(name), size, zoneType);
    }

    static void init()
    {
        BuildingTypes.init();
    }
}
