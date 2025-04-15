package org.exolin.citysim.bt;

import java.util.LinkedHashMap;
import java.util.Map;
import static org.exolin.citysim.bt.Streets.rail;
import static org.exolin.citysim.bt.Streets.street;
import org.exolin.citysim.model.Animation;
import org.exolin.citysim.model.street.StreetType;
import org.exolin.citysim.model.street.cross.CrossConnectionType;

/**
 *
 * @author Thomas
 */
public class CrossConnections
{
    private static final Map<Key, CrossConnectionType> types = new LinkedHashMap<>();
    
    public static CrossConnectionType STREET_RAIL = createCrossConnectionType(street, rail);
    public static CrossConnectionType RAIL_STREET = createCrossConnectionType(rail, street);
    
    record Key(StreetType xtype, StreetType ytype){}
    
    public static CrossConnectionType createCrossConnectionType(StreetType xtype, StreetType ytype)
    {
        String name = xtype.getName()+"_"+ytype.getName();
        System.out.println(name);
        CrossConnectionType type = new CrossConnectionType(name, Animation.createUnanimated("cross_connection/"+name), 1, xtype, ytype);
        types.put(new Key(xtype, ytype), type);
        return type;
    }
    
    public static CrossConnectionType get(StreetType xtype, StreetType ytype)
    {
        return types.get(new Key(xtype, ytype));
    }
}
