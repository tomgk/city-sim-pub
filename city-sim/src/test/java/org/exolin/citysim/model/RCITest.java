package org.exolin.citysim.model;

import java.util.List;
import org.exolin.citysim.bt.Zones;
import org.exolin.citysim.model.zone.Zone;
import org.exolin.citysim.model.zone.ZoneType;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

/**
 *
 * @author Thomas
 */
public class RCITest
{
    @Test
    public void testEmpty()
    {
        RCI r = new RCI();
        r.update(List.of());
        assertEquals(100, r.getR());
        assertEquals(100, r.getC());
        assertEquals(100, r.getI());
    }
    
    @Test
    public void testSingleZone()
    {
        RCI r = new RCI();
        r.update(List.of(new Zone(Zones.business, 1, 1, ZoneType.Variant.DEFAULT)));
        assertEquals(100, r.getR());
        assertEquals(-100, r.getC());
        assertEquals(100, r.getI());
    }
    
    @Test
    public void testTwoZone()
    {
        RCI r = new RCI();
        r.update(List.of(
                new Zone(Zones.residential, 1, 1, ZoneType.Variant.DEFAULT),
                new Zone(Zones.business, 1, 1, ZoneType.Variant.DEFAULT)
        ));
        assertEquals(0, r.getR());
        assertEquals(0, r.getC());
        assertEquals(100, r.getI());
    }
    
    @Test
    public void testThreeZone()
    {
        RCI r = new RCI();
        r.update(List.of(
                new Zone(Zones.residential, 1, 1, ZoneType.Variant.DEFAULT),
                new Zone(Zones.business, 1, 1, ZoneType.Variant.DEFAULT),
                new Zone(Zones.industrial, 1, 1, ZoneType.Variant.DEFAULT)
        ));
        assertEquals(34, r.getR());
        assertEquals(34, r.getC());
        assertEquals(34, r.getI());
    }
}
