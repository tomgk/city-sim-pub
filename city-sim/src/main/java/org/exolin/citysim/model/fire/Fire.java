package org.exolin.citysim.model.fire;

import java.math.BigDecimal;
import java.util.function.IntSupplier;
import org.exolin.citysim.model.Structure;
import org.exolin.citysim.model.World;
import org.exolin.citysim.model.building.Building;
import org.exolin.citysim.model.connection.Connection;
import org.exolin.citysim.model.tree.Tree;
import org.exolin.citysim.model.zone.Zone;
import org.exolin.citysim.ui.ErrorDisplay;
import org.exolin.citysim.utils.Utils;

/**
 *
 * @author Thomas
 */
public class Fire extends Structure<Fire, FireType, FireType.Variant, FireParameters>
{
    /*
    public Fire(FireType type, int x, int y, FireType.Variant variant)
    {
        this(type, x, y, variant, new FireParameters());
    }*/
    
    public Fire(FireType type, int x, int y, FireType.Variant variant, FireParameters data)
    {
        super(type, x, y, variant, data);
    }

    @Override
    public BigDecimal getTaxRevenue()
    {
        return BigDecimal.ZERO;
    }

    @Override
    public BigDecimal getMaintenance()
    {
        return BigDecimal.ZERO;
    }

    private static final double BURN_TREE_PROBABILITY = 0.0002;
    private static final double BURN_PROBABILITY = 0.0001;
    private static final double BURN_EMPTY_PROBABILITY = 0.000005;
    private static final double STOP_PROBABILITY = 0.00001;
    
    private static double getSpreadProbability(Structure s)
    {
        if(s == null)
            return BURN_EMPTY_PROBABILITY;
        else if(s instanceof Tree t)
            return Utils.getProbabilityForTicks(BURN_TREE_PROBABILITY, t.getCount());
        else
            return BURN_PROBABILITY;
    }
    
    private static boolean spreadFire(double speed, int ticks, Structure s)
    {
        double probability = Utils.getProbabilityForTicks(speed * getSpreadProbability(s), ticks);
        
        double r = Math.random();
        boolean spread = r < probability;
        return spread;
    }
    
    private static final double NORMAL = 1;
    private static final double DIAGONAL = 1/3.;
    private static final double JUMP = 0.125;
    
    @Override
    protected void updateAfterTick(World w, int ticks)
    {
        if(ticks <= 0)
            throw new IllegalArgumentException();
        
        if(data.remainingLife == -1)
        {
            w.removeBuildingAt(this);
            throw new IllegalStateException("undefined life");
        }
        
        //System.out.println("Reducde "+remainingLife+" by "+ticks);
        data.remainingLife -= ticks;
        if(data.remainingLife <= 0)
        {
            //System.out.println("death");
            w.removeBuildingAt(this);
            return;
        }
        
        if(Math.random() < STOP_PROBABILITY)
        {
            w.removeBuildingAt(this);
            return;
        }
        
        int x = getX();
        int y = getY();
        
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
        int gridSize = w.getGridSize();
        if(x < 0 || x >= gridSize || y < 0 || y >= gridSize)
            return true;
        
        //don't put street/rail/water on fire
        Structure s = w.getBuildingAt(x, y);
        if(s instanceof Connection)
            return false;
        else if(s instanceof org.exolin.citysim.model.fire.Fire)
            //count as spread
            return true;
        
        if(!spreadFire(speed, ticks, s))
            return true;
        
        if(s == null)
            placeFire(w, x, y);
        else
            replaceWithFire(w, s);
        
        return true;
    }

    private static int getExpectedLife(Structure s)
    {
        IntSupplier el = () -> {
            if(s == null || s instanceof Zone || s instanceof Connection)
                return 5;
            else if(s instanceof Building)
                return 15;
            else if(s instanceof Tree)
                return 10;
            else
            {
                ErrorDisplay.show(null, new UnsupportedOperationException("Unsupported: "+s.getClass().getName()));
                return 0;
            }
        };
        
        int e = el.getAsInt() * 1000;
        //System.out.println("Start with "+e);
        return e;
    }
    
    public static void placeFire(World w, int x, int y)
    {
        w.addBuilding(FireType.fire, x, y, FireType.Variant.random(), new FireParameters(getExpectedLife(null)));
    }
    
    public static void replaceWithFire(World w, Structure s)
    {
        int x = s.getX();
        int y = s.getY();
        int buildingSize = s.getSize();
        for(int yi=0;yi<buildingSize;++yi)
        {
            for(int xi=0;xi<buildingSize;++xi)
                w.addBuilding(FireType.fire, x+xi, y+yi, FireType.Variant.random(), new FireParameters(getExpectedLife(s)));
        }
    }
}
