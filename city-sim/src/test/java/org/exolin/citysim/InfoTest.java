package org.exolin.citysim;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.exolin.citysim.bt.Plants;
import org.exolin.citysim.bt.StructureTypes;
import org.exolin.citysim.bt.Vacants;
import org.exolin.citysim.bt.Zones;
import org.exolin.citysim.bt.buildings.BusinessBuildings;
import org.exolin.citysim.bt.buildings.Parks;
import org.exolin.citysim.bt.buildings.PowerPlants;
import org.exolin.citysim.bt.connections.CrossConnections;
import org.exolin.citysim.bt.connections.SelfConnections;
import org.exolin.citysim.model.StructureType;
import org.exolin.citysim.model.fire.FireType;
import org.exolin.citysim.model.plant.PlantTypeType;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
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
            ),
            Map.entry(
                    Plants.get(PlantTypeType.TREE, 3),
                    """
                    ==== trees_3 =====
                    Type: PlantType
                    Cost: 9
                    Count: 3
                    Type: TREE
                    IsAlive: true
                    """
            ),
            Map.entry(
                    PowerPlants.fusion_plant,
                    """
                    ==== Fusion Power =====
                    Type: BuildingType
                    Cost: 40000
                    Zone: zone_plants
                    megaWatt: 2500 MW
                    """
            ),
            Map.entry(
                    Vacants.abandoned_middle_2,
                    """
                    ==== destruction/abandoned_middle_2 =====
                    Type: VacantType
                    Cost: -
                    """
            ),
            Map.entry(
                    Parks.zoo,
                    """
                    ==== parks_zoo =====
                    Type: BuildingType
                    Cost: 100
                    Zone: zone_parks
                    """
            ),
            Map.entry(
                    CrossConnections.RAIL_STREET,
                    """
                    ==== rail_street =====
                    Type: CrossConnectionType
                    Cost: -
                    X-Type: rail
                    Y-Type: street
                    """
            ),
            Map.entry(
                    SelfConnections.rail,
                    """
                    ==== rail =====
                    Type: SelfConnectionType
                    Cost: 25
                    """
            ),
            Map.entry(
                    FireType.fire,
                    """
                    ==== fire =====
                    Type: FireType
                    Cost: -
                    """
            ),
            Map.entry(
                    Zones.business,
                    """
                    ==== zone_business =====
                    Type: ZoneType
                    Cost: 5
                    """
            ),
            Map.entry(
                    Zones.parks,
                    """
                    ==== zone_parks =====
                    Type: ZoneType
                    Cost: -
                    """
            ),
            Map.entry(
                    Zones.plants,
                    """
                    ==== zone_plants =====
                    Type: ZoneType
                    Cost: -
                    """
            )
    );
    
    private void assertEqualSet(Set expected, Set actual)
    {
        if(expected.equals(actual))
            return;
        
        //expected without actual = what is missing
        Set missing = new HashSet(expected);
        missing.removeAll(actual);
        
        //actal without expected = tooMuch
        Set tooMuch = new HashSet(actual);
        tooMuch.removeAll(expected);
        
        List<String> msg = new ArrayList<>();
        if(!missing.isEmpty())
            msg.add("missing: "+missing);
        if(!tooMuch.isEmpty())
            msg.add("too much: "+tooMuch);
        
        fail(String.join("\n", msg));
    }
    
    @Test
    public void testTypeClasses()
    {
        Set<Class> expected = Info.getTypeClasses().collect(Collectors.toSet());
        Set<Class<?>> actual = EXPECTED.keySet()
                .stream()
                .map(Object::getClass)
                .collect(Collectors.toSet());
        
        assertEqualSet(expected, actual);
    }
    
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
