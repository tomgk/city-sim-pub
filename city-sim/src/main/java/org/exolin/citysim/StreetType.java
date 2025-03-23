package org.exolin.citysim;

import java.awt.image.BufferedImage;

/**
 *
 * @author Thomas
 */
public class StreetType extends BuildingType
{
    public StreetType(String name, BufferedImage image, int size)
    {
        super(name, image, size);
    }
    
    @Override
    public Object createBuilding(int x, int y)
    {
        return new Street(this, x, y);
    }
}
