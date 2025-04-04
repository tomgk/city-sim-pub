package org.exolin.citysim.model;

import java.awt.Point;

/**
 *
 * @author Thomas
 */
public enum Rotation
{
    ORIGINAL,
    PLUS_90,
    PLUS_180,
    PLUS_270;
    
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

    public void rotate(int gridSize, int x, int y, Point point)
    {
        rotateTop(gridSize, x, y, 1, point);
    }
    
    public void rotateTop(int gridSize, int x, int y, int size, Point point)
    {
        size -= 1;
        
        switch(this)
        {
            case ORIGINAL:
                point.x = x;
                point.y = y;
                break;
            case PLUS_180:
                point.x = gridSize - x - 1 - size;
                point.y = gridSize - y - 1 - size;
                break;
                
            case PLUS_90:
                point.x = gridSize - y - 1 - size;
                point.y = x;
                break;
                
            case PLUS_270:
                point.x = y;
                point.y = gridSize - x - 1 - size;
                break;
                
            default:
                throw new AssertionError();
        }
    }
}
