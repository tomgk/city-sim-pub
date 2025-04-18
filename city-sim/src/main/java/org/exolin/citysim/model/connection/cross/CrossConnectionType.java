package org.exolin.citysim.model.connection.cross;

import java.util.Objects;
import org.exolin.citysim.model.Animation;
import org.exolin.citysim.model.connection.ConnectionType;
import org.exolin.citysim.model.connection.regular.SelfConnectionType;
import org.exolin.citysim.model.StructureVariant;

/**
 *
 * @author Thomas
 */
public class CrossConnectionType extends ConnectionType<CrossConnection, CrossConnectionType, CrossConnectionType.Variant>
{
    public enum Variant implements StructureVariant
    {
        DEFAULT
    }
    
    private final SelfConnectionType xtype;
    private final SelfConnectionType ytype;

    public CrossConnectionType(String name, Animation animation, int size, SelfConnectionType type1, SelfConnectionType type2)
    {
        super(name, animation, size);
        this.xtype = Objects.requireNonNull(type1);
        this.ytype = Objects.requireNonNull(type2);
    }

    @Override
    public CrossConnection createBuilding(int x, int y, Variant variant)
    {
        return new CrossConnection(this, x, y, variant);
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
    public Variant getDefaultVariant()
    {
        return Variant.DEFAULT;
    }
}
