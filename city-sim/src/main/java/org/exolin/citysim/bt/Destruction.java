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
    
    private static boolean spreadFire(boolean high)
    {
        double r = Math.random();
        double probability = high ? 0.0001 : 0.0001/3;
        boolean spread = r < probability;
        return spread;
    }
    
    private static void updateFire(World w, Building b)
    {
        int x = b.getX();
        int y = b.getY();
        
        maybeAddFire(w, x-1, y, true);
        maybeAddFire(w, x+1, y, true);
        maybeAddFire(w, x, y-1, true);
        maybeAddFire(w, x, y+1, true);
        
        maybeAddFire(w, x-1, y-1, false);
        maybeAddFire(w, x-1, y+1, false);
        maybeAddFire(w, x+1, y-1, false);
        maybeAddFire(w, x+1, y+1, false);
    }
    
    private static void maybeAddFire(World w, int x, int y, boolean high)
    {
        int size = w.getGridSize();
        if(x < 0 || x >= size || y < 0 || y >= size)
            return;
        
        if(!spreadFire(high))
            return;
        
        w.addBuilding(fire, x, y);
    }
}
