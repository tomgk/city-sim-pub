package org.exolin.citysim.bt.connections;

import java.util.LinkedHashMap;
import java.util.Map;
import static org.exolin.citysim.bt.connections.SelfConnections.circuit;
import static org.exolin.citysim.bt.connections.SelfConnections.rail;
import static org.exolin.citysim.bt.connections.SelfConnections.street;
import static org.exolin.citysim.bt.connections.SelfConnections.water;
import org.exolin.citysim.model.Animation;
import static org.exolin.citysim.model.StructureSize._1;
import org.exolin.citysim.model.connection.ConnectionType;
import org.exolin.citysim.model.connection.cross.CrossConnectionType;
import org.exolin.citysim.model.connection.regular.SelfConnectionType;

/**
 *
 * @author Thomas
 */
public class CrossConnections
{
    private static final Map<Key, CrossConnectionType> types = new LinkedHashMap<>();
    
    public static CrossConnectionType STREET_RAIL = createCrossConnectionType(street, rail);
    public static CrossConnectionType RAIL_STREET = createCrossConnectionType(rail, street);
    public static CrossConnectionType STREET_WATER = createCrossConnectionType(street, water);
    public static CrossConnectionType WATER_STREET = createCrossConnectionType(water, street);
    public static CrossConnectionType STREET_CIRCUIT = createCrossConnectionType(street, circuit);
    public static CrossConnectionType CIRCUIT_STREET = createCrossConnectionType(circuit, street);
    
    public static CrossConnectionType RAIL_WATER = createCrossConnectionType(rail, water);
    public static CrossConnectionType WATER_RAIL = createCrossConnectionType(water, rail);
    public static CrossConnectionType RAIL_CIRCUIT = createCrossConnectionType(rail, circuit);
    public static CrossConnectionType CIRCUIT_RAIL = createCrossConnectionType(circuit, rail);
    
    public static CrossConnectionType CIRCUIT_WATER = createCrossConnectionType(circuit, water);
    public static CrossConnectionType WATER_CIRCUIT = createCrossConnectionType(water, circuit);
    
    record Key(SelfConnectionType xtype, SelfConnectionType ytype){}
    
    public static CrossConnectionType createCrossConnectionType(SelfConnectionType xtype, SelfConnectionType ytype)
    {
        String name = xtype.getName()+"_"+ytype.getName();
        CrossConnectionType type = new CrossConnectionType(name, Animation.createUnanimated("cross_connection/"+name), _1, xtype, ytype);
        types.put(new Key(xtype, ytype), type);
        return type;
    }
    
    public static ConnectionType<?, ?, ?, ?> get(SelfConnectionType xtype, SelfConnectionType ytype)
    {
        if(xtype == ytype)
            return xtype;
        
        CrossConnectionType ct = types.get(new Key(xtype, ytype));
        if(ct == null)
            throw new UnsupportedOperationException(xtype.getName()+"/"+ytype.getName());
        return ct;
    }
}
