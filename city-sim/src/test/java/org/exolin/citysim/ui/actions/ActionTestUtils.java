package org.exolin.citysim.ui.actions;

import java.awt.Point;
import org.exolin.citysim.model.Structure;
import org.exolin.citysim.model.World;
import org.exolin.citysim.model.zone.Zone;
import org.exolin.citysim.model.zone.ZoneType;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 *
 * @author Thomas
 */
public class ActionTestUtils
{
    public static void assertZone(Structure b, int x, int y, ZoneType t)
    {
        String msg = x+"/"+y;
        assertEquals(x, b.getX(), msg+" - x");
        assertEquals(y, b.getY());
        assertEquals(t, b.getType());
        assertEquals(Zone.class, b.getClass());
    }
    
    public static void makeZonePlacementMove(Point start, Point end, World world, ZoneType type)
    {
        ZonePlacement z = new ZonePlacement(() -> world, type, ZoneType.Variant.DEFAULT);
        makeMove(start, end, world, z);
    }
    
    public static void makeMove(Point start, Point end, World world, Action a)
    {
        a.mouseDown(start);
        a.moveMouse(end);
        a.mouseReleased(end);
    }
}
