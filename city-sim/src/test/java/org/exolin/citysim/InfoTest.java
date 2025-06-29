package org.exolin.citysim;

import java.util.Map;
import java.util.function.Consumer;
import java.util.stream.Stream;
import org.exolin.citysim.bt.StructureTypes;
import org.exolin.citysim.bt.buildings.BusinessBuildings;
import org.exolin.citysim.model.StructureType;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

/**
 *
 * @author Thomas
 */
public class InfoTest
{
    static
    {
        StructureTypes.init();
    }
    
    private static class StringPrinter implements Info.Printer
    {
        private final StringBuilder out = new StringBuilder();

        @Override
        public void println(String str)
        {
            out.append(str).append("\n");
        }
    }
    
    private static String print(Consumer<StringPrinter> out)
    {
        StringPrinter p = new StringPrinter();
        out.accept(p);
        return p.out.toString();
    }
    
    @Test
    public void testClassInfo()
    {
        String expected = """
                        ==== ZoneType =====
                        Variants: DEFAULT
                        ==== BuildingType =====
                        Variants: DEFAULT, ROTATED
                        ==== SelfConnectionType =====
                        Variants:
                          StraightConnectionVariant.CONNECT_X
                          StraightConnectionVariant.CONNECT_Y
                          XIntersection.X_INTERSECTION
                          Curve.CURVE_1
                          Curve.CURVE_2
                          Curve.CURVE_3
                          Curve.CURVE_4
                          TIntersection.T_INTERSECTION_1
                          TIntersection.T_INTERSECTION_2
                          TIntersection.T_INTERSECTION_3
                          TIntersection.T_INTERSECTION_4
                          End.NORTH
                          End.WEST
                          End.SOUTH
                          End.EAST
                          Unconnected.UNCONNECTED
                        ==== CrossConnectionType =====
                        Variants: DEFAULT
                        ==== FireType =====
                        Variants: V1, V2, V3, V4, V5, V6, V7, V8, V9, V10, V11, V12, V13, V14, V15, V16
                        ==== VacantType =====
                        Variants: DEFAULT
                        """;
        
        assertEquals(expected, print(Info::classInfo));
    }
    
    private static final Map<StructureType<?, ?, ?>, String> EXPECTED = Map.ofEntries(
            Map.entry(
                    BusinessBuildings.car_cinema,
                    """
                    ==== business_car-cinema =====
                    Type: BuildingType
                    Cost: -
                    Zone: zone_business
                    """
            )
    );
    
    @ParameterizedTest
    @MethodSource("types")
    public void testTypeInfo(StructureType<?, ?, ?> type)
    {
        String expected = EXPECTED.get(type);
        if(expected == null)
            return;
        
        String actual = print(out -> Info.typeInfo(out, type));
        assertEquals(expected, actual);
    }
    
    public static void main(String[] args)
    {
        System.out.println(StructureType.types());
    }
    
    private static Stream<Arguments> types()
    {
        return StructureType.types()
                .stream()
                .map(Arguments::of);
    }
}
