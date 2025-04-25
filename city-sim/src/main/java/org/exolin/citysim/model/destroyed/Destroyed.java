package org.exolin.citysim.model.destroyed;

import java.math.BigDecimal;
import org.exolin.citysim.model.PlainStructureParameters;
import org.exolin.citysim.model.Structure;

/**
 *
 * @author Thomas
 */
public class Destroyed extends Structure<Destroyed, DestroyedType, DestroyedType.Variant, PlainStructureParameters>
{
    public Destroyed(DestroyedType type, int x, int y, DestroyedType.Variant variant, PlainStructureParameters data)
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
}
