package org.exolin.citysim.model;

import java.util.Collections;
import java.util.Comparator;
import java.util.EnumSet;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Stream;
import org.exolin.citysim.bt.StructureTypes;
import org.exolin.citysim.model.building.BuildingType;
import org.exolin.citysim.model.connection.cross.CrossConnectionType;
import org.exolin.citysim.model.connection.regular.ConnectionVariant;
import static org.exolin.citysim.model.connection.regular.Curve.CURVE_1;
import static org.exolin.citysim.model.connection.regular.Curve.CURVE_2;
import static org.exolin.citysim.model.connection.regular.Curve.CURVE_3;
import static org.exolin.citysim.model.connection.regular.Curve.CURVE_4;
import static org.exolin.citysim.model.connection.regular.End.EAST;
import static org.exolin.citysim.model.connection.regular.End.NORTH;
import static org.exolin.citysim.model.connection.regular.End.SOUTH;
import static org.exolin.citysim.model.connection.regular.End.WEST;
import org.exolin.citysim.model.connection.regular.SelfConnectionType;
import org.exolin.citysim.model.connection.regular.StraightConnectionVariant;
import static org.exolin.citysim.model.connection.regular.StraightConnectionVariant.CONNECT_X;
import static org.exolin.citysim.model.connection.regular.StraightConnectionVariant.CONNECT_Y;
import static org.exolin.citysim.model.connection.regular.TIntersection.T_INTERSECTION_1;
import static org.exolin.citysim.model.connection.regular.TIntersection.T_INTERSECTION_2;
import static org.exolin.citysim.model.connection.regular.TIntersection.T_INTERSECTION_3;
import static org.exolin.citysim.model.connection.regular.TIntersection.T_INTERSECTION_4;
import static org.exolin.citysim.model.connection.regular.Unconnected.UNCONNECTED;
import static org.exolin.citysim.model.connection.regular.XIntersection.X_INTERSECTION;
import org.exolin.citysim.model.zone.ZoneType;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Named;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

/**
 *
 * @author Thomas
 */
public class StructureVariantTest
{
    static
    {
        StructureTypes.init();
        
        StructureVariant.getValues(StraightConnectionVariant.class);
        StructureVariant.getValues(ConnectionVariant.class);
    }
    
    @Test
    public void testGetValues_StraightConnectionVariant()
    {
        Set<? extends StructureVariant> values = StructureVariant.getValues(StraightConnectionVariant.class);
        assertEquals(EnumSet.of(CONNECT_X, CONNECT_Y), values);
    }
    
    private final static Set ALL_VALUS = Set.of(
            CONNECT_X, CONNECT_Y,
            X_INTERSECTION,
            
            CURVE_1, CURVE_2, CURVE_3, CURVE_4,
            
            T_INTERSECTION_1,
            T_INTERSECTION_2,
            T_INTERSECTION_3,
            T_INTERSECTION_4,
            
            NORTH, WEST, SOUTH, EAST,
            UNCONNECTED
        );
    
    @Test
    public void testGetVariantCount_ConnectionVariant_Size()
    {
        int size = StructureVariant.getVariantCount(ConnectionVariant.class);
        assertEquals(ALL_VALUS.size(), size);
    }
    
    @Test
    public void testGetValues_ConnectionVariant()
    {
        Set<? extends StructureVariant> values = StructureVariant.getValues(ConnectionVariant.class);
        assertEquals(ALL_VALUS, values);
    }
    
    @Test
    public void testGetValues_ZoneType()
    {
        Set<? extends StructureVariant> values = StructureVariant.getValues(ZoneType.Variant.class);
        assertEquals(Set.of(ZoneType.Variant.DEFAULT), values);
    }
    
    @Test
    public void testGetValues_BuildingType()
    {
        Set<? extends StructureVariant> values = StructureVariant.getValues(BuildingType.Variant.class);
        assertEquals(Set.of(BuildingType.Variant.DEFAULT, BuildingType.Variant.ROTATED), values);
    }
    
    private static Stream<Arguments> structureVariantClasses()
    {
        return StructureType.classes()
                .stream()
                .map(c -> StructureType.getStructureVariantClass(c))
                .map(t -> Arguments.of(Named.named(t.getSimpleName(), t)));
    }
    
    @ParameterizedTest
    @MethodSource("structureVariantClasses")
    public void testGetValues_All(Class c)
    {
        Set values = StructureVariant.getValues(c);
        assertNotNull(values);
        assertFalse(values.isEmpty());
        //check for type
        values.stream().forEach(c::cast);
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
        Set expected = ALL_VALUS;
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
