package org.exolin.citysim.model.connection.cross;

import java.util.Objects;
import org.exolin.citysim.model.Animation;
import org.exolin.citysim.model.EmptyStructureParameters;
import org.exolin.citysim.model.SingleVariant;
import org.exolin.citysim.model.StructureSize;
import org.exolin.citysim.model.StructureVariant;
import org.exolin.citysim.model.connection.ConnectionType;
import org.exolin.citysim.model.connection.regular.SelfConnectionType;

/**
 *
 * @author Thomas
 */
public class CrossConnectionType extends ConnectionType<CrossConnection, CrossConnectionType, SingleVariant, EmptyStructureParameters>
{
    private final SelfConnectionType xtype;
    private final SelfConnectionType ytype;

    public CrossConnectionType(String name, Animation animation, StructureSize size, SelfConnectionType type1, SelfConnectionType type2)
    {
        super(name, animation, size);
        this.xtype = Objects.requireNonNull(type1);
        this.ytype = Objects.requireNonNull(type2);
    }

    @Override
    public int getBuildingCost(SingleVariant variant)
    {
        return NO_COST;
    }

    @Override
    public CrossConnection createBuilding(int x, int y, SingleVariant variant, EmptyStructureParameters data)
    {
        return new CrossConnection(this, x, y, variant, data);
    }

    @Override
    public SelfConnectionType getXType()
    {
        return xtype;
    }

    @Override
    public SelfConnectionType getYType()
    {
        return ytype;
    }
    
    @Override
    public SingleVariant getVariantForDefaultImage()
    {
        return SingleVariant.DEFAULT;
    }
}
