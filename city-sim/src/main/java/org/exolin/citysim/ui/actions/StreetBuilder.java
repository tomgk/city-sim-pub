package org.exolin.citysim.ui.actions;

import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import org.exolin.citysim.model.BuildingType;
import org.exolin.citysim.model.GetWorld;
import org.exolin.citysim.model.World;
import static org.exolin.citysim.model.street.ConnectVariant.CONNECT_X;
import static org.exolin.citysim.model.street.ConnectVariant.CONNECT_Y;
import org.exolin.citysim.model.street.StreetType;
import org.exolin.citysim.model.street.StreetVariant;
import static org.exolin.citysim.model.street.XIntersection.X_INTERSECTION;

/**
 *
 * @author Thomas
 */
public class StreetBuilder implements BuildingAction
{
    private final GetWorld getWorld;
    private final StreetType type;
    private Point start;
    private Rectangle marking;
    private boolean onlyLine;

    public StreetBuilder(GetWorld getWorld, StreetType type, boolean onlyLine)
    {
        this.getWorld = getWorld;
        this.type = type;
        this.onlyLine = onlyLine;
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

        if(onlyLine)
        {
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
        else
            AreaAction.mouseMove(gridPoint, start, marking);
    }

    @Override
    public void releaseMouse(Point gridPoint)
    {
        World world = this.getWorld.get();
        
        if(onlyLine)
        {
            int diffX;
            int diffY;
            int len;
            StreetVariant variant;
            if(marking.width == 1)
            {
                diffX = 0;
                diffY = 1;
                len = marking.height;
                variant = CONNECT_Y;
            }
            else
            {
                diffX = 1;
                diffY = 0;
                len = marking.width;
                variant = CONNECT_X;
            }

            for(int i=0;i<len;++i)
            {
                int x = marking.x + diffX * i;
                int y = marking.y + diffY * i;

                world.addBuilding(type, x, y, variant);
            }
            world.reduceMoney(type.getCost() * len);
        }
        else
        {
            ZonePlacement.performAction(marking, world, type, CONNECT_X);
        }

        start = null;
        marking = null;
    }

    @Override
    public BuildingType getBuilding()
    {
        return type;
    }

    @Override
    public Image getMarker()
    {
        if(marking == null)
            return null;

        StreetVariant variant;
        if(!onlyLine)
            variant = X_INTERSECTION;
        else if(marking.width == 1)
            variant = CONNECT_Y;
        else
            variant = CONNECT_X;

        return type.getBrightImage(variant);
    }

    @Override
    public boolean scaleMarker()
    {
        return false;
    }
}
