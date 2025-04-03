package org.exolin.citysim.model;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Thomas
 */
public class ZoneType extends BuildingType<Zone, ZoneType.Variant>
{
    private final boolean userPlaceableZone;
    
    public enum Variant implements BuildingVariant
    {
        DEFAULT
    }
    
    private final List<ActualBuildingType> buildings = new ArrayList<>();
    
    public ZoneType(String name, Animation animation, int size, boolean userPlaceableZone)
    {
        super(name, animation, size);
        this.userPlaceableZone = userPlaceableZone;
    }

    public boolean isUserPlaceableZone()
    {
        return userPlaceableZone;
    }
    
    void addBuilding(ActualBuildingType building)
    {
        this.buildings.add(building);
    }
    
    ActualBuildingType getRandomBuilding()
    {
        List<ActualBuildingType> smallBuildings = buildings.stream()
                .filter(b -> b.getSize() == 1)
                .toList();
        
        if(smallBuildings.isEmpty())
            return null;
        
        return smallBuildings.get((int)(Math.random()*smallBuildings.size()));
    }

    @Override
    public Class<Variant> getVariantClass()
    {
        return Variant.class;
    }


    @Override
    public Zone createBuilding(int x, int y, Variant variant)
    {
        return new Zone(this, x, y, variant);
    }
}
