package org.exolin.citysim.model;

import java.awt.Point;
import org.exolin.citysim.utils.Utils;

/**
 * Rotation view on the world.
 * 
 * @author Thomas
 */
public enum Rotation
{
    ORIGINAL{
        @Override
        protected void rotateTop0(int gridSize, int x, int y, int size, Point point)
        {
            point.x = x;
            point.y = y;
        }
    },
    PLUS_90{
        @Override
        protected void rotateTop0(int gridSize, int x, int y, int size, Point point)
        {
            point.x = gridSize - y - 1 - size;
            point.y = x;
        }
    },
    PLUS_180{
        @Override
        protected void rotateTop0(int gridSize, int x, int y, int size, Point point)
        {
            point.x = gridSize - x - 1 - size;
            point.y = gridSize - y - 1 - size;
        }
    },
    PLUS_270{
        @Override
        protected void rotateTop0(int gridSize, int x, int y, int size, Point point)
        {
            point.x = y;
            point.y = gridSize - x - 1 - size;
        }
    };
    
    private static final Rotation[] values = values();
    private Rotation counterRotation;
    static
    {
        ORIGINAL.counterRotation = ORIGINAL;
        PLUS_180.counterRotation = PLUS_180;
        PLUS_90.counterRotation = PLUS_270;
        PLUS_270.counterRotation = PLUS_90;
    }
    
    public int getAmount()
    {
        return ordinal();
    }
    
    public Rotation getNext()
    {
        return Utils.getNext(values, this);
    }
    
    public Rotation getPrev()
    {
        return Utils.getPrev(values, this);
    }
    
    public void counterRotate(int gridSize, int x, int y, Point point)
    {
        counterRotation.rotate(gridSize, x, y, point);
    }
    
    public void rotate(int gridSize, int x, int y, Point point)
    {
        rotateTop(gridSize, x, y, 1, point);
    }
    
    public void rotateTop(int gridSize, int x, int y, int size, Point point)
    {
        rotateTop0(gridSize, x, y, size-1, point);
    }
    
    protected abstract void rotateTop0(int gridSize, int x, int y, int size, Point point);
}
