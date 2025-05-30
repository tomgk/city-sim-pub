package org.exolin.citysim.storage;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import org.exolin.citysim.bt.connections.SelfConnections;
import org.exolin.citysim.model.SimulationSpeed;
import org.exolin.citysim.model.Structure;
import org.exolin.citysim.model.World;
import org.exolin.citysim.model.connection.regular.SelfConnection;
import static org.exolin.citysim.model.connection.regular.TIntersection.T_INTERSECTION_4;
import static org.exolin.citysim.model.connection.regular.Unconnected.UNCONNECTED;
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
public class SelfConnectionTest
{
    @Test
    public void testSerializeStreet() throws IOException
    {
        SelfConnection street = new SelfConnection(SelfConnections.street, 16, 99, T_INTERSECTION_4);
        String output = serialize(WorldStorage::serialize, street);
        String expected = """
                          {"type":"street","x":16,"y":99,"variant":"t_intersection_4"}
                          """;
        JSONAssert.assertEquals(expected, output, true);
    }
    
    @Test
    public void testDeserializeStreet() throws IOException
    {
        World w = new World("Test", 30, BigDecimal.ZERO, SimulationSpeed.PAUSED);
        InputStream in = createInputStream("""
                                           {"type":"street","x":16,"y":5,"variant":"t_intersection_4"}
                                           """);
        WorldStorage.deserialize(in, w);
        Structure<?, ?, ?, ?> b = getBuilding(w);
        assertEquals(SelfConnections.street, b.getType());
        assertEquals(16, b.getX());
        assertEquals(5, b.getY());
        assertEquals(UNCONNECTED, b.getVariant());
    }
}
