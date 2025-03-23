package org.exolin.citysim;

import java.awt.image.BufferedImage;

/**
 *
 * @author Thomas
 */
public class StreetType extends BuildingType
{
    public StreetType(int id, String name, BufferedImage image, int size)
    {
        super(id, name, image, size);
    }
    
    @Override
    public Object createBuilding(int x, int y)
    {
        return new Street(this, x, y);
    }
}
