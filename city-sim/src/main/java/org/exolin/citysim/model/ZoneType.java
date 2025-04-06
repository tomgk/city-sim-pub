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
        DEFAULT,
        LOW_DENSITY
    }
    
    private final List<ActualBuildingType> buildings = new ArrayList<>();
    
    private static List<Animation> getAnimations(String name, boolean withLowDensity)
    {
        Animation baseAnimation = Animation.createUnanimated(name);
        
        if(withLowDensity)
        {
            Animation lowRes = Animation.createUnanimated(name+"_low");
            return List.of(baseAnimation, lowRes);
        }
        
        return List.of(baseAnimation);
    }
    
    public ZoneType(String name, String filename, int size, boolean userPlaceableZone, boolean withLowDensity)
    {
        super(name, getAnimations(filename, withLowDensity), size);
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
    public Variant getDefaultVariant()
    {
        return Variant.DEFAULT;
    }

    @Override
    public Zone createBuilding(int x, int y, Variant variant)
    {
        return new Zone(this, x, y, variant);
    }
}
