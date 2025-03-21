package org.exolin.citysim;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import static org.exolin.citysim.Utils.loadImage;

/**
 *
 * @author Thomas
 */
public final class World
{
    static final BuildingType office = createBuildingType("office", 4);
    static final BuildingType office2 = createBuildingType("office_2", 3);
    static final BuildingType office3 = createBuildingType("office_3", 3);
    static final BuildingType car_cinema = createBuildingType("car-cinema", 4);
    static final BuildingType cinema = createBuildingType("cinema", 3);
    static final BuildingType parkbuilding = createBuildingType("parkbuilding", 3);
    static final BuildingType street1 = createBuildingType("street_1", 1);
    static final BuildingType street2 = createBuildingType("street_2", 1);
    
    private static BuildingType createBuildingType(String name, int size)
    {
        return new BuildingType(name, loadImage(name), size);
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
    
    boolean containsBuilding(int x, int y)
    {
        for(Building b: buildings)
            if(b.isOccupying(x, y))
                return true;
        
        return false;
    }
    
    void addBuilding(BuildingType type, int x, int y)
    {
        buildings.add(new Building(type, x, y));
        buildings.sort(Comparator.comparing(Building::getLevel));
    }

    public List<Building> getBuildings()
    {
        return buildings;
    }
}
