package org.exolin.citysim;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import static org.exolin.citysim.Utils.loadImage;

/**
 *
 * @author Thomas
 */
public class World
{
    private static final BuildingType office = createBuildingType("office", 1);
    private static final BuildingType office2 = createBuildingType("office_2", 1);
    private static final BuildingType office3 = createBuildingType("office_3", 1);
    private static final BuildingType car_cinema = createBuildingType("car-cinema", 1);
    private static final BuildingType cinema = createBuildingType("cinema", 1);
    private static final BuildingType parkbuilding = createBuildingType("parkbuilding", 1);
    private static final BuildingType street1 = createBuildingType("street_1", 1);
    
    private static BuildingType createBuildingType(String name, int size)
    {
        return new BuildingType(loadImage(name), size);
    }
    
    private final static int GRID_SIZE = 10;
    
    public int GRID_SIZE()
    {
        return GRID_SIZE;
    }
    
    private final List<Building> buildings = new ArrayList<>();
    
    {
        addBuilding(office, 2, 1);
        addBuilding(office, 3, 4);
        addBuilding(car_cinema, 6, 6);
        addBuilding(cinema, 6, 7);
        addBuilding(office2, 6, 8);
        addBuilding(parkbuilding, 6, 9);
        addBuilding(office3, 5, 9);
        for(int i=0;i<3;++i)
            addBuilding(street1, 2+i, 2);
        
        addBuilding(office, 0, 0);
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
