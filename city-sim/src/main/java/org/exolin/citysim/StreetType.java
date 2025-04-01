package org.exolin.citysim;

import java.util.List;

/**
 *
 * @author Thomas
 */
public class StreetType extends BuildingType<Street, StreetType.Variant>
{
    public enum Variant
    {
        CONNECT_X,
        CONNECT_Y,
        X_INTERSECTION,
        CURVE_1,
        CURVE_2,
        CURVE_3,
        CURVE_4,
        T_INTERSECTION_1,
        T_INTERSECTION_2,
        T_INTERSECTION_3,
        T_INTERSECTION_4,
    }
    
    public StreetType(String name, List<Animation> images, int size)
    {
        super(name, images, size);
        
        if(images.size() != Variant.values().length)
            throw new IllegalArgumentException("incorrect image count");
    }

    @Override
    public Class<Variant> getVariantClass()
    {
        return Variant.class;
    }
    
    @Override
    public Street createBuilding(int x, int y, StreetType.Variant variant)
    {
        return new Street(this, x, y, variant);
    }
}
