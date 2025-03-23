package org.exolin.citysim.ui;

import java.awt.Point;
import java.awt.Rectangle;

/**
 *
 * @author Thomas
 */
public abstract class AreaAction implements Action
{
    private Point start;
    private Rectangle marking;

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
    }

    @Override
    public final void releaseMouse(Point gridPoint)
    {
        performAction(marking);
        start = null;
        marking = null;
    }
    
    protected abstract void performAction(Rectangle marking);
}
