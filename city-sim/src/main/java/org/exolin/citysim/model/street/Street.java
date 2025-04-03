package org.exolin.citysim.model.street;

import org.exolin.citysim.model.Building;
import org.exolin.citysim.model.Rotation;
import org.exolin.citysim.model.World;
import static org.exolin.citysim.model.street.StreetType.ConnectVariant.CONNECT_X;
import static org.exolin.citysim.model.street.StreetType.ConnectVariant.CONNECT_Y;
import static org.exolin.citysim.model.street.StreetType.Curve.*;
import static org.exolin.citysim.model.street.StreetType.TIntersection.*;
import static org.exolin.citysim.model.street.StreetType.XIntersection.X_INTERSECTION;

/**
 *
 * @author Thomas
 */
public class Street extends Building<Street, StreetType, StreetType.StreetVariant>
{
    public Street(StreetType type, int x, int y, StreetType.StreetVariant variant)
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
    public StreetType.StreetVariant getVariant(Rotation rotation)
    {
        StreetType.StreetVariant variant = getVariant();
        
        if(variant == CONNECT_X || variant == CONNECT_Y)
            return StreetType.StreetVariant.rotate(variant, rotation,
                        CONNECT_X,
                        CONNECT_Y,
                        CONNECT_X,
                        CONNECT_Y);
        
        if(variant == CURVE_1 || variant == CURVE_2 || variant == CURVE_3 || variant == CURVE_4)
                return StreetType.StreetVariant.rotate(variant, rotation,
                        CURVE_1, CURVE_2, CURVE_3, CURVE_4);
        
        if(variant == T_INTERSECTION_1 || variant == T_INTERSECTION_2 || variant == T_INTERSECTION_3 || variant == T_INTERSECTION_4)
                return StreetType.StreetVariant.rotate(variant, rotation,
                        T_INTERSECTION_1, T_INTERSECTION_2, T_INTERSECTION_3, T_INTERSECTION_4);
        
        if(variant == X_INTERSECTION)
            return X_INTERSECTION;
        
        throw new AssertionError();
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
