package org.exolin.citysim.model.zone;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import org.exolin.citysim.model.Animation;
import org.exolin.citysim.model.EmptyStructureParameters;
import org.exolin.citysim.model.StructureSize;
import org.exolin.citysim.model.StructureType;
import org.exolin.citysim.model.StructureVariant;
import org.exolin.citysim.model.building.BuildingType;
import org.exolin.citysim.utils.RandomUtils;

/**
 *
 * @author Thomas
 */
public class ZoneType extends StructureType<Zone, ZoneType.Variant, EmptyStructureParameters>
{
    public enum Variant implements StructureVariant
    {
        DEFAULT
    }
    
    private final ZoneTypeType type;
    private final Density density;
    private final List<BuildingType> buildings = new ArrayList<>();
    
    private final String title;
    
    public ZoneType(String title, String filename, StructureSize size, ZoneTypeType type, Density density)
    {
        super("zone_"+title, List.of(Animation.createUnanimated(density.isLowDensity() ? filename+"_low" : filename)), size);
        this.title = Objects.requireNonNull(title);
        this.type = Objects.requireNonNull(type);
        this.density = Objects.requireNonNull(density);
    }

    public String getTitle()
    {
        return title;
    }
    
    public ZoneTypeType getCategory()
    {
        return type;
    }
    
    public boolean isUserPlaceableZone()
    {
        return type.isUserPlaceableZone();
    }

    @Override
    public int getBuildingCost(Variant variant)
    {
        return type.getCost() * density.getFactor();
    }
    
    public void addBuilding(BuildingType building)
    {
        this.buildings.add(building);
    }
    
    public BuildingType getRandomBuilding(int maxSize)
    {
        /*
        //returns biggest building
        if(false)
        return buildings.stream()
                .filter(b -> b.getSize() <= maxSize)
                .sorted(Comparator.comparing((BuildingType b) -> b.getSize()).reversed())
                .findFirst().get();
        */
        
        List<BuildingType> smallBuildings = buildings.stream()
                .filter(b -> b.getSize().toInteger() <= maxSize)
                //.filter(b -> b.getSize() > 1)
                .toList();
        
        if(smallBuildings.isEmpty())
            return null;
        
        return RandomUtils.random(smallBuildings);
    }

    @Override
    public Variant getVariantForDefaultImage()
    {
        return Variant.DEFAULT;
    }

    @Override
    public Zone createBuilding(int x, int y, Variant variant, EmptyStructureParameters data)
    {
        return new Zone(this, x, y, variant, data);
    }
}
