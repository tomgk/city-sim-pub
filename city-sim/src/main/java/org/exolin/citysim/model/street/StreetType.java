package org.exolin.citysim.model.street;

import java.util.List;
import org.exolin.citysim.model.Animation;
import org.exolin.citysim.model.BuildingType;

/**
 *
 * @author Thomas
 */
public class StreetType extends BuildingType<Street, StreetVariant>
{
    private final int cost;
    
    public StreetType(String name, List<Animation> images, int size, int cost)
    {
        super(name, images, size);
        this.cost = cost;
        
        if(images.size() != StreetVariant.VALUES.size())
            throw new IllegalArgumentException("incorrect image count");
    }

    public int getCost()
    {
        return cost;
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
