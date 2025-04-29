package org.exolin.citysim.model.tree;

import java.math.BigDecimal;
import java.util.Optional;
import org.exolin.citysim.bt.Trees;
import org.exolin.citysim.model.EmptyStructureParameters;
import org.exolin.citysim.model.Structure;
import org.exolin.citysim.model.World;
import org.exolin.citysim.utils.RandomUtils;

/**
 *
 * @author Thomas
 */
public class Tree extends Structure<Tree, TreeType, TreeVariant, EmptyStructureParameters>
{
    public Tree(TreeType type, int x, int y, TreeVariant variant)
    {
        this(type, x, y, variant, EmptyStructureParameters.getInstance());
    }
    
    public Tree(TreeType type, int x, int y, TreeVariant variant, EmptyStructureParameters data)
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
    
    private static final double PROBABILITY_GROWTH = 0.00005;
    private static final double PROBABILITY_SPREAD = 0.0001;

    @Override
    protected void updateAfterTick(World world, int ticks)
    {
        if(!getType().isAlive())
            return;
        
        if(RandomUtils.atLeast(RandomUtils.getProbabilityForTicks(PROBABILITY_GROWTH, ticks)))
        {
            Optional<TreeType> plusOne = getType().plusOne();
            if(plusOne.isPresent())
            {
                world.removeBuildingAt(this);
                world.addBuilding(plusOne.get(), getX(), getY(), getVariant());
            }
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
        
        double p = RandomUtils.getProbabilityForTicks(PROBABILITY_SPREAD, ticks);
        if(RandomUtils.atLeast(p))
            world.addBuilding(Trees.TREES.getFirst(), x, y);
    }

    public boolean isAlive()
    {
        return getType().isAlive();
    }
}
