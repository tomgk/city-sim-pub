package org.exolin.citysim;

import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;
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
public class InfoTypeTest
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
                    PowerPlants.hydro_plant,
                    """
                    ==== Hydro Power =====
                    Type: BuildingType
                    Cost: 400
                    Zone: zone_plants
                    megaWatt: 20 MW
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
                    CrossConnections.WATER_CIRCUIT,
                    """
                    ==== water_circuit =====
                    Type: CrossConnectionType
                    Cost: -
                    X-Type: water
                    Y-Type: circuit
                    """
            ),
            Map.entry(
                    SelfConnections.street,
                    """
                    ==== street =====
                    Type: SelfConnectionType
                    Cost: 10
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
                    Zones.low_business,
                    """
                    ==== zone_business_low =====
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
    public void testTypeInfo(StructureType<?, ?, ?> type)
    {
        String expected = EXPECTED_TYPEINFO.get(type);
        if(expected == null)
            return;
        
        String actual = StringPrinter.print(out -> InfoType.typeInfo(out, type));
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
