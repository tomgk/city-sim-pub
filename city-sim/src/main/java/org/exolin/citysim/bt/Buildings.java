package org.exolin.citysim.bt;

import java.math.BigDecimal;
import org.exolin.citysim.model.Animation;
import org.exolin.citysim.model.building.BuildingType;
import org.exolin.citysim.model.zone.ZoneType;

/**
 *
 * @author Thomas
 */
public class Buildings
{
    public static BuildingType createBuildingType(Animation animation, int size, ZoneType zoneType, int cost)
    {
        return createBuildingType(animation, size, zoneType, cost, BigDecimal.ZERO);
    }
    
    public static <T> BuildingType createBuildingType(Animation animation, int size, ZoneType zoneType, int cost, BigDecimal maintenance)
    {
        return new BuildingType(animation.getName(), animation, size, zoneType, cost, maintenance);
    }

    static void init()
    {
        BuildingTypes.init();
    }
}
