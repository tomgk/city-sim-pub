package org.exolin.citysim.model.connection.regular;

import java.util.List;
import java.util.Objects;
import org.exolin.citysim.model.Animation;
import org.exolin.citysim.model.EmptyStructureParameters;
import org.exolin.citysim.model.StructureSize;
import org.exolin.citysim.model.connection.ConnectionType;

/**
 *
 * @author Thomas
 */
public class SelfConnectionType extends ConnectionType<SelfConnection, SelfConnectionType, ConnectionVariant, EmptyStructureParameters>
{
    private final int cost;
    private final ConnectionVariant defaultImageVariant;
    
    public SelfConnectionType(String name, List<Animation> images, StructureSize size, int cost, ConnectionVariant defaultImageVariant)
    {
        super(name, images, size);
        this.cost = cost;
        
        if(images.size() != ConnectionVariants.VALUES.size())
            throw new IllegalArgumentException("incorrect image count: expected "+images.size()+" but expected "+ConnectionVariants.VALUES.size());
        
        this.defaultImageVariant = Objects.requireNonNull(defaultImageVariant);
    }

    @Override
    public int getBuildingCost(ConnectionVariant variant)
    {
        return getBuildingCost();
    }

    /**
     * @return how much it costs to build one section
     */
    public int getBuildingCost()
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
        return defaultImageVariant;
    }

    @Override
    public SelfConnection createBuilding(int x, int y, ConnectionVariant variant, EmptyStructureParameters data)
    {
        return new SelfConnection(this, x, y, variant, data);
    }
}
