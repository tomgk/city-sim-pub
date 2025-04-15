package org.exolin.citysim.model.street.regular;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import org.exolin.citysim.model.Building;
import org.exolin.citysim.model.Rotation;
import org.exolin.citysim.model.World;
import org.exolin.citysim.model.street.AnyStreet;
import org.exolin.citysim.model.street.AnyStreetType;
import static org.exolin.citysim.model.street.regular.ConnectVariant.CONNECT_X;
import static org.exolin.citysim.model.street.regular.ConnectVariant.CONNECT_Y;
import static org.exolin.citysim.model.street.regular.Curve.*;
import static org.exolin.citysim.model.street.regular.End.*;
import static org.exolin.citysim.model.street.regular.TIntersection.*;
import static org.exolin.citysim.model.street.regular.Unconnected.UNCONNECTED;
import static org.exolin.citysim.model.street.regular.XIntersection.X_INTERSECTION;

/**
 *
 * @author Thomas
 */
public class Street extends AnyStreet<Street, StreetType, StreetVariant>
{
    public Street(StreetType type, int x, int y, StreetVariant variant)
    {
        super(type, x, y, variant);
    }

    private AnyStreet getStreet(World world, int x, int y, boolean xDirection)
    {
        Building b = world.getBuildingAt(x, y);
        if(b == null)
            return null;
        
        //TODO: add cross logic
        
        if(!(b instanceof AnyStreet))
            return null;
        
        AnyStreet<?, ?, ?> s = (AnyStreet) b;
        
        AnyStreetType<?, ?, ?> streetType = s.getType();
        StreetType conType = xDirection ? streetType.getXType() : streetType.getYType();
        
        if(conType != getType())
            return null;
        
        return (AnyStreet)b;
    }

    @Override
    public StreetVariant getVariant(Rotation rotation)
    {
        return getVariant().rotate(rotation);
    }
    
    private final List<AnyStreet> connections = new ArrayList<>(4);

    private void addConnection(AnyStreet street)
    {
        if(street != null)
            connections.add(street);
    }
    
    @Override
    protected void update(World world)
    {
        AnyStreet x_before_street = getStreet(world, getX()-1, getY(), true);
        AnyStreet x_after_street = getStreet(world, getX()+1, getY(), true);
        AnyStreet y_before_street = getStreet(world, getX(), getY()-1, false);
        AnyStreet y_after_street = getStreet(world, getX(), getY()+1, false);
        
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
        
        else if(x_before && x_after)
            setVariant(world, CONNECT_X);
        
        else if(y_before && y_after)
            setVariant(world, CONNECT_Y);
        
        //----- ends
        else if(x_before)
            setVariant(world, NORTH);
        else if(x_after)
            setVariant(world, SOUTH);
        
        else if(y_before)
            setVariant(world, WEST);
        
        else if(y_after)
            setVariant(world, EAST);
        
        else if(!x_before && !x_after && !y_before && !y_after)
            setVariant(world, UNCONNECTED);
    }

    @Override
    public BigDecimal getMaintenance()
    {
        return BigDecimal.valueOf(getType().getCost()).divide(BigDecimal.valueOf(100));
    }

    @Override
    public BigDecimal getTaxRevenue()
    {
        return BigDecimal.ZERO;
    }
}
