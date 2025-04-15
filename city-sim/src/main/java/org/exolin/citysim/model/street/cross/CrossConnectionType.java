package org.exolin.citysim.model.street.cross;

import java.util.Objects;
import org.exolin.citysim.model.Animation;
import org.exolin.citysim.model.BuildingVariant;
import org.exolin.citysim.model.street.AnyStreetType;
import org.exolin.citysim.model.street.StreetType;

/**
 *
 * @author Thomas
 */
public class CrossConnectionType extends AnyStreetType<CrossConnection, CrossConnectionType, CrossConnectionType.Variant>
{
    public enum Variant implements BuildingVariant
    {
        DEFAULT
    }
    
    private final StreetType xtype;
    private final StreetType ytype;

    public CrossConnectionType(String name, Animation animation, int size, StreetType type1, StreetType type2)
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
    public StreetType getXType()
    {
        return xtype;
    }

    @Override
    public StreetType getYType()
    {
        return ytype;
    }
    
    @Override
    public Variant getDefaultVariant()
    {
        return Variant.DEFAULT;
    }
}
