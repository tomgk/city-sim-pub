package org.exolin.citysim;

import java.awt.image.BufferedImage;
import java.io.Reader;
import java.util.List;

/**
 *
 * @author Thomas
 */
public class StreetType extends BuildingType<Street>
{
    public enum Variant
    {
        CONNECT_X,
        CONNECT_Y,
        X_INTERSECTION,
        CURVE_1,
        CURVE_2,
        CURVE_3
    }
    
    public StreetType(int id, String name, List<BufferedImage> images, int size)
    {
        super(id, name, images, size);
        
        if(images.size() != Variant.values().length)
            throw new IllegalArgumentException("incorrect image count");
    }
    
    @Override
    public Street createBuilding(int x, int y, int variant)
    {
        return new Street(this, x, y, variant);
    }

    @Override
    protected Street readImpl(Reader reader)
    {
        throw new UnsupportedOperationException();
    }
}
