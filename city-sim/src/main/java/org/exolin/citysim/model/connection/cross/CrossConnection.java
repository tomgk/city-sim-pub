package org.exolin.citysim.model.connection.cross;

import java.math.BigDecimal;
import org.exolin.citysim.model.EmptyStructureParameters;
import org.exolin.citysim.model.SingleVariant;
import org.exolin.citysim.model.connection.Connection;

/**
 *
 * @author Thomas
 */
public class CrossConnection extends Connection<CrossConnection, CrossConnectionType, SingleVariant, EmptyStructureParameters>
{
    public CrossConnection(CrossConnectionType type, int x, int y)
    {
        this(type, x, y, SingleVariant.DEFAULT, EmptyStructureParameters.getInstance());
    }
    
    public CrossConnection(CrossConnectionType type, int x, int y, SingleVariant variant, EmptyStructureParameters data)
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
        return BigDecimal.valueOf((type.getXType().getBuildingCost()+type.getYType().getBuildingCost())/2);
    }
}
