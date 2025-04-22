package org.exolin.citysim.bt.destruction;

import java.math.BigDecimal;
import static org.exolin.citysim.bt.Buildings.createBuildingType;
import org.exolin.citysim.bt.Zones;
import static org.exolin.citysim.model.Animation.createAnimation;
import org.exolin.citysim.model.building.BuildingType;

/**
 *
 * @author Thomas
 */
public class Destruction
{
    public static final BuildingType fire = createBuildingType(
            createAnimation("destruction/fire", 4, 500),
            1, Zones.destroyed, 0, BigDecimal.ZERO, Fire::updateFire, null);
}
