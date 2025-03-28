package org.exolin.citysim;

import java.io.Writer;

/**
 *
 * @author Thomas
 */
public class Street extends Building<Street, StreetType, StreetType.Variant>
{
    public Street(StreetType type, int x, int y, StreetType.Variant variant)
    {
        super(type, x, y, variant);
    }

    @Override
    public StreetType getType()
    {
        return (StreetType)super.getType();
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
        //System.out.println("Update street @ "+getX()+"/"+getY());
        
        boolean x_before = containsStreet(world, getX()-1, getY());
        boolean x_after = containsStreet(world, getX()+1, getY());
        
        boolean y_before = containsStreet(world, getX(), getY()-1);
        boolean y_after = containsStreet(world, getX(), getY()+1);
        
        if(x_before && x_after && y_before && y_after)
            setVariant(world, StreetType.Variant.X_INTERSECTION);
        
        //----- curves
        
        else if(x_before && !x_after && !y_before && y_after)
            setVariant(world, StreetType.Variant.CURVE_1);
        
        else if(!x_before && x_after && y_before && !y_after)
            setVariant(world, StreetType.Variant.CURVE_3);
        
        else if(!x_before && x_after && !y_before && y_after)
            setVariant(world, StreetType.Variant.CURVE_2);
        
        else if(x_before && !x_after && y_before && !y_after)
            setVariant(world, StreetType.Variant.CURVE_4);
        
        //----- t intersection
        
        else if(x_before && !x_after && y_before && y_after)
            setVariant(world, StreetType.Variant.T_INTERSECTION_1);
        
        else if(!x_before && x_after && y_before && y_after)
            setVariant(world, StreetType.Variant.T_INTERSECTION_3);
        
        else if(x_before && x_after && !y_before && y_after)
            setVariant(world, StreetType.Variant.T_INTERSECTION_2);
        
        else if(x_before && x_after && y_before && !y_after)
            setVariant(world, StreetType.Variant.T_INTERSECTION_4);
        
        //----- straight
        
        else if(x_before || x_after)
            setVariant(world, StreetType.Variant.CONNECT_X);
        
        else if(y_before || y_after)
            setVariant(world, StreetType.Variant.CONNECT_Y);
    }
}
