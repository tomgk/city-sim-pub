package org.exolin.citysim.ui.actions;

import java.awt.Point;
import java.util.Optional;
import org.exolin.citysim.model.GetWorld;
import org.exolin.citysim.model.Structure;
import org.exolin.citysim.model.StructureType;
import org.exolin.citysim.model.World;
import org.exolin.citysim.model.tree.Tree;
import org.exolin.citysim.model.tree.TreeType;
import org.exolin.citysim.model.zone.Zone;
import org.exolin.citysim.model.zone.ZoneType;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

/**
 *
 * @author Thomas
 */
public class ActionTestUtils
{
    private static void assertStructure(Structure<?, ?, ?, ?> b, int x, int y, StructureType<?, ?, ?> expectedType)
    {
        if(b == null)
            fail("Expected "+expectedType.getName()+" but there is nothing at "+x+"/"+y);
    }
    
    public static void assertZone(Structure<?, ?, ?, ?> b, int x, int y, ZoneType t)
    {
        assertStructure(b, x, y, t);
        
        String msg = x+"/"+y;
        assertEquals(x, b.getX(), msg+" - x");
        assertEquals(y, b.getY());
        assertEquals(t, b.getType());
        assertEquals(Zone.class, b.getClass());
    }
    
    public static void assertTree(Structure<?, ?, ?, ?> b, int x, int y, TreeType t, Optional<ZoneType> zone)
    {
        assertStructure(b, x, y, t);
        
        String msg = x+"/"+y;
        assertEquals(x, b.getX(), msg+" - x");
        assertEquals(y, b.getY());
        assertEquals(t, b.getType());
        assertEquals(zone, b.getTheZoneType());
        assertEquals(Tree.class, b.getClass());
    }
    
    public static void makeZonePlacementMove(Point start, Point end, World world, ZoneType type)
    {
        ZonePlacement z = new ZonePlacement(GetWorld.ofStatic(world), type, ZoneType.Variant.DEFAULT);
        makeMove(start, end, world, z);
    }
    
    public static void makeMove(Point start, Point end, World world, Action a)
    {
        a.mouseDown(start);
        a.moveMouse(end);
        a.mouseReleased(end);
    }
}
