package org.exolin.citysim.ui;

import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import org.exolin.citysim.BuildingType;
import org.exolin.citysim.StreetType;
import org.exolin.citysim.World;

/**
 *
 * @author Thomas
 */
public class StreetBuilder implements BuildingAction
{
    private final World world;
    private Point start;
    private Rectangle marking;

    public StreetBuilder(World world)
    {
        this.world = world;
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
        return "build street";
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

        if(Math.abs(diffX) > Math.abs(diffY))
        {
            diffX = diffX + Integer.signum(diffX);
            diffY = 1;
        }
        else
        {
            diffX = 1;
            diffY = diffY + Integer.signum(diffY);
        }

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
        int diffX;
        int diffY;
        int len;
        StreetType.Variant variant;
        if(marking.width == 1)
        {
            diffX = 0;
            diffY = 1;
            len = marking.height;
            variant = StreetType.Variant.CONNECT_Y;
        }
        else
        {
            diffX = 1;
            diffY = 0;
            len = marking.width;
            variant = StreetType.Variant.CONNECT_X;
        }

        for(int i=0;i<len;++i)
        {
            int x = marking.x + diffX * i;
            int y = marking.y + diffY * i;

            if(world.containsBuilding(x, y))
                continue;

            world.addBuilding(World.street, x, y, variant);
        }

        start = null;
        marking = null;
    }

    @Override
    public BuildingType getBuilding()
    {
        return World.street;
    }

    @Override
    public Image getMarker()
    {
        if(marking == null)
            return null;

        StreetType.Variant variant;
        if(marking.width == 1)
            variant = StreetType.Variant.CONNECT_Y;
        else
            variant = StreetType.Variant.CONNECT_X;

        return World.street.getBrightImage(variant);
    }

    @Override
    public boolean scaleMarker()
    {
        return false;
    }
}
