package org.exolin.citysim.model.street;

import org.exolin.citysim.model.Building;
import org.exolin.citysim.model.Rotation;
import org.exolin.citysim.model.World;
import static org.exolin.citysim.model.street.ConnectVariant.CONNECT_X;
import static org.exolin.citysim.model.street.ConnectVariant.CONNECT_Y;
import static org.exolin.citysim.model.street.Curve.*;
import static org.exolin.citysim.model.street.TIntersection.*;
import static org.exolin.citysim.model.street.XIntersection.X_INTERSECTION;

/**
 *
 * @author Thomas
 */
public class Street extends Building<Street, StreetType, StreetVariant>
{
    public Street(StreetType type, int x, int y, StreetVariant variant)
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
    public StreetVariant getVariant(Rotation rotation)
    {
        return getVariant().rotate(rotation);
    }
    
    @Override
    protected void update(World world)
    {
        //System.out.println("Update street @ "+getX()+"/"+getY());
        
        boolean x_before = containsStreet(world, getX()-1, getY());
        boolean x_after = containsStreet(world, getX()+1, getY());
        
        boolean y_before = containsStreet(world, getX(), getY()-1);
        boolean y_after = containsStreet(world, getX(), getY()+1);
        
        if(x_before && x_after && y_before && y_after)
            setVariant(world, X_INTERSECTION);
        
        //----- curves
        
        else if(x_before && !x_after && !y_before && y_after)
            setVariant(world, CURVE_1);
        
        else if(!x_before && x_after && y_before && !y_after)
            setVariant(world, CURVE_3);
        
        else if(!x_before && x_after && !y_before && y_after)
            setVariant(world, CURVE_2);
        
        else if(x_before && !x_after && y_before && !y_after)
            setVariant(world, CURVE_4);
        
        //----- t intersection
        
        else if(x_before && !x_after && y_before && y_after)
            setVariant(world, T_INTERSECTION_1);
        
        else if(!x_before && x_after && y_before && y_after)
            setVariant(world, T_INTERSECTION_3);
        
        else if(x_before && x_after && !y_before && y_after)
            setVariant(world, T_INTERSECTION_2);
        
        else if(x_before && x_after && y_before && !y_after)
            setVariant(world, T_INTERSECTION_4);
        
        //----- straight
        
        else if(x_before || x_after)
            setVariant(world, CONNECT_X);
        
        else if(y_before || y_after)
            setVariant(world, CONNECT_Y);
    }
}
