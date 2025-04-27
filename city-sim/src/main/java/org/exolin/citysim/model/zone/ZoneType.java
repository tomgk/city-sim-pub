package org.exolin.citysim.model.zone;

import java.util.ArrayList;
import java.util.List;
import org.exolin.citysim.model.Animation;
import org.exolin.citysim.model.PlainStructureParameters;
import org.exolin.citysim.model.StructureType;
import org.exolin.citysim.model.StructureVariant;
import org.exolin.citysim.model.building.BuildingType;

/**
 *
 * @author Thomas
 */
public class ZoneType extends StructureType<Zone, ZoneType.Variant, PlainStructureParameters>
{
    private final boolean userPlaceableZone;
    private final int cost;
    
    public enum Variant implements StructureVariant
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
    
    private final List<BuildingType> buildings = new ArrayList<>();
    
    private static List<Animation> getAnimations(String name, boolean withLowDensity)
    {
        Animation baseAnimation = Animation.createUnanimated(name);
        
        if(withLowDensity)
        {
            Animation lowRes = Animation.createUnanimated(name+"_low");
            return List.of(baseAnimation, lowRes);
        }
        else
            return List.of(baseAnimation, baseAnimation);
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
    
    public void addBuilding(BuildingType building)
    {
        this.buildings.add(building);
    }
    
    public BuildingType getRandomBuilding(int maxSize)
    {
        List<BuildingType> smallBuildings = buildings.stream()
                .filter(b -> b.getSize() <= maxSize)
                //.filter(b -> b.getSize() > 1)
                .toList();
        
        if(smallBuildings.isEmpty())
            return null;
        
        return smallBuildings.get((int)(Math.random()*smallBuildings.size()));
    }

    @Override
    public Variant getVariantForDefaultImage()
    {
        return Variant.DEFAULT;
    }

    @Override
    public Zone createBuilding(int x, int y, Variant variant, PlainStructureParameters data)
    {
        return new Zone(this, x, y, variant, data);
    }
}
