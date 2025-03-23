package org.exolin.citysim;

import java.awt.image.BufferedImage;
import java.io.Reader;

/**
 *
 * @author Thomas
 */
public class ActualBuildingType extends BuildingType<ActualBuilding>
{
    private final ZoneType zoneType;
    
    public ActualBuildingType(int id, String name, BufferedImage image, int size, ZoneType zoneType)
    {
        super(id, name, image, size);
        //zoneType is null if it can be placed directly by player
        this.zoneType = zoneType;
    }

    public ZoneType getZoneType()
    {
        return zoneType;
    }

    @Override
    public ActualBuilding createBuilding(int x, int y)
    {
        return new ActualBuilding(this, x, y);
    }

    @Override
    protected ActualBuilding readImpl(Reader reader)
    {
        throw new UnsupportedOperationException();
    }
}
