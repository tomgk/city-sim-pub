package org.exolin.citysim.model.connection.cross;

import java.math.BigDecimal;
import org.exolin.citysim.model.PlainStructureData;
import org.exolin.citysim.model.connection.Connection;

/**
 *
 * @author Thomas
 */
public class CrossConnection extends Connection<CrossConnection, CrossConnectionType, CrossConnectionType.Variant, PlainStructureData>
{
    public CrossConnection(CrossConnectionType type, int x, int y, CrossConnectionType.Variant variant, PlainStructureData data)
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
        CrossConnectionType type = getType();
        //TODO: maybe cost more than just the average
        return BigDecimal.valueOf((type.getXType().getCost()+type.getYType().getCost())/2);
    }
}
