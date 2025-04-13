package org.exolin.citysim.model.street.cross;

import java.math.BigDecimal;
import org.exolin.citysim.model.street.AnyStreet;

/**
 *
 * @author Thomas
 */
public class CrossConnection extends AnyStreet<CrossConnection, CrossConnectionType, CrossConnectionType.Variant>
{
    public CrossConnection(CrossConnectionType type, int x, int y, CrossConnectionType.Variant variant)
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
        CrossConnectionType type = getType();
        //TODO: maybe cost more than just the average
        return BigDecimal.valueOf((type.getType1().getCost()+type.getType2().getCost())/2);
    }
}
