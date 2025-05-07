package org.exolin.citysim.ui.actions;

import java.awt.Point;
import org.exolin.citysim.model.World;
import org.exolin.citysim.model.zone.ZoneType;

/**
 *
 * @author Thomas
 */
public class ActionTestUtils
{
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
