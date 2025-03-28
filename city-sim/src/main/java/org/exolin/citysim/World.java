package org.exolin.citysim;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.exolin.citysim.bt.BuildingTypes;
import static org.exolin.citysim.bt.BusinessBuildings.*;
import org.exolin.citysim.bt.Streets;
import static org.exolin.citysim.bt.Streets.*;
import org.exolin.citysim.bt.Zones;

/**
 *
 * @author Thomas
 */
public final class World
{
    static
    {
        BuildingTypes.init();
    }
    
    private long lastChange = System.currentTimeMillis();
    
    private final int gridSize = 30;
    
    public int getGridSize()
    {
        return gridSize;
    }
    
    private final List<Building> buildings = new ArrayList<>();
    
    private World()
    {
        
    }

    public static World Empty()
    {
        return new World();
    }

    public static World World1()
    {
        World w = new World();
        w.addBuilding(office, 6, 3);
        w.addBuilding(office, 9, 12);
        w.addBuilding(car_cinema, 18, 18);
        w.addBuilding(cinema, 18, 21);
        w.addBuilding(office2, 18, 24);
        w.addBuilding(parkbuilding, 18, 27);
        w.addBuilding(office3, 15, 27);
        for(int i=0;i<3;++i)
            w.addBuilding(street, 2+i, 2, StreetType.Variant.CONNECT_X);
        
        w.addBuilding(office, 0, 0);
        
        return w;
    }

    public static World World2()
    {
        World w = new World();
        
        w.addBuilding(office, 6, 6);
        w.addBuilding(parkbuilding, 3, 6);
        
        w.addBuilding(office3, 6, 2);
        
        w.addBuilding(car_cinema, 16, 6);
        w.addBuilding(cinema, 16, 2);
        w.addBuilding(office2, 19, 2);
        
        for(int i=0;i<25;++i)
            w.addBuilding(street, 2+i, 5, StreetType.Variant.CONNECT_X);
        
        for(int i=0;i<4;++i)
            w.addBuilding(street, 20, 6+i, StreetType.Variant.CONNECT_Y);
        
        return w;
    }
    
    public Building getBuildingAt(int x, int y)
    {
        for(Building b: buildings)
            if(b.isOccupying(x, y))
                return b;
        
        return null;
    }
    
    public boolean containsBuilding(int x, int y)
    {
        return getBuildingAt(x, y) != null;
    }

    public void removeBuildingAt(int x, int y)
    {
        //System.out.println("Remove "+x+"/"+y);
        for (Iterator<Building> it = buildings.iterator(); it.hasNext();)
        {
            Building b = it.next();
            if(b.isOccupying(x, y))
            {
                it.remove();
                
                ZoneType zoneType = b.getZoneType();
                if(zoneType != null)
                    placeZone(zoneType, b.getX(), b.getY(), b.getSize());
                
                updateBuildingsAround(b.getX(), b.getY(), b.getSize());
                return;
            }
        }
    }

    private void placeZone(ZoneType zoneType, int x, int y, int size)
    {
        for(int yi=0;yi<size;++yi)
            for(int xi=0;xi<size;++xi)
                addBuilding(zoneType, x+xi, y+yi, ZoneType.Variant.DEFAULT);
    }
    
    public <B extends Building, E extends Enum<E>> B addBuilding(BuildingType<B, E> type, int x, int y)
    {
        return addBuilding(type, x, y, type.getVariantClass().getEnumConstants()[0]);
    }
    
    public <B extends Building, E extends Enum<E>> B addBuilding(BuildingType<B, E> type, int x, int y, E variant)
    {
        if(x < 0 || y < 0 || x+type.getSize()>=gridSize || y+type.getSize()>=gridSize)
            throw new IllegalArgumentException("out of grid");
        
        B b = type.createBuilding(x, y, variant);
        buildings.add(b);
        buildings.sort(Comparator.comparing(Building::getLevel));
        updateBuilding(b);
        updateBuildCount(b, true);
        onChange();
        return b;
    }
    
    private Building getBuildingAtForUpdate(int x, int y)
    {
        //System.out.println(x+"/"+y);
        return getBuildingAt(x, y);
    }
    
    private void updateBuilding(Building b)
    {
        b.update(this);
        updateBuildingsAround(b.getX(), b.getY(), b.getSize());
    }
    
    private void updateBuildingsAround(int bx, int by, int bsize)
    {
        //System.out.println("-------");
        for(int x=bx-1;x<bx+bsize+1;++x)
        {
            {
                //  xxxxx
                //   ***
                //   ***
                //   ***
                Building buildingAt = getBuildingAtForUpdate(x, by-1);
                if(buildingAt != null)
                    buildingAt.update(this);
            }
            {
                //   ***
                //   ***
                //   ***
                //  xxxxx
                Building buildingAt = getBuildingAtForUpdate(x, by+bsize);
                if(buildingAt != null)
                    buildingAt.update(this);
            }
        }
        
        for(int y=by;y<by+bsize;++y)
        {
            //only cover sides, corner was already covered in x loop
            
            //  x***
            //  x***
            //  x***
            {
                Building buildingAt = getBuildingAtForUpdate(bx-1, y);
                if(buildingAt != null)
                    buildingAt.update(this);
            }
            //   ***x
            //   ***x
            //   ***x
            {
                Building buildingAt = getBuildingAtForUpdate(bx+bsize, y);
                if(buildingAt != null)
                    buildingAt.update(this);
            }
        }
    }

    public List<Building> getBuildings()
    {
        return buildings;
    }

    void onChange()
    {
        lastChange = System.currentTimeMillis();
    }

    public long getLastChange()
    {
        return lastChange;
    }
    
    private <B> void replaceBuilding(ZoneType type, int x, int y)
    {
        Building b = getBuildingAt(x, y);
        if(b == null)
            return;
        
        ActualBuildingType bt = type.getRandomBuilding();
        if(bt != null && Math.random() < 0.01)
        {
            buildings.remove(b);
            updateBuildCount(b, false);
            addBuilding(bt, x, y);
        }
    }
    
    private final Map<ZoneType, Integer> buildCount = new HashMap<>();
    {
        for(ZoneType t: Zones.BASIC_ZONES)
            buildCount.put(t, 0);
    }
    
    private void updateBuildCount(Building building, boolean up)
    {
        ZoneType type = building.getZoneType();
        if(type == null)
            return;
        
        buildCount.computeIfPresent(type, (k, v) -> v+(up?+building.getSupply():-building.getSupply()));
    }

    public void update()
    {
        //TODO: maybe no copy
        List<Building> originalBuildings = new ArrayList<>(this.buildings);
        
        for(Building b: originalBuildings)
        {
            if(b.getType() instanceof ZoneType z)
            {
                if(z.getSize() != 1)
                    continue;
                
                if(hasAnyInRadius(Streets.street, b.getX(), b.getY(), Zones.BUILDING_DISTANCE))
                    replaceBuilding(z, b.getX(), b.getY());
            }
        }
    }

    private boolean hasAnyInRadius(StreetType street, int cx, int cy, int distance)
    {
        for(int y=cy-distance;y<=cy+distance;++y)
        {
            for(int x=cx-distance;x<=cx+distance;++x)
            {
                Building b = getBuildingAt(x, y);
                if(b == null)
                    continue;
                if(b.getType() == street)
                    return true;
            }
        }
        
        return false;
    }
}
