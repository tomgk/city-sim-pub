package org.exolin.citysim.ui.actions;

import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import org.exolin.citysim.bt.connections.CrossConnections;
import org.exolin.citysim.model.GetWorld;
import org.exolin.citysim.model.StructureType;
import org.exolin.citysim.model.World;
import org.exolin.citysim.model.connection.Connection;
import org.exolin.citysim.model.connection.ConnectionType;
import static org.exolin.citysim.model.connection.regular.StraightConnectionVariant.CONNECT_X;
import static org.exolin.citysim.model.connection.regular.StraightConnectionVariant.CONNECT_Y;
import org.exolin.citysim.model.connection.regular.ConnectionVariant;
import org.exolin.citysim.model.connection.regular.SelfConnectionType;
import static org.exolin.citysim.model.connection.regular.XIntersection.X_INTERSECTION;

/**
 *
 * @author Thomas
 */
public class StreetBuilder implements BuildingAction
{
    private final GetWorld getWorld;
    private final SelfConnectionType type;
    private Point start;
    private Rectangle marking;
    private final boolean onlyLine;
    
    public static final boolean AREA = false;
    public static final boolean ONLY_LINE = true;

    public StreetBuilder(GetWorld getWorld, SelfConnectionType type, boolean onlyLine)
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
    public void mouseReleased(Point gridPoint)
    {
        World world = this.getWorld.get();
        
        if(onlyLine)
        {
            int diffX;
            int diffY;
            int len;
            ConnectionVariant variant;
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
                
                ConnectionType curType;
                
                if(world.getBuildingAt(x, y) instanceof Connection<?, ?, ?, ?> t)
                {
                    //crossing x if currently not building x
                    boolean crossX = diffX == 0;
                    
                    //type which gets crossed
                    SelfConnectionType crossingType = crossX ? t.getType().getXType() : t.getType().getYType();
                    //resulting type from crossing
                    curType = CrossConnections.get(crossX ? crossingType : type, crossX ? type : crossingType);
                    if(curType == null)
                        curType = type;
                }
                else
                    curType = type;

                world.addBuilding(curType, x, y, curType.getVariantForDefaultImage());
                world.reduceMoney(type.getBuildingCost());
            }
        }
        else
        {
            ZonePlacement.performAction(marking, world, type, CONNECT_X, type.getBuildingCost(), true);
        }

        start = null;
        marking = null;
    }

    @Override
    public StructureType getBuilding()
    {
        return type;
    }

    @Override
    public int getCost()
    {
        return type.getBuildingCost();
    }

    @Override
    public Image getMarker()
    {
        if(marking == null)
            return null;

        ConnectionVariant variant;
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
