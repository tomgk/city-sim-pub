package org.exolin.citysim.ui.actions;

import java.awt.Cursor;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;

/**
 *
 * @author Thomas
 */
public interface Action
{
    void mouseDown(Point gridPoint);
    void moveMouse(Point gridPoint);
    void mouseReleased(Point gridPoint);
    Rectangle getSelection();
    Image getMarker();
    boolean scaleMarker();
    String getName();
    int getCost();

    default Cursor getCursor()
    {
        return null;
    }
    
    public static final NoAction NONE = NoAction.INSTANCE;
    
    public static boolean isNone(Action a)
    {
        return a instanceof NoAction;
    }
}

final class NoAction implements Action
{
    public static final NoAction INSTANCE = new NoAction();

    private NoAction()
    {
    }

    @Override
    public void mouseDown(Point gridPoint)
    {

    }

    @Override
    public void moveMouse(Point gridPoint)
    {

    }

    @Override
    public void mouseReleased(Point gridPoint)
    {

    }

    @Override
    public Rectangle getSelection()
    {
        return null;
    }

    @Override
    public Image getMarker()
    {
        return null;
    }

    @Override
    public boolean scaleMarker()
    {
        return false;
    }

    @Override
    public String getName()
    {
        return "none";
    }

    @Override
    public String toString()
    {
        return getName();
    }

    @Override
    public int getCost()
    {
        return 0;
    }
}