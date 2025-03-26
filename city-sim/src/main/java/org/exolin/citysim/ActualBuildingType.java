package org.exolin.citysim;

import java.awt.image.BufferedImage;
import java.io.Reader;

/**
 *
 * @author Thomas
 */
public class ActualBuildingType extends BuildingType<ActualBuilding, ActualBuildingType.Variant>
{
    public enum Variant
    {
        DEFAULT
    }
    
    private final ZoneType zoneType;
    
    public ActualBuildingType(String name, BufferedImage image, int size, ZoneType zoneType)
    {
        super(name, image, size);
        //zoneType is null if it can be placed directly by player
        this.zoneType = zoneType;
        if(zoneType != null)
            zoneType.addBuilding(this);
    }
    
    public ZoneType getZoneType()
    {
        return zoneType;
    }

    @Override
    public Class<Variant> getVariantClass()
    {
        return Variant.class;
    }

    @Override
    public ActualBuilding createBuilding(int x, int y, Variant variant)
    {
        return new ActualBuilding(this, x, y, variant);
    }

    @Override
    protected ActualBuilding readImpl(Reader reader)
    {
        throw new UnsupportedOperationException();
    }
}
