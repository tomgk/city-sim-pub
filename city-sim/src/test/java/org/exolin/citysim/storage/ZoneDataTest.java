package org.exolin.citysim.storage;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import org.exolin.citysim.bt.Zones;
import org.exolin.citysim.model.SimulationSpeed;
import org.exolin.citysim.model.Structure;
import org.exolin.citysim.model.World;
import org.exolin.citysim.model.zone.Zone;
import org.exolin.citysim.model.zone.ZoneType;
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
public class ZoneDataTest
{
    @Test
    public void testSerializeZone() throws IOException
    {
        Zone zone = new Zone(Zones.residential, 16, 99, ZoneType.Variant.DEFAULT);
        String output = serialize(WorldStorage::serialize, zone);
        String expected = """
                          {"type":"zone_residential","x":16,"y":99}
                          """;
        JSONAssert.assertEquals(expected, output, true);
    }
    
    @Test
    public void testDeserializeZone() throws IOException
    {
        World w = new World("Test", 30, BigDecimal.ZERO, SimulationSpeed.SPEED1);
        InputStream in = createInputStream("""
                                           {"type":"zone_residential","x":16,"y":5}
                                           """);
        WorldStorage.deserialize(in, w);
        Structure b = getBuilding(w);
        assertEquals(Zones.residential, b.getType());
        assertEquals(16, b.getX());
        assertEquals(5, b.getY());
        assertEquals(ZoneType.Variant.DEFAULT, b.getVariant());
    }
}
