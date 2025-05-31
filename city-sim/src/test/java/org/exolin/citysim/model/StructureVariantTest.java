package org.exolin.citysim.model;

import static java.awt.BorderLayout.EAST;
import static java.awt.BorderLayout.NORTH;
import static java.awt.BorderLayout.SOUTH;
import static java.awt.BorderLayout.WEST;
import java.util.Collections;
import java.util.Comparator;
import java.util.EnumSet;
import java.util.Set;
import java.util.TreeSet;
import org.exolin.citysim.bt.StructureTypes;
import org.exolin.citysim.model.building.BuildingType;
import org.exolin.citysim.model.connection.cross.CrossConnectionType;
import org.exolin.citysim.model.connection.regular.ConnectVariant;
import static org.exolin.citysim.model.connection.regular.ConnectVariant.CONNECT_X;
import static org.exolin.citysim.model.connection.regular.ConnectVariant.CONNECT_Y;
import org.exolin.citysim.model.connection.regular.ConnectionVariant;
import static org.exolin.citysim.model.connection.regular.Curve.CURVE_1;
import static org.exolin.citysim.model.connection.regular.Curve.CURVE_2;
import static org.exolin.citysim.model.connection.regular.Curve.CURVE_3;
import static org.exolin.citysim.model.connection.regular.Curve.CURVE_4;
import org.exolin.citysim.model.connection.regular.SelfConnectionType;
import static org.exolin.citysim.model.connection.regular.TIntersection.T_INTERSECTION_1;
import static org.exolin.citysim.model.connection.regular.TIntersection.T_INTERSECTION_2;
import static org.exolin.citysim.model.connection.regular.TIntersection.T_INTERSECTION_3;
import static org.exolin.citysim.model.connection.regular.TIntersection.T_INTERSECTION_4;
import static org.exolin.citysim.model.connection.regular.Unconnected.UNCONNECTED;
import static org.exolin.citysim.model.connection.regular.XIntersection.X_INTERSECTION;
import org.exolin.citysim.model.zone.ZoneType;
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
    
    @Test
    public void testZoneType()
    {
        Set<? extends StructureVariant> values = StructureVariant.getValues(ZoneType.Variant.class);
        assertEquals(Set.of(ZoneType.Variant.DEFAULT), values);
    }
    
    @Test
    public void testBuildingType()
    {
        Set<? extends StructureVariant> values = StructureVariant.getValues(BuildingType.Variant.class);
        assertEquals(Set.of(BuildingType.Variant.DEFAULT), values);
    }
    
    private static final Comparator C = Comparator.comparing(Object::toString);
    
    private static Set create(Set a)
    {
        Set s = Collections.checkedSet(new TreeSet(C), ConnectionVariant.class);
        s.addAll(a);
        if(a.size() != s.size())
            throw new IllegalStateException();
        return s;
    }
    
    @Test
    @Disabled
    public void testSelfConnectionType()
    {
        Set<? extends StructureVariant> values = StructureVariant.getValues(StructureType.getStructureVariantClass(SelfConnectionType.class));
        Set expected = Set.of(
                CONNECT_X, CONNECT_Y, X_INTERSECTION, CURVE_1, CURVE_2, CURVE_3, CURVE_4, T_INTERSECTION_1, T_INTERSECTION_2, T_INTERSECTION_3, T_INTERSECTION_4, NORTH, WEST, SOUTH, EAST, UNCONNECTED
        );
        assertEquals(
                create(expected),
                create(values)
        );
    }
    
    @Test
    public void testCrossConnectionType()
    {
        Set<? extends StructureVariant> values = StructureVariant.getValues(CrossConnectionType.Variant.class);
        assertEquals(Set.of(CrossConnectionType.Variant.DEFAULT), values);
    }
}
