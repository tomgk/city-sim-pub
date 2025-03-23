package org.exolin.citysim;

import java.awt.image.BufferedImage;
import java.io.Reader;

/**
 *
 * @author Thomas
 */
public class StreetType extends BuildingType<Street>
{
    public StreetType(int id, String name, BufferedImage image, int size)
    {
        super(id, name, image, size);
    }
    
    @Override
    public Street createBuilding(int x, int y)
    {
        return new Street(this, x, y);
    }

    @Override
    protected Street readImpl(Reader reader)
    {
        throw new UnsupportedOperationException();
    }
}
