package org.exolin.citysim.model.tree;

import java.math.BigDecimal;
import org.exolin.citysim.bt.Trees;
import org.exolin.citysim.model.PlainStructureParameters;
import org.exolin.citysim.model.Structure;
import org.exolin.citysim.model.World;
import org.exolin.citysim.utils.Utils;

/**
 *
 * @author Thomas
 */
public class Tree extends Structure<Tree, TreeType, TreeType.Variant, PlainStructureParameters>
{
    public Tree(TreeType type, int x, int y, TreeType.Variant variant)
    {
        this(type, x, y, variant, new PlainStructureParameters());
    }
    
    public Tree(TreeType type, int x, int y, TreeType.Variant variant, PlainStructureParameters data)
    {
        super(type, x, y, variant, data);
    }
    
    public int getCount()
    {
        return getType().getCount();
    }

    @Override
    public BigDecimal getMaintenance()
    {
        return BigDecimal.ZERO;
    }

    @Override
    public BigDecimal getTaxRevenue()
    {
        return BigDecimal.ZERO;
    }
    
    private static final double PROBABILITY_GROWTH = 0.001;
    private static final double PROBABILITY_SPREAD = 0.0001;

    @Override
    protected void updateAfterTick(World world, int ticks)
    {
        if(Math.random() < Utils.getProbabilityForTicks(PROBABILITY_GROWTH, ticks))
        {
            if(false)
                world.removeBuildingAt(this);
            //world.addBuilding(getType().plusOne(), getX(), getY(), getVariant());
        }
        
        maybeGrow(world, getX()-1, getY(), ticks);
        maybeGrow(world, getX()+1, getY(), ticks);
        
        maybeGrow(world, getX(), getY()-1, ticks);
        maybeGrow(world, getX(), getY()+1, ticks);
    }

    private void maybeGrow(World world, int x, int y, int ticks)
    {
        if(x < 0 || y < 0)
            return;
        if(x >= world.getGridSize())
            return;
        if(y >= world.getGridSize())
            return;
        
        if(world.getBuildingAt(x, y) != null)
            return;
        
        double d = Math.random();
        double p = Utils.getProbabilityForTicks(PROBABILITY_SPREAD, ticks);
        if(d < p)
        {
            world.addBuilding(Trees.TREES.getFirst(), x, y);
        }
    }
}
