package org.exolin.citysim.model.building;

import org.exolin.citysim.model.World;

/**
 *
 * @author Thomas
 */
public interface UpdateAfterTick<T>
{
    void update(World world, Building b, int ticks, T data);
    public static final UpdateAfterTick NOTHING = new NothingUpdateAfterTick();
    public static final Object NOTHING_DATA = null;
}

class NothingUpdateAfterTick implements UpdateAfterTick
{
    @Override
    public void update(World t, Building u, int ticks, Object data)
    {
    }
}