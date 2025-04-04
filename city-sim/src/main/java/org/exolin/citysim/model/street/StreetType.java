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
    public StreetType(String name, List<Animation> images, int size)
    {
        super(name, images, size);
        
        if(images.size() != StreetVariant.VALUES.size())
            throw new IllegalArgumentException("incorrect image count");
    }

    @Override
    public Class<StreetVariant> getVariantClass()
    {
        return StreetVariant.class;
    }
    
    @Override
    public Street createBuilding(int x, int y, StreetVariant variant)
    {
        return new Street(this, x, y, variant);
    }
}
