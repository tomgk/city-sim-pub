package org.exolin.citysim;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import static org.exolin.citysim.ui.Utils.loadImage;

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
    
    static final BuildingType small_house_1 = createBuildingType("small_house_1", 1);
    static final BuildingType house_1 = createBuildingType("house_1", 3);
    static final BuildingType house_2 = createBuildingType("house_2", 3);
    static final BuildingType house_3 = createBuildingType("house_3", 4);
    static final BuildingType house_4 = createBuildingType("house_4", 4);
    
    static final BuildingType industrial_small_1 = createBuildingType("industrial_small_1", 1);
    static final BuildingType industrial_small_2 = createBuildingType("industrial_small_2", 1);
    static final BuildingType industrial_small_3 = createBuildingType("industrial_small_3", 1);
    static final BuildingType industrial_small_4 = createBuildingType("industrial_small_4", 1);
    
    static final BuildingType plant_solar = createBuildingType("plant_solar", 4);
    
    public static final BuildingType street1 = createStreetType("street_1", 1);
    public static final BuildingType street2 = createStreetType("street_2", 1);
    
    public static final BuildingType zone_residential = createZone("zone_residential");
    public static final BuildingType zone_business = createZone("zone_business");
    
    private static BuildingType createStreetType(String name, int size)
    {
        return new BuildingType(name, loadImage(name), size, BuildingType.Category.STREET);
    }
    
    private static BuildingType createBuildingType(String name, int size)
    {
        return new BuildingType(name, loadImage(name), size, BuildingType.Category.BUILDING);
    }
    
    private static BuildingType createZone(String name)
    {
        return new BuildingType(name, loadImage(name), 1, BuildingType.Category.ZONE);
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
    
    public boolean containsBuilding(int x, int y)
    {
        for(Building b: buildings)
            if(b.isOccupying(x, y))
                return true;
        
        return false;
    }
    
    public void addBuilding(BuildingType type, int x, int y)
    {
        buildings.add(new Building(type, x, y));
        buildings.sort(Comparator.comparing(Building::getLevel));
    }

    public List<Building> getBuildings()
    {
        return buildings;
    }
}
