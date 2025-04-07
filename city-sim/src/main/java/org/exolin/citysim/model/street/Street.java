package org.exolin.citysim.model.street;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
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

    private Street getStreet(World world, int x, int y)
    {
        Building b = world.getBuildingAt(x, y);
        if(b == null)
            return null;
        
        if(b.getType() != getType())
            return null;
        
        return (Street)b;
    }

    @Override
    public StreetVariant getVariant(Rotation rotation)
    {
        return getVariant().rotate(rotation);
    }
    
    private final List<Street> connections = new ArrayList<>(4);

    private void addConnection(Street street)
    {
        if(street != null)
            connections.add(street);
    }
    
    @Override
    protected void update(World world)
    {
        Street x_before_street = getStreet(world, getX()-1, getY());
        Street x_after_street = getStreet(world, getX()+1, getY());
        Street y_before_street = getStreet(world, getX(), getY()-1);
        Street y_after_street = getStreet(world, getX(), getY()+1);
        
        connections.clear();
        addConnection(x_before_street);
        addConnection(x_after_street);
        addConnection(y_before_street);
        addConnection(y_after_street);
        
        boolean x_before = x_before_street != null;
        boolean x_after = x_after_street != null;
        
        boolean y_before = y_before_street != null;
        boolean y_after = y_after_street != null;
        
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

    @Override
    public BigDecimal getMaintenance()
    {
        return BigDecimal.valueOf(getType().getCost()).divide(BigDecimal.valueOf(100));
    }
}
