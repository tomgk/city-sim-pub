package org.exolin.citysim.model.building;

import org.exolin.citysim.model.World;

/**
 *
 * @author Thomas
 */
public interface UpdateAfterTick
{
    void update(World world, Building b);
    public static final UpdateAfterTick NOTHING = new NothingUpdateAfterTick();    
}

class NothingUpdateAfterTick implements UpdateAfterTick
{
    @Override
    public void update(World t, Building u)
    {
    }
}