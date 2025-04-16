package org.exolin.citysim.bt;

import org.exolin.citysim.model.Animation;
import org.exolin.citysim.model.ab.ActualBuildingType;
import org.exolin.citysim.model.zone.ZoneType;

/**
 *
 * @author Thomas
 */
public class Buildings
{
    static ActualBuildingType createBuildingType(Animation animation, int size, ZoneType zoneType, int cost)
    {
        return new ActualBuildingType(animation.getName(), animation, size, zoneType, cost);
    }

    static void init()
    {
        BuildingTypes.init();
    }
}
