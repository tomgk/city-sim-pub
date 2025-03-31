package org.exolin.citysim;

import java.awt.Image;
import java.awt.Rectangle;

/**
 *
 * @author Thomas
 * @param <B> building class
 * @param <T> building type
 * @param <E> building variant
 */
public abstract class Building<B, T extends BuildingType<B, E>, E extends Enum<E>>
{
    private final T type;
    private final int x;
    private final int y;
    private E variant;
    
    public Building(T type, int x, int y, E variant)
    {
        type.checkVariant(variant);
        
        this.type = type;
        this.x = x;
        this.y = y;
        this.variant = variant;
    }

    public T getType()
    {
        return type;
    }

    public Image getImage()
    {
        return type.getImage(variant.ordinal()).getDefault();
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

    public E getVariant()
    {
        return variant;
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
    
    protected void setVariant(World w, E variant)
    {
        type.checkVariant(variant);
        this.variant = variant;
        w.onChange();
    }

    void update(World world)
    {
        
    }
    
    /**
     * @return zone type or {@code null} if not belonging to a zone type
     */
    public ZoneType getZoneType()
    {
        return null;
    }

    /**
     * @return how much supply this building gives to the demand
     */
    public int getSupply()
    {
        //assume high rises
        int base = getSize() * 2;
        return base * base;
    }
}
