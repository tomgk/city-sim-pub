package org.exolin.citysim;

import java.awt.image.BufferedImage;

/**
 *
 * @author Thomas
 */
public class ZoneType extends BuildingType<Zone>
{
    public ZoneType(String name, BufferedImage image, int size)
    {
        super(name, image, size);
    }

    @Override
    public Zone createBuilding(int x, int y)
    {
        return new Zone(this, x, y);
    }
}
