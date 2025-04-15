package org.exolin.citysim.model.street.regular;

import java.util.List;
import org.exolin.citysim.model.Animation;
import org.exolin.citysim.model.street.AnyStreetType;

/**
 *
 * @author Thomas
 */
public class StreetType extends AnyStreetType<Street, StreetType, StreetVariant>
{
    private final int cost;
    
    public StreetType(String name, List<Animation> images, int size, int cost)
    {
        super(name, images, size);
        this.cost = cost;
        
        if(images.size() != StreetVariants.VALUES.size())
            throw new IllegalArgumentException("incorrect image count: expected "+images.size()+" but expected "+StreetVariants.VALUES.size());
    }

    public int getCost()
    {
        return cost;
    }

    @Override
    public StreetType getXType()
    {
        return this;
    }

    @Override
    public StreetType getYType()
    {
        return this;
    }

    @Override
    public StreetVariant getDefaultVariant()
    {
        return ConnectVariant.CONNECT_X;
    }

    @Override
    public Street createBuilding(int x, int y, StreetVariant variant)
    {
        return new Street(this, x, y, variant);
    }
}
