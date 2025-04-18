package org.exolin.citysim.bt;

import org.exolin.citysim.model.Animation;
import org.exolin.citysim.model.ab.BuildingType;
import org.exolin.citysim.model.zone.ZoneType;

/**
 *
 * @author Thomas
 */
public class Buildings
{
    static BuildingType createBuildingType(Animation animation, int size, ZoneType zoneType, int cost)
    {
        return new BuildingType(animation.getName(), animation, size, zoneType, cost);
    }

    static void init()
    {
        BuildingTypes.init();
    }
}
