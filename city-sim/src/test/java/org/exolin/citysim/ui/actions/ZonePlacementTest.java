package org.exolin.citysim.ui.actions;

import java.awt.Point;
import java.math.BigDecimal;
import java.util.List;
import org.exolin.citysim.bt.Zones;
import org.exolin.citysim.model.SimulationSpeed;
import org.exolin.citysim.model.Structure;
import org.exolin.citysim.model.World;
import org.exolin.citysim.model.zone.ZoneType;
import static org.exolin.citysim.ui.actions.ActionTestUtils.makeZonePlacementMove;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

/**
 *
 * @author Thomas
 */
public class ZonePlacementTest
{
    private void assertZone(Structure b, int x, int y, ZoneType t)
    {
        String msg = x+"/"+y;
        assertEquals(x, b.getX(), msg+" - x");
        assertEquals(y, b.getY());
        assertEquals(t, b.getType());
    }
    
    @Test
    public void testZonePlacementOnEmpty()
    {
        World world = new World("Test", 100, BigDecimal.ZERO, SimulationSpeed.SPEED1);
        
        makeZonePlacementMove(new Point(1, 6), new Point(3, 9), world, Zones.business);
        
        List<Structure> buildings = world.getStructures();
        assertEquals(6, world.getStructures().size(), buildings.toString());
        
        //for(Structure b: buildings)
        //    System.out.println(b);
        
        assertZone(world.getBuildingAt(1, 6), 1, 6, Zones.business);
        assertZone(world.getBuildingAt(1, 7), 1, 7, Zones.business);
        assertZone(world.getBuildingAt(1, 8), 1, 8, Zones.business);
        assertZone(world.getBuildingAt(2, 6), 2, 6, Zones.business);
        assertZone(world.getBuildingAt(2, 7), 2, 7, Zones.business);
        assertZone(world.getBuildingAt(2, 8), 2, 8, Zones.business);
    }
    
    @Test
    @Disabled
    public void testZoneDisplacement()
    {
        World world = new World("Test", 100, BigDecimal.ZERO, SimulationSpeed.SPEED1);
        
        makeZonePlacementMove(new Point(1, 6), new Point(3, 9), world, Zones.residential);
        makeZonePlacementMove(new Point(2, 7), new Point(4, 10), world, Zones.business);
        
        List<Structure> buildings = world.getStructures();
        assertEquals(10, world.getStructures().size(), buildings.toString());
        
        for(Structure b: buildings)
            System.out.println(b);
        
        assertZone(world.getBuildingAt(1, 6), 1, 6, Zones.business);
        assertZone(world.getBuildingAt(1, 7), 1, 7, Zones.business);
        assertZone(world.getBuildingAt(1, 8), 1, 8, Zones.business);
        assertZone(world.getBuildingAt(2, 6), 2, 6, Zones.business);
        assertZone(world.getBuildingAt(2, 7), 2, 7, Zones.business);
        assertZone(world.getBuildingAt(2, 8), 2, 8, Zones.business);
    }
    
    @Test
    @Disabled
    public void testZonePlacementOnTrees()
    {
        World world = new World("Test", 100, BigDecimal.ZERO, SimulationSpeed.SPEED1);
        
        makeZonePlacementMove(new Point(1, 6), new Point(3, 9), world, Zones.business);
        
        List<Structure> buildings = world.getStructures();
        assertEquals(6, world.getStructures().size(), buildings.toString());
        
        //for(Structure b: buildings)
        //    System.out.println(b);
        
        assertZone(world.getBuildingAt(1, 6), 1, 6, Zones.business);
        assertZone(world.getBuildingAt(1, 7), 1, 7, Zones.business);
        assertZone(world.getBuildingAt(1, 8), 1, 8, Zones.business);
        assertZone(world.getBuildingAt(2, 6), 2, 6, Zones.business);
        assertZone(world.getBuildingAt(2, 7), 2, 7, Zones.business);
        assertZone(world.getBuildingAt(2, 8), 2, 8, Zones.business);
    }
}
