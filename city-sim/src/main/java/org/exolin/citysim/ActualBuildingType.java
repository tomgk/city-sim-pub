package org.exolin.citysim;

import java.awt.image.BufferedImage;

/**
 *
 * @author Thomas
 */
public class ActualBuildingType extends BuildingType
{
    private final ZoneType zoneType;
    
    public ActualBuildingType(String name, BufferedImage image, int size, ZoneType zoneType)
    {
        super(name, image, size);
        //zoneType is null if it can be placed directly by player
        this.zoneType = zoneType;
    }

    public ZoneType getZoneType()
    {
        return zoneType;
    }
}
