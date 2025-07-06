package org.exolin.citysim;

import org.exolin.citysim.bt.StructureTypes;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

/**
 *
 * @author Thomas
 */
public class InfoClassesTest
{
    static
    {
        StructureTypes.init();
    }
    
    @Test
    public void testClassInfo()
    {
        String expected = 
                        """
                        ==== BuildingType =====
                        Variants:
                          DEFAULT (0°)
                          ROTATED (90°)
                        ==== CrossConnectionType =====
                        Super: ConnectionType
                        No variants
                        ==== FireType =====
                        Variants: V1, V2, V3, V4, V5, V6, V7, V8, V9, V10, V11, V12, V13, V14, V15, V16
                        ==== PlantType =====
                        Variants:
                          DEFAULT (X: 0, Y: 0)
                          LEFT (X: -1, Y: 0)
                          RIGHT (X: 1, Y: 0)
                          TOP_LEFT (X: -1, Y: -1)
                          TOP_MIDDLE (X: 0, Y: -1)
                          TOP_RIGHT (X: 1, Y: -1)
                          BOTTOM_LEFT (X: -1, Y: -1)
                          BOTTOM_MIDDLE (X: 0, Y: -1)
                          BOTTOM_RIGHT (X: 1, Y: -1)
                        ==== SelfConnectionType =====
                        Super: ConnectionType
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
                          End.NORTH (^)
                          End.WEST (<)
                          End.SOUTH (v)
                          End.EAST (>)
                          Unconnected.UNCONNECTED
                        ==== VacantType =====
                        No variants
                        ==== ZoneType =====
                        No variants
                        """;
        
        assertEquals(expected, StringPrinter.toString(InfoClasses::classInfo));
    }
}
