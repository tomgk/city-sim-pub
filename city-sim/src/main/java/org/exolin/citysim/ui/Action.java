package org.exolin.citysim.ui;

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
    void releaseMouse(Point gridPoint);
    Rectangle getSelection();
    Image getMarker();
    boolean scaleMarker();
    String getName();
    
    final class NoAction implements Action
    {
        @Override
        public void mouseDown(Point gridPoint)
        {
            
        }
        
        @Override
        public void moveMouse(Point gridPoint)
        {
            
        }
        
        @Override
        public void releaseMouse(Point gridPoint)
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
    }
}
