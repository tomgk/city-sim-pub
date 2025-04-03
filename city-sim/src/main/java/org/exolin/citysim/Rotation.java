package org.exolin.citysim;

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
        switch(this)
        {
            case ORIGINAL:
                point.x = x;
                point.y = y;
                break;
            case PLUS_180:
                point.x = gridSize - x - 1;
                point.y = gridSize - y - 1;
                break;
                
            case PLUS_90:
                point.x = gridSize - y - 1;
                point.y = x;
                break;
                
            case PLUS_270:
                point.x = y;
                point.y = gridSize - x - 1;
                break;
                
            default:
                throw new AssertionError();
        }
    }
}
