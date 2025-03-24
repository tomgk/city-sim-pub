package org.exolin.citysim;

import java.awt.image.BufferedImage;
import java.io.Reader;

/**
 *
 * @author Thomas
 */
public class ZoneType extends BuildingType<Zone>
{
    public ZoneType(int id, String name, BufferedImage image, int size)
    {
        super(id, name, image, size);
    }

    @Override
    public Zone createBuilding(int x, int y, int variant)
    {
        return new Zone(this, x, y, variant);
    }

    @Override
    protected Zone readImpl(Reader reader)
    {
        throw new UnsupportedOperationException();
    }
}
