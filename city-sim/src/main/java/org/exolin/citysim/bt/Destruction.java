package org.exolin.citysim.bt;

import java.math.BigDecimal;
import static org.exolin.citysim.bt.Buildings.createBuildingType;
import static org.exolin.citysim.model.Animation.createAnimation;
import org.exolin.citysim.model.World;
import org.exolin.citysim.model.building.Building;
import org.exolin.citysim.model.building.BuildingType;

/**
 *
 * @author Thomas
 */
public class Destruction
{
    public static final BuildingType fire = createBuildingType(
            createAnimation("destruction/fire", 4, 500),
            1, Zones.destroyed, 0, BigDecimal.ZERO, Destruction::updateFire);
    
    private static void updateFire(World w, Building b)
    {
        //TODO
    }
}
