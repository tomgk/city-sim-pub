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
        
        if(containsStreet(world, getX()-1, getY()) || containsStreet(world, getX()+1, getY()))
            setVariant(StreetType.CONNECT_X);
        
        else if(containsStreet(world, getX(), getY()-1) || containsStreet(world, getX(), getY()+1))
            setVariant(StreetType.CONNECT_Y);
    }
}
