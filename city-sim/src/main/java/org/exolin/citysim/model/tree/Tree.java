package org.exolin.citysim.model.tree;

import java.math.BigDecimal;
import java.util.Optional;
import org.exolin.citysim.bt.Trees;
import org.exolin.citysim.model.Structure;
import org.exolin.citysim.model.World;
import org.exolin.citysim.model.sim.RemoveMode;
import org.exolin.citysim.model.zone.Zone;
import org.exolin.citysim.model.zone.ZoneType;
import static org.exolin.citysim.ui.actions.ZonePlacement.DEBUG_TREEZONE;
import org.exolin.citysim.utils.RandomUtils;

/**
 *
 * @author Thomas
 */
public class Tree extends Structure<Tree, TreeType, TreeVariant, TreeParameters>
{
    public Tree(TreeType type, int x, int y, TreeVariant variant, TreeParameters data)
    {
        super(type, x, y, variant, data);
        if(DEBUG_TREEZONE) System.out.println("New tree "+System.identityHashCode(this)+" with zone "+data.getZone());
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
                world.addBuilding(plusOne.get(), getX(), getY(), getVariant(), getDataRaw());
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
        
        Structure<?, ?, ?, ?> b = world.getBuildingAt(x, y);
        Optional<ZoneType> zoneType = Optional.empty();
        if(b instanceof Zone z)
            zoneType = Optional.of(z.getType());
        else if(b instanceof Tree t)
            zoneType = t.getTheZoneType();
        else if(b != null)
        {
            //TODO: check what this code is for when Tree has already been checked before
            if(b instanceof Tree t)
            {
                if(!t.isAlive())
                {
                   if(RandomUtils.atLeast(RandomUtils.getProbabilityForTicks(PROBABILITY_GROWTH, ticks)))
                   {
                       world.removeBuildingAt(x, y, RemoveMode.CLEAR);
                       world.addBuilding(Trees.get(getType().isGrass()).getFirst(), x, y, getVariant(), t.getDataCopy());
                   }
                }
            }
            
            return;
        }
        
        double p = RandomUtils.getProbabilityForTicks(PROBABILITY_SPREAD, ticks);
        if(RandomUtils.atLeast(p))
            world.addBuilding(Trees.get(getType().isGrass()).getFirst(), x, y, TreeVariant.random(), new TreeParameters(zoneType));
    }

    public boolean isAlive()
    {
        return getType().isAlive();
    }

    @Override
    public Optional<ZoneType> getZoneType(boolean includeEmptyZone)
    {
        return getDataRaw().getZone();
    }
    
    public void setZone(Optional<ZoneType> zone)
    {
        if(DEBUG_TREEZONE) System.out.println("Tree "+System.identityHashCode(this)+"="+zone+", tree="+toString());
        getDataRaw().setZone(zone);
        
        if(zone.isPresent() != getDataRaw().getZone().isPresent())
            throw new IllegalStateException("expected "+zone+" but got "+getDataRaw().getZone());
        else
            System.out.println(toString());
        
        if(DEBUG_TREEZONE) System.out.println("Tree "+System.identityHashCode(this)+" after setZone: "+toString());
    }
    
    @Override
    public boolean drawZone()
    {
        return true;
    }
}
