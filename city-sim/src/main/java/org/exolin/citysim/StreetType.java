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
    public static final int CONNECT_X = 0;
    public static final int CONNECT_Y = 1;
    
    public StreetType(int id, String name, List<BufferedImage> images, int size)
    {
        super(id, name, images, size);
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
