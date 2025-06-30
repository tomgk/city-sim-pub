package org.exolin.citysim;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import static org.exolin.citysim.TestUtils.assertEqualSet;
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
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

/**
 *
 * @author Thomas
 */
public class InfoResourcesTest
{
    static
    {
        StructureTypes.init();
    }
    
    private static final Map<StructureType<?, ?, ?>, String> EXPECTED_TYPEINFO = Map.ofEntries(
            Map.entry(
                    BusinessBuildings.car_cinema,
                    """
                    ==== business_car-cinema =====
                    Image:
                     - DEFAULT/ROTATED: business/car-cinema, 9 f, 1000 ms/f
                    """
            ),
            Map.entry(
                    Plants.get(PlantTypeType.TREE, 3),
                    """
                    ==== trees_3 =====
                    Image:
                     - DEFAULT: trees_3
                     - LEFT: trees_3@LEFT
                     - RIGHT: trees_3@RIGHT
                     - TOP_LEFT: trees_3@TOP_LEFT
                     - TOP_MIDDLE: trees_3@TOP_MIDDLE
                     - TOP_RIGHT: trees_3@TOP_RIGHT
                     - BOTTOM_LEFT: trees_3@BOTTOM_LEFT
                     - BOTTOM_MIDDLE: trees_3@BOTTOM_MIDDLE
                     - BOTTOM_RIGHT: trees_3@BOTTOM_RIGHT
                    """
            ),
            Map.entry(
                    PowerPlants.fusion_plant,
                    """
                    ==== Fusion Power =====
                    Image:
                     - DEFAULT/ROTATED: plants/fusion_plant
                    """
            ),
            Map.entry(
                    PowerPlants.hydro_plant,
                    """
                    ==== Hydro Power =====
                    Image:
                     - DEFAULT: plants/hydro_x
                     - ROTATED: plants/hydro_y
                    """
            ),
            Map.entry(
                    Vacants.abandoned_middle_2,
                    """
                    ==== destruction/abandoned_middle_2 =====
                    Image: destruction/abandoned_middle_2
                    """
            ),
            Map.entry(
                    Parks.zoo,
                    """
                    ==== parks_zoo =====
                    Image:
                     - DEFAULT/ROTATED: parks/zoo
                    """
            ),
            Map.entry(
                    CrossConnections.RAIL_STREET,
                    """
                    ==== rail_street =====
                    Image: cross_connection/rail_street
                    """
            ),
            Map.entry(
                    CrossConnections.WATER_CIRCUIT,
                    """
                    ==== water_circuit =====
                    Image: cross_connection/water_circuit
                    """
            ),
            Map.entry(
                    SelfConnections.street,
                    """
                    ==== street =====
                    DefaultImage: X_INTERSECTION
                    Image:
                     - CONNECT_X: street/street_1
                     - CONNECT_Y: street/street_2
                     - X_INTERSECTION: street/street_x_intersection, 5 f, 1000 ms/f
                     - CURVE_1: street/street_curve_1
                     - CURVE_2: street/street_curve_2
                     - CURVE_3: street/street_curve_3
                     - CURVE_4: street/street_curve_4
                     - T_INTERSECTION_1: street/street_t_1
                     - T_INTERSECTION_2: street/street_t_2
                     - T_INTERSECTION_3: street/street_t_3
                     - T_INTERSECTION_4: street/street_t_4
                     - NORTH: street/street_1
                     - WEST: street/street_2
                     - SOUTH: street/street_1
                     - EAST: street/street_2
                     - UNCONNECTED: street/street_1
                    """
            ),
            Map.entry(
                    SelfConnections.rail,
                    """
                    ==== rail =====
                    DefaultImage: CONNECT_X
                    Image:
                     - CONNECT_X/NORTH/SOUTH/UNCONNECTED: rail/rail_1
                     - CONNECT_Y/WEST/EAST: rail/rail_2
                     - X_INTERSECTION: rail/rail_x_intersection
                     - CURVE_1: rail/rail_curve_1
                     - CURVE_2: rail/rail_curve_2
                     - CURVE_3: rail/rail_curve_3
                     - CURVE_4: rail/rail_curve_4
                     - T_INTERSECTION_1: rail/rail_t_1
                     - T_INTERSECTION_2: rail/rail_t_2
                     - T_INTERSECTION_3: rail/rail_t_3
                     - T_INTERSECTION_4: rail/rail_t_4
                    """
            ),
            Map.entry(
                    FireType.fire,
                    """
                    ==== fire =====
                    DefaultImage: V1
                    Image:
                     - V1: destruction/fire, 16 f, 500 ms/f
                     - V2: destruction/fire2, 16 f, 500 ms/f
                     - V3: destruction/fire3, 16 f, 500 ms/f
                     - V4: destruction/fire4, 16 f, 500 ms/f
                     - V5: destruction/fire5, 16 f, 500 ms/f
                     - V6: destruction/fire6, 16 f, 500 ms/f
                     - V7: destruction/fire7, 16 f, 500 ms/f
                     - V8: destruction/fire8, 16 f, 500 ms/f
                     - V9: destruction/fire9, 16 f, 500 ms/f
                     - V10: destruction/fire10, 16 f, 500 ms/f
                     - V11: destruction/fire11, 16 f, 500 ms/f
                     - V12: destruction/fire12, 16 f, 500 ms/f
                     - V13: destruction/fire13, 16 f, 500 ms/f
                     - V14: destruction/fire14, 16 f, 500 ms/f
                     - V15: destruction/fire15, 16 f, 500 ms/f
                     - V16: destruction/fire16, 16 f, 500 ms/f
                    """
            ),
            Map.entry(
                    Zones.business,
                    """
                    ==== zone_business =====
                    Image: zone/business
                    """
            ),
            Map.entry(
                    Zones.low_business,
                    """
                    ==== zone_business_low =====
                    Image: zone/business_low
                    """
            ),
            Map.entry(
                    Zones.parks,
                    """
                    ==== zone_parks =====
                    Image: zone/special
                    """
            ),
            Map.entry(
                    Zones.plants,
                    """
                    ==== zone_plants =====
                    Image: zone/special
                    """
            )
    );
    
    @Test
    public void testTypeClasses()
    {
        Set<Class> expected = InfoClasses.getTypeClasses().collect(Collectors.toSet());
        Set<Class<?>> actual = EXPECTED_TYPEINFO.keySet()
                .stream()
                .map(Object::getClass)
                .collect(Collectors.toSet());
        
        assertEqualSet(expected, actual);
    }
    
    @ParameterizedTest
    @MethodSource("types")
    public void testResourceInfo(StructureType<?, ?, ?> type)
    {
        String expected = EXPECTED_TYPEINFO.get(type);
        if(expected == null)
            return;
        
        String actual = StringPrinter.print(out -> InfoResources.resourceInfo(out, type));
        assertEquals(expected, actual);
    }
    
    public static void main(String[] args)
    {
        System.out.println(StructureType.types());
    }
    
    private static Stream<Arguments> types()
    {
        return EXPECTED_TYPEINFO.keySet()
                .stream()
                .map(Arguments::of);
    }
}
