package org.exolin.citysim;

import java.io.Writer;

/**
 *
 * @author Thomas
 */
public class Street extends Building
{
    public Street(StreetType type, int x, int y, int variant)
    {
        super(type, x, y, variant);
    }

    @Override
    public BuildingType getType()
    {
        return (StreetType)super.getType();
    }

    @Override
    protected void serializeImpl(Writer out)
    {
    }
    
    private boolean containsStreet(World world, int x, int y)
    {
        Building b = world.getBuildingAt(x, y);
        if(b == null)
            return false;
        
        return b.getType() == getType();
    }
    
    @Override
    void update(World world)
    {
        System.out.println("Update street @ "+getX()+"/"+getY());
        
        boolean x_before = containsStreet(world, getX()-1, getY());
        boolean x_after = containsStreet(world, getX()+1, getY());
        
        boolean y_before = containsStreet(world, getX(), getY()-1);
        boolean y_after = containsStreet(world, getX(), getY()+1);
        
        if(x_before && x_after && y_before && y_after)
            setVariant(StreetType.Variant.X_INTERSECTION);
        
        else if(x_before || x_after)
            setVariant(StreetType.Variant.CONNECT_X);
        
        else if(y_before || y_after)
            setVariant(StreetType.Variant.CONNECT_Y);
    }
}
