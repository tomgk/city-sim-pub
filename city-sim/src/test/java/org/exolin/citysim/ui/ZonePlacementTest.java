package org.exolin.citysim.ui;

import java.awt.Point;
import java.util.List;
import org.exolin.citysim.Building;
import org.exolin.citysim.World;
import org.exolin.citysim.Zone;
import org.exolin.citysim.ZoneType;
import org.exolin.citysim.bt.Zones;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import org.junit.jupiter.api.Test;

/**
 *
 * @author Thomas
 */
public class ZonePlacementTest
{
    private void assertZone(Building b, int x, int y, ZoneType t)
    {
        String msg = x+"/"+y;
        assertEquals(x, b.getX(), msg+" - x");
        assertEquals(y, b.getY());
        assertEquals(t, b.getType());
    }
    
    private void makeMove(Point start, Point end, World world)
    {
        ZonePlacement z = new ZonePlacement(() -> world, Zones.zone_business);
        
        z.mouseDown(start);
        z.moveMouse(end);
        z.releaseMouse(end);
    }
    
    @Test
    public void testZonePlacementOnEmpty()
    {
        World world = new World(100);
        
        makeMove(new Point(1, 6), new Point(3, 9), world);
        
        List<Building> buildings = world.getBuildings();
        assertEquals(6, world.getBuildings().size(), buildings.toString());
        
        for(Building b: buildings)
            System.out.println(b);
        
        assertZone(world.getBuildingAt(1, 6), 1, 6, Zones.zone_business);
        assertZone(world.getBuildingAt(1, 7), 1, 7, Zones.zone_business);
        assertZone(world.getBuildingAt(1, 8), 1, 8, Zones.zone_business);
        assertZone(world.getBuildingAt(2, 6), 2, 6, Zones.zone_business);
        assertZone(world.getBuildingAt(2, 7), 2, 7, Zones.zone_business);
        assertZone(world.getBuildingAt(2, 8), 2, 8, Zones.zone_business);
    }
    /*
    @Test
    public void testZoneDisplacement()
    {
        World world = new World(100);
        
        ZonePlacement z = new ZonePlacement(() -> world, Zones.zone_business);
        
        z.mouseDown(new Point(1, 6));
        z.moveMouse(new Point(2, 8));
        z.releaseMouse(new Point(2, 8));
        
        List<Building> buildings = world.getBuildings();
        assertEquals(2, world.getBuildings().size(), buildings.toString());
        
        assertZone(buildings.get(0), 1, 6, Zones.zone_business);
        assertZone(buildings.get(0), 2, 6, Zones.zone_business);
    }*/
}
