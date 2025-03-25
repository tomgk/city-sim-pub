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
    //private final List<ActualBuilding> buildings = new ArrayList<>();
    
    public ActualBuildingType(int id, String name, BufferedImage image, int size, ZoneType zoneType)
    {
        super(id, name, image, size);
        //zoneType is null if it can be placed directly by player
        this.zoneType = zoneType;
    }
    /*
    void addBuilding(ActualBuilding b)
    {
        buildings.add(b);
    }
    */
    public ZoneType getZoneType()
    {
        return zoneType;
    }

    @Override
    public ActualBuilding createBuilding(int x, int y, int variant)
    {
        return new ActualBuilding(this, x, y, variant);
    }

    @Override
    protected ActualBuilding readImpl(Reader reader)
    {
        throw new UnsupportedOperationException();
    }
}
