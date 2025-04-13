package org.exolin.citysim.bt;

import org.exolin.citysim.model.Animation;
import org.exolin.citysim.model.street.StreetType;
import org.exolin.citysim.model.street.cross.CrossConnectionType;

/**
 *
 * @author Thomas
 */
public class CrossConnections
{
    public static CrossConnectionType createCrossConnectionType(StreetType type1, StreetType type2, String name)
    {
        return new CrossConnectionType(name, Animation.createUnanimated("cross_connection/"+name), 1, type1, type2);
    }
}
