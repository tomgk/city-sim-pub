package org.exolin.citysim.model.connection.regular;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import org.exolin.citysim.model.Rotation;
import org.exolin.citysim.model.Structure;
import org.exolin.citysim.model.World;
import org.exolin.citysim.model.connection.Connection;
import org.exolin.citysim.model.connection.ConnectionType;
import static org.exolin.citysim.model.connection.regular.ConnectVariant.CONNECT_X;
import static org.exolin.citysim.model.connection.regular.ConnectVariant.CONNECT_Y;
import static org.exolin.citysim.model.connection.regular.Curve.*;
import static org.exolin.citysim.model.connection.regular.End.*;
import static org.exolin.citysim.model.connection.regular.TIntersection.*;
import static org.exolin.citysim.model.connection.regular.Unconnected.UNCONNECTED;
import static org.exolin.citysim.model.connection.regular.XIntersection.X_INTERSECTION;

/**
 *
 * @author Thomas
 */
public class SelfConnection extends Connection<SelfConnection, SelfConnectionType, ConnectionVariant>
{
    public SelfConnection(SelfConnectionType type, int x, int y, ConnectionVariant variant)
    {
        super(type, x, y, variant);
    }

    private Connection getStreet(World world, int x, int y, boolean xDirection)
    {
        Structure b = world.getBuildingAt(x, y);
        if(b == null)
            return null;
        
        //TODO: add cross logic
        
        if(!(b instanceof Connection))
            return null;
        
        Connection<?, ?, ?> s = (Connection) b;
        
        ConnectionType<?, ?, ?> streetType = s.getType();
        SelfConnectionType conType = xDirection ? streetType.getXType() : streetType.getYType();
        
        if(conType != getType())
            return null;
        
        return (Connection)b;
    }

    @Override
    public ConnectionVariant getVariant(Rotation rotation)
    {
        return getVariant().rotate(rotation);
    }
    
    private final List<Connection> connections = new ArrayList<>(4);

    private void addConnection(Connection street)
    {
        if(street != null)
            connections.add(street);
    }
    
    @Override
    protected void updateAfterChange(World world)
    {
        Connection x_before_street = getStreet(world, getX()-1, getY(), true);
        Connection x_after_street = getStreet(world, getX()+1, getY(), true);
        Connection y_before_street = getStreet(world, getX(), getY()-1, false);
        Connection y_after_street = getStreet(world, getX(), getY()+1, false);
        
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
