package org.exolin.citysim.storage;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import org.exolin.citysim.bt.Trees;
import org.exolin.citysim.model.Structure;
import org.exolin.citysim.model.World;
import org.exolin.citysim.model.tree.Tree;
import org.exolin.citysim.model.tree.TreeType;
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
        Tree street = new Tree(Trees.TREES.get(3), 16, 99, TreeType.Variant.TOP_RIGHT);
        String output = serialize(WorldStorage::serialize, street);
        String expected = """
                          {"type":"trees_4","x":16,"y":99,"variant":"top_right"}
                          """;
        JSONAssert.assertEquals(expected, output, false);
    }
    
    @Test
    public void testDeserializeTree() throws IOException
    {
        World w = new World("Test", 30, BigDecimal.ZERO);
        InputStream in = createInputStream(
                          """
                          {"type":"trees_4","x":16,"y":22,"variant":"top_right"}
                          """);
        WorldStorage.deserialize(in, w);
        Structure b = getBuilding(w);
        assertEquals(Trees.TREES.get(3), b.getType());
        assertEquals(16, b.getX());
        assertEquals(22, b.getY());
        assertEquals(TreeType.Variant.TOP_RIGHT, b.getVariant());
    }
}
