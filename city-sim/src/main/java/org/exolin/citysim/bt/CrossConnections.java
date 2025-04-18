package org.exolin.citysim.bt;

import java.util.LinkedHashMap;
import java.util.Map;
import static org.exolin.citysim.bt.SelfConnections.rail;
import static org.exolin.citysim.bt.SelfConnections.street;
import static org.exolin.citysim.bt.SelfConnections.water;
import org.exolin.citysim.model.Animation;
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
    public static CrossConnectionType RAIL_WATER = createCrossConnectionType(rail, water);
    public static CrossConnectionType WATER_RAIL = createCrossConnectionType(water, rail);
    
    record Key(SelfConnectionType xtype, SelfConnectionType ytype){}
    
    public static CrossConnectionType createCrossConnectionType(SelfConnectionType xtype, SelfConnectionType ytype)
    {
        String name = xtype.getName()+"_"+ytype.getName();
        System.out.println(name);
        CrossConnectionType type = new CrossConnectionType(name, Animation.createUnanimated("cross_connection/"+name), 1, xtype, ytype);
        types.put(new Key(xtype, ytype), type);
        return type;
    }
    
    public static CrossConnectionType get(SelfConnectionType xtype, SelfConnectionType ytype)
    {
        return types.get(new Key(xtype, ytype));
    }
}
