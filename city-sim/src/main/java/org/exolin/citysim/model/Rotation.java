package org.exolin.citysim.model;

import java.awt.Point;

/**
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
    
    public int getAmount()
    {
        return ordinal();
    }
    
    public Rotation getNext()
    {
        int num = ordinal()+1;
        if(num == values.length)
            num = 0;
        
        return values[num];
    }
    
    public Rotation getPrev()
    {
        int num = ordinal()-1;
        if(num == -1)
            num = values.length-1;
        
        return values[num];
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
