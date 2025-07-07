package org.exolin.citysim.bt.buildings;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;
import org.exolin.citysim.bt.Plants;
import org.exolin.citysim.bt.Zones;
import org.exolin.citysim.bt.connections.CrossConnections;
import org.exolin.citysim.bt.connections.SelfConnections;
import static org.exolin.citysim.bt.connections.SelfConnections.circuit;
import static org.exolin.citysim.bt.connections.SelfConnections.rail;
import static org.exolin.citysim.bt.connections.SelfConnections.street;
import org.exolin.citysim.model.building.Building;
import org.exolin.citysim.model.building.BuildingType;
import org.exolin.citysim.model.connection.ConnectionType;
import org.exolin.citysim.model.connection.cross.CrossConnection;
import org.exolin.citysim.model.connection.cross.CrossConnectionType;
import org.exolin.citysim.model.connection.regular.Curve;
import org.exolin.citysim.model.connection.regular.SelfConnection;
import org.exolin.citysim.model.connection.regular.SelfConnectionType;
import org.exolin.citysim.model.plant.Plant;
import org.exolin.citysim.model.plant.PlantParameters;
import org.exolin.citysim.model.plant.PlantTypeType;
import org.exolin.citysim.model.plant.PlantVariant;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

/**
 *
 * @author Thomas
 */
public class PlantsTest
{
    @Test
    public void getGasPlantBuildingCost()
    {
        assertEquals(2000, PowerPlants.gas_plant.getBuildingCost(BuildingType.Variant.DEFAULT));
    }
    
    //test test
    @Test
    public void test_Direction_Tests()
    {
        assertEquals(2, ConnectionType.Direction.values().length);
    }
    
    @Test
    public void testGetElectricity_Building_X()
    {
        assertEquals(Electricity.NEEDS, PowerPlants.getElectricity(
                new Building(ResidentialBuildings.house_1, 0, 0, BuildingType.Variant.DEFAULT),
                ConnectionType.Direction.X));
    }
    
    @Test
    public void testGetElectricity_Building_Y()
    {
        assertEquals(Electricity.NEEDS, PowerPlants.getElectricity(
                new Building(ResidentialBuildings.house_1, 0, 0, BuildingType.Variant.DEFAULT),
                ConnectionType.Direction.Y));
    }
    
    @Test
    public void testGetElectricity_Plant_X()
    {
        assertEquals(Electricity.NEEDS, PowerPlants.getElectricity(new Building(PowerPlants.gas_plant, 0, 0, BuildingType.Variant.DEFAULT),
                ConnectionType.Direction.X));
    }
    
    @Test
    public void testGetElectricity_Plant_Y()
    {
        assertEquals(Electricity.NEEDS, PowerPlants.getElectricity(new Building(PowerPlants.gas_plant, 0, 0, BuildingType.Variant.DEFAULT),
                ConnectionType.Direction.Y));
    }
    
    @Test
    public void testGetElectricity_Street_X()
    {
        assertEquals(Electricity.CONDUCTS, PowerPlants.getElectricity(new SelfConnection(street, 0, 0, Curve.CURVE_1), ConnectionType.Direction.X));
    }
    
    @Test
    public void testGetElectricity_Street_Y()
    {
        assertEquals(Electricity.CONDUCTS, PowerPlants.getElectricity(new SelfConnection(street, 0, 0, Curve.CURVE_1), ConnectionType.Direction.Y));
    }
    
    @Test
    public void testGetElectricity_Trees_Unzoned_X()
    {
        assertEquals(Electricity.INSULATOR, PowerPlants.getElectricity(new Plant(Plants.get(PlantTypeType.TREE, 3), 0, 0, PlantVariant.BOTTOM_LEFT, new PlantParameters(Optional.empty())),
                ConnectionType.Direction.X));
    }
    
    @Test
    public void testGetElectricity_Trees_Unzoned_Y()
    {
        assertEquals(Electricity.INSULATOR, PowerPlants.getElectricity(new Plant(Plants.get(PlantTypeType.TREE, 3), 0, 0, PlantVariant.BOTTOM_LEFT, new PlantParameters(Optional.empty())),
                ConnectionType.Direction.Y));
    }
    
    @Test
    public void testGetElectricity_Trees_Zoned_X()
    {
        assertEquals(Electricity.NEEDS, PowerPlants.getElectricity(new Plant(Plants.get(PlantTypeType.TREE, 3), 0, 0, PlantVariant.BOTTOM_LEFT, new PlantParameters(Optional.of(Zones.business))),
                ConnectionType.Direction.X));
    }
    
    @Test
    public void testGetElectricity_Trees_Zoned_Y()
    {
        assertEquals(Electricity.NEEDS, PowerPlants.getElectricity(new Plant(Plants.get(PlantTypeType.TREE, 3), 0, 0, PlantVariant.BOTTOM_LEFT, new PlantParameters(Optional.of(Zones.business))),
                ConnectionType.Direction.Y));
    }
    
    @Test
    public void testGetElectricity_Circuit_X()
    {
        assertEquals(Electricity.TRANSFER, PowerPlants.getElectricity(new SelfConnection(circuit, 0, 0, Curve.CURVE_1), ConnectionType.Direction.X));
    }
    
    @Test
    public void testGetElectricity_Circuit_Y()
    {
        assertEquals(Electricity.TRANSFER, PowerPlants.getElectricity(new SelfConnection(circuit, 0, 0, Curve.CURVE_1), ConnectionType.Direction.Y));
    }
    
    @Test
    public void testGetAnyElectricity_Self()
    {
        for(SelfConnectionType t : SelfConnections.values())
            assertEquals(PowerPlants.getElectricity(t), PowerPlants.getAnyElectricity(new SelfConnection(t, 0, 0, Curve.CURVE_1)));
    }
    
    private record Pair(SelfConnectionType x, SelfConnectionType y)
    {
        
    }
    
    private static final Map<Pair, Electricity> crossElectricity = new LinkedHashMap<>();
    static{
        crossElectricity.put(new Pair(rail, street), Electricity.CONDUCTS);
        crossElectricity.put(new Pair(circuit, street), Electricity.TRANSFER);
        crossElectricity.put(new Pair(street, rail), Electricity.CONDUCTS);
        crossElectricity.put(new Pair(circuit, rail), Electricity.TRANSFER);
        crossElectricity.put(new Pair(street, circuit), Electricity.TRANSFER);
        crossElectricity.put(new Pair(rail, circuit), Electricity.TRANSFER);
    }
    
    static Stream<Arguments> provideStringsForTesting()
    {
        return crossElectricity.entrySet()
                .stream()
                .map(e -> Arguments.of(e.getKey().x, e.getKey().y, e.getValue()));
    }
    
    @ParameterizedTest
    @MethodSource("provideStringsForTesting")
    public void testGetAnyElectricity_Cross(SelfConnectionType x, SelfConnectionType y, Electricity e)
    {
            //skip selfs
            if(x == y)
                return;

            String take = x.getName()+"/"+y.getName();

            //Electricity e = crossElectricity.get(new Pair(x, y));
            assertNotNull(e, take);

            CrossConnection z = new CrossConnection((CrossConnectionType)CrossConnections.get(x, y), 0, 0);
            assertEquals(e, PowerPlants.getAnyElectricity(z), take);
    }
    
    @Test
    public void testGetElectricity_Cross_Street_Crircuit_X()
    {
        assertEquals(Electricity.CONDUCTS, PowerPlants.getElectricity(
                new CrossConnection(CrossConnections.STREET_CIRCUIT, 0, 0),
                ConnectionType.Direction.X));
    }
    
    @Test
    public void testGetElectricity_Cross_Street_Crircuit_Y()
    {
        assertEquals(Electricity.TRANSFER, PowerPlants.getElectricity(
                new CrossConnection(CrossConnections.STREET_CIRCUIT, 0, 0),
                ConnectionType.Direction.Y));
    }
    
    @Test
    public void testGetElectricity_Cross_Crircuit_Street_X()
    {
        assertEquals(Electricity.TRANSFER, PowerPlants.getElectricity(
                new CrossConnection(CrossConnections.CIRCUIT_STREET, 0, 0),
                ConnectionType.Direction.X));
    }
    
    @Test
    public void testGetElectricity_Cross_Crircuit_Street_Y()
    {
        assertEquals(Electricity.CONDUCTS, PowerPlants.getElectricity(
                new CrossConnection(CrossConnections.CIRCUIT_STREET, 0, 0),
                ConnectionType.Direction.Y));
    }
    
    @Test
    public void testGetElectricity_Cross_Rail_Crircuit_X()
    {
        assertEquals(Electricity.INSULATOR, PowerPlants.getElectricity(
                new CrossConnection(CrossConnections.RAIL_CIRCUIT, 0, 0),
                ConnectionType.Direction.X));
    }
    
    @Test
    public void testGetElectricity_Cross_Rail_Crircuit_Y()
    {
        assertEquals(Electricity.TRANSFER, PowerPlants.getElectricity(
                new CrossConnection(CrossConnections.RAIL_CIRCUIT, 0, 0),
                ConnectionType.Direction.Y));
    }
    
    @Test
    public void testGetElectricity_Cross_Crircuit_Rail_X()
    {
        assertEquals(Electricity.TRANSFER, PowerPlants.getElectricity(
                new CrossConnection(CrossConnections.CIRCUIT_RAIL, 0, 0),
                ConnectionType.Direction.X));
    }
    
    @Test
    public void testGetElectricity_Cross_Crircuit_Rail_Y()
    {
        assertEquals(Electricity.INSULATOR, PowerPlants.getElectricity(
                new CrossConnection(CrossConnections.CIRCUIT_RAIL, 0, 0),
                ConnectionType.Direction.Y));
    }
}
