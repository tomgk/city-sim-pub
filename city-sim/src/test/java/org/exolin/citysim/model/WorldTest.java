package org.exolin.citysim.model;

import java.math.BigDecimal;
import java.util.stream.Collectors;
import org.exolin.citysim.bt.BusinessBuildings;
import org.exolin.citysim.bt.Zones;
import static org.exolin.citysim.model.Worlds.placeStreet;
import static org.exolin.citysim.model.Worlds.placeZone;
import org.exolin.citysim.model.zone.ZoneType;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

/**
 *
 * @author Thomas
 */
public class WorldTest
{
    @Test
    public void testGetBuilding()
    {
        World w = new World("Test", 30, BigDecimal.valueOf(100000), SimulationSpeed.PAUSED);
        
        assertEquals(3, BusinessBuildings.cinema.getSize());
        
        //[10, 7] .. [12, 9]
        Structure b = w.addBuilding(BusinessBuildings.cinema, 10, 7);
        
        //-----------------
        
        assertSame(b, w.getBuildingAt(10, 7));
        assertSame(b, w.getBuildingAt(11, 7));
        assertSame(b, w.getBuildingAt(12, 7));
        
        assertSame(b, w.getBuildingAt(10, 8));
        assertSame(b, w.getBuildingAt(11, 8));
        assertSame(b, w.getBuildingAt(12, 8));
        
        assertSame(b, w.getBuildingAt(10, 9));
        assertSame(b, w.getBuildingAt(11, 9));
        assertSame(b, w.getBuildingAt(12, 9));
        
        //-----------------
        /*
        assertNull(w.getBuildingAt(10, 7));
        assertNull(w.getBuildingAt(11, 7));
        assertNull(w.getBuildingAt(12, 7));
        */
        
        //to the left (x-1)
        assertNull(w.getBuildingAt(9, 7));
        assertNull(w.getBuildingAt(9, 8));
        assertNull(w.getBuildingAt(9, 9));
        
        //to the right (x+size)
        assertNull(w.getBuildingAt(13, 7));
        assertNull(w.getBuildingAt(13, 8));
        assertNull(w.getBuildingAt(13, 9));
        
        //above (y-1)
        assertNull(w.getBuildingAt(10, 6));
        assertNull(w.getBuildingAt(11, 6));
        assertNull(w.getBuildingAt(12, 6));
        
        //below (y+size)
        assertNull(w.getBuildingAt(10, 10));
        assertNull(w.getBuildingAt(11, 10));
        assertNull(w.getBuildingAt(12, 10));
        
        //to the top left (x-1, y-1)
        assertNull(w.getBuildingAt(9, 6));
        //to the top right (x-1, y+size)
        assertNull(w.getBuildingAt(13, 6));
        //to the bottom left (x+size, y-1)
        assertNull(w.getBuildingAt(9, 13));
        //to the bottom right (x+size, y+size)
        assertNull(w.getBuildingAt(13, 13));
    }
    
    @Test
    @Disabled
    public void test()
    {
        World w = new World("Test", 30, BigDecimal.ONE, SimulationSpeed.PAUSED);
        
        GetWorld getWorld = GetWorld.ofStatic(w);
        
        placeZone(w, Zones.residential, ZoneType.Variant.DEFAULT, 0, 0, 2, 2);
        placeStreet(w, 0, 2, 2, 2);
        
        w.updateAfterTick(1);
        
        System.out.println(w.getBuildings().stream().map(Object::toString).collect(Collectors.joining("\n")));
        assertEquals("xxxx", w.getBuildings());
    }
}
