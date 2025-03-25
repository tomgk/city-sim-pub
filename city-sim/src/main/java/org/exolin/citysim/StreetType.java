package org.exolin.citysim;

import java.awt.image.BufferedImage;
import java.io.Reader;
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
    
    public StreetType(int id, String name, List<BufferedImage> images, int size)
    {
        super(id, name, images, size);
        
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

    @Override
    protected Street readImpl(Reader reader)
    {
        throw new UnsupportedOperationException();
    }
}
