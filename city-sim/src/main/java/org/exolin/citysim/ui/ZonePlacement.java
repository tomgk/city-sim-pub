package org.exolin.citysim.ui;

import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import org.exolin.citysim.BuildingType;
import org.exolin.citysim.World;

/**
 *
 * @author Thomas
 */
public class ZonePlacement implements BuildingAction
{
    private final World world;
    private final BuildingType building;
    private Point start;
    private Rectangle marking;

    public ZonePlacement(World world, BuildingType building)
    {
        this.world = world;
        this.building = building;
    }

    @Override
    public void mouseDown(Point gridPoint)
    {
        this.start = new Point(gridPoint);
        marking = new Rectangle(start.x, start.y, 1, 1);
    }

    @Override
    public String getName()
    {
        return "zone for "+building.getName();
    }

    @Override
    public String toString()
    {
        return getName();
    }

    @Override
    public Rectangle getSelection()
    {
        return marking;
    }

    @Override
    public void moveMouse(Point gridPoint)
    {
        if(start == null)
            return;

        int diffX = gridPoint.x - start.x;
        int diffY = gridPoint.y - start.y;

        marking.x = start.x;
        marking.y = start.y;

        if(diffX > 0)
            marking.width = diffX;
        else
        {
            marking.width = -diffX;
            marking.x += diffX;
        }

        if(diffY > 0)
            marking.height = diffY;
        else
        {
            marking.height = -diffY;
            marking.y += diffY;
        }
    }

    @Override
    public void releaseMouse(Point gridPoint)
    {
        for(int y=0;y<marking.height;++y)
        {
            for(int x=0;x<marking.width;++x)
            {
                if(world.containsBuilding(x, y))
                    continue;

                world.addBuilding(building, marking.x + x, marking.y + y);
            }
        }

        start = null;
        marking = null;
    }

    @Override
    public BuildingType getBuilding()
    {
        return building;
    }

    @Override
    public Image getMarker()
    {
        return building.getImage();
    }

    @Override
    public boolean scaleMarker()
    {
        return false;
    }
}
