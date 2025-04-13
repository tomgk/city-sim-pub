package org.exolin.citysim.bt;

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
    public static CrossConnectionType STREET_RAIL = createCrossConnectionType(street, rail);
    public static CrossConnectionType RAIL_STREET = createCrossConnectionType(rail, street);
    
    public static CrossConnectionType createCrossConnectionType(StreetType type1, StreetType type2)
    {
        String name = type1.getName()+"_"+type2.getName();
        System.out.println(name);
        return new CrossConnectionType(name, Animation.createUnanimated("cross_connection/"+name), 1, type1, type2);
    }
}
