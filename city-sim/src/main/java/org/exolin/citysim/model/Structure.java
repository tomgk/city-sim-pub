package org.exolin.citysim.model;

import java.awt.Point;
import java.awt.Rectangle;
import java.math.BigDecimal;
import org.exolin.citysim.model.zone.ZoneType;

/**
 *
 * @author Thomas
 * @param <B> building class
 * @param <T> building type
 * @param <E> building variant
 * @param <D>
 */
public abstract class Structure<B, T extends StructureType<B, E, D>, E extends StructureVariant, D extends StructureData<D>>
{
    private final T type;
    private final int x;
    private final int y;
    private E variant;
    protected final D data;
    
    public Structure(T type, int x, int y, E variant, D data)
    {
        type.checkVariant(variant);
        
        this.type = type;
        this.x = x;
        this.y = y;
        this.variant = variant;
        this.data = data.copy();
    }

    public T getType()
    {
        return type;
    }

    public Animation getAnimation(Rotation rotation)
    {
        return type.getImage(getVariant(rotation));
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
    
    public void getViewLocation(int gridSize, Rotation rotation, Point point)
    {
        rotation.rotate(gridSize, x, y, point);
    }

    public E getVariant()
    {
        return variant;
    }
    
    public E getVariant(Rotation rotation)
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
    
    public int getLevel(int gridSize, Rotation rotation)
    {
        Point p = new Point(-1, -1);
        rotation.rotate(gridSize, x, y, p);
        return p.x + p.y + type.getSize() * 2 / 2;
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

    protected void updateAfterChange(World world)
    {
        
    }
    
    protected void updateAfterTick(World world, int ticks)
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

    @Override
    public String toString()
    {
        return getClass().getSimpleName()+"[x="+x+",y="+y+",size="+type.getSize()+",type="+type.getName()+"]";
    }
    
    public abstract BigDecimal getTaxRevenue();
    public abstract BigDecimal getMaintenance();
}
