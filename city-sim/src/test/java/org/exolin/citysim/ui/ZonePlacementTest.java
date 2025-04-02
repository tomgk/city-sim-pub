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
        assertEquals(x, b.getX(), x);
        assertEquals(y, b.getY(), y);
        assertEquals(t, b.getType());
    }
    
    @Test
    public void testZonePlacementOnEmpty()
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
    }
}
