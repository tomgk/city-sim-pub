package org.exolin.citysim.model;

import java.awt.Point;
import java.awt.Rectangle;
import java.math.BigDecimal;
import java.util.Optional;
import org.exolin.citysim.model.zone.ZoneType;
import org.exolin.citysim.utils.PropertyWriter;

/**
 * Any structure on the terrain.
 * 
 * @author Thomas
 * @param <B> building class
 * @param <T> building type
 * @param <E> building variant
 * @param <D> additional data type
 */
public abstract class Structure<B, T extends StructureType<B, E, D>, E extends StructureVariant, D extends StructureParameters<D>>
{
    /**
     * type
     */
    private final T type;
    
    /**
     * X-coordinate for top left corner
     */
    private final int x;
    
    /**
     * Y-coordinate for top left corner
     */
    private final int y;
    
    /**
     * variant
     */
    private E variant;
    
    /**
     * additional data, otherwise {@link EmptyStructureParameters}
     */
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

    public D getDataCopy()
    {
        return data.copy();
    }

    protected D getDataRaw()
    {
        return data;
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
    
    /**
     * Calculates the level, which determines the order in which
     * structures get drawn.
     * 
     * @param gridSize grid size of world
     * @param rotation current rotation view
     * @return level
     */
    public int getLevel(int gridSize, Rotation rotation)
    {
        Point p = new Point(-1, -1);
        rotation.rotate(gridSize, x, y, p);
        return p.x + p.y + type.getSize() * 2 / 2;
    }
    
    /**
     * Calculates the level, which determines the order in which
     * structures get drawn.
     * 
     * @param r position and size
     * @return level
     */
    public static int getLevel(Rectangle r)
    {
        //divide r.width+r.height by 2 to put level to center
        //which fixes the issue of smaller buildings getting hidden
        //by bigger ones on the upper half
        return r.x+r.y+(r.width+r.height)/2;
    }
    
    /**
     * Returns if the structure occupies the given coordinates
     * 
     * @param x X-coordinate
     * @param y y-coordinate
     * @return {@code true} if structure is there, otherwise {@code false}
     */
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

    /**
     * Gets alled when something changed around the structure.
     * 
     * @param world the world
     */
    protected void updateAfterChange(World world)
    {
        
    }
    
    /**
     * Gets called periodically.
     * 
     * @param world the world
     * @param ticks the number of ticks (can be higher than 1 if sped up)
     */
    protected void updateAfterTick(World world, int ticks)
    {
        
    }
    
    /**
     * @return zone type or {@code null} if not belonging to a zone type
     */
    public Optional<ZoneType> getZoneType()
    {
        return Optional.empty();
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
        PropertyWriter sb = new PropertyWriter(getClass().getSimpleName());
        
        sb.add("x", x);
        sb.add("y", y);
        sb.add("size", type.getSize());
        sb.add("type", type.getName());
        
        data.writeAdditional(sb);
        
        sb.finish();
        
        return sb.toString();
    }
    
    /**
     * Returns the tax revenue.
     * 
     * @return tax revenue
     */
    public abstract BigDecimal getTaxRevenue();
    
    /**
     * Returns the maintenance cost for the structure.
     * 
     * @return maintenance cost
     */
    public abstract BigDecimal getMaintenance();
    
    /**
     * Returns if zone should be drawn too.
     * 
     * @return if zone should be drawn
     */
    public boolean drawZone()
    {
        return false;
    }
}
