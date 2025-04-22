package org.exolin.citysim.bt;

import java.math.BigDecimal;
import static org.exolin.citysim.bt.Buildings.createBuildingType;
import static org.exolin.citysim.model.Animation.createAnimation;
import org.exolin.citysim.model.Structure;
import org.exolin.citysim.model.World;
import org.exolin.citysim.model.building.Building;
import org.exolin.citysim.model.building.BuildingType;
import org.exolin.citysim.model.connection.Connection;
import org.exolin.citysim.utils.Utils;

/**
 *
 * @author Thomas
 */
public class Destruction
{
    public static final BuildingType fire = createBuildingType(
            createAnimation("destruction/fire", 4, 500),
            1, Zones.destroyed, 0, BigDecimal.ZERO, Destruction::updateFire);
    
    public static final double SPREAD_PROBABILITY = 0.0001;
    public static final double STOP_PROBABILITY = 0.00001;
    
    private static boolean spreadFire(double speed, int ticks)
    {
        double probability = Utils.getProbabilityForTicks(speed * SPREAD_PROBABILITY, ticks);
        
        double r = Math.random();
        boolean spread = r < probability;
        return spread;
    }
    
    private static final double NORMAL = 1;
    private static final double DIAGONAL = 1/3.;
    private static final double JUMP = 0.125;
    
    private static void updateFire(World w, Building b, int ticks)
    {
        if(ticks <= 0)
            throw new IllegalArgumentException();
        
        if(Math.random() < STOP_PROBABILITY)
        {
            w.removeBuildingAt(b);
            return;
        }
        
        int x = b.getX();
        int y = b.getY();
        
        boolean leftCanBurn = maybeAddFire(w, x-1, y, NORMAL, ticks);
        boolean rightCanBurn = maybeAddFire(w, x+1, y, NORMAL, ticks);
        boolean downCanBurn = maybeAddFire(w, x, y-1, NORMAL, ticks);
        boolean upCanBurn = maybeAddFire(w, x, y+1, NORMAL, ticks);
        
        maybeAddFire(w, x-1, y-1, DIAGONAL, ticks);
        maybeAddFire(w, x-1, y+1, DIAGONAL, ticks);
        maybeAddFire(w, x+1, y-1, DIAGONAL, ticks);
        maybeAddFire(w, x+1, y+1, DIAGONAL, ticks);
        
        //only jump if there is something in the way
        if(!leftCanBurn)
            maybeAddFire(w, x-2, y, JUMP, ticks);
        if(!rightCanBurn)
            maybeAddFire(w, x+2, y, JUMP, ticks);
        if(!downCanBurn)
            maybeAddFire(w, x, y-2, JUMP, ticks);
        if(!upCanBurn)
            maybeAddFire(w, x, y+2, JUMP, ticks);
    }
    
    /**
     * 
     * @param w
     * @param x
     * @param y
     * @param speed
     * @return {@true} if fire can spread here, regardless if it did now
     */
    private static boolean maybeAddFire(World w, int x, int y, double speed, int ticks)
    {
        int size = w.getGridSize();
        if(x < 0 || x >= size || y < 0 || y >= size)
            return true;
        
        //don't put street/rail/water on fire
        Structure b = w.getBuildingAt(x, y);
        if(b instanceof Connection)
            return false;
        
        if(!spreadFire(speed, ticks))
            return true;
        
        w.addBuilding(fire, x, y);
        return true;
    }
}
