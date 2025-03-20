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
    private static final BufferedImage office = loadImage("office");
    private static final BufferedImage office2 = loadImage("office_2");
    private static final BufferedImage office3 = loadImage("office_3");
    private static final BufferedImage car_cinema = loadImage("car-cinema");
    private static final BufferedImage cinema = loadImage("cinema");
    private static final BufferedImage parkbuilding = loadImage("parkbuilding");
    
    private final List<Building> buildings = new ArrayList<>();
    
    {
        addBuilding(office, 2, 1);
        addBuilding(office, 3, 4);
        addBuilding(car_cinema, 6, 6);
        addBuilding(cinema, 6, 7);
        addBuilding(office2, 6, 8);
        addBuilding(parkbuilding, 6, 9);
        addBuilding(office3, 5, 9);
        
        addBuilding(office, 0, 0);
    }
    
    void addBuilding(Image image, int x, int y)
    {
        buildings.add(new Building(image, x, y));
        buildings.sort(Comparator.comparing(Building::getLevel));
    }

    public List<Building> getBuildings()
    {
        return buildings;
    }
}
