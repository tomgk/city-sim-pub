package org.exolin.citysim.ui;

import java.awt.Point;
import java.awt.Rectangle;
import org.exolin.citysim.model.GetWorld;
import org.exolin.citysim.model.World;

/**
 *
 * @author Thomas
 */
public abstract class AreaAction implements Action
{
    protected final GetWorld getWorld;
    private Point start;
    private Rectangle marking;

    public AreaAction(GetWorld world)
    {
        this.getWorld = world;
    }
    
    @Override
    public void mouseDown(Point gridPoint)
    {
        this.start = new Point(gridPoint);
        marking = new Rectangle(start.x, start.y, 1, 1);
    }
    
    @Override
    public final Rectangle getSelection()
    {
        return marking;
    }

    @Override
    public final void moveMouse(Point gridPoint)
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
        
        //removeOutSideWorldSelection();
    }

    private void removeOutSideWorldSelection()
    {
        World world = getWorld.get();
        
        if(marking.x < 0)
        {
            marking.width -= Math.abs(marking.x);
            marking.x = 0;
        }
        if(marking.y < 0)
        {
            marking.height -= Math.abs(marking.y);
            marking.y = 0;
        }
        
        int overflowx = (marking.x + marking.width) - world.getGridSize();
        int overflowy = (marking.y + marking.height) - world.getGridSize();
        
        if(overflowx > 0)
            marking.width -= overflowx;
        if(overflowy > 0)
            marking.height -= overflowy;
    }

    @Override
    public final void releaseMouse(Point gridPoint)
    {
        //can be null because left and right mouse buttons aren't handled yet
        if(marking != null)
            performAction(marking);
        
        start = null;
        marking = null;
    }
    
    protected abstract void performAction(Rectangle marking);
}
