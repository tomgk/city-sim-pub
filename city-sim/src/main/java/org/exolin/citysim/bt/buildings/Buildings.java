package org.exolin.citysim.bt.buildings;

import java.math.BigDecimal;
import org.exolin.citysim.bt.StructureTypes;
import org.exolin.citysim.model.Animation;
import org.exolin.citysim.model.building.BuildingType;
import org.exolin.citysim.model.zone.ZoneType;

/**
 *
 * @author Thomas
 */
public class Buildings
{
    public static BuildingType createZoneBuildingType(Animation animation, int size, ZoneType zoneType)
    {
        return createBuildingType(animation, size, zoneType, 0);
    }
    
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
        StructureTypes.init();
    }
}
