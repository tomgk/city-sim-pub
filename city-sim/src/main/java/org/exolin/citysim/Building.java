package org.exolin.citysim;

import java.awt.Image;
import java.awt.Rectangle;

/**
 *
 * @author Thomas
 */
public class Building
{
    private final BuildingType type;
    private final int x;
    private final int y;

    public Building(BuildingType type, int x, int y)
    {
        this.type = type;
        this.x = x;
        this.y = y;
    }

    public Image getImage()
    {
        return type.getImage();
    }
    
    public int getSize()
    {
        return type.getSize();
    }

    public int getX()
    {
        return x;
    }

    public int getY()
    {
        return y;
    }

    public int getLevel()
    {
        //each +1 of x and y pushes further down
        return x + y + type.getSize() * 2;
    }
    
    public static int getLevel(Rectangle r)
    {
        return r.x+r.y+r.width+r.height;
    }
    
    public boolean isOccupying(int x, int y)
    {
        if(this.x < x)
            return false;
        
        if(this.x+type.getSize() >= x)
            return false;
        
        if(this.y < y)
            return false;
        
        if(this.y + type.getSize() >= x)
            return false;
        
        return true;
    }
}
