package org.exolin.citysim;

import org.exolin.citysim.bt.BusinessBuildings;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
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
        World w = new World(30);
        
        assertEquals(3, BusinessBuildings.cinema.getSize());
        
        //[10, 7] .. [12, 9]
        Building b = w.addBuilding(BusinessBuildings.cinema, 10, 7);
        
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
}
