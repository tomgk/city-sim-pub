package org.exolin.citysim.model.connection.regular;

import java.util.List;
import org.exolin.citysim.model.Animation;
import org.exolin.citysim.model.PlainStructureParameters;
import org.exolin.citysim.model.connection.ConnectionType;

/**
 *
 * @author Thomas
 */
public class SelfConnectionType extends ConnectionType<SelfConnection, SelfConnectionType, ConnectionVariant, PlainStructureParameters>
{
    private final int cost;
    
    public SelfConnectionType(String name, List<Animation> images, int size, int cost)
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
    public SelfConnectionType getXType()
    {
        return this;
    }

    @Override
    public SelfConnectionType getYType()
    {
        return this;
    }

    @Override
    public ConnectionVariant getVariantForDefaultImage()
    {
        return ConnectVariant.CONNECT_X;
    }

    @Override
    public SelfConnection createBuilding(int x, int y, ConnectionVariant variant, PlainStructureParameters data)
    {
        return new SelfConnection(this, x, y, variant, data);
    }
}
