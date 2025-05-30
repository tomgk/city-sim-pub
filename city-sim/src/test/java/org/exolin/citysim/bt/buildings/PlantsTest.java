package org.exolin.citysim.bt.buildings;

import java.util.Optional;
import org.exolin.citysim.bt.Trees;
import org.exolin.citysim.bt.Zones;
import static org.exolin.citysim.bt.connections.SelfConnections.circuit;
import static org.exolin.citysim.bt.connections.SelfConnections.street;
import org.exolin.citysim.model.building.Building;
import org.exolin.citysim.model.building.BuildingType;
import org.exolin.citysim.model.connection.ConnectionType;
import org.exolin.citysim.model.connection.regular.Curve;
import org.exolin.citysim.model.connection.regular.SelfConnection;
import org.exolin.citysim.model.tree.Tree;
import org.exolin.citysim.model.tree.TreeParameters;
import org.exolin.citysim.model.tree.TreeVariant;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

/**
 *
 * @author Thomas
 */
public class PlantsTest
{
    @Test
    public void testGetElectricity_Building()
    {
        assertEquals(Electricity.NEEDS, Plants.getElectricity(new Building(ResidentialBuildings.house_1, 0, 0, BuildingType.Variant.DEFAULT), ConnectionType.Direction.X));
        assertEquals(Electricity.NEEDS, Plants.getElectricity(new Building(ResidentialBuildings.house_1, 0, 0, BuildingType.Variant.DEFAULT), ConnectionType.Direction.Y));
    }
    
    @Test
    public void testGetElectricity_Plant()
    {
        assertEquals(Electricity.NEEDS, Plants.getElectricity(new Building(Plants.gas_plant, 0, 0, BuildingType.Variant.DEFAULT), ConnectionType.Direction.X));
        assertEquals(Electricity.NEEDS, Plants.getElectricity(new Building(Plants.gas_plant, 0, 0, BuildingType.Variant.DEFAULT), ConnectionType.Direction.Y));
    }
    
    @Test
    public void testGetElectricity_Stret()
    {
        assertEquals(Electricity.CONDUCTS, Plants.getElectricity(new SelfConnection(street, 0, 0, Curve.CURVE_1), ConnectionType.Direction.X));
        assertEquals(Electricity.CONDUCTS, Plants.getElectricity(new SelfConnection(street, 0, 0, Curve.CURVE_1), ConnectionType.Direction.Y));
    }
    
    @Test
    public void testGetElectricity_Trees_Unzoned()
    {
        assertEquals(Electricity.INSULATOR, Plants.getElectricity(new Tree(Trees.XTREES.get(2), 0, 0, TreeVariant.BOTTOM_LEFT, new TreeParameters(Optional.empty())), ConnectionType.Direction.X));
        assertEquals(Electricity.INSULATOR, Plants.getElectricity(new Tree(Trees.XTREES.get(2), 0, 0, TreeVariant.BOTTOM_LEFT, new TreeParameters(Optional.empty())), ConnectionType.Direction.Y));
    }
    
    @Test
    public void testGetElectricity_Trees_Zoned()
    {
        assertEquals(Electricity.NEEDS, Plants.getElectricity(new Tree(Trees.XTREES.get(2), 0, 0, TreeVariant.BOTTOM_LEFT, new TreeParameters(Optional.of(Zones.business))), ConnectionType.Direction.X));
        assertEquals(Electricity.NEEDS, Plants.getElectricity(new Tree(Trees.XTREES.get(2), 0, 0, TreeVariant.BOTTOM_LEFT, new TreeParameters(Optional.of(Zones.business))), ConnectionType.Direction.Y));
    }
    
    @Test
    public void testGetElectricity_Circuit()
    {
        assertEquals(Electricity.TRANSFER, Plants.getElectricity(new SelfConnection(circuit, 0, 0, Curve.CURVE_1), ConnectionType.Direction.X));
        assertEquals(Electricity.TRANSFER, Plants.getElectricity(new SelfConnection(circuit, 0, 0, Curve.CURVE_1), ConnectionType.Direction.Y));
    }
}
