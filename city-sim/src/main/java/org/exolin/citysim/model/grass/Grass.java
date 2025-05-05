package org.exolin.citysim.model.grass;

import java.math.BigDecimal;
import org.exolin.citysim.model.EmptyStructureParameters;
import org.exolin.citysim.model.Structure;

/**
 *
 * @author Thomas
 */
public class Grass extends Structure<Grass, GrassType, GrassType.Variant, EmptyStructureParameters>
{
    public Grass(GrassType type, int x, int y, GrassType.Variant variant, EmptyStructureParameters data)
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
