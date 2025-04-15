package org.exolin.citysim.model.zone;

import org.exolin.citysim.model.ab.ActualBuildingType;
import java.util.ArrayList;
import java.util.List;
import org.exolin.citysim.model.Animation;
import org.exolin.citysim.model.BuildingType;
import org.exolin.citysim.model.BuildingVariant;

/**
 *
 * @author Thomas
 */
public class ZoneType extends BuildingType<Zone, ZoneType.Variant>
{
    private final boolean userPlaceableZone;
    private final int cost;
    
    public enum Variant implements BuildingVariant
    {
        DEFAULT(1),
        LOW_DENSITY(2);
        
        private final int factor;

        private Variant(int factor)
        {
            this.factor = factor;
        }

        public int getFactor()
        {
            return factor;
        }
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
    
    private final String title;
    
    public ZoneType(String title, String filename, int size, boolean userPlaceableZone, boolean withLowDensity, int cost)
    {
        super("zone_"+title, getAnimations(filename, withLowDensity), size);
        this.userPlaceableZone = userPlaceableZone;
        this.title = title;
        this.cost = cost;
    }

    public String getTitle()
    {
        return title;
    }
    
    public boolean isUserPlaceableZone()
    {
        return userPlaceableZone;
    }

    public int getCost(Variant variant)
    {
        return cost * variant.getFactor();
    }
    
    public void addBuilding(ActualBuildingType building)
    {
        this.buildings.add(building);
    }
    
    public ActualBuildingType getRandomBuilding(int maxSize)
    {
        List<ActualBuildingType> smallBuildings = buildings.stream()
                .filter(b -> b.getSize() <= maxSize)
                //.filter(b -> b.getSize() > 1)
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
