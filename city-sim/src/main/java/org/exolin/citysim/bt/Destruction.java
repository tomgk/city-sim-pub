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
    
    private static boolean spreadFire()
    {
        double r = Math.random();
        System.out.println(r);
        boolean spread = r < 0.1;
        if(spread)
            System.out.println("SPREAD FIRE");
        return spread;
    }
    
    private static void updateFire(World w, Building b)
    {
        int x = b.getX();
        int y = b.getY();
        
        maybeAddFire(w, x-1, y);
        maybeAddFire(w, x+1, y);
        maybeAddFire(w, x, y-1);
        maybeAddFire(w, x, y+1);
    }
    
    private static void maybeAddFire(World w, int x, int y)
    {
        int size = w.getGridSize();
        if(x < 0 || x >= size || y < 0 || y <= size)
            return;
        
        if(!spreadFire())
            return;
        
        w.addBuilding(fire, x, y);
    }
}
