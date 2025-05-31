package org.exolin.citysim.model;

import static java.awt.BorderLayout.EAST;
import static java.awt.BorderLayout.NORTH;
import static java.awt.BorderLayout.SOUTH;
import static java.awt.BorderLayout.WEST;
import java.util.EnumSet;
import java.util.Set;
import org.exolin.citysim.bt.StructureTypes;
import org.exolin.citysim.model.connection.regular.ConnectVariant;
import static org.exolin.citysim.model.connection.regular.ConnectVariant.CONNECT_X;
import static org.exolin.citysim.model.connection.regular.ConnectVariant.CONNECT_Y;
import org.exolin.citysim.model.connection.regular.ConnectionVariant;
import static org.exolin.citysim.model.connection.regular.Curve.CURVE_1;
import static org.exolin.citysim.model.connection.regular.Curve.CURVE_2;
import static org.exolin.citysim.model.connection.regular.Curve.CURVE_3;
import static org.exolin.citysim.model.connection.regular.Curve.CURVE_4;
import static org.exolin.citysim.model.connection.regular.TIntersection.T_INTERSECTION_1;
import static org.exolin.citysim.model.connection.regular.TIntersection.T_INTERSECTION_2;
import static org.exolin.citysim.model.connection.regular.TIntersection.T_INTERSECTION_3;
import static org.exolin.citysim.model.connection.regular.TIntersection.T_INTERSECTION_4;
import static org.exolin.citysim.model.connection.regular.Unconnected.UNCONNECTED;
import static org.exolin.citysim.model.connection.regular.XIntersection.X_INTERSECTION;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

/**
 *
 * @author Thomas
 */
public class StructureVariantTest
{
    static
    {
        StructureTypes.init();
    }
    
    @Test
    public void testConnectVariant_Values()
    {
        Set<? extends StructureVariant> values = StructureVariant.getValues(ConnectVariant.class);
        assertEquals(EnumSet.of(CONNECT_X, CONNECT_Y), values);
    }
    
    //private static final Comparator<StructureVariant> COMPARE = Comparator.comparing((StructureVariant e) -> e);
    
    private final static Set Y = Set.of(
                CONNECT_X, CONNECT_Y, X_INTERSECTION, CURVE_1, CURVE_2, CURVE_3, CURVE_4, T_INTERSECTION_1, T_INTERSECTION_2, T_INTERSECTION_3, T_INTERSECTION_4, NORTH, WEST, SOUTH, EAST, UNCONNECTED
        );
    
    @Test
    public void testConnectVariant_Values_Size()
    {
        Set<? extends StructureVariant> values = StructureVariant.getValues(ConnectionVariant.class);
        
        Set x = Y;
        
        assertEquals(x.size(), values.size());
    }
    
    @Test
    @Disabled
    public void testConnectVariant_Values_Values()
    {
        Set<? extends StructureVariant> values = StructureVariant.getValues(ConnectionVariant.class);
        
        Set x = Y;
        
        assertEquals(x, values);
    }
    
    @Test
    @Disabled
    public void testConnectVariant_Valuesx()
    {
        Set<? extends StructureVariant> values = StructureVariant.getValues(ConnectionVariant.class);
        assertEquals(EnumSet.of(CONNECT_X, CONNECT_Y), values);
    }
}
