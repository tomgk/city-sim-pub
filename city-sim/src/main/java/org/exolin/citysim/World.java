package org.exolin.citysim;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import static org.exolin.citysim.ui.Utils.loadImage;

/**
 *
 * @author Thomas
 */
public final class World
{
    public static final ZoneType zone_residential = createZone("zone_residential");
    public static final ZoneType zone_business = createZone("zone_business");
    public static final ZoneType zone_industrial = createZone("zone_industrial");
    
    static final BuildingType office = createBuildingType("office", 4, zone_business);
    static final BuildingType office2 = createBuildingType("office_2", 3, zone_business);
    static final BuildingType office3 = createBuildingType("office_3", 3, zone_business);
    static final BuildingType car_cinema = createBuildingType("car-cinema", 4, zone_business);
    static final BuildingType cinema = createBuildingType("cinema", 3, zone_business);
    static final BuildingType parkbuilding = createBuildingType("parkbuilding", 3, zone_business);
    
    static final BuildingType small_house_1 = createBuildingType("small_house_1", 1, zone_residential);
    static final BuildingType house_1 = createBuildingType("house_1", 3, zone_residential);
    static final BuildingType house_2 = createBuildingType("house_2", 3, zone_residential);
    static final BuildingType house_3 = createBuildingType("house_3", 4, zone_residential);
    static final BuildingType house_4 = createBuildingType("house_4", 4, zone_residential);
    
    static final BuildingType industrial_small_1 = createBuildingType("industrial_small_1", 1, zone_industrial);
    static final BuildingType industrial_small_2 = createBuildingType("industrial_small_2", 1, zone_industrial);
    static final BuildingType industrial_small_3 = createBuildingType("industrial_small_3", 1, zone_industrial);
    static final BuildingType industrial_small_4 = createBuildingType("industrial_small_4", 1, zone_industrial);
    
    static final BuildingType plant_solar = createBuildingType("plant_solar", 4, null);
    
    public static final BuildingType street1 = createStreetType("street_1", 1);
    public static final BuildingType street2 = createStreetType("street_2", 1);
    
    private static BuildingType createStreetType(String name, int size)
    {
        return new StreetType(name, loadImage(name), size);
    }
    
    private static BuildingType createBuildingType(String name, int size, ZoneType zoneType)
    {
        return new ActualBuildingType(name, loadImage(name), size, zoneType);
    }
    
    private static ZoneType createZone(String name)
    {
        return new ZoneType(name, loadImage(name), 1);
    }
    
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
            w.addBuilding(street1, 2+i, 2);
        
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
            w.addBuilding(street1, 2+i, 5);
        
        for(int i=0;i<4;++i)
            w.addBuilding(street2, 20, 6+i);
        
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
        System.out.println("Remove "+x+"/"+y);
        for (Iterator<Building> it = buildings.iterator(); it.hasNext();)
        {
            Building b = it.next();
            if(b.isOccupying(x, y))
                it.remove();
        }
    }
    
    public <B extends Building> B addBuilding(BuildingType<B> type, int x, int y)
    {
        if(x < 0 || y < 0 || x+type.getSize()>=gridSize || y+type.getSize()>=gridSize)
            throw new IllegalArgumentException("out of grid");
        
        B b = type.createBuilding(x, y);
        buildings.add(b);
        buildings.sort(Comparator.comparing(Building::getLevel));
        return b;
    }

    public List<Building> getBuildings()
    {
        return buildings;
    }
}
