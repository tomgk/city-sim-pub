package org.exolin.citysim;

import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Thomas
 */
public class ZoneType extends BuildingType<Zone, ZoneType.Variant>
{
    public enum Variant
    {
        DEFAULT
    }
    
    private final List<ActualBuildingType> buildings = new ArrayList<>();
    
    public ZoneType(String name, Animation animation, int size)
    {
        super(name, animation, size);
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

    @Override
    protected Zone readImpl(Reader reader)
    {
        throw new UnsupportedOperationException();
    }
}
