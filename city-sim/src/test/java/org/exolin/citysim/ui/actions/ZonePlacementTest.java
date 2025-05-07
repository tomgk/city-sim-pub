package org.exolin.citysim.ui.actions;

import java.awt.Point;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import org.exolin.citysim.bt.Trees;
import org.exolin.citysim.bt.Zones;
import org.exolin.citysim.model.SimulationSpeed;
import org.exolin.citysim.model.Structure;
import org.exolin.citysim.model.World;
import org.exolin.citysim.model.tree.Tree;
import org.exolin.citysim.model.tree.TreeParameters;
import org.exolin.citysim.model.tree.TreeType;
import org.exolin.citysim.model.tree.TreeVariant;
import static org.exolin.citysim.ui.actions.ActionTestUtils.assertTree;
import static org.exolin.citysim.ui.actions.ActionTestUtils.assertZone;
import static org.exolin.citysim.ui.actions.ActionTestUtils.makeZonePlacementMove;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

/**
 *
 * @author Thomas
 */
public class ZonePlacementTest
{
    @Test
    public void testZonePlacementOnEmpty()
    {
        World world = new World("Test", 100, BigDecimal.ZERO, SimulationSpeed.SPEED1);
        
        makeZonePlacementMove(new Point(1, 6), new Point(3, 9), world, Zones.business);
        
        List<Structure> buildings = world.getStructures();
        assertEquals(6, world.getStructures().size(), buildings.toString());
        
        //for(Structure b: buildings)
        //    System.out.println(b);
        
        assertZone(world.getBuildingAt(1, 6), 1, 6, Zones.business);
        assertZone(world.getBuildingAt(1, 7), 1, 7, Zones.business);
        assertZone(world.getBuildingAt(1, 8), 1, 8, Zones.business);
        assertZone(world.getBuildingAt(2, 6), 2, 6, Zones.business);
        assertZone(world.getBuildingAt(2, 7), 2, 7, Zones.business);
        assertZone(world.getBuildingAt(2, 8), 2, 8, Zones.business);
    }
    
    @Test
    @Disabled
    public void testZoneDisplacement()
    {
        World world = new World("Test", 100, BigDecimal.ZERO, SimulationSpeed.SPEED1);
        
        makeZonePlacementMove(new Point(1, 6), new Point(3, 9), world, Zones.residential);
        makeZonePlacementMove(new Point(2, 7), new Point(4, 10), world, Zones.business);
        
        List<Structure> buildings = world.getStructures();
        assertEquals(10, world.getStructures().size(), buildings.toString());
        
        for(Structure b: buildings)
            System.out.println(b);
        
        assertZone(world.getBuildingAt(1, 6), 1, 6, Zones.business);
        assertZone(world.getBuildingAt(1, 7), 1, 7, Zones.business);
        assertZone(world.getBuildingAt(1, 8), 1, 8, Zones.business);
        assertZone(world.getBuildingAt(2, 6), 2, 6, Zones.business);
        assertZone(world.getBuildingAt(2, 7), 2, 7, Zones.business);
        assertZone(world.getBuildingAt(2, 8), 2, 8, Zones.business);
    }
    
    @Test
    public void testZonePlacementOnTrees_Single()
    {
        ZonePlacement.DEBUG_TREEZONE = true;
        System.out.println("START testZonePlacementOnTrees_Single");
        try{
            World world = new World("Test", 100, BigDecimal.ZERO, SimulationSpeed.SPEED1);

            Tree t = world.addBuilding(Trees.XTREES.get(1), 1, 8, TreeVariant.DEFAULT, new TreeParameters(Optional.empty()));
            System.out.println("Created Tree in test="+System.identityHashCode(t)+" with "+t.getZoneType());

            makeZonePlacementMove(new Point(1, 8), new Point(2, 9), world, Zones.business);
            
            System.out.println("Test Tree after zone placement="+System.identityHashCode(t)+" with "+t.getZoneType());

            List<Structure> buildings = world.getStructures();
            assertEquals(1, world.getStructures().size(), buildings.toString());

            Structure tt = world.getBuildingAt(1, 8);
            System.out.println("Tree "+System.identityHashCode(tt)+"="+tt.getZoneType());

            assertTree(tt, 1, 8, Trees.XTREES.get(1), Optional.of(Zones.business));

            buildings.stream().forEach(System.out::println);
        }finally{
            ZonePlacement.DEBUG_TREEZONE = false;
            System.out.println("END testZonePlacementOnTrees_Single");
        }
    }
    
    @Test
    public void testZonePlacementOnTrees()
    {
        ZonePlacement.DEBUG_TREEZONE = true;
        System.out.println("START testZonePlacementOnTrees");
        try{
            World world = new World("Test", 100, BigDecimal.ZERO, SimulationSpeed.SPEED1);

            Tree t = world.addBuilding(Trees.XTREES.get(1), 1, 8, TreeVariant.DEFAULT, new TreeParameters(Optional.empty()));
            System.out.println("Tree="+System.identityHashCode(t));

            makeZonePlacementMove(new Point(1, 6), new Point(3, 9), world, Zones.business);

            List<Structure> buildings = world.getStructures();
            assertEquals(6, world.getStructures().size(), buildings.toString());

            Structure tt = world.getBuildingAt(1, 8);
            System.out.println("Tree "+System.identityHashCode(tt)+"="+tt.getZoneType());

            assertZone(world.getBuildingAt(1, 6), 1, 6, Zones.business);
            assertZone(world.getBuildingAt(1, 7), 1, 7, Zones.business);
            assertTree(tt, 1, 8, Trees.XTREES.get(1), Optional.of(Zones.business));
            assertZone(world.getBuildingAt(2, 6), 2, 6, Zones.business);
            assertZone(world.getBuildingAt(2, 7), 2, 7, Zones.business);
            assertZone(world.getBuildingAt(2, 8), 2, 8, Zones.business);

            buildings.stream().forEach(System.out::println);
        }finally{
            ZonePlacement.DEBUG_TREEZONE = false;
            System.out.println("END testZonePlacementOnTrees");
        }
    }
}
