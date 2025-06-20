package org.exolin.citysim.bt.buildings;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;
import org.exolin.citysim.bt.Trees;
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
import org.exolin.citysim.model.tree.Tree;
import org.exolin.citysim.model.tree.TreeParameters;
import org.exolin.citysim.model.tree.TreeVariant;
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
        assertEquals(2000, Plants.gas_plant.getBuildingCost(BuildingType.Variant.DEFAULT));
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
        assertEquals(Electricity.NEEDS, Plants.getElectricity(
                new Building(ResidentialBuildings.house_1, 0, 0, BuildingType.Variant.DEFAULT),
                ConnectionType.Direction.X));
    }
    
    @Test
    public void testGetElectricity_Building_Y()
    {
        assertEquals(Electricity.NEEDS, Plants.getElectricity(
                new Building(ResidentialBuildings.house_1, 0, 0, BuildingType.Variant.DEFAULT),
                ConnectionType.Direction.Y));
    }
    
    @Test
    public void testGetElectricity_Plant_X()
    {
        assertEquals(Electricity.NEEDS, Plants.getElectricity(
                new Building(Plants.gas_plant, 0, 0, BuildingType.Variant.DEFAULT),
                ConnectionType.Direction.X));
    }
    
    @Test
    public void testGetElectricity_Plant_Y()
    {
        assertEquals(Electricity.NEEDS, Plants.getElectricity(
                new Building(Plants.gas_plant, 0, 0, BuildingType.Variant.DEFAULT),
                ConnectionType.Direction.Y));
    }
    
    @Test
    public void testGetElectricity_Street_X()
    {
        assertEquals(Electricity.CONDUCTS, Plants.getElectricity(new SelfConnection(street, 0, 0, Curve.CURVE_1), ConnectionType.Direction.X));
    }
    
    @Test
    public void testGetElectricity_Street_Y()
    {
        assertEquals(Electricity.CONDUCTS, Plants.getElectricity(new SelfConnection(street, 0, 0, Curve.CURVE_1), ConnectionType.Direction.Y));
    }
    
    @Test
    public void testGetElectricity_Trees_Unzoned_X()
    {
        assertEquals(Electricity.INSULATOR, Plants.getElectricity(
                new Tree(Trees.XTREES.get(2), 0, 0, TreeVariant.BOTTOM_LEFT, new TreeParameters(Optional.empty())),
                ConnectionType.Direction.X));
    }
    
    @Test
    public void testGetElectricity_Trees_Unzoned_Y()
    {
        assertEquals(Electricity.INSULATOR, Plants.getElectricity(
                new Tree(Trees.XTREES.get(2), 0, 0, TreeVariant.BOTTOM_LEFT, new TreeParameters(Optional.empty())),
                ConnectionType.Direction.Y));
    }
    
    @Test
    public void testGetElectricity_Trees_Zoned_X()
    {
        assertEquals(Electricity.NEEDS, Plants.getElectricity(
                new Tree(Trees.XTREES.get(2), 0, 0, TreeVariant.BOTTOM_LEFT, new TreeParameters(Optional.of(Zones.business))),
                ConnectionType.Direction.X));
    }
    
    @Test
    public void testGetElectricity_Trees_Zoned_Y()
    {
        assertEquals(Electricity.NEEDS, Plants.getElectricity(
                new Tree(Trees.XTREES.get(2), 0, 0, TreeVariant.BOTTOM_LEFT, new TreeParameters(Optional.of(Zones.business))),
                ConnectionType.Direction.Y));
    }
    
    @Test
    public void testGetElectricity_Circuit_X()
    {
        assertEquals(Electricity.TRANSFER, Plants.getElectricity(new SelfConnection(circuit, 0, 0, Curve.CURVE_1), ConnectionType.Direction.X));
    }
    
    @Test
    public void testGetElectricity_Circuit_Y()
    {
        assertEquals(Electricity.TRANSFER, Plants.getElectricity(new SelfConnection(circuit, 0, 0, Curve.CURVE_1), ConnectionType.Direction.Y));
    }
    
    @Test
    public void testGetAnyElectricity_Self()
    {
        for(SelfConnectionType t : SelfConnections.values())
            assertEquals(Plants.getElectricity(t), Plants.getAnyElectricity(new SelfConnection(t, 0, 0, Curve.CURVE_1)));
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

            CrossConnection z = new CrossConnection((CrossConnectionType)CrossConnections.get(x, y), 0, 0, CrossConnectionType.Variant.DEFAULT);
            assertEquals(e, Plants.getAnyElectricity(z), take);
    }
    
    @Test
    public void testGetElectricity_Cross_Street_Crircuit_X()
    {
        assertEquals(Electricity.CONDUCTS, Plants.getElectricity(
                new CrossConnection(CrossConnections.STREET_CIRCUIT, 0, 0, CrossConnectionType.Variant.DEFAULT),
                ConnectionType.Direction.X));
    }
    
    @Test
    public void testGetElectricity_Cross_Street_Crircuit_Y()
    {
        assertEquals(Electricity.TRANSFER, Plants.getElectricity(
                new CrossConnection(CrossConnections.STREET_CIRCUIT, 0, 0, CrossConnectionType.Variant.DEFAULT),
                ConnectionType.Direction.Y));
    }
    
    @Test
    public void testGetElectricity_Cross_Crircuit_Street_X()
    {
        assertEquals(Electricity.TRANSFER, Plants.getElectricity(
                new CrossConnection(CrossConnections.CIRCUIT_STREET, 0, 0, CrossConnectionType.Variant.DEFAULT),
                ConnectionType.Direction.X));
    }
    
    @Test
    public void testGetElectricity_Cross_Crircuit_Street_Y()
    {
        assertEquals(Electricity.CONDUCTS, Plants.getElectricity(
                new CrossConnection(CrossConnections.CIRCUIT_STREET, 0, 0, CrossConnectionType.Variant.DEFAULT),
                ConnectionType.Direction.Y));
    }
    
    @Test
    public void testGetElectricity_Cross_Rail_Crircuit_X()
    {
        assertEquals(Electricity.INSULATOR, Plants.getElectricity(
                new CrossConnection(CrossConnections.RAIL_CIRCUIT, 0, 0, CrossConnectionType.Variant.DEFAULT),
                ConnectionType.Direction.X));
    }
    
    @Test
    public void testGetElectricity_Cross_Rail_Crircuit_Y()
    {
        assertEquals(Electricity.TRANSFER, Plants.getElectricity(
                new CrossConnection(CrossConnections.RAIL_CIRCUIT, 0, 0, CrossConnectionType.Variant.DEFAULT),
                ConnectionType.Direction.Y));
    }
    
    @Test
    public void testGetElectricity_Cross_Crircuit_Rail_X()
    {
        assertEquals(Electricity.TRANSFER, Plants.getElectricity(
                new CrossConnection(CrossConnections.CIRCUIT_RAIL, 0, 0, CrossConnectionType.Variant.DEFAULT),
                ConnectionType.Direction.X));
    }
    
    @Test
    public void testGetElectricity_Cross_Crircuit_Rail_Y()
    {
        assertEquals(Electricity.INSULATOR, Plants.getElectricity(
                new CrossConnection(CrossConnections.CIRCUIT_RAIL, 0, 0, CrossConnectionType.Variant.DEFAULT),
                ConnectionType.Direction.Y));
    }
}
