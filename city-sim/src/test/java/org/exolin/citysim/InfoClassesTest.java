package org.exolin.citysim;

import java.util.Set;
import java.util.stream.Collectors;
import static org.exolin.citysim.TestUtils.assertEqualSet;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

/**
 *
 * @author Thomas
 */
public class InfoClassesTest
{
    @Test
    public void testClassInfo()
    {
        String expected = 
                        """
                        ==== BuildingType =====
                        Variants: DEFAULT, ROTATED
                        ==== CrossConnectionType =====
                        Variants: DEFAULT
                        ==== FireType =====
                        Variants: V1, V2, V3, V4, V5, V6, V7, V8, V9, V10, V11, V12, V13, V14, V15, V16
                        ==== PlantType =====
                        Variants: DEFAULT, LEFT, RIGHT, TOP_LEFT, TOP_MIDDLE, TOP_RIGHT, BOTTOM_LEFT, BOTTOM_MIDDLE, BOTTOM_RIGHT
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
                        ==== VacantType =====
                        Variants: DEFAULT
                        ==== ZoneType =====
                        Variants: DEFAULT
                        """;
        
        assertEquals(expected, StringPrinter.print(InfoClasses::classInfo));
    }
}
