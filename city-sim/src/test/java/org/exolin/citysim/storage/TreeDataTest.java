package org.exolin.citysim.storage;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.Optional;
import org.exolin.citysim.bt.Trees;
import org.exolin.citysim.bt.Zones;
import org.exolin.citysim.model.SimulationSpeed;
import org.exolin.citysim.model.Structure;
import org.exolin.citysim.model.World;
import org.exolin.citysim.model.tree.Tree;
import org.exolin.citysim.model.tree.TreeParameters;
import org.exolin.citysim.model.tree.TreeVariant;
import static org.exolin.citysim.storage.WorldStorageTest.createInputStream;
import static org.exolin.citysim.storage.WorldStorageTest.getBuilding;
import static org.exolin.citysim.storage.WorldStorageTest.serialize;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;

/**
 *
 * @author Thomas
 */
public class TreeDataTest
{
    @Test
    public void testSerializeTree() throws IOException
    {
        Tree street = new Tree(Trees.TREES.get(3), 16, 99, TreeVariant.TOP_RIGHT, new TreeParameters(Optional.empty()));
        String output = serialize(WorldStorage::serialize, street);
        String expected = """
                          {"type":"trees_4","x":16,"y":99,"variant":"top_right"}
                          """;
        System.out.println(output);
        JSONAssert.assertEquals(expected, output, true);
    }
    
    @Test
    public void testSerializeTree_WithZone() throws IOException
    {
        Tree street = new Tree(Trees.TREES.get(3), 16, 99, TreeVariant.TOP_RIGHT, new TreeParameters(Optional.of(Zones.residential)));
        String output = serialize(WorldStorage::serialize, street);
        String expected = """
                          {"type":"trees_4","x":16,"y":99,"variant":"top_right","zone":"zone_residential"}
                          """;
        JSONAssert.assertEquals(expected, output, true);
    }
    
    @Test
    public void testDeserializeTree() throws IOException
    {
        World w = new World("Test", 30, BigDecimal.ZERO, SimulationSpeed.PAUSED);
        InputStream in = createInputStream(
                          """
                          {"type":"trees_4","x":16,"y":22,"variant":"top_right"}
                          """);
        WorldStorage.deserialize(in, w);
        Structure b = getBuilding(w);
        assertEquals(Trees.TREES.get(3), b.getType());
        assertEquals(16, b.getX());
        assertEquals(22, b.getY());
        assertEquals(TreeVariant.TOP_RIGHT, b.getVariant());
        TreeParameters data = (TreeParameters) b.getData();
        assertEquals(Optional.empty(), data.getZone());
    }
    
    @Test
    public void testDeserializeTree_WithZone() throws IOException
    {
        World w = new World("Test", 30, BigDecimal.ZERO, SimulationSpeed.PAUSED);
        InputStream in = createInputStream(
                          """
                          {"type":"trees_4","x":16,"y":22,"variant":"top_right","zone":"zone_residential"}
                          """);
        WorldStorage.deserialize(in, w);
        Structure b = getBuilding(w);
        assertEquals(Trees.TREES.get(3), b.getType());
        assertEquals(16, b.getX());
        assertEquals(22, b.getY());
        assertEquals(TreeVariant.TOP_RIGHT, b.getVariant());
        TreeParameters data = (TreeParameters) b.getData();
        assertEquals(Optional.of(Zones.residential), data.getZone());
    }
}
