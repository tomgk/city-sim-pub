package org.exolin.citysim;

import java.awt.Image;
import java.awt.Rectangle;
import java.io.IOException;
import java.io.Writer;

/**
 *
 * @author Thomas
 */
public abstract class Building
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

    public BuildingType getType()
    {
        return type;
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
        //divide size*2 by 2 to put level to center
        //which fixes the issue of smaller buildings getting hidden
        //by bigger ones on the upper half
        return x + y + type.getSize() * 2 / 2;
    }
    
    public static int getLevel(Rectangle r)
    {
        //divide r.width+r.height by 2 to put level to center
        //which fixes the issue of smaller buildings getting hidden
        //by bigger ones on the upper half
        return r.x+r.y+(r.width+r.height)/2;
    }
    
    public boolean isOccupying(int x, int y)
    {
        if(x < this.x)
            return false;
        
        if(x >= this.x+type.getSize())
            return false;
        
        if(y < this.y)
            return false;
        
        if(y >= this.y + type.getSize())
            return false;
        
        return true;
    }
    
    public void serialize(Writer out) throws IOException
    {
        out.write(Integer.toString(type.getId()));
        out.write(";");
        out.write(Integer.toString(x));
        out.write(";");
        out.write(Integer.toString(y));
        serializeImpl(out);
    }
    
    protected abstract void serializeImpl(Writer out) throws IOException;
}
