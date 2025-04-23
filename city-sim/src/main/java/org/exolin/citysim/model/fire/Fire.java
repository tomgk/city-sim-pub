package org.exolin.citysim.model.fire;

import java.math.BigDecimal;
import org.exolin.citysim.model.Structure;
import org.exolin.citysim.model.World;

/**
 *
 * @author Thomas
 */
public class Fire extends Structure<Fire, FireType, FireType.Variant>
{
    public Fire(FireType type, int x, int y, FireType.Variant variant)
    {
        super(type, x, y, variant);
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

    @Override
    protected void updateAfterTick(World world, int ticks)
    {
        org.exolin.citysim.bt.destruction.Fire.updateFire(world, this, ticks, null);
    }
}
